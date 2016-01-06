package org.geoint.terpene.task.event;

import org.geoint.terpene.task.ObservableTask;

/**
 *
 */
public final class TaskCompletedEvent<R> extends TaskEvent<R> {

    private final static long serialVersionUID = 1L;

    public TaskCompletedEvent(ObservableTask<R> task) {
        super(task);
    }

    public TaskCompletedEvent(ObservableTask<R> task, long eventTime) {
        super(task, eventTime);
    }
}
