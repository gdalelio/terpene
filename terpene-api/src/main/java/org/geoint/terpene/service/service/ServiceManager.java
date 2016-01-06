package org.geoint.terpene.service.service;

import org.geoint.terpene.service.service.TerpeneService;
import java.util.Collection;

/**
 *
 */
@Service(name = "Terpene Service Manager",
        description = "Terpene Service management.")
public interface ServiceManager {

    /**
     * List all terpene services.
     *
     * @return
     */
    Collection<TerpeneService> listServices();

    Collection<TerpeneService> listAvailableServices();

    /**
     * List all terpene services which implement the specified service
     * interface.
     *
     * @param <S>
     * @param <C>
     * @param serviceType
     * @return
     */
    <S, C extends S> Collection<TerpeneService<S, C>> listServices(Class<S> serviceType);

    /**
     * Check if this terpene platform is currently online or offline.
     *
     * @return true if the platform is currently offline to its cluster, or if
     * not a member of a cluster, otherwise false
     */
    boolean isOffline();

    /**
     * Check if this terpene platform is capable of offline operations.
     *
     * @return true if the platform is capable of offline operations.
     */
    boolean isOfflineCapable();

}
