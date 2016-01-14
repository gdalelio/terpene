package org.geoint.terpene.task.trigger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.geoint.terpene.task.ObservableTask;

/**
 *
 */
public abstract class TaskTriggerService<E> extends ObservableTask {

    private final List<TriggerListener<E>> listeners = Collections.synchronizedList(new ArrayList<TriggerListener<E>>());
    private final static Logger logger = Logger.getLogger("harpoon.triggerService");

    protected void trigger(TriggerEvent<E> event) {
        for (TriggerListener<E> l : listeners) {
            l.trigger(event);
        }
    }

    public void addTriggerListener(TriggerListener<E> l) {
        listeners.add(l);
    }

    public void removeTriggerListener(TriggerListener<E> l) {
        listeners.remove(l);
    }

    @Override
    public Object invoke() {
        for (TriggerListener<E> l : listeners) {
            l.started(this);
        }
        try {
            start();
        } catch (Throwable ex) {
            logger.log(Level.SEVERE, "Unable to start trigger service "
                    + this.getClass().getName(), ex);
        }
        return null;
    }

    abstract protected void start() throws Exception;
}
