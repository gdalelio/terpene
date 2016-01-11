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

import java.util.Optional;
import org.geoint.terpene.domain.model.EventModel;
import org.geoint.terpene.util.GUID;

/**
 * A domain event instance.
 *
 * @author steve_siebert
 * @param <T> java class representing the domain event
 */
public interface DomainEvent<T> extends DomainObject<T> {

    /**
     * Model of the domain event.
     *
     * @return domain event model
     */
    @Override
    EventModel<T> getModel();

    /**
     * Unique identifier of the event instance.
     *
     * @return domain event unique id
     */
    GUID getId();

    /**
     * Domain entity instance which this event was associated.
     * <p>
     * Note that this method is guaranteed to return the domain entity instance
     * metadata, but a call to (@link DomainEntity#getInstance() the instance
     * data} may return null if the entity instance at the specified version
     * could not be resolved.
     *
     * @return associated entity metadata
     */
    DomainEntity<?> getAssociatedEntity();

    /**
     * Metadata about the event that triggered this event.
     * <p>
     * Note that this method is guaranteed to return the domain event metadata,
     * but a call to the {@link DomainEvent#getInstance() the instance data} may
     * return null if the event instance data could not be resolved.
     *
     * @return triggering event information or null if there was no triggering
     * event
     */
    Optional<DomainEvent> getTriggeringEvent();

}
