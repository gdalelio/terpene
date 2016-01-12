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

import java.util.Collection;
import java.util.Optional;
import java.util.ServiceLoader;
import org.geoint.terpene.domain.model.DomainModel;
import org.geoint.version.Version;

/**
 * Programmatic retrieve of application domain models.
 * <p>
 * {@link DomainModel Domain models} are loaded from {@link DomainProvider}
 * instances, discovered from a {@link ServiceLoader}.
 *
 * @see DomainProvider
 * @see DomainModel
 * @author steve_siebert
 */
public final class Domains {

    public static final ServiceLoader<DomainProvider> providers
            = ServiceLoader.load(DomainProvider.class);

    /**
     * Return all known domain model instances.
     *
     * @return known application domain models
     */
    public static Collection<DomainModel> getModels() {
        
    }

    /**
     * Retrieve a specific domain model, if available.
     *
     * @param domainName domain name
     * @param domainVersion domain version
     * @return domain model, if known
     */
    public static Optional<DomainModel> findModel(String domainName,
            Version domainVersion) {
        
    }
}
