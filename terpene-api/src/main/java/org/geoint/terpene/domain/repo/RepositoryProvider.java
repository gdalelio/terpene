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

import java.util.stream.Stream;

/**
 * Provides EntityRepository instances.
 * <p>
 * Instances of RepositoryProvider must be discoverable through the
 * {@link ServiceLoader}.
 *
 * @author steve_siebert
 */
public interface RepositoryProvider {

    /**
     * A stream of repositories available from the provider instance.
     *
     * @return entity repositories available to the provider
     */
    Stream<EntityRepository> stream();

}
