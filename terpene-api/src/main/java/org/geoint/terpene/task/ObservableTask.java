package org.geoint.terpene.task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import static org.geoint.terpene.task.TaskState.CANCELLED;
import static org.geoint.terpene.task.TaskState.COMPLETED;
import static org.geoint.terpene.task.TaskState.EXCEPTION;
import org.geoint.terpene.task.event.TaskCancelledEvent;
import org.geoint.terpene.task.event.TaskCompletedEvent;
import org.geoint.terpene.task.event.TaskEvent;
import org.geoint.terpene.task.event.TaskExceptionEvent;
import org.geoint.terpene.task.event.TaskStartedEvent;

/**
 * A task that will provide lifecycle events to listeners.
 */
public abstract class ObservableTask<R> extends Task<R> {

    private final static long serialVersionUID = 1L;
    private transient List<TaskListener> listeners = new ArrayList<>();
    private ReadWriteLock listenerLock = new ReentrantReadWriteLock();
    protected Throwable exception;
    protected R result;
    private Thread processingThread;

    public ObservableTask() {
    }

    protected ObservableTask(String key) {
        super(key);
    }

    public ObservableTask<R> addListener(TaskListener l) {

        if (!state.isDone()) {
            Lock lock = listenerLock.writeLock();
            lock.lock();
            try {
                listeners.add(l);
            } finally {
                lock.unlock();
            }
        } else {
            throw new IllegalStateException("Listeners cannot be added to a "
                    + "completed task.");
        }
        return this;
    }

    public void removeListener(TaskListener l) {
        Lock lock = listenerLock.writeLock();
        lock.lock();
        try {
            listeners.remove(l);
        } finally {
            lock.unlock();
        }
    }

    public Throwable getException() {
        return exception;
    }

    @Override
    public synchronized void run() {
        if (getState().isDone()) {
            throw new IllegalStateException("Task " + getKey()
                    + " has already completed execution, unable to restart task.");
        } else if (state.equals(TaskState.RUNNING)) {
            throw new IllegalStateException("Task " + getKey()
                    + " is already running.");
        }


        try {

            processingThread = Thread.currentThread();
            notifyListeners(new TaskStartedEvent(this));
            preExecute();

            if (!state.isDone()) { //make sure preExecute didn't abort
                result = invoke();
                
                if (!state.equals(TaskState.CANCELLED)) { //no need to lock state
                    complete();
                }
                
            } else {
                throw new RejectedExecutionException("Task is already complete.");
            }
        } catch (Throwable ex) {
            exception(ex);
        } finally {
            try {
                //if preExecute is called, postExecute will be called
                postExecute();
            } catch (Throwable ex) {
                exception(ex);
            }
            processingThread = null;
        }
    }

    @Override
    public boolean isCancelled() {
        return getState().equals(TaskState.CANCELLED);
    }

    @Override
    public boolean isDone() {
        return getState().isDone();
    }

    public boolean isException() {
        return getState().equals(TaskState.EXCEPTION);
    }

    /**
     * Synchronously waits on this task to be complete.
     *
     * If asynchronous notifications can be used, developers should consider
     * utilizing a {@link TaskListener} to be notified of completion.
     *
     * @return
     * @throws InterruptedException if the thread is interrupted while waiting
     * @throws ExecutionException if there was an exception thrown during the
     * execution
     * @throws CancellationException if the task was cancelled
     */
    @Override
    public R get() throws InterruptedException, ExecutionException, CancellationException {
        try {
            return this.get(Long.MAX_VALUE, TimeUnit.DAYS);
        } catch (TimeoutException ex) {
            //we should never get here...ever
            throw new ExecutionException(ex);
        }
    }

    @Override
    public R get(long timeout, TimeUnit unit) throws InterruptedException,
            CancellationException, ExecutionException, TimeoutException {
        Lock lock = stateLock.readLock();
        lock.lock();
        try {
            if (getState().isDone()) {
                return returnResult();
            }
        } finally {
            lock.unlock();
        }

        //wait for the task to complete, up to the timeout requirements
        WaitObservableTaskListener l = new WaitObservableTaskListener();
        this.addListener(l);
        synchronized (l) {
            l.wait(unit.toMillis(timeout));
        }
        try {
            return returnResult();
        } catch (IllegalStateException ex) {
            throw new TimeoutException("Task has not completed in specified "
                    + "time period.");
        }
    }

    /**
     * Called internally to return the result
     *
     * This method does not check wait to return the results, and if the task is
     * either pending or currently running, it will throw an
     * IllegalStateException
     *
     * @return
     */
    private R returnResult() throws ExecutionException, CancellationException,
            IllegalStateException {
        Lock lock = stateLock.readLock();
        lock.lock();
        try {
            switch (this.state) {
                case COMPLETED:
                    return result;
                case EXCEPTION:
                    throw new ExecutionException(exception);
                case CANCELLED:
                    throw new CancellationException();
                default:
                    throw new IllegalStateException("Invalid task state" + state);
            }
        } finally {
            lock.unlock();
        }
    }

    protected void notifyListeners(TaskEvent<R> e) {
        Lock lock = listenerLock.readLock();
        lock.lock();
        try {
            for (TaskListener l : listeners) {
                l.handle(e);
            }
            if (e.getTask().getState().isDone()) {
                Lock wlock = null;
                synchronized (listenerLock) {
                    //upgrade read lock to write lock
                    lock.unlock();
                    lock = null; //have to set this null so we can conditionally 
                    //check if it's already been unlocked
                    wlock = listenerLock.writeLock();
                    wlock.lock();
                }
                try {
                    listeners.clear();
                } finally {
                    wlock.unlock();
                }
            }
        } finally {
            if (lock != null) {
                lock.unlock();
            }
        }
    }

    /**
     * The operation to complete by this task.
     *
     * This method is called by execute, which provides the observable
     * mechanics.
     *
     *
     * @return
     */
    protected abstract R invoke() throws Exception;

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        Lock lock = stateLock.writeLock();
        lock.lock();
        try {
            if (getState().isDone()) {
                if (state.equals(TaskState.CANCELLED)) {
                    return true;
                }
                return false;
            }
            state = TaskState.CANCELLED;
            if (processingThread != null && mayInterruptIfRunning) {
                processingThread.interrupt();
            }
            notifyListeners(new TaskCancelledEvent(this));
        } finally {
            lock.unlock();
        }

        return true;
    }

    protected void complete() {
        setState(TaskState.COMPLETED);
        notifyListeners(new TaskCompletedEvent(this));
    }

    protected void exception(Throwable ex) {
        setState(TaskState.EXCEPTION);
        exception = ex;
        notifyListeners(new TaskExceptionEvent(this, ex));
    }

    /**
     * Creates an ObservableTask from a Runnable with a random UUID key
     *
     * @param r
     * @return
     */
    public static ObservableTask<?> adapt(Runnable r) {
        return adapt(generateUUID(), r);
    }

    public static ObservableTask<?> adapt(String key, Runnable r) {
        return new RunnableObservableTask(key, r);
    }

    /**
     * Creates an ObservableTask from a Runnable, with a provided result, with a
     * random UUID key
     *
     * @param <R>
     * @param r
     * @param result
     * @return
     */
    public static <R> ObservableTask<R> adapt(Runnable r, R result) {
        return adapt(generateUUID(), r, result);
    }

    public static <R> ObservableTask<R> adapt(String key, Runnable r, R result) {
        return new RunnableObservableTask(key, r, result);
    }

    /**
     * Creates an ObservableTask from a Callable with a random UUID key
     *
     * @param <R>
     * @param c
     * @return
     */
    public static <R> ObservableTask<R> adapt(Callable<R> c) {
        return adapt(generateUUID(), c);
    }

    public static <R> ObservableTask<R> adapt(String key, Callable<R> c) {
        return new CallableObservableTask(key, c);
    }

    private static class CallableObservableTask<R> extends ObservableTask<R> {

        private final static long serialVersionUID = 1L;
        private final Callable<R> callable;

        public CallableObservableTask(String key, Callable<R> callable) {
            super(key);
            this.callable = callable;
        }

        @Override
        protected R invoke() throws Exception {
            this.result = this.callable.call();
            return result;
        }
    }

    private static class RunnableObservableTask<R> extends ObservableTask<R> {

        private final static long serialVersionUID = 1L;
        private final Runnable runnable;

        private RunnableObservableTask(String key, Runnable r) {
            this(key, r, null);
        }

        public RunnableObservableTask(String key, Runnable runnable, R result) {
            super(key);
            this.runnable = runnable;
            this.result = result;
        }

        @Override
        protected R invoke() throws Exception {
            runnable.run();
            return result;
        }
    }
}
