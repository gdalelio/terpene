package org.geoint.terpene.task.event;

import org.geoint.terpene.task.ObservableTask;

/**
 *
 */
public final class TaskCancelledEvent<R> extends TaskEvent<R> {

    private final static long serialVersionUID = 1L;

    public TaskCancelledEvent(ObservableTask<R> task) {
        super(task);
    }

    public TaskCancelledEvent(ObservableTask<R> task, long eventTime) {
        super(task, eventTime);
    }
}
