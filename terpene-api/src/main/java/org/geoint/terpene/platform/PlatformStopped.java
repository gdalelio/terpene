package org.geoint.terpene.platform;

import java.time.ZonedDateTime;
import org.geoint.terpene.domain.Event;

/**
 *
 */
@Event(domain = "org.geoint.terpene",
        version = "1.0")
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
