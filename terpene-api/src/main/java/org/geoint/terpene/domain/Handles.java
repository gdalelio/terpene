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
 * Defines a method as one that handles a domain event.
 *
 * @author steve_siebert
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Handles {

    /**
     * Optional explicit name of the handler.
     * <p>
     * If not explicitly set the handler name will be derived from the domain,
     * class, and method.
     *
     * @return explicit handler name or null if the default name should be used
     */
    String name() default "";

    /**
     * Optional description.
     * <p>
     * If not set there will not be a description.
     *
     * @return handler description or null if no description is provided
     */
    String desc() default "";
}
