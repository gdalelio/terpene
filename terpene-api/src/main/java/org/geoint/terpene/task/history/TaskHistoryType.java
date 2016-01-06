package org.geoint.terpene.task.history;

/**
 * <p>The type of task history the task is interested in</p>
 * 
 * @author mdgsies
 */
public enum TaskHistoryType {
    /**
     * The last successful task run
     */ 
    SUCCESS,
    
    /**
     * The last failed task run
     */
    FAILURE,
    
    /**
     * The last task run, no matter the status
     */
    ANY;
}
