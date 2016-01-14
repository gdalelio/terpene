package org.geoint.terpene.task;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.geoint.terpene.task.Task.generateUUID;
import org.geoint.terpene.task.event.SubTaskStartedEvent;
import org.geoint.terpene.task.event.TaskCompletedEvent;
import org.geoint.terpene.task.event.TaskEvent;
import org.geoint.terpene.task.event.TaskExceptionEvent;
import org.geoint.terpene.task.event.TaskStartedEvent;

/**
 *
 */
public abstract class ChainedTask<I, R> extends ObservableTask<R> implements Chained<I> {

    protected I previousResult;
    protected ExecutorService exe;
    private final ArrayList<ChainedTask<R, ?>> successGraph = new ArrayList<>();
    private final ArrayList<ObservableTask<?>> failGraph = new ArrayList<>();
    private static final Logger logger = Logger.getLogger("terpene.task");

    public ChainedTask(ExecutorService exe) {
        this.exe = exe;
    }

    public ChainedTask(String key, ExecutorService exe) {
        super(key);
        this.exe = exe;
    }

    /**
     * Adds a task that will be submitted to the executor upon successful
     * completion of this task. The results of this task will be provided to the
     * next task through its #setResults method.
     *
     * Any number of tasks can be added to be called upon the success of this
     * task, simply by adding calling this method for each of these tasks. These
     * tasks may be executed in parallel - if a consecutive execution is
     * desired, chain the tasks.
     *
     * This method returns the {@link TaskChainFuture} for the submitted task,
     * not this task. This allows additional task chaining for that task.
     *
     * @param <R>
     * @param <T>
     * @param t
     * @return
     * @throws IllegalStateException
     */
    private <T> ChainedTask<R, T> doOnSuccess(ChainedTask<R, T> t) throws IllegalStateException {
        Lock lock = stateLock.readLock();
        lock.lock();
        try {
            if (state.isDone()) {
                throw new IllegalStateException("Unable to add a follow-on "
                        + "task, the task has already completed.");
            }
            successGraph.add(t);
            return t;
        } finally {
            lock.unlock();
        }
    }

    public <T> ChainedTask<R, T> onSuccess(ChainedTask<R, T> t) throws IllegalStateException {
        return doOnSuccess(t);
    }

    public <T> ChainedTask<R, T> onSuccess(String key, ChainedRunnable<R> chained, T result) {
        return doOnSuccess(new ChainedRunnableTask(key, chained, result, this.exe));
    }

    public <T> ChainedTask<R, T> onSuccess(ChainedRunnable<R> chained, T result) {
        return onSuccess(generateUUID(), chained, result);
    }

    public ChainedTask<R, ?> onSuccess(ChainedRunnable<R> chained) {
        return onSuccess(generateUUID(), chained, null);
    }

    public ChainedTask<?, ?> onSuccess(Runnable runnable) {
        return doOnSuccess(new ChainedRunnableTask(generateUUID(), runnable, null, this.exe));
    }

    public <T> ChainedTask<R, T> onSuccess(String key, ChainedCallable<R, T> chained) {
        return doOnSuccess(new ChainedCallableTask(key, chained, this.exe));
    }

    public <T> ChainedTask<R, T> onSuccess(ChainedCallable<R, T> chained) {
        final String uuid = generateUUID();
        return doOnSuccess(new ChainedCallableTask(uuid, chained, this.exe));
    }

    public <E, T> SplitChainedTask<E, T> forEach(String key, ChainedTaskFactory<E, T> factory) {
        SplitChainedTask split = new SplitChainedTask(key, factory, this.exe);
        doOnSuccess(split);  //intentionally lose my generic typing here
        return split;
    }

    public static <R, T> ChainedTask<R, T> adapt(String key, ChainedCallable<R, T> callable, ExecutorService exe) {
        return new ChainedCallableTask(key, callable, exe);
    }

    public static <R, Object> ChainedTask<R, Object> adapt(String key, ChainedRunnable<R> runnable, ExecutorService exe) {
        return new ChainedRunnableTask(key, runnable, exe);
    }

    public static <R, T> ChainedTask<R, T> adapt(String key, ChainedRunnable<R> runnable, T result, ExecutorService exe) {
        return new ChainedRunnableTask(key, runnable, result, exe);
    }

    public static ChainedTask<?, ?> adapt(String key, Runnable r, ExecutorService exe) {
        return new ChainedRunnableTask(key, r, exe);
    }

    public static <T> ChainedTask<?, T> adapt(String key, Runnable r, T result, ExecutorService exe) {
        return new ChainedRunnableTask(key, r, result, exe);
    }

    public static <T> ChainedTask<?, T> adapt(String key, Callable<T> c, ExecutorService exe) {
        return new ChainedCallableTask(key, c, exe);
    }

    /**
     * Adds a task that will be submitted to the executor upon a failed
     * completion of this task (an Exception is thrown). The results of this
     * task will not be provided to the next task, since this task has failed.
     *
     * Any number of tasks can be added to be called upon the failure of this
     * task, simply by adding calling this method for each of these tasks. These
     * tasks may be executed in parallel - if a consecutive execution is
     * desired, chain the tasks.
     *
     * This method returns the TaskChain for the submitted task, not this task.
     * This allows additional task chaining for that task.
     *
     * @param <T>
     * @param t
     * @return
     * @throws IllegalStateException
     */
    public <T> Task<T> onFail(ObservableTask<T> t) throws IllegalStateException {
        Lock lock = stateLock.readLock();
        lock.lock();
        try {
            if (state.isDone()) {
                throw new IllegalStateException("Unable to add a follow-on "
                        + "task, the task has already completed.");
            }
            failGraph.add(t);
            return t;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Method is overridden to ensure that the state of this task is not "done"
     * even after the task has completed execution. The task is not actually
     * "done" until all sub-tasks have been completed, the status of which are
     * provided asynchronously through the TaskListener interface.
     *
     * The purpose of this is that this task no longer blocks a Thread - the
     * thread is complete...the specific executable code for this task has
     * completed on it's own thread...but the task in its whole hasn't been
     * completed yet.
     */
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
            notifyListeners(new TaskStartedEvent(this));
            preExecute();

            if (!state.isDone()) { //make sure preExecute didn't abort
                result = invoke();
                executeSuccessTasks();
            }
        } catch (Throwable ex) {
            //call any tasks waiting for this task to fail
            logger.log(Level.SEVERE, "Failed", ex);

            //cancel and follow-on success tasks
            for (ChainedTask<R, ?> s : successGraph) {
                s.cancel(true);
            }
            executeFailTasks(ex);
        } finally {
            try {
                //if preExecute is called, postExecute will be called
                postExecute();
            } catch (Throwable ex) {
                exception(ex);
            }
        }
    }

    /**
     * Guaranteed to be called prior to execution.
     *
     * At a minimum, a null object will be passed
     *
     * @param result result of previous task or null
     */
    @Override
    public void setResults(I result) {
        this.previousResult = result;
    }

    /**
     * Cancel any subtasks
     *
     * @param interrupt
     * @return
     */
    @Override
    public boolean cancel(boolean interrupt) {
        boolean res = super.cancel(interrupt);

        for (ChainedTask<R, ?> s : successGraph) {
            s.cancel(interrupt);
        }
        return res;
    }

    protected int executeSuccessTasks() {
        //submit tasks that are waiting for successful completion
        final int subtaskSize = successGraph.size();
        if (subtaskSize == 0) {
            complete();
        } else {
            SubTaskListener subtaskListener = new SubTaskListener(this, successGraph.size());
            for (ChainedTask<R, ?> s : successGraph) {
                s.setResults(result);
                s.addListener(subtaskListener);
                exe.execute(s);
            }
        }
        return subtaskSize;
    }

    protected void executeFailTasks(Throwable ex) {
        final int numFailed = failGraph.size();
        if (numFailed == 0) {
            //do it once in this situation, since fail tasks will propegate back up
            exception(ex);
        } else {
            SubTaskListener subtaskListener = new SubTaskListener(this, failGraph.size());
            for (ObservableTask<?> f : failGraph) {
                f.addListener(subtaskListener);
                exe.submit(f);
            }
        }
    }

    private static class ChainedRunnableTask<I, R> extends ChainedTask<I, R> implements ChainedRunnable<I> {

        private final Runnable runnable;

        public ChainedRunnableTask(String key, Runnable runnable, ExecutorService exe) {
            this(key, runnable, null, exe);
        }

        public ChainedRunnableTask(String key, Runnable r, R result, ExecutorService exe) {
            super(key, exe);
            this.runnable = r;
            this.result = result;
        }

        @Override
        protected R invoke() throws Exception {
            if (runnable instanceof ChainedRunnable) {
                ((ChainedRunnable) runnable).setResults(this.previousResult);
            }
            if (runnable instanceof ObservableTask) {
                return (R) ((ObservableTask) runnable).invoke();
            } else {
                runnable.run();
            }
            return result;
        }
    }

    private static class ChainedCallableTask<I, R> extends ChainedTask<I, R> implements ChainedCallable<I, R> {

        private final Callable<R> callable;

        public ChainedCallableTask(String key, ChainedCallable<I, R> callable, ExecutorService exe) {
            super(key, exe);
            this.callable = callable;
        }

        public ChainedCallableTask(String key, Callable<R> callable, ExecutorService exe) {
            super(key, exe);
            this.callable = callable;
        }

        @Override
        protected R invoke() throws Exception {
            if (callable instanceof ChainedCallable) {
                ((ChainedCallable) callable).setResults(previousResult);
            }
            return this.callable.call();
        }

        @Override
        public R call() throws Exception {
            return invoke();
        }
    }

    /**
     * Listener to wait for all sub-tasks to complete.
     *
     * If any exceptions are received, any running or tasks yet to be executed
     * will be cancelled.
     */
    protected static class SubTaskListener implements TaskListener {

        private final ChainedTask parent;
        private final AtomicInteger countdown;

        public SubTaskListener(ChainedTask parent, int numSubs) {
            this.parent = parent;
            countdown = new AtomicInteger(numSubs);
        }

        @Override
        public void handle(TaskEvent e) {
            if (e instanceof TaskCompletedEvent) {
                if (countdown.decrementAndGet() == 0) {
                    parent.complete();
                }
            } else if (e instanceof TaskExceptionEvent) {
                countdown.decrementAndGet();
                parent.exception(e.getTask().getException());
            } else if (e instanceof SubTaskStartedEvent) {
            }
        }
    }
}
