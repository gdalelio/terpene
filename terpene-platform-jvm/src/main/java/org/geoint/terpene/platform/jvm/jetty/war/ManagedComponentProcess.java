package org.geoint.terpene.platform.jvm.jetty.war;

import org.geoint.terpene.TerpeneLocality;
import org.geoint.terpene.component.ComponentStatus;
import org.geoint.terpene.component.TerpeneComponent;
import org.geoint.terpene.component.TerpeneComponentException;
import org.geoint.terpene.component.container.ExecutionContext;
import org.geoint.terpene.component.container.ManagedTerpeneComponent;
import org.geoint.terpene.component.event.ComponentHeartbeat;

/**
 *
 * @author steve_siebert
 */
public class ManagedComponentProcess<C> implements ManagedTerpeneComponent<C>{

    private final TerpeneComponent<C> component;
    private final ExecutionContext context;

    public ManagedComponentProcess(TerpeneComponent<C> component, 
            ExecutionContext context) {
        this.component = component;
        this.context = context;
    }
    
    /**
     * Interprocess communication port.
     * 
     * @return 
     */
    public int getPort() {
        
    }
    
    @Override
    public String getName() {

    }

    @Override
    public String getDescription() {

    }

    @Override
    public String getVersion() {

    }

    @Override
    public ComponentStatus getStatus() {

    }

    @Override
    public TerpeneLocality getLocality() {

    }

    @Override
    public boolean isOfflineCapable() {

    }

    @Override
    public void start() throws TerpeneComponentException {

    }

    @Override
    public boolean canPause() {

    }

    @Override
    public void pause() throws TerpeneComponentException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void resume() throws TerpeneComponentException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void stop() throws TerpeneComponentException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ComponentHeartbeat heartbeat() throws TerpeneComponentException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Class getComponentType() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
