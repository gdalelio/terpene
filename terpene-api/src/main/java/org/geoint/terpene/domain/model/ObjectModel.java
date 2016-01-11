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
 * Model of a domain object.
 *
 * @author steve_siebert
 * @param <T> java type that represents this domain object
 */
public interface ObjectModel<T> extends DataModel<T> {

    /**
     * Model(s) of the domain objects that compose this object.
     *
     * @return composite models
     */
    Collection<DataModel> getComposites();

}
