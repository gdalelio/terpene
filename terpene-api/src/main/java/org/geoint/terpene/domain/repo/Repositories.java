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

import java.util.function.Predicate;
import org.geoint.version.Version;

/**
 * Programmatic retrieval of available entity repositories.
 * <p>
 * {@link EntityRepository Repositories} are loaded from
 * {@link RepositoryProvider} instances discovered using a
 * {@link ServiceLoader}.
 *
 * @see RepositoryProvider
 * @see EntityRepository
 * @author steve_siebert
 */
public final class Repositories {

    /**
     * Retrieve an entity repository that queries all relevant repositories,
     * returning the latest version of the entity from the repositories.
     *
     * @param domainName
     * @param domainVersion
     * @param entityName
     * @return proxy of all known relevant repositories
     */
    public static EntityRepository<?> all(String domainName,
            Version domainVersion, String entityName) {

    }

    /**
     * Retrieve an entity repository that queries all relevant repositories,
     * returning the latest version of the entity from the repositories.
     *
     * @param <T> java representation of a domain entity
     * @param entityClass domain entity class
     * @return proxy of all known relevant repositories
     */
    public static <T> EntityRepository<T> all(Class<T> entityClass) {

    }

    /**
     * Retrieve an entity repository for this entity.
     *
     * @param domainName
     * @param domainVersion
     * @param entityName
     * @return
     */
    public static EntityRepository<?> any(String domainName,
            Version domainVersion, String entityName) {

    }

    /**
     * Retrieve an entity repository for this entity.
     *
     * @param <T>
     * @param entityClass
     * @return
     */
    public static <T> EntityRepository<T> any(Class<T> entityClass) {

    }

    /**
     * Filter the available entity repositories returning a specific repository
     * implementation.
     *
     * @param domainName
     * @param domainVersion
     * @param entityName
     * @param filter
     * @return
     */
    public static EntityRepository select(String domainName,
            Version domainVersion, String entityName,
            Predicate<EntityRepository> filter) {

    }

    /**
     * Filter the available entity repositories returning a specific repository
     * implementation.
     *
     * @param <T>
     * @param entityClass
     * @param filter
     * @return
     */
    public static <T> EntityRepository<T> select(Class<T> entityClass,
            Predicate<EntityRepository<T>> filter) {

    }
}
