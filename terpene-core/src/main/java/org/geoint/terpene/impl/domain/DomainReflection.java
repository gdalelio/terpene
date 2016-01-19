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

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.geoint.terpene.domain.Domain;
import org.geoint.terpene.domain.Entity;
import org.geoint.terpene.domain.Event;
import org.geoint.terpene.domain.Value;
import org.geoint.terpene.domain.model.InvalidDomainException;

/**
 * Reflection utility methods to derive domain metamodel information.
 *
 * @author steve_siebert
 */
public final class DomainReflection {

    private static final Logger LOGGER
            = Logger.getLogger(DomainReflection.class.getName());

    /**
     * Determine the domain identity of the provided class by reflecting on the
     * class/package and interpreting the appropriate annotations.
     *
     * @see Domain
     * @see Value
     * @see Entity
     * @see Event
     * @param domainClass type to introspect
     * @return domain identity details or null if domain is not identified by
     * annotations
     * @throws InvalidDomainException if the domain annotation defines an
     * invalid domain model
     */
    public static Optional<DomainIdentity> identify(Class<?> domainClass)
            throws InvalidDomainException {
        Domain domain = domainClass.getAnnotation(Domain.class);

        if (domain == null) {
            //not declared on class, check package
            domain = domainClass.getPackage().getAnnotation(Domain.class);
        }

        if (domain == null) {
            LOGGER.log(Level.FINER, "Domain identification was not found "
                    + "for class '%s'", domainClass.getCanonicalName());
            return Optional.empty();
        }

        return Optional.of(new DomainIdentity(domain));
    }

    /**
     * Determine the domain identity of the provided method by reflecting on the
     * method/class/package and interpreting the appropriate annotations.
     *
     * @param domainMethod method to introspect
     * @return domain identity details if a domain-defined behavior or null
     * @throws InvalidDomainException thrown if the domain annotations define an
     * invalid domain model
     */
    public static Optional<DomainIdentity> identify(Method domainMethod)
            throws InvalidDomainException {
        return identify(domainMethod.getDeclaringClass());
    }
}
