package org.geoint.terpene.component.event;

import org.geoint.acetate.domain.event.Event;
import org.geoint.terpene.component.ComponentStatus;
import org.geoint.terpene.component.TerpeneComponent;
import org.geoint.terpene.platform.TerpenePlatform;

/**
 *
 */
@Event(name="heatbeat")
public final class ComponentHeartbeat {

    private final TerpenePlatform platform;
    private final TerpeneComponent component;
    private final ComponentStatus status;

    public ComponentHeartbeat(TerpenePlatform platform, TerpeneComponent component,
            ComponentStatus status) {
        this.platform = platform;
        this.component = component;
        this.status = status;
    }

    public TerpenePlatform getPlatform() {
        return platform;
    }

    public TerpeneComponent getComponent() {
        return component;
    }

    public ComponentStatus getStatus() {
        return status;
    }

}
