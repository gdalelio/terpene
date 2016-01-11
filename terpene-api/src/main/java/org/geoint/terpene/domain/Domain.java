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
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Declares a class as one which defines a domain object.
 * <p>
 * If this annotation is used along with {@link Value}, {@link Entity}, or
 * {@link Event} the values contained in those more specific declarations will
 * override any contained within this annotation.
 *
 * @author steve_siebert
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
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

    /**
     * Optional component name.
     * <p>
     * If not provided the component name is derived from the domain and class
     * name.
     *
     * @return component name
     */
    String name() default "";

    /**
     * Optional description.
     * <p>
     * If not set there will not be a description.
     *
     * @return description or null if no description is provided
     */
    String desc() default "";
}
