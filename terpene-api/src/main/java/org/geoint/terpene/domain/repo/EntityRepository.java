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
package org.geoint.terpene.domain.repo;

import java.util.Optional;
import javax.lang.model.UnknownEntityException;
import org.geoint.terpene.domain.DomainEntity;
import org.geoint.terpene.domain.UnknownEntityExcpetion;
import org.geoint.terpene.util.GUID;

/**
 * Provides storage and retrieval of current entity instances within a domain.
 *
 * @author steve_siebert
 * @param <T>
 */
public interface EntityRepository<T> {

    /**
     * Retrieve the latest version of a specific entity instance.
     *
     * @param id entity instance identifier
     * @return entity instance, if found
     */
    Optional<DomainEntity<T>> find(GUID id);

    EntityQuery<T> query ();
}
