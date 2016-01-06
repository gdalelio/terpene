package org.geoint.terpene.config.store;

import org.geoint.terpene.config.TerpeneConfigException;

/**
 * Thrown when an an attempted update to a managed property value fails.
 */
public class ConfigChangeException extends TerpeneConfigException {

    private final String propertyName;
    private final Object oldValue;
    private final Object newValue;

    public ConfigChangeException(String propertyName, Object oldValue,
            Object newValue) {
        this.propertyName = propertyName;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public ConfigChangeException(String propertyName, Object oldValue,
            Object newValue, String message) {
        super(message);
        this.propertyName = propertyName;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public ConfigChangeException(String propertyName, Object oldValue,
            Object newValue, String message, Throwable cause) {
        super(message, cause);
        this.propertyName = propertyName;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public ConfigChangeException(String propertyName, Object oldValue,
            Object newValue, Throwable cause) {
        super(cause);
        this.propertyName = propertyName;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public Object getOldValue() {
        return oldValue;
    }

    public Object getNewValue() {
        return newValue;
    }

}
