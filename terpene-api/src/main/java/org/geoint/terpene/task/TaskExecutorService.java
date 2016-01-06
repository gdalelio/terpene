package org.geoint.terpene.task;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.geoint.terpene.task.event.TaskEvent;

/**
 *
 */
public class TaskExecutorService extends ThreadPoolExecutor {

    private Map<String, Task<?>> currentTasks = new ConcurrentHashMap<>();
    private ReadWriteLock tasksLock = new ReentrantReadWriteLock();

    /**
     * Provides the following default settings:
     * <ul>
     * <li>corePoolSize = 30</li>
     * <li>maxPoolSize = 30</li>
     * <li>keepAliveTime = 1</li>
     * <li>keepAliveUnit = DAYS</li>
     * <li>workQueue = java.util.concurrent.LinkedBlockingQueue</li>
     * </ul>
     */
    public TaskExecutorService() {
        this(30, 30, 1, TimeUnit.DAYS, new LinkedBlockingQueue<Runnable>());
    }

    public TaskExecutorService(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public TaskExecutorService(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public TaskExecutorService(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public TaskExecutorService(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    @Override
    protected <R> Task<R> newTaskFor(Runnable r, R result) {
        return newTaskFor(UUID.randomUUID().toString(), r, result);
    }

    protected <R> Task<R> newTaskFor(String key, Runnable r, R result) {
        return ObservableTask.adapt(key, r, result);
    }

    @Override
    protected <R> Task<R> newTaskFor(Callable<R> c) {
        return newTaskFor(UUID.randomUUID().toString(), c);
    }

    protected <R> Task<R> newTaskFor(String key, Callable<R> c) {
        return ObservableTask.adapt(key, c);
    }

    @Override
    public Task<?> submit(Runnable r) {
        Task t = newTaskFor(r, null);
        return submit(t);
    }

    public Task submit(String key, Runnable r) {
        Task t = newTaskFor(key, r, null);
        return submit(t);
    }

    @Override
    public <R> Task<R> submit(Runnable r, R result) {
        Task t = newTaskFor(r, result);
        return submit(t);
    }

    public <R> Task<R> submit(String key, Runnable r, R result) {
        Task t = newTaskFor(key, r, result);
        return submit(t);
    }

    @Override
    public <R> Task<R> submit(Callable<R> c) {
        Task t = newTaskFor(c);
        return submit(t);
    }

    public <R> Task<R> submit(Task<R> t) {
        this.execute(t);
        return t;
    }

    public <R, T> ChainedTask<R, T> chain(ChainedTask<R, T> task) {
        task.exe = this;
        this.execute(task);
        return task;
    }

    public <R, T> ChainedTask<R, ?> chain(String key, ChainedRunnable<R> r) {
        // return TaskChain.create(key, r, null, this);
        return ChainedTask.adapt(key, r, this);
    }

    public <R, T> ChainedTask<R, T> chain(String key, ChainedRunnable<R> r, T result) {
        //return TaskChain.create(key, r, result, this);
        return ChainedTask.adapt(key, r, result, this);
    }

    public ChainedTask<?, ?> chain(String key, Runnable r) {
        return ChainedTask.adapt(key, r, this);
    }

    public ChainedTask<?, ?> chain(Runnable r) {
        return ChainedTask.adapt(ChainedTask.generateUUID(), r, this);
    }

    public <T> ChainedTask<?, T> chain(String key, Runnable r, T result) {
        return ChainedTask.adapt(key, r, result, this);
    }

    public <T> ChainedTask<?, T> chain(Runnable r, T result) {
        return ChainedTask.adapt(ChainedTask.generateUUID(), r, result, this);
    }

    public <T> ChainedTask<?, T> chain(String key, Callable<T> c) {
        return ChainedTask.adapt(key, c, this);
    }

    public <T> ChainedTask<?, T> chain(Callable<T> c) {
        return ChainedTask.adapt(ChainedTask.generateUUID(), c, this);
    }

    /**
     * Monitors the Task for completion and removes it from the managed task
     * list once compelte
     */
    private class TaskCompletionMonitor implements TaskListener {

        @Override
        public void handle(TaskEvent e) {
            if (e.getTask().isDone()) {
                currentTasks.remove(e.getTask().getKey()); //no locking needed, it's a concurrent map
            }
        }
    }
}
