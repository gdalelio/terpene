package org.geoint.terpene.platform;

import java.time.ZonedDateTime;
import org.geoint.terpene.domain.DomainEvent;

/**
 *
 */
@DomainEvent(domain = "org.geoint.terpene",
        version = "1.0")
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
