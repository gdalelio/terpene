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

import java.util.Objects;
import org.geoint.terpene.domain.Domain;
import org.geoint.terpene.domain.VersionRange;
import org.geoint.terpene.domain.model.InvalidDomainException;

/**
 * Domain identification details.
 *
 * @author steve_siebert
 */
public final class DomainIdentity {

    private final String domainName;
    private final VersionRange version;

    public DomainIdentity(String domainName, VersionRange version)
            throws InvalidDomainException {
        if (domainName == null) {
            throw new InvalidDomainException("Invalid domain identity, domain "
                    + "name is required.");
        }
        if (version == null) {
            throw new InvalidDomainException("Invalid domain identitiy, domain "
                    + "version is required.");
        }
        
        this.domainName = domainName;
        this.version = version;
    }

    DomainIdentity(Domain domain) throws InvalidDomainException {
        this(domain.domain(), VersionRange.valueOf(domain.version()));
    }

    public String getDomainName() {
        return domainName;
    }

    public VersionRange getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return String.format("%s:%s", domainName, version.asString());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.domainName);
        hash = 71 * hash + Objects.hashCode(this.version);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DomainIdentity other = (DomainIdentity) obj;
        if (!Objects.equals(this.domainName, other.domainName)) {
            return false;
        }
        if (!Objects.equals(this.version, other.version)) {
            return false;
        }
        return true;
    }

}
