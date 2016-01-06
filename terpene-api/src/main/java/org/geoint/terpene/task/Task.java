package org.geoint.terpene.task;

import java.io.Serializable;
import java.util.UUID;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 */
public abstract class Task<R> implements RunnableFuture<R>, Serializable {

    public static final int PRIORITY_HIGH = 0;
    public static final int PRIORITY_MEDIUM = 50;
    public static final int PRIORITY_LOW = 100;
    private final static long serialVersionUID = 1L;
    private final String key;
    private volatile int priority = PRIORITY_MEDIUM;
    protected TaskState state = TaskState.PENDING;
    protected ReadWriteLock stateLock = new ReentrantReadWriteLock();

    public Task() {
        this(generateUUID());
    }

    public Task(String key) {
        this.key = key;
    }

    /**
     * Sets the priority of the task, capable of being used to prioritize thread
     * execution using a PriorityBlockingQueue and the
     * {@link ThreadPriorityComparator}
     *
     * @param priority
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }

    /**
     * Retrieve the current priority of the thread
     *
     * @return
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Returns the task key.
     *
     * A task, and its key, is considered "alive" between the states of PENDING
     * (inclusive) and a state that returns true from TaskState#isDone
     * (exclusive). A {@link TaskExecutorService} manages all "alive" tasks that
     * has been submitted, only running a single task with the same task key. As
     * a result, if two tasks are submitted with the same key, the first task
     * registered will become managed and will be executed (the later will not).
     * The other Task will be cancelled.
     *
     * The TaskExecutorService returns the managed Task for each Task submitted,
     * so it is imperative that developers ensure they are referencing the task
     * that will be executed.
     *
     * @return
     */
    public String getKey() {
        return key;
    }

    public TaskState getState() {
        Lock lock = stateLock.readLock();
        lock.lock();
        try {
            return state;
        } finally {
            lock.unlock();
        }
    }

    protected void setState(TaskState state) {
        Lock lock = stateLock.writeLock();
        lock.lock();
        try {
            this.state = state;
            if (state.isDone()) {
                synchronized (this) {
                    notifyAll();  //if there are any threads that have waited on this Task, notify them
                }
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * Subclass hook, called prior to execution
     *
     * @throws Throwable
     */
    protected void preExecute() throws Throwable {
    }

    /**
     * Subclass hook, called after execution
     *
     * @throws Throwable
     */
    protected void postExecute() throws Throwable {
    }

    /**
     * Called when one Task is merged with another Task. This occurs when there
     * is a task key collision.
     *
     * @param task
     */
    protected void merge(Task<R> task) {
    }

    protected static String generateUUID() {
        return UUID.randomUUID().toString();
    }
}
