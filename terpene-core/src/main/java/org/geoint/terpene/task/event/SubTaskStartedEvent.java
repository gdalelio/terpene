package org.geoint.terpene.task.event;

import org.geoint.terpene.task.ObservableTask;

/**
 * Called by subtasks of {@link TakChain}
 */
public class SubTaskStartedEvent<R> extends TaskEvent<R> {

    private final static long serialVersionUID = 1L;

    public SubTaskStartedEvent(ObservableTask<R> task) {
        super(task);
    }

    public SubTaskStartedEvent(ObservableTask<R> task, long eventTime) {
        super(task, eventTime);
    }
}
