package org.geoint.terpene.platform.platform;

import java.time.ZonedDateTime;
import org.geoint.acetate.domain.event.Event;

/**
 *
 */
@Event(name="platformStarted")
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
