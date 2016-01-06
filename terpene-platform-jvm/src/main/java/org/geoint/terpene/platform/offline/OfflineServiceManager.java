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
package org.geoint.terpene.platform.offline;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.geoint.terpene.component.ComponentStatus;
import org.geoint.terpene.service.Service;
import org.geoint.terpene.service.ServiceManager;
import org.geoint.terpene.service.TerpeneService;

/**
 * Provides cluster-unaware service management.
 *
 * @author steve_siebert
 */
@Service(name="Offline Terpene Service Manager")
public class OfflineServiceManager implements ServiceManager {

    private final ClassLoader serviceLoader;
    private final Map<Class<?>, TerpeneService> services = new HashMap<>();
    private final static Logger logger
            = Logger.getLogger(OfflineServiceManager.class.getName());

    public OfflineServiceManager() {
        this(Thread.currentThread().getContextClassLoader());
    }

    public OfflineServiceManager(ClassLoader serviceLoader) {
        this.serviceLoader = serviceLoader;
    }

    @Override
    public Collection<TerpeneService> listServices() {
        return services.entrySet().stream()
                .map(Entry::getValue)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<TerpeneService> listAvailableServices() {
        return services.entrySet().stream()
                .map(Entry::getValue)
                .filter((s) -> s.getStatus().equals(ComponentStatus.RUNNING))
                .collect(Collectors.toList());
    }

    @Override
    public <S, C extends S> Collection<TerpeneService<S, C>> listServices(Class<S> serviceType) {
        return services.entrySet().stream()
                .map(Entry::getValue)
                .filter((s) -> serviceType.isAssignableFrom(s.getServiceType()))
                .map((s) -> (TerpeneService<S,C>) s)
                .collect(Collectors.toList());
    }
    
    @Override
    public boolean isOffline() {
        return true; //always offline!
    }

    @Override
    public boolean isOfflineCapable() {
        return true;
    }

    
    private Collection<TerpeneService> checkForNewServices() {
         throw new UnsupportedOperationException();
    }


    /**
     * Aggregates multiple terpene service implementations delegating every
     * method call to each service instance.
     *
     * @param <S>
     */
//    private final class MultiServiceWrapper<S> implements TerpeneService<S, Object> {
//
//        private final Class<S> serviceType;
//        private ComponentStatus status = ComponentStatus.NOT_RUNNING;
//        private final List<TerpeneService<S, ?>> services = new ArrayList<>();
//
//        public MultiServiceWrapper(Class<S> serviceType) {
//            this.serviceType = serviceType;
//        }
//
//        @Override
//        public Class<S> getServiceType() {
//            return serviceType;
//        }
//
//        @Override
//        public void start() throws TerpeneComponentException {
//            try {
//                for (TerpeneService s : services) {
//                    switch (s.getStatus()) {
//                        case RUNNING:
//                            break;
//                        case PAUSED:
//                            resume();
//                            break;
//                        default:
//                            s.start();
//                    }
//                }
//                status = ComponentStatus.RUNNING;
//            } catch (TerpeneComponentException ex) {
//                for (TerpeneService s : services) {
//                    if (s.getStatus().equals(ComponentStatus.RUNNING)) {
//                        logger.log(Level.SEVERE, String.format("Problem starting "
//                                + "multi-services for service type '%s', "
//                                + "shutting down service implementation '%s'",
//                                serviceType.getCanonicalName(),
//                                s.getServiceType().getCanonicalName()));
//                        try {
//                            s.stop();
//                        } catch (TerpeneComponentException e) {
//                            logger.log(Level.SEVERE, String.format("Unable to  "
//                                    + "shutdown multi-services instance type '%s', "
//                                    + "while attempting to recover for a failed start.",
//                                    s.getServiceType().getCanonicalName()));
//                        }
//                    }
//                }
//                throw ex;
//            }
//        }
//
//        @Override
//        public boolean canPause() {
//            return services.stream().anyMatch(TerpeneService::canPause);
//        }
//
//        @Override
//        public void pause() throws TerpeneComponentException {
//            services.stream()
//                    .filter(TerpeneService::canPause)
//                    .forEach((TerpeneService<S, ?> s) -> {
//                        try {
//                            s.pause();
//                        } catch (TerpeneComponentException ex) {
//                            logger.log(Level.WARNING, String.format(
//                                            "Unable to pause terpene component "
//                                            + "instance type "
//                                            + "'%s'",
//                                            s.getComponent().getClass()
//                                            .getCanonicalName()),
//                                    ex);
//                        }
//                    });
//            status = ComponentStatus.PAUSED;
//        }
//
//        @Override
//        public void resume() throws TerpeneComponentException {
//            status = ComponentStatus.RUNNING;
//            services.stream()
//                    .filter((s) -> s.getStatus().equals(ComponentStatus.PAUSED))
//                    .filter(TerpeneService::canPause)
//                    .forEach((TerpeneService<S, ?> s) -> {
//                        try {
//                            s.resume();
//                        } catch (TerpeneComponentException ex) {
//                            logger.log(Level.WARNING, String.format(
//                                            "Unable to resume terpene component "
//                                            + "instance type "
//                                            + "'%s'",
//                                            s.getComponent().getClass()
//                                            .getCanonicalName()),
//                                    ex);
//                        }
//                    });
//        }
//
//        @Override
//        public void stop() throws TerpeneComponentException {
//            for (TerpeneService s : services) {
//                try {
//                    s.stop();
//                } catch (TerpeneComponentException ex) {
//                    logger.log(Level.SEVERE, String.format("Unable to shutdown "
//                            + "terpene service '%s'",
//                            s.getServiceType().getCanonicalName()), ex);
//                }
//            }
//            status = ComponentStatus.NOT_RUNNING;
//        }
//
//        @Override
//        public ComponentStatus getStatus() {
//            return status;
//        }
//
//        @Override
//        public ComponentHeartbeat heartbeat() throws TerpeneComponentException {
//            throw new UnsupportedOperationException();
//        }
//
//        @Override
//        public TerpeneLocality getLocality() {
//            return TerpeneLocality.NODE;
//        }
//
//        @Override
//        public boolean isOfflineCapable() {
//            return true;
//        }
//
//        @Override
//        public Object getComponent() {
//            return Proxy.
//        }

    }
