package org.geoint.terpene.task.trigger;

/**
 * Triggering event
 */
public class TriggerEvent<E> {

    private final long eventTime;
    private final E event;

    public TriggerEvent(E event) {
        this.event = event;
        this.eventTime = System.currentTimeMillis();
    }

    public long getEventTime() {
        return eventTime;
    }

    public E getTriggeringEvent() {
        return event;
    }
}
