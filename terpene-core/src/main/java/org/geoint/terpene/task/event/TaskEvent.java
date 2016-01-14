package org.geoint.terpene.task.event;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import org.geoint.terpene.task.ObservableTask;

/**
 *
 */
public abstract class TaskEvent<R> implements Serializable {

    private final static long serialVersionUID = 1L;
    private final String eventId;
    private final long eventTime;
    private final ObservableTask<R> task;

    public TaskEvent(ObservableTask<R> task, long eventTime) {
        this.eventId = UUID.randomUUID().toString();
        this.eventTime = eventTime;
        this.task = task;
    }

    public TaskEvent(ObservableTask<R> task) {
        this(task, System.currentTimeMillis());
    }

    public ObservableTask<R> getTask() {
        return task;
    }

    public String getEventId() {
        return eventId;
    }

    public long getEventTime() {
        return eventTime;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.eventId);
        hash = 67 * hash + (int) (this.eventTime ^ (this.eventTime >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TaskEvent<R> other = (TaskEvent<R>) obj;
        if (!Objects.equals(this.eventId, other.eventId)) {
            return false;
        }
        if (this.eventTime != other.eventTime) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "[" + eventId + "] " + this.getClass().getSimpleName() + " on " + task.getKey();
    }
}
