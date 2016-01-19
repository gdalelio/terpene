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
 * Thrown if a class is attempted to be used as a domain model component, but
 * the domain model or component model could not be resolved.
 *
 * @author steve_siebert
 */
public class NotDomainClassException extends DomainException {

    private final Class<?> invalidClass;

    public NotDomainClassException(Class<?> invalidClass) {
        this(invalidClass, String.format("%s is not a domain model component.",
                invalidClass.getCanonicalName()));
    }

    public NotDomainClassException(Class<?> invalidClass, String message) {
        super(message);
        this.invalidClass = invalidClass;
    }

    public NotDomainClassException(Class<?> invalidClass, String message, Throwable cause) {
        super(message, cause);
        this.invalidClass = invalidClass;
    }

    public NotDomainClassException(Class<?> invalidClass, Throwable cause) {
        super(cause);
        this.invalidClass = invalidClass;
    }

    public Class<?> getInvalidClass() {
        return invalidClass;
    }

}
