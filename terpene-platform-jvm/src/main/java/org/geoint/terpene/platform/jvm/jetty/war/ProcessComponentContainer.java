package org.geoint.terpene.platform.jvm.jetty.war;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.geoint.terpene.component.TerpeneComponent;
import org.geoint.terpene.component.TerpeneComponentException;
import org.geoint.terpene.component.container.ComponentContainer;
import org.geoint.terpene.component.container.ContainerContext;
import org.geoint.terpene.component.container.ExecutionContext;
import org.geoint.terpene.component.container.ManagedTerpeneComponent;
import org.geoint.terpene.component.container.UnsupportedComponentException;

/**
 * Creates a new JVM process for each component deployed to the container.
 *
 * @author steve_siebert
 */
public abstract class ProcessComponentContainer implements ComponentContainer {

    private final Map<Integer, ManagedComponentProcess> processes = new HashMap<>();
    private ContainerContext context;

    private static final Logger logger
            = Logger.getLogger(ProcessComponentContainer.class.getName());

    @Override
    public void start(ContainerContext context) {

        //TODO verify containers current state
        logger.log(Level.INFO, String.format("Starting container %s",
                this.getClass().getName()));
        this.context = context;
    }

    @Override
    public void stop() {
        //TODO verify containers current state
        logger.log(Level.INFO, String.format("Stopping container %s",
                this.getClass().getName()));

        processes.values().stream().forEach((p) -> {
            try {
                p.stop();
            } catch (TerpeneComponentException ex) {
                logger.log(Level.WARNING, String.format("Component '%s' did not "
                        + "shutdown gracefully.", p.toString()), ex);
            }
            logger.log(Level.INFO, String.format("Successfully shutdown component '%s'",
                    p.toString()));
        });
        logger.log(Level.INFO, String.format("Container %s stopped successfully",
                this.getClass().getName()));
    }

    @Override
    public ManagedTerpeneComponent deploy(TerpeneComponent<?> component,
            ExecutionContext context) throws Exception {
        if (!canDeploy(component)) {
            throw new UnsupportedComponentException(component);
        }

        ManagedComponentProcess p
                = new ManagedComponentProcess(component, context);
        processes.put(p.getPort(), p);
        return p;
    }

    @Override
    public ManagedTerpeneComponent[] getManagedComponents() {
        return processes.entrySet().stream()
                .map(Entry::getValue)
                .toArray((i) -> new ManagedTerpeneComponent[i]);
    }

    private File newComponentDir(JettyWarComponent warComponent) {

    }

    private Integer newPort() {

    }
}
