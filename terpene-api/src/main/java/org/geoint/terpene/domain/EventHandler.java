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
package org.geoint.terpene.domain;

import org.geoint.terpene.domain.model.EventModel;

/**
 * Handles a domain event after it is fired.
 *
 * @author steve_siebert
 * @param <T> java class representing the domain event type
 */
public interface EventHandler<T> {

    /**
     * Model of the event handler.
     *
     * @return event handler model
     */
    EventModel<T> getModel();

    /**
     * Synchronously executes the domain event handler.
     *
     * @param event event to handle
     * @throws EventNotSupportedException thrown if the handler does not support
     * the provided domain event
     * @throws HandlerExecutionException thrown if the handler throws an
     * exception
     */
    void handle(DomainEvent<T> event)
            throws EventNotSupportedException, HandlerExecutionException;

}
