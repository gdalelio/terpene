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
package org.geoint.terpene.domain.model;

import java.util.Collection;
import org.geoint.terpene.domain.VersionRange;

/**
 * The model of an application domain.
 *
 * @author steve_siebert
 */
public interface DomainModel {

    /**
     * Unique domain name.
     *
     * @return domain name
     */
    String getName();

    /**
     * Version range supported by this domain model.
     *
     * @return domain version
     */
    VersionRange getVersion();

    /**
     * Models of the entities defined by the domain.
     *
     * @return domain entity models
     */
    Collection<EntityModel> getEntities();

    /**
     * Models of the event handlers defined by the domain.
     *
     * @return domain event handlers
     */
    Collection<HandlerModel> getHandlers();

    /**
     * Models of the operations defined by the domain.
     *
     * @return domain operations
     */
    Collection<OperationModel> getOperations();

    /**
     * Models of the objects, other than entity and events, defined by the
     * domain.
     *
     * @return non-entity objects
     */
    Collection<ObjectModel> getObjects();

    /**
     * Models of the events defined by the domain.
     *
     * @return domain events
     */
    Collection<EventModel> getEvents();

    /**
     * All domain model components defined by the domain.
     *
     * @return all domain defined components
     */
    Collection<DomainComponentModel> getComponents();

}
