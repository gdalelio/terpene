package org.geoint.terpene.task;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Identifies a class as a Task
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface TaskInfo {

    String name();

    String displayName() default "";

    double version() default 1.0;

    String description() default "";

    /**
     * Optional task execution schedule.
     *
     * <p>
     * This is the schedule the task management system will use to issue the
     * task to the task execution queue. There may still be a delay between the
     * time the task is entered into the distributed task execution queue and
     * the time it actually is executed, based on available resources.
     *
     * @return
     */
    String schedule() default "";

    /**
     * Specifies if the task should be enabled by default once it is added to
     * the task management, or if it should be disabled by default and
     * requirement manual activation. The default value is true (enabled by
     * default).
     *
     * @return
     */
    boolean enabled() default true;

    /**
     * Identifies the task able to be distributed, which means it can run on any
     * node in the terpene cloud without node-specific configuration.
     *
     * @return
     */
    boolean distributable() default false;
}
