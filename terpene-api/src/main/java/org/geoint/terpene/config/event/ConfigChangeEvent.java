package org.geoint.terpene.config.event;

import org.geoint.terpene.config.ConfigProperty;

/**
 * Fired (via CDI) when a configuration property value has been changed.
 *
 * Components accessing a configuration value through a {@link ConfigProperty}
 * will automatically have the new configuration. Those components already
 * running using direct value injection will not receive the configuration
 * changes, and may be interested in receiving these events.
 */
public interface ConfigChangeEvent {

    public String getName();

    public String getOldValue();

    public String getNewValue();
}
