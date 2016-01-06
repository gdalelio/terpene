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
package org.geoint.terpene.platform.jvm.jetty.war;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.geoint.terpene.component.TerpeneComponent;
import org.geoint.terpene.component.container.ContainerContext;
import org.geoint.terpene.component.container.ExecutionContext;

/**
 * Terpene Component wrapper that deploys a JSR-compliant .war file on a Jetty
 * container.
 *
 * @author steve_siebert
 */
public class JettyWarContainer extends ProcessComponentContainer{

    public static final String WAR_EXTENSION = ".war";
    private static final Logger logger
            = Logger.getLogger(JettyWarContainer.class.getName());



    @Override
    public boolean canDeploy(TerpeneComponent<?> component) {
        return component instanceof JettyWarComponent;
    }

    @Override
    protected ProcessBuilder createProcess(TerpeneComponent<?> component, ExecutionContext context) throws Exception {
        
        //TODO start the container if it hasn't already been started
        JettyWarComponent warComponent = (JettyWarComponent) component;
        final File warFile = warComponent.getWarFile();
        final File componentDir = newComponentDir(warComponent);
        final Integer port = newPort();

        ProcessBuilder pb = new ProcessBuilder("java org.geoint.terpene.jetty.war.JettyWarProcess",
                JettyWarProcess.CLI_WAR_FILE, warFile.getAbsolutePath(),
                JettyWarProcess.CLI_PORT, port.toString(),
                JettyWarProcess.CLI_EXTRACT, componentDir.getAbsolutePath());
        pb.directory(componentDir);
//        pb.redirectError(ProcessBuilder.Redirect.INHERIT);
//        pb.redirectError(ProcessBuilder.Redirect.INHERIT);
        
    }


}
