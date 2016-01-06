package org.geoint.terpene.task;

/**
 *
 */
public enum TaskState {

    //SUBMITTED(false),
    PENDING (false),
    RUNNING(false),
    COMPLETED(true),
    EXCEPTION(true),
    CANCELLED(true);
    private boolean done;

    private TaskState(boolean done) {
        this.done = done;
    }

    public boolean isDone() {
        return done;
    }
}
