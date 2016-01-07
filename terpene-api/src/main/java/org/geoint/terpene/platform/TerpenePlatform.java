package org.geoint.terpene.platform;

import java.util.Collection;
import org.geoint.acetate.domain.entity.Entity;
import org.geoint.terpene.TerpeneException;
import org.geoint.terpene.component.ComponentDeploymentException;
import org.geoint.terpene.component.TerpeneComponent;
import org.geoint.terpene.component.container.ManagedTerpeneComponent;
import org.geoint.terpene.component.event.ComponentDeployed;
import org.geoint.terpene.service.ServiceManager;
import org.geoint.terpene.service.TerpeneService;

/**
 * A TerpenePlatform provides environmental (local/remote, IaaS, PaaS, hybrid,
 * etc) abstraction by executing software components in easy-to-implement
 * containers. Software components are loosely coupled and make use of common
 * services defined through software contracts. The platform handles many of the
 * non-functional and cross-cutting concerns for secure distributed computing,
 * allowing software components and services to focus on business requirements.
 *
 */
@Entity
public abstract class TerpenePlatform {

    private final String platformId;
    private final ServiceManager services;
    private final ComponentManager components;
    private final InterfaceManager interfaces;
    
    public abstract void start () throws TerpeneException;
    
    public abstract void stop() throws TerpeneException;
    
    public <C> ComponentDeployed<C> deploy (TerpeneComponent<C> component) 
            throws ComponentDeploymentException {
        
    }
    
    public Collection<ManagedTerpeneComponent> getManagedComponents() {
        
    }
    
    /**
     * Return all services currently available to this platform.
     * 
     * @return services currently available to this platform
     */
    public Collection<TerpeneService> getAvailableServices() {
        
    }
    

}
