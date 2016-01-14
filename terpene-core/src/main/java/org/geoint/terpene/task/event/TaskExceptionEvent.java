package org.geoint.terpene.task.event;

import org.geoint.terpene.task.ObservableTask;

/**
 *
 */
public final class TaskExceptionEvent<R> extends TaskEvent<R> {

    private final static long serialVersionUID = 1L;
    private final Throwable exception;

    public TaskExceptionEvent(Throwable exception, ObservableTask<R> task, long eventTime) {
        super(task, eventTime);
        this.exception = exception;
    }

    public TaskExceptionEvent(ObservableTask<R> task, Throwable exception) {
        super(task);
        this.exception = exception;
    }

    public Throwable getException() {
        return exception;
    }
}
