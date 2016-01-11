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

/**
 * Metamodel of an Entity within a domain.
 *
 * @author steve_siebert
 * @param <T> java class used to represent this domain entity
 */
public interface EntityModel<T> extends ObjectModel<T> {

    /**
     * Model(s) of aggregate (linked) entities.
     *
     * @return aggregate models
     */
    Collection<EntityModel> getAggegates();

    /**
     * Model(s) of any event handlers that are called for an event associated
     * with an instance of this entity.
     *
     * @return event handlers defined by this domain object
     */
    Collection<HandlerModel> getHandlers();

    /**
     * Model(s) of entity operations.
     *
     * @return entity operations
     */
    Collection<OperationModel> getOperations();

}
