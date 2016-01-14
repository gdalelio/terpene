package org.geoint.terpene.task.trigger;

/**
 * 
 */
public interface TriggerListener<E> {
    
    void trigger(TriggerEvent<E> e);
    void started(TaskTriggerService<E> service);
    
}
