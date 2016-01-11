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

import org.geoint.terpene.TerpeneException;

/**
 * Thrown if a DomainOperation execution throws an exception.
 *
 * @author steve_siebert
 */
public class OperationExecutionException extends DomainException {

    private final String domainName;
    private final String domainVersion;
    private final String entityName;
    private final String operationName;

    public OperationExecutionException(String domainName, String domainVersion,
            String entityName, String operationName, String message,
            Throwable cause) {
        super(message, cause);
        this.domainName = domainName;
        this.domainVersion = domainVersion;
        this.entityName = entityName;
        this.operationName = operationName;
    }

    public OperationExecutionException(String domainName, String domainVersion,
            String entityName, String operationName, Throwable cause) {
        super(cause);
        this.domainName = domainName;
        this.domainVersion = domainVersion;
        this.entityName = entityName;
        this.operationName = operationName;
    }

    public String getDomainName() {
        return domainName;
    }

    public String getDomainVersion() {
        return domainVersion;
    }

    public String getEntityName() {
        return entityName;
    }

    public String getOperationName() {
        return operationName;
    }

}
