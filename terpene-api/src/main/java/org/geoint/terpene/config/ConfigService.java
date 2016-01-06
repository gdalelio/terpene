package org.geoint.terpene.config;

import org.geoint.terpene.config.store.ConfigStore;
import org.geoint.util.hierarchy.HierarchicalVisitor;

/**
 * Terpene config service interface.
 *
 *
 * <p>
 * <h1>Configurating Terpene Configuration</h1>
 * <p>
 * A possible point of confusion is understanding how to bootstrap a terpene
 * platform: if terpene components use terpene-config to retrieve their
 * configuration, how does terpene-config (a terpene component) get configured?
 * <p>
 * terpene-config is self-configuring in that it uses the same configuration
 * stores that provides configuration for other terpene components and as new 
 * stores are registered to terpene-config the derived configuration is 
 * updated.  Since the "jvm system property" and "system environment" stores 
 * are required for all terpene-config implementations this makes three 
 *
 *
 * <p>
 * <h1>Using Configuration</h1>
 * <p>
 * Terpene-managed configuration can be accessed in one of two ways: through CDI
 * and programmatically.
 * <p>
 * <h2>Injecting Configuration Values</h2>
 * The easiest way to access configuration data is to directly inject the
 * property values:
 *
 * {@code
 * @Inject @Config
 * private String myStringConfig;
 * @Inject @Config
 * private int myIntConfig;
 * }
 *
 * Configuration can also be injected using constructor injection:
 *
 * {@code
 * @Inject
 * public Foo (@Config String myStringConfig, @Config int myIntConfig) {
 *    ...
 * }
 * }
 *
 * <h2>Programmatically Accessing Configuration</h2>
 * Configuration can also be programmatically queried and visited through
 * injecting a {@link ConfigProperty} or using the methods on the ConfigService
 * instance, which can be retrieved from the {@link ServiceManager}.
 *
 * {@code
 *
 * //programmatically access the configuration
 * ConfigProperty<C> prop = ...
 * String prop = prop.getName();
 * C value = prop.getValue();
 * Collection<ConfigProperty<?>> children = prop.getChildren();
 *
 * //using the ConfigVisitor to visit configuration
 * ConfigProperty<?> prop = ...
 * prop.visit((p) -> {  ...do something...  });
 *
 * or
 *
 * ConfigService config = ...
 * config.visit((p) -> { ...do something... });
 *
 * }
 *
 */
public interface ConfigService {

    /**
     * Programmatically retrieve the requested configuration property from the
     * <i>derived</i> configuration.
     *
     * <p>
     * If the configuration property isn't known by the service this method will
     * return null.
     *
     * @param name the unique name of the configuration property
     * @return property for the service or null if the name is unknown to the
     * service
     */
    ConfigProperty getProperty(String name);

    /**
     * Returns the <i>derived</i> store for the terpene component.
     *
     * @return
     */
    ConfigStore getStore();

    /**
     * Retrieves a source (non-derived) store for the specified terpene runtime
     * tier.
     *
     * This method is effectively the same as calling {@link #getStore(int) }
     * using the priority from the {@link ConfigLevel#getPriority() }.
     *
     * @param level the terpene runtime tier
     * @return returns the ConfigStore associated with that runtime tier or null
     * if no store has been configured for that tier
     */
    ConfigStore getStore(ConfigLevel level);

    /**
     * Retrieves a source (non-derived) store for the specific load priority.
     *
     * This method is generally considered more "low-level" and most
     * applications will likely be more interested in calling either 
     * {@link #getStore() }, {@link #getStore(ConfigLevel) }, or simply 
     * {@link #getProperty(String) }.
     *
     * @param priority
     * @return the ConfigStore associated with the provided priority or null if
     * there isn't a store for that priority.
     */
    ConfigStore getStore(int priority);

    /**
     * Programmatically register a new source {@link ConfigStore} which the
     * service will use to derive the configuration properties.
     *
     * @param priority priority level of the store
     * @param store the store to register with the configuration service
     */
    void registerStore(int priority, ConfigStore store);

    /**
     * Visits all the <i>derived</i> configuration properties managed by the
     * service.
     *
     * @param visitor
     */
    void visit(HierarchicalVisitor visitor);
}
