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
package org.geoint.terpene.component.container;

import org.geoint.terpene.component.TerpeneComponent;

/**
 * Service interface for terpene component containers.
 * <p>
 * Component containers provide an execution environment for components and
 * services.
 *
 * @author steve_siebert
 */
public interface ComponentContainer {

    void start(ContainerContext context);

    void stop();

    /**
     * Deploy the component on the container, bringing the component under
     * management of the container.
     *
     * Calling this method does not start the component, this still must be done
     * by calling {@code TerpeneComponent#start}.
     *
     * @param component
     * @param context
     * @return
     * @throws Exception
     */
    ManagedTerpeneComponent deploy(TerpeneComponent<?> component, ExecutionContext context)
            throws Exception;

    boolean canDeploy(TerpeneComponent<?> component);
    
    ManagedTerpeneComponent[] getManagedComponents();
    
    //TerpeneComponent[] findSupportedComponents(ZipFile jarStream);
}
