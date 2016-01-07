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
 * Identifies and describes domain-defined behavior.
 *
 * @author steve_siebert
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Operation {

    /**
     * Optionally define the name of the domain operation.
     * <p>
     * If not specifically defined, the operation name generated from the entity
     * and method names.
     *
     * @return optional operation name override, or null
     */
    String name() default "";

    /**
     * Optionally provide a description of the domain operation.
     * <p>
     * If not defined the operation will not have a domain-defined description.
     *
     * @return optional domain description of operation, no null
     */
    String desc() default "";

}
