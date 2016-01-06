package org.geoint.terpene.component;

import org.geoint.terpene.TerpeneException;

/**
 *
 */
public class TerpeneComponentException extends TerpeneException {

    private final Class<?> componentType;

    public TerpeneComponentException(Class<?> componentType) {
        this.componentType = componentType;
    }

    public TerpeneComponentException(Class<?> componentType, String message) {
        super(message);
        this.componentType = componentType;
    }

    public TerpeneComponentException(Class<?> componentType, String message, Throwable cause) {
        super(message, cause);
        this.componentType = componentType;
    }

    public TerpeneComponentException(Class<?> componentType, Throwable cause) {
        super(cause);
        this.componentType = componentType;
    }

    public Class<?> getComponentType() {
        return componentType;
    }

}
