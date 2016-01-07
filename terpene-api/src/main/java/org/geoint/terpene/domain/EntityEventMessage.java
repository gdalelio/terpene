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

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import org.geoint.canon.event.EventMessage;
import org.geoint.canon.event.EventPayload;
import org.geoint.terpene.util.GUID;

/**
 * Domain event related to an Entity.
 *
 * @author steve_siebert
 */
public class EntityEventMessage implements EventMessage {

    private final String domainName;
    private final String entityType;
    private final GUID entityId;
    private final GUID entityVersion;
    private final String channelName;
    private final String streamName;
    private final String authorizerId;
    private final String[] triggerIds;
    private final String eventType;
    private final Map<String, String> headers;
    private final EventPayload payload;

    public EntityEventMessage(String domainName, String entityType,
            GUID entityId, GUID entityVersion, String channelName,
            String streamName, String authorizerId, String[] triggerIds,
            String eventType, Map<String, String> headers, EventPayload payload) {
        this.domainName = domainName;
        this.entityType = entityType;
        this.entityId = entityId;
        this.entityVersion = entityVersion;
        this.channelName = channelName;
        this.streamName = streamName;
        this.authorizerId = authorizerId;
        this.triggerIds = triggerIds;
        this.eventType = eventType;
        this.headers = headers;
        this.payload = payload;
    }

    public EntityEventMessage(String domainName, String entityType,
            GUID entityId, GUID entityVersion, EventMessage event) {
        this.domainName = domainName;
        this.entityType = entityType;
        this.entityId = entityId;
        this.entityVersion = entityVersion;
        this.channelName = event.getChannelName();
        this.streamName = event.getStreamName();
        this.authorizerId = event.getAuthorizerId();
        this.triggerIds = event.getTriggerIds();
        this.eventType = event.getEventType();
        this.headers = event.getHeaders();
        this.payload = event.getPayload();
    }

    public String getDomainName() {
        return domainName;
    }

    public String getEntityType() {
        return entityType;
    }

    public GUID getEntityId() {
        return entityId;
    }

    public GUID getEntityVersion() {
        return entityVersion;
    }

    @Override
    public String getChannelName() {
        return channelName;
    }

    @Override
    public String getStreamName() {
        return streamName;
    }

    @Override
    public String getAuthorizerId() {
        return authorizerId;
    }

    @Override
    public String[] getTriggerIds() {
        return triggerIds;
    }

    @Override
    public String getEventType() {
        return eventType;
    }

    @Override
    public Map<String, String> getHeaders() {
        return headers;
    }

    @Override
    public Optional<String> findHeader(String headerName) {
        return Optional.ofNullable(headers.get(headerName));
    }

    @Override
    public String getHeader(String headerName, Supplier<String> defaultValue) {
        return findHeader(headerName).orElse(defaultValue.get());
    }

    @Override
    public EventPayload getPayload() {
        return payload;
    }

}
