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

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines the domain model the domain component(s) are a member of.
 * <p>
 * This annotation is used along with {@link Value}, {@link Entity}, or
 * {@link Event}, which specify the domain component type the java class
 * represents.
 *
 * @see Value
 * @see Entity
 * @see Event
 * @author steve_siebert
 */
@Target({ElementType.TYPE, ElementType.PACKAGE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Domain {

    /**
     * Name of the domain this component.
     *
     * @return domain name
     */
    String domain();

    /**
     * Version(s) of the domain this class represents the component.
     *
     * @return domain version
     */
    String version();

}
