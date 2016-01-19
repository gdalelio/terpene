package org.geoint.terpene.platform;

import java.time.ZonedDateTime;
import org.geoint.terpene.domain.Event;

/**
 *
 */
@Event(name = "platformStarted",
        desc = "Indicates a terpene platform has started.")
public class PlatformStarted {

    private final TerpenePlatform platform;
    private final ZonedDateTime startTime;

    public PlatformStarted(TerpenePlatform platform, ZonedDateTime startTime) {
        this.platform = platform;
        this.startTime = startTime;
    }

    public TerpenePlatform getPlatform() {
        return platform;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

}
