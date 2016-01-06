package org.geoint.terpene.config.store;

import java.util.ServiceLoader;
import org.geoint.terpene.config.ConfigProperty;

/**
 * Defines a single configuration store.
 *
 * <p>
 * This interface provides a point of extension for configuration storage, as
 * Terpene Config load all implementations of this interface that comply with
 * the ServiceLoader specification. If there can be only a single instance of a
 * store, it is recommended to implement this service interface and register the
 * concrete class in the {@code META-INF/services}. However, if there can be
 * multiple instances of a ConfigStore, it's recommended to implement this
 * interface for the store instance, but instead to create and register a
 * {@link ConfigStoreFactory} to be loaded.
 *
 * @see ServiceLoader
 * @see ConfigStoreFactory
 */
public interface ConfigStore {

    /**
     * Returns the configuration property for the requested configuration
     * property name as defined by the configuration store instance.
     *
     *
     * @param name unique name of the configuration property
     * @return configuration property as defined by the store or null if not
     * defined by the store.
     */
    ConfigProperty<?> getProperty(String name);

    /**
     * Optional method which sets the configuration property value on the store
     * if supported by the configuration store.
     *
     *
     * @param <T> the value type
     * @param name the unique name of the property to change
     * @param value the new value of the configuration property, can be null
     * @return the old property value, may be null if the value was not
     * previously set in this store
     * @throws ConfigChangeException if the store does not implement this method
     */
    <T extends Object> T setValue(String name, T value)
            throws ConfigChangeException;

}
