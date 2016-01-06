package org.geoint.terpene.component;

/**
 * Status of a terpene component.
 */
public enum ComponentStatus {

    NOT_RUNNING,
    STARTING,
    RUNNING,
    PAUSING,
    PAUSED,
    RESUMING,
    STOPPING,
    UNREACHABLE,
    UNKNOWN;
}
