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

import org.geoint.terpene.domain.model.OperationModel;

/**
 * Domain operation.
 *
 * @author steve_siebert
 */
public interface DomainOperation {

    /**
     * Domain operation metamodel.
     *
     * @return operation model
     */
    OperationModel getModel();

    /**
     * Synchronously execute the domain operation.
     *
     * @param param operation parameters
     * @return domain event if operation completed successfully
     * @throws OperationExecutionException thrown if the operation threw an
     * exception
     */
    DomainEvent<?> execute(DomainObject... param)
            throws OperationExecutionException;

}
