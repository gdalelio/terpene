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

/**
 * Thrown if a requested component is not valid for the domain model.
 *
 * @author steve_siebert
 */
public class UnknownComponentException extends DomainException {

    private final String domainName;
    private final String domainValue;
    private final String componentName;

    public UnknownComponentException(String domainName, String domainValue,
            String componentName) {
        this.domainName = domainName;
        this.domainValue = domainValue;
        this.componentName = componentName;
    }

    public UnknownComponentException(String domainName, String domainValue,
            String componentName, String message) {
        super(message);
        this.domainName = domainName;
        this.domainValue = domainValue;
        this.componentName = componentName;
    }

    public UnknownComponentException(String domainName, String domainValue,
            String componentName, String message, Throwable cause) {
        super(message, cause);
        this.domainName = domainName;
        this.domainValue = domainValue;
        this.componentName = componentName;
    }

    public UnknownComponentException(String domainName, String domainValue,
            String componentName, Throwable cause) {
        super(cause);
        this.domainName = domainName;
        this.domainValue = domainValue;
        this.componentName = componentName;
    }

    public String getDomainName() {
        return domainName;
    }

    public String getDomainValue() {
        return domainValue;
    }

    public String getComponentName() {
        return componentName;
    }

}
