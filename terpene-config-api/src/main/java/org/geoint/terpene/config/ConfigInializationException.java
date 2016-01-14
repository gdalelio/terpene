package org.geoint.terpene.config;

/**
 * Thrown when terpene-config cannot complete initialization.
 *
 */
public class ConfigInializationException extends TerpeneConfigException {

    public ConfigInializationException() {
    }

    public ConfigInializationException(String message) {
        super(message);
    }

    public ConfigInializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigInializationException(Throwable cause) {
        super(cause);
    }
}
