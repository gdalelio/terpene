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

import org.geoint.terpene.domain.model.EntityModel;
import java.util.Optional;
import org.geoint.terpene.domain.model.HandlerModel;
import org.geoint.terpene.domain.model.OperationModel;
import org.geoint.terpene.util.GUID;

/**
 * Instance of a domain entity.
 *
 * @author steve_siebert
 * @param <T> java type representing the domain entity data
 */
public interface DomainEntity<T> extends DomainObject<T> {

    /**
     * Framework-managed identifier of the entity instance within the domain.
     * <p>
     * The identifier of a domain entity instance never changes throughout its
     * life and may not be reused if the entity is removed from the domain.
     *
     * @return globally unique entity instance identifier
     */
    GUID getId();

    /**
     * Framework-managed unique entity version identifier.
     * <p>
     * Every stateful change of an DomainEntity instance changes the entity
     * version.
     *
     * @return entity instance version
     */
    GUID getVersion();

    /**
     * Metamodel of the domain entity.
     *
     * @return metamodel
     */
    @Override
    EntityModel<T> getModel();

    /**
     * Returns the requested operation if defined by the entity.
     *
     * @param operationName name of the operation
     * @return operation, if declared by the entity model
     */
    Optional<DomainOperation> findOperation(String operationName);

    /**
     * Returns the requested operation.
     *
     * @param model operation model
     * @return operation
     * @throws UnknownComponentException thrown if the operation is not defined
     * by the entity
     */
    DomainOperation getOperation(OperationModel model)
            throws UnknownComponentException;

    /**
     * Returns the requested handler.
     *
     * @param handlerName handler name
     * @return handler, if declared by the entity model
     */
    Optional<EventHandler> findHandler(String handlerName);

    /**
     * Returns the requested handler.
     *
     * @param model event handler
     * @return handler
     * @throws UnknownComponentException thrown if the entity does not define
     * the handler
     */
    EventHandler getHandler(HandlerModel model)
            throws UnknownComponentException;

    /**
     * Returns the requested aggregate.
     *
     * @param <A> java class representation of the domain aggregate (entity)
     * @param name aggregate name
     * @param domainType java class of the domain aggregate (entity)
     * @return aggregate, if set
     */
    <A> Optional<DomainEntity<A>> findAggregate(String name, Class<A> domainType);

    /**
     * Return the requested aggregate.
     *
     * @param <A> java class representation of the domain aggregate (entity)
     * @param model model of the aggregate
     * @return aggregate metadata or null if not set
     * @throws UnknownComponentException thrown if the entity does not define an
     * aggregate of this type
     */
    <A> DomainEntity<A> getAggregate(EntityModel<A> model)
            throws UnknownComponentException;

}
