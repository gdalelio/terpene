package org.geoint.terpene.component;

import java.util.Set;
import org.geoint.acetate.domain.annotation.Accessor;
import org.geoint.acetate.domain.entity.Entity;
import org.geoint.terpene.TerpeneLocality;
import org.geoint.terpene.component.code.TerpeneComponentBundle;
import org.geoint.terpene.component.container.ComponentContainer;
import org.geoint.terpene.service.ServiceContract;

/**
 * Entity to manage a terpene executable which runs on a supporting
 * {@link ComponentContainer}.
 * <p>
 * A component is defined (and discovered) by {@link ComponentContainer container} 
 * implementations.  Components do not implement the TerpeneComponent interface, 
 * but rather implement the contract defined by the container.  Containers 
 * provide TerpeneComponent implementations to communicate with
 * 
 * @param <C> component type
 */
@Entity(name = "component")
public interface TerpeneComponent<C> {

    public static final String DEFAULT_VERSION = "1.0-BETA";

    @Accessor
    TerpeneComponentBundle getBundle();
    
    @Accessor
    String getName();

    @Accessor
    String getDescription();

    @Accessor
    String getVersion();

    @Accessor
    Set<ServiceContract> getServiceDependencies();
    
    @Accessor
    ComponentStatus getStatus();

    @Accessor
    TerpeneLocality getLocality();

    @Accessor
    boolean isOfflineCapable();

    Class<C> getComponentType();

}
