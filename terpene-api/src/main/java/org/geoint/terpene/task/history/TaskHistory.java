package org.geoint.terpene.task.history;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>Annotates a tasks field to be injected with historic task
 * run information.</p>
 * 
 * <p>Tasks may need this information to figure out how to execute. 
 * For example, if a task requires information on the last successful run time 
 * of the task, this information will be provided.</p>
 * 
 * @see TaskHistoryType
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface TaskHistory {
    
    /**
     * The type of task history requested
     */
    TaskHistoryType value();
}
