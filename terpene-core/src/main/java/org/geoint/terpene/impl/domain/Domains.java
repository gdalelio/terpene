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
package org.geoint.terpene.impl.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.logging.Logger;
import org.geoint.terpene.spi.domain.DomainProvider;
import org.geoint.terpene.domain.NotDomainClassException;
import org.geoint.terpene.domain.UnknownComponentException;
import org.geoint.terpene.domain.Version;
import org.geoint.terpene.domain.model.DomainModel;
import org.geoint.terpene.domain.model.InvalidDomainException;

/**
 * Programmatic retrieve of application domain models.
 * <p>
 * {@link DomainModel Domain models} are loaded from {@link DomainProvider}
 * instances, discovered from a {@link ServiceLoader}.
 * <p>
 * Domains does not provide runtime reloading of domain model components,
 * preventing application code from loading code from untrusted classpaths.
 *
 * @see DomainProvider
 * @see DomainModel
 * @author steve_siebert
 */
public enum Domains {

    INSTANCE;

    private static final Set<DomainModel> domains;
    private static final Logger LOGGER = Logger.getLogger(Domains.class.getName());

    static {
        //load domains from current classpath using ServiceLoader
        final ServiceLoader<DomainProvider> providers
                = ServiceLoader.load(DomainProvider.class);

        domains = new HashSet<>();

        //TODO load and cache domain models
    }

    /**
     * All domain models known to this instance.
     *
     * @return non-backed collection of all known application domain models
     */
    public Collection<DomainModel> getModels() {
        return new ArrayList(domains); //defensive copy
    }

    /**
     * Retrieve a specific domain model, if available.
     *
     * @param domainName domain name
     * @param domainVersion domain version
     * @return domain model, if known
     */
    public Optional<DomainModel> findModel(String domainName,
            Version domainVersion) {
        return domains.stream().filter((dm)
                -> dm.getName().contentEquals(domainName)
                && dm.getVersion().isWithin(domainVersion))
                .findFirst();
    }

    /**
     * Return the domain model for the provided class.
     *
     * @param domainClass java class that represents a domain component
     * @return associated domain model, if discoverable, or null
     * @throws UnknownComponentException thrown if the class defines a domain
     * but the domain model could not be found
     * @throws NotDomainClassException thrown if a domain could not be
     * identified for the class
     * @throws InvalidDomainException thrown if the domain defined by the class
     * is invalid
     */
    public DomainModel getModel(Class<?> domainClass)
            throws UnknownComponentException, NotDomainClassException,
            InvalidDomainException {

        final DomainIdentity id = DomainReflection.identify(domainClass)
                .orElseThrow(() -> new NotDomainClassException(domainClass));

        return domains.stream()
                .filter((dm) -> dm.getName().contentEquals(id.getDomainName()))
                .filter((dm) -> dm.getVersion().equals(id.getVersion()))
                .findAny()
                .orElseThrow(()
                        -> new UnknownComponentException(id.getDomainName(),
                                id.getVersion().asString(),
                                domainClass.getCanonicalName(),
                                "Unknown domain model requested."));
    }

}
