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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.geoint.terpene.domain.DomainEntity;
import org.geoint.terpene.domain.model.DataModel;
import org.geoint.terpene.util.GUID;
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

    private static final ServiceLoader<RepositoryProvider> providers
            = ServiceLoader.load(RepositoryProvider.class);

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
        MultiRepository multi = new MultiRepository();
        providers.forEach((p) -> {
            p.stream(domainName, domainVersion, entityName)
                    .forEach(multi::addRepo);
        });
        return multi;
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

    /**
     * Queries all of the contained repositories, returning the latest version
     * of the entity returned from all of the repositories.
     */
    private static class MultiRepository<T> implements EntityRepository<T> {

        private final List<EntityRepository<T>> repos;

        public MultiRepository() {
            repos = new ArrayList<>();
        }

        protected void addRepo(EntityRepository<T> repo) {
            this.repos.add(repo);
        }

        public MultiRepository(Iterator<EntityRepository> repos,
                Predicate<EntityRepository> filter) {
            this.repos = new ArrayList<>();
            repos.forEachRemaining((r) -> {
                if (filter.test(r)) {
                    this.repos.add(r);
                }
            });
        }

        @Override
        public Optional<DomainEntity<T>> find(GUID id) {
            DomainEntity<T> entity = null;
            for (EntityRepository<T> r : this.repos) {
                Optional<DomainEntity<T>> e = r.find(id);
                if (entity != null
                        && e.isPresent()
                        && entity.getVersion().isLessThan(e.get().getVersion())) {
                    entity = e.get();
                }
            }
            return Optional.ofNullable(entity);
        }

        @Override
        public EntityQuery<T> query() {
            return new MultiEntityQuery(this.repos);
        }

    }

    /**
     * Queries multiple repositories, returning the latest version of the entity
     * found.
     *
     * @param <T> entity type
     */
    private static class MultiEntityQuery<T> implements EntityQuery<T> {

        private final List<EntityQuery<T>> queries;

        public MultiEntityQuery(List<EntityRepository<T>> repos) {
            this.queries = repos.stream()
                    .map(EntityRepository::query)
                    .collect(Collectors.toList());
        }

        @Override
        public EntityQuery<T> equalTo(String componentName, Object value) {
            queries.forEach((q) -> q.equalTo(componentName, value));
            return this;
        }

        @Override
        public <V> EntityQuery<T> equalTo(DataModel<V> model, V value) {
            queries.forEach((q) -> q.equalTo(model, value));
            return this;
        }

        @Override
        public EntityQuery<T> greaterThan(String componentName, Object value) {
            queries.forEach((q) -> q.greaterThan(componentName, value));
            return this;
        }

        @Override
        public <V> EntityQuery<T> greaterThan(DataModel<V> model, V value) {
            queries.forEach((q) -> q.greaterThan(model, value));
            return this;
        }

        @Override
        public EntityQuery<T> greaterThanOrEqual(String componentName, Object value) {
            queries.forEach((q) -> q.greaterThanOrEqual(componentName, value));
            return this;
        }

        @Override
        public <V> EntityQuery<T> greaterThanOrEqual(DataModel<V> model, V value) {
            queries.forEach((q) -> q.greaterThanOrEqual(model, value));
            return this;
        }

        @Override
        public EntityQuery<T> lessThan(String componentName, Object value) {
            queries.forEach((q) -> q.lessThan(componentName, value));
            return this;
        }

        @Override
        public <V> EntityQuery<T> lessThan(DataModel<V> model, V value) {
            queries.forEach((q) -> q.lessThan(model, value));
            return this;
        }

        @Override
        public EntityQuery<T> lessThanOrEqual(String componentName, Object value) {
            queries.forEach((q) -> q.lessThanOrEqual(componentName, value));
            return this;
        }

        @Override
        public <V> EntityQuery<T> lessThanOrEqual(DataModel<V> model, V value) {
            queries.forEach((q) -> q.lessThanOrEqual(model, value));
            return this;
        }

        @Override
        public EntityQuery<T> matches(String componentName, Pattern pattern) {
            queries.forEach((q) -> q.matches(componentName, pattern));
            return this;
        }

        @Override
        public <V> EntityQuery<T> matches(DataModel<V> model, Pattern pattern) {
            queries.forEach((q) -> q.matches(model, pattern));
            return this;
        }

        @Override
        public EntityQuery<T> sort(Comparator<DomainEntity<T>> sort) {
            queries.forEach((q) -> q.sort(sort));
            return this;
        }

        @Override
        public EntityQuery<T> filter(Predicate<DomainEntity<T>> filter) {
            queries.forEach((q) -> q.filter(filter));
            return this;
        }

        @Override
        public void forEach(Consumer<DomainEntity<T>> consumer) {
            //we need to pre-process the results from all the repositories first, 
            //ensuring we're only returning the latest version of each entity
            Collection<DomainEntity<T>> results = get();

            //we have the latest entity version(s), execute the callback
            results.forEach(consumer);
        }

        @Override
        public Optional<DomainEntity<T>> first() {
            DomainEntity<T> result = null;
            for (EntityQuery<T> q : queries) {
                Optional<DomainEntity<T>> e = q.first();
                if (e.isPresent()) {
                    if (result == null) {
                        result = e.get();
                    } else if (result.getVersion().isLessThan(e.get().getVersion())) {
                        result = e.get();
                    }
                }
            }
            return Optional.ofNullable(result);
        }

        @Override
        public Collection<DomainEntity<T>> get() {
            final Map<GUID, DomainEntity<T>> results = new HashMap<>(); //key is entity id

            queries.forEach((q) -> q.forEach((e) -> {
                GUID id = e.getId();
                //results contain only the newest version of the entity found
                if (!results.containsKey(id)
                        || results.get(id).getVersion().isLessThan(e.getVersion())) {
                    results.put(id, e);
                }
            }));
            return results.values();
        }

    }
}
