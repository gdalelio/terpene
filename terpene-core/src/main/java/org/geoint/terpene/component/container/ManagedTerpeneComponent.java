/*
 * Copyright 2015 geoint.org.
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

import org.geoint.acetate.domain.operation.Operation;
import org.geoint.terpene.component.TerpeneComponent;
import org.geoint.terpene.component.event.ComponentHeartbeat;

/**
 *
 * @author steve_siebert
 * @param <C>
 */
public interface ManagedTerpeneComponent<C> extends TerpeneComponent<C> {
    

    /**
     * Calls the component method annotated with {@code Start} to start the
     * component.
     *
     */
    @Operation(name = "start")
    void start();

    boolean canPause();

    @Operation(name = "pause")
    void pause();

    @Operation(name = "resume")
    void resume();

    @Operation(name = "stop")
    void stop();

    @Operation(name = "heartbeat")
    ComponentHeartbeat heartbeat();
}
