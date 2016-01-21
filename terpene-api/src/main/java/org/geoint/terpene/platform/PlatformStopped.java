package org.geoint.terpene.platform;

import java.time.ZonedDateTime;
import org.geoint.terpene.domain.Event;

/**
 *
 */
@Event(name = "platformStopped",
        desc = "Indicates a terpene platform has stopped.")
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
