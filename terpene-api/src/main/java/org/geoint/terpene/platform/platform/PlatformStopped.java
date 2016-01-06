package org.geoint.terpene.platform.platform;

import java.time.ZonedDateTime;
import org.geoint.acetate.domain.event.Event;

/**
 *
 */
@Event(name="platformStopped")
public class PlatformStopped {

    private final TerpenePlatform platform;
    private final ZonedDateTime stoppedTime;

    public PlatformStopped(TerpenePlatform platform, ZonedDateTime stoppedTime) {
        this.platform = platform;
        this.stoppedTime = stoppedTime;
    }

    public TerpenePlatform getPlatform() {
        return platform;
    }

    public ZonedDateTime getStoppedTime() {
        return stoppedTime;
    }

}
