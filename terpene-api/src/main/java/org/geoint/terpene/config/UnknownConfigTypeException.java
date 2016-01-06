package org.geoint.terpene.config;

/**
 * Thrown when a requested configuration property type is unknown, and terpene
 * config could not cast this type.
 */
public class UnknownConfigTypeException extends TerpeneConfigException {

    private final String requested;

    public UnknownConfigTypeException(String requested) {
        this.requested = requested;
    }

    public UnknownConfigTypeException(String requested, String message) {
        super(message);
        this.requested = requested;
    }

    public UnknownConfigTypeException(String requested, String message, Throwable cause) {
        super(message, cause);
        this.requested = requested;
    }

    public UnknownConfigTypeException(String requested, Throwable cause) {
        super(cause);
        this.requested = requested;
    }

}
