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
package org.geoint.terpene.component;

import java.util.Arrays;

/**
 * Encapsulates the component status transition logic using method references to
 * define the transition behavior for the component instance.
 * <p>
 * Instances of ComponentState are thread-safe.
 *
 * @author steve_siebert
 * @see ComponentStatus
 */
public final class ComponentState {

    private volatile ComponentStatus status;
    private final ComponentStatusTransition startTransition;
    private final ComponentStatusTransition pauseTransition;
    private final ComponentStatusTransition resumeTransition;
    private final ComponentStatusTransition stopTransition;

    public ComponentState(ComponentStatus status,
            ComponentStatusTransition startTransition,
            ComponentStatusTransition pauseTransition,
            ComponentStatusTransition resumeTransition,
            ComponentStatusTransition stopTransition) {
        this.status = status;
        this.startTransition = startTransition;
        this.pauseTransition = pauseTransition;
        this.resumeTransition = resumeTransition;
        this.stopTransition = stopTransition;
    }

    /**
     * Transition using the provided callback if the current status matches any
     * of the provided statuses, waiting if the component is currently in the
     * middle of a transition.
     *
     * @param transition
     * @param transitionOn
     * @throws TerpeneComponentException
     */
    private void doTransition(ComponentStatusTransition transition,
            ComponentStatus... transitionOn) throws TerpeneComponentException {

        final ComponentStatus beforeStatus = status;
        try {
            switch (status) {
                case PAUSING://intentional fall through
                case RESUMING://intentional fall through
                case STARTING://intentional fall through
                case STOPPING:
                    waitForStatusChange(transition);
                    break;
                default:
                    if (Arrays.stream(transitionOn).anyMatch(status::equals)) {
                        transition.transition();
                    }
            }
        } catch (TerpeneComponentException ex) {
            status = beforeStatus; //revert status
            throw ex;
        }
    }

    public void start() throws TerpeneComponentException {
        doTransition(startTransition, ComponentStatus.PAUSED, ComponentStatus.NOT_RUNNING);
    }

    public void pause() throws TerpeneComponentException {
        doTransition(pauseTransition, ComponentStatus.RUNNING);
    }

    public void resume() throws TerpeneComponentException {
        doTransition(resumeTransition, ComponentStatus.PAUSED);
    }

    public void stop() throws TerpeneComponentException {
        doTransition(stopTransition, ComponentStatus.PAUSED, ComponentStatus.RUNNING);
    }

    private void waitForStatusChange(ComponentStatusTransition trasition)
            throws TerpeneComponentException {
        synchronized (this) {
            try {
                this.wait();
                trasition.transition();
            } catch (InterruptedException ex) {
                Thread.interrupted();
                throw new TerpeneComponentException(ComponentState.class,
                        "Waiting for component state change was interrupted.",
                        ex);
            }
        }
    }

    @FunctionalInterface
    public interface ComponentStatusTransition {

        void transition() throws TerpeneComponentException;
    }

}
