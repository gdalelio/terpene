/*
 * Copyright 2016 geoint.org.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.geoint.terpene.domain;

import org.geoint.terpene.domain.Version;

/**
 * Thrown if a {@link EventHandler} does not support the event type submitted to
 * its {@link EventHandler#handle(org.geoint.terpene.domain.Event) handle}
 * method.
 *
 * @author steve_siebert
 */
public class EventNotSupportedException extends DomainException {

    private final String handlerDomain;
    private final Version handlerVersion;
    private final String handlerName;
    private final String eventDomain;
    private final Version eventVersion;
    private final String eventName;

    public EventNotSupportedException(String handlerDomain,
            Version handlerVersion, String handlerName, String eventDomain,
            Version eventVersion, String eventName) {
        this.handlerDomain = handlerDomain;
        this.handlerVersion = handlerVersion;
        this.handlerName = handlerName;
        this.eventDomain = eventDomain;
        this.eventVersion = eventVersion;
        this.eventName = eventName;
    }

    public String getHandlerDomain() {
        return handlerDomain;
    }

    public Version getHandlerVersion() {
        return handlerVersion;
    }

    public String getHandlerName() {
        return handlerName;
    }

    public String getEventDomain() {
        return eventDomain;
    }

    public Version getEventVersion() {
        return eventVersion;
    }

    public String getEventName() {
        return eventName;
    }

}
