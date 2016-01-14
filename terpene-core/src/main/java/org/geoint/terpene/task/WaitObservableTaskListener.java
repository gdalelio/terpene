package org.geoint.terpene.task;

import org.geoint.terpene.task.event.TaskEvent;

/**
 * On completion of a task, notifies any waiting threads
 */
public class WaitObservableTaskListener<R> implements TaskListener<R> {

    @Override
    public void handle(TaskEvent<R> e) {
        if (e.getTask().isDone()) {
            //notify any threads waiting on this listener
            synchronized (this) {
                this.notifyAll();
            }
        }
    }
}
