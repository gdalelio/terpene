package org.geoint.terpene.config.store;

import org.geoint.terpene.config.ConfigService;
import java.util.ServiceLoader;

/**
 * Creates zero or more {@link ConfigStore} instances.
 *
 * <p>
 * ConfigStoreFactories are detected through their interface
 * ({@link ServiceLoader}) and executed randomly. All ConfigStoreFactory
 * instances will be loaded prior to configuration being resolved, so the
 * priority self-declared by the {@link ConfigStore} is abided.
 */
public interface ConfigStoreFactory {

    /**
     * Sets the ConfigService.
     *
     * <p>
     * This method is guaranteed to be called after construction, and before any
     * other methods on this interface are called.
     *
     * <p>
     * The ConfigService will have already had the direct-instantiated
     * {@link ConfigStore} instances loaded by this point, available through
     * programmatic retrieval, so these stores could contain bootstrap
     * configuration for these stores.
     *
     * @param cm 
     */
    void setConfigService(ConfigService cm);

    /**
     * Returns zero or more {@link ConfigurationStore} instances.
     *
     * @return store instances to register with the terpene-config service
     */
    ConfigStore[] getStores();
}
