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

import org.geoint.terpene.util.GUID;

/**
 * An Entity instance is a stateful object instance within a domain.
 *
 * @author steve_siebert
 */
public interface Entity<T> {

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
     * Every stateful change of an Entity instance changes the entity version.
     *
     * @return entity instance version
     */
    GUID getVersion();

    /**
     * Metamodel of the domain entity.
     *
     * @return metamodel
     */
    EntityModel getModel();

    /**
     * Object representation of the entity instance.
     *
     * @return entity instance
     */
    T getInstance();

}
