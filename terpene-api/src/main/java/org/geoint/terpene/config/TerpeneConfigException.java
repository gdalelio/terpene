package org.geoint.terpene.config;

import org.geoint.terpene.TerpeneException;

/**
 * Base terpene-config checked exception class.
 */
public abstract class TerpeneConfigException extends TerpeneException {

    public TerpeneConfigException() {
    }

    public TerpeneConfigException(String message) {
        super(message);
    }

    public TerpeneConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    public TerpeneConfigException(Throwable cause) {
        super(cause);
    }
}
