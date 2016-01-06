package org.geoint.terpene.spi.component;

/**
 * Unique component instance identifier.
 */
public interface InstanceId {

    /**
     * The terpene component instance unique identifier as a String.
     *
     * This String can be used to identify the instance in methods that accept a
     * String as an instance identifier.
     *
     * @return
     */
    String asString();
}
