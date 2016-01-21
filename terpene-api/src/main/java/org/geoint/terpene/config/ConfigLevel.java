package org.geoint.terpene.config;

/**
 * The configuration level defines at what tier of the terpene runtime
 * environment that a configuration value has been associated with a property.
 *
 * A configuration level is a "human" label for a standard, reserved,
 * configuration value priority
 *
 */
public enum ConfigLevel {

    /**
     * Default configuration is defined in code.
     */
    DEFAULT(100),
    /**
     * Global configuration values are defined on the terpene platform.
     */
    GLOBAL(50),
    /**
     * Configuration values that are specifically associated with a single
     * terpene node.
     */
    NODE(20),
    /**
     * Configuration values associated with a specific component instance.
     */
    INSTANCE(10);

    private final int priority;

    private ConfigLevel(int priority) {
        this.priority = priority;
    }

    /**
     * Returns the priority value of the level.
     *
     * @return the priority level between 0 and 100 with 0 being the highest
     * priority
     */
    public int getPriority() {
        return priority;
    }

}
