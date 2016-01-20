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
package org.geoint.terpene.platform.jvm;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.eclipse.jetty.jmx.MBeanContainer;
import org.eclipse.jetty.server.Server;

/**
 *
 * @author steve_siebert
 */
public class JettyWarProcess {

//    private final WebAppContext context;
//    private final int port;
//    private final File extractDir;
//    private Server server;
//
//    private static final Options CLI_OPTIONS = new Options();
//    public static final String CLI_WAR_FILE = "f";
//    public static final String CLI_PORT = "p";
//    public static final String CLI_EXTRACT = "x";
//    
//    static {
//        CLI_OPTIONS.addOption(Option.builder("f")
//                .hasArg(true)
//                .numberOfArgs(1)
//                .argName("warFile")
//                .desc("WAR file")
//                .required(true)
//                .type(String.class)
//                .longOpt("war")
//                .build()
//        );
//
//        CLI_OPTIONS.addOption(Option.builder("p")
//                .longOpt("port")
//                .desc("server port")
//                .hasArg(true)
//                .numberOfArgs(1)
//                .argName("port")
//                .required(true)
//                .type(Integer.class)
//                .build()
//        );
//
//        CLI_OPTIONS.addOption(Option.builder("x")
//                .longOpt("extract")
//                .desc("war extraction location")
//                .hasArg(true)
//                .numberOfArgs(1)
//                .argName("dir")
//                .required(true)
//                .type(String.class)
//                .build()
//        );
//    }
//
//    private static final Logger logger
//            = Logger.getLogger(JettyWarProcess.class.getName());
//
//    public static void main(String... args) throws Exception {
//        DefaultParser parser = new DefaultParser();
//        CommandLine cli = parser.parse(CLI_OPTIONS, args);
//
//        final JettyWarProcess process = new JettyWarProcess(
//                new File(cli.getOptionValue(CLI_WAR_FILE)),
//                Integer.valueOf(cli.getOptionValue(CLI_PORT)),
//                new File(cli.getOptionValue(CLI_EXTRACT))
//        );
//
//        process.start();
//
//        Runtime.getRuntime().addShutdownHook(new Thread() {
//            @Override
//            public void start() {
//                try {
//                    process.stop();
//                } catch (Exception ex) {
//                    logger.log(Level.SEVERE, "Problems shutting down jetty war"
//                            + "process gracefully.");
//                }
//            }
//        });
//    }
//
//    private JettyWarProcess(File warFile, int port, File workDir) {
//        context = new WebAppContext();
//        context.setContextPath("/");
//        context.setWar(warFile.getAbsolutePath());
//        this.port = port;
//        this.extractDir = workDir;
//    }
//
//    public void start() throws Exception {
//        try {
//
//            server = new Server(port);
//
//            startJmx(server);
//
//            //deploy war
//            server.setHandler(context);
//
//            if (logger.isLoggable(Level.FINEST)) {
//                logger.log(Level.FINEST, String.format("Starting Jetty WAR "
//                        + "container on post %d for '%s'", port,
//                        context.getContextPath()));
//            }
//
//            server.start();
//            server.join();
//
//            if (logger.isLoggable(Level.FINE)) {
//                logger.log(Level.FINE, String.format("Jetty WAR container is "
//                        + "listing on port %d for '%s'", port,
//                        context.getContextPath()));
//            }
//
//        } catch (Exception ex) {
//            logger.log(Level.WARNING, String.format("Problem while attempting to "
//                    + "start Jetty WAR container for '%s'; shutting down container.",
//                    context.getContextPath()), ex);
//            stop();
//        }
//    }
//
//    public void stop() throws Exception {
//        logger.log(Level.INFO, String.format("Stopping Jetty WAR container for "
//                + "'%s'", context.getContextPath()));
//
//        if (server != null
//                && (server.isStarted() || server.isRunning())) {
//            server.stop();
//        }
//    }
//
//    private void startJmx(Server server) {
//        MBeanContainer mbContainer = new MBeanContainer(
//                ManagementFactory.getPlatformMBeanServer());
//        server.addBean(mbContainer);
//    }
}
