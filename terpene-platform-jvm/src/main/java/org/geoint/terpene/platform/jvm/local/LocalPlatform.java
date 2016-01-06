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
package org.geoint.terpene.platform.jvm.local;

import java.io.File;
import javax.inject.Inject;
import org.geoint.terpene.TerpeneException;
import org.geoint.terpene.cdi.PlatformScoped;
import org.geoint.terpene.component.ComponentState;
import org.geoint.terpene.component.ComponentStatus;
import org.geoint.terpene.component.container.ComponentContainer;
import org.geoint.terpene.component.container.ContainerRepository;
import org.geoint.terpene.platform.TerpenePlatform;

/**
 * Local (on system) terpene platform instance.
 *
 * @author steve_siebert
 */
public class LocalPlatform implements TerpenePlatform {

    private final String platformId;
    private final File platformBaseDir;
    private final ComponentState state;
    @Inject
    @PlatformScoped
    ContainerRepository containers;

    public LocalPlatform(String platformId, File platformBaseDir) {
        this.platformId = platformId;
        this.platformBaseDir = platformBaseDir;
        this.state = new ComponentState(ComponentStatus.NOT_RUNNING,
                this::doStart,
                this::doPause,
                this::doResume,
                this::doStop);
    }

    @Override
    public String getPlatformId() {
        return platformId;
    }

    @Override
    public void start() throws TerpeneException {
        state.start();
    }

    @Override
    public void stop() throws TerpeneException {
        state.stop();
    }

    private void doStart() {
        //TODO
    }

    private void doPause() {
//TODO
    }

    private void doResume() {
//TODO
    }

    private void doStop() {
//TODO
    }

    @Override
    public ComponentContainer[] getContainers() {
        return containers.getContainers();
    }

}
