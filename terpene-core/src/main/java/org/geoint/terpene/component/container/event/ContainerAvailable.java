/*
 * Copyright 2015 steve_siebert.
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
package org.geoint.terpene.component.container.event;

import java.util.Objects;
import org.geoint.terpene.component.container.ComponentContainer;

/**
 * Event indicating a container is available to a platform instance.
 *
 * @author steve_siebert
 */
public class ContainerAvailable {

    private final String platformId;
    private final ComponentContainer container;

    public ContainerAvailable(String platformId,
            ComponentContainer container) {
        this.platformId = platformId;
        this.container = container;
    }

    public String getPlatformId() {
        return platformId;
    }

    public ComponentContainer getContainer() {
        return container;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.platformId);
        hash = 73 * hash + Objects.hashCode(this.container);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ContainerAvailable other = (ContainerAvailable) obj;
        if (!Objects.equals(this.platformId, other.platformId)) {
            return false;
        }
        if (!Objects.equals(this.container, other.container)) {
            return false;
        }
        return true;
    }

}
