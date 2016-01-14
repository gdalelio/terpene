package org.geoint.terpene.task;

import org.geoint.terpene.task.event.TaskEvent;

/**
 * A TaskListener will receive asynchronous lifecycle events for task(s) for
 * which it is listening.
 */
public interface TaskListener<R> {

    void handle(TaskEvent<R> e);
}
