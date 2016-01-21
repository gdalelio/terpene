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

import org.geoint.terpene.domain.model.DataModel;
import org.geoint.terpene.domain.model.ObjectModel;

/**
 * Domain object instance.
 *
 * @author steve_siebert
 * @param <T> java type representing this domain object
 */
public interface DomainObject<T> extends DomainData<T> {

    /**
     * Return the model of this domain object.
     *
     * @return model of domain object
     */
    ObjectModel<T> getModel();

    /**
     * Composite data instance.
     *
     * @param <C> java type of the domain data
     * @param compositeModel model of the composite
     * @return instance of the requested domain data or null composite of this
     * type is not defined by the model
     * @throws UnknownComponentException thrown if the domain model does not 
     * define a composite of this model within this object
     */
    <C> DomainData<C> getComposite(DataModel<C> compositeModel)
            throws UnknownComponentException;

    /**
     * Composite data instance.
     *
     * @param <C> java type of the domain data
     * @param compositeName name of the composite data
     * @param compositeType java type representing the composite type
     * @return instance of the requested domain data if found in the model
     */
    <C> DomainData<C> findComposite(String compositeName, Class<C> compositeType);

}
