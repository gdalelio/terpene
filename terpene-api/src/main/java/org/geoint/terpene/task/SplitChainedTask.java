package org.geoint.terpene.task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.geoint.terpene.task.event.TaskCompletedEvent;
import org.geoint.terpene.task.event.TaskEvent;
import org.geoint.terpene.task.event.TaskExceptionEvent;
import org.geoint.terpene.task.event.TaskStartedEvent;

/**
 * If the results are a collection or an array, a new task is generated for each
 * and the results are aggregated back.
 */
public class SplitChainedTask<I, R> extends ChainedTask<I, Collection<R>> {

    private List<ChainedTask> subs = new ArrayList<>();
    private final ChainedTaskFactory factory;
    private final static Logger logger = Logger.getLogger("terpene.task");
    private boolean subTasksComplete = false;

    public SplitChainedTask(String key, ChainedTaskFactory factory, ExecutorService exe) {
        super(key, exe);
        this.factory = factory;
        this.result = new ArrayList<>();
    }

    //do nothing here - polymorphic hazard =)
    @Override
    protected final Collection<R> invoke() {
        return null;
    }

    /**
     * On completion of all sub tasks, run this task. This effectively is a join
     * operation
     *
     * @param <T>
     * @param key
     * @param chained
     * @param result
     * @return
     */
    @Override
    public <T> ChainedTask<Collection<R>, T> onSuccess(String key, ChainedRunnable<Collection<R>> chained, T result) {
        return super.onSuccess(key, chained, result);
    }

    /**
     * On completion of all sub tasks, run this task. This effectively is a join
     * operation
     *
     * @param <T>
     * @param chained
     * @param result
     * @return
     */
    @Override
    public <T> ChainedTask<Collection<R>, T> onSuccess(ChainedRunnable<Collection<R>> chained, T result) {
        return super.onSuccess(chained, result);
    }

    /**
     * On completion of all sub tasks, run this task. This effectively is a join
     * operation
     *
     * @param chained
     * @return
     */
    @Override
    public ChainedTask<Collection<R>, ?> onSuccess(ChainedRunnable<Collection<R>> chained) {
        return super.onSuccess(chained);
    }

    /**
     * On completion of all sub tasks, run this task. This effectively is a join
     * operation
     *
     * @param <T>
     * @param key
     * @param chained
     * @return
     */
    @Override
    public <T> ChainedTask<Collection<R>, T> onSuccess(String key, ChainedCallable<Collection<R>, T> chained) {
        return super.onSuccess(key, chained);
    }

    /**
     * On completion of all sub tasks, run this task. This effectively is a join
     * operation
     *
     * @param <T>
     * @param chained
     * @return
     */
    @Override
    public <T> ChainedTask<Collection<R>, T> onSuccess(ChainedCallable<Collection<R>, T> chained) {
        return super.onSuccess(chained);
    }

    /**
     * On failure of any sub tasks, cancel all sub tasks and run this task. This
     * effectively is a join operation
     *
     * @param <T>
     * @param t
     * @return
     * @throws IllegalStateException
     */
    @Override
    public <T> Task<T> onFail(ObservableTask<T> t) throws IllegalStateException {
        return super.onFail(t);
    }

    /**
     * For each of the results of this split, provide them as input to a new set
     * of tasks (not a join operation - keeps the split...split).
     *
     * @param <E>
     * @param <T>
     * @param key
     * @param factory
     * @return
     */
    @Override
    public <E, T> SplitChainedTask<E, T> forEach(String key, ChainedTaskFactory<E, T> factory) {
        return super.forEach(key, factory);
    }

    /**
     * Creates a new subtask for each result, submitting it to the executor, and
     * registering a callback to satisfy this task
     *
     * @return
     * @throws Exception
     */
    @Override
    public void run() {

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

                SplitTaskListener subListener;
                if (previousResult instanceof Collection) {
                    subListener = new SplitTaskListener(this, ((Collection) previousResult).size());
                    for (I i : (Collection<I>) previousResult) {
                        ChainedTask<I, R> splitTask = factory.create(i, (TaskExecutorService) exe);
                        splitTask.setResults(i);
                        splitTask.addListener(subListener);
                        this.exe.execute(splitTask);
                        subs.add(splitTask);
                    }
                } else if (previousResult.getClass().isArray()) {
                    subListener = new SplitTaskListener(this, ((I[]) previousResult).length);
                    for (I i : (I[]) previousResult) {
                        ChainedTask<I, R> splitTask = factory.create(i, (TaskExecutorService) exe);
                        splitTask.setResults(i);
                        splitTask.addListener(subListener);
                        this.exe.execute(splitTask);
                        subs.add(splitTask);
                    }
                } else {
                    subListener = new SplitTaskListener(this, 1);
                    ChainedTask<I, R> splitTask = factory.create(previousResult, (TaskExecutorService) exe);
                    splitTask.setResults(previousResult);
                    splitTask.addListener(subListener);
                    this.exe.execute(splitTask);
                    subs.add(splitTask);
                }
            }
        } catch (Throwable ex) {
            //call any tasks waiting for this task to fail
            logger.log(Level.SEVERE, "Failed", ex);

            //cancel and follow-on success tasks
            for (ChainedTask<R, ?> s : subs) {
                s.cancel(true);
            }
            exception(ex);
        } finally {
            try {
                //if preExecute is called, postExecute will be called
                postExecute();
            } catch (Throwable ex) {
                exception(ex);
            }
        }
    }

    private class SplitTaskListener implements TaskListener<R> {

        private final ChainedTask parent;
        private final AtomicInteger countdown;

        public SplitTaskListener(ChainedTask parent, int numSubs) {
            this.parent = parent;
            countdown = new AtomicInteger(numSubs);
        }

        @Override
        public void handle(TaskEvent<R> e) {
            if (e instanceof TaskCompletedEvent) {
                try {
                    ((Collection<R>) parent.result).add(e.getTask().get());
                } catch (InterruptedException | ExecutionException | CancellationException ex) {
                    //should never get here, it was a TaskCompletedEvent
                    logger.log(Level.SEVERE, "Unexpetedly received an exception "
                            + "when attempting to assign split task results to "
                            + "parent task " + parent.getKey() + " results", ex);
                }
                if (countdown.decrementAndGet() == 0) {
                    if (!subTasksComplete) {
                        subTasksComplete = true;
                        //now we need to execute the onSuccess chain...a join function =)
                        int numSubs = parent.executeSuccessTasks();
                        countdown.set(numSubs);
                    } else {
                        parent.complete();
                    }
                }
            } else if (e instanceof TaskExceptionEvent) {
                countdown.decrementAndGet();
                parent.exception(e.getTask().getException());
            }
        }
    }
}
