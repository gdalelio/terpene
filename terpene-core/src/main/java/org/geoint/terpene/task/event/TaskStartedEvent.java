package org.geoint.terpene.task.event;

import org.geoint.terpene.task.ObservableTask;

/**
 *
 */
public final class TaskStartedEvent<R> extends TaskEvent<R> {

    private final static long serialVersionUID = 1L;

    public TaskStartedEvent(ObservableTask<R> task) {
        super(task);
    }

    public TaskStartedEvent(ObservableTask<R> task, long eventTime) {
        super(task, eventTime);
    }
}
