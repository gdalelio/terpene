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
import org.geoint.terpene.domain.Entity;
import org.geoint.terpene.domain.Event;
import org.geoint.terpene.domain.Handles;
import org.geoint.terpene.domain.Operation;
import org.geoint.terpene.domain.Value;
import org.geoint.terpene.domain.VersionRange;
import org.geoint.terpene.domain.model.DomainComponentModel;
import org.geoint.terpene.domain.model.EntityModel;
import org.geoint.terpene.domain.model.EventModel;
import org.geoint.terpene.domain.model.HandlerModel;
import org.geoint.terpene.domain.model.InvalidDomainException;
import org.geoint.terpene.domain.model.OperationModel;

/**
 * Unique domain component identity, defined by domain/version/component name.
 *
 * @see DomainComponentModel
 * @see EntityModel
 * @see EventModel
 * @see HandlerModel
 * @see OperationModel
 * @author steve_siebert
 */
public class DomainComponentIdentity extends DomainIdentity {

    private final String componentName;

    private DomainComponentIdentity(String domainName,
            String domainVersion, String componentName)
            throws InvalidDomainException {
        super(domainName, VersionRange.valueOf(domainVersion));
        if (componentName == null) {
            throw new InvalidDomainException("Invalid domain component identity,"
                    + "component name is required.");
        }
        this.componentName = componentName;
    }

    public DomainComponentIdentity(Domain domain, Entity entity)
            throws InvalidDomainException {
        this(domain.domain(), domain.version(), entity.name());
    }

    public DomainComponentIdentity(Domain domain, Value value)
            throws InvalidDomainException {
        this(domain.domain(), domain.version(), value.name());
    }

    public DomainComponentIdentity(Domain domain, Event event)
            throws InvalidDomainException {
        this(domain.domain(), domain.version(), event.name());
    }

    public DomainComponentIdentity(Domain domain, Operation operation)
            throws InvalidDomainException {
        this(domain.domain(), domain.version(), operation.name());
    }

    public DomainComponentIdentity(DomainIdentity id, Operation operation)
            throws InvalidDomainException {
        super(id.getDomainName(), id.getVersion());
        this.componentName = operation.name();
    }

    public DomainComponentIdentity(Domain domain, Handles handler)
            throws InvalidDomainException {
        this(domain.domain(), domain.version(), handler.name());
    }

    public DomainComponentIdentity(DomainIdentity id, Handles handler)
            throws InvalidDomainException {
        super(id.getDomainName(), id.getVersion());
        this.componentName = handler.name();
    }

    public String getComponentName() {
        return componentName;
    }

    @Override
    public String toString() {
        return String.format("%s:%s:%s",
                this.getDomainName(),
                this.getVersion().asString(),
                componentName);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.getDomainName());
        hash = 79 * hash + Objects.hashCode(this.getVersion());
        hash = 79 * hash + Objects.hashCode(this.componentName);
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
        final DomainComponentIdentity other = (DomainComponentIdentity) obj;
        if (!Objects.equals(this.getDomainName(), other.getDomainName())) {
            return false;
        }
        if (!Objects.equals(this.componentName, other.componentName)) {
            return false;
        }
        if (!Objects.equals(this.getVersion(), other.getVersion())) {
            return false;
        }
        return true;
    }

}
