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
import org.geoint.terpene.domain.codec.BinaryCodec;
import org.geoint.terpene.domain.codec.CharCodec;

/**
 * Defines a java object as one that represents a {@link DomainValue}.
 *
 * @author steve_siebert
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Value {

    /**
     * Name of the domain this value.
     *
     * @return domain name
     */
    String domain();

    /**
     * Version(s) of the domain this class represents the value.
     *
     * @return domain version
     */
    String version();

    /**
     * Optional component name for this value component.
     * <p>
     * If not provided the value component name is derived from the domain and
     * class name.
     *
     * @return event type
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

    /**
     * Default CharCodec to use to serialize the Value object as a string.
     *
     * @return codec class or interface, indicating no default codec
     */
    Class<? extends CharCodec> charCodec() default CharCodec.class;

    /**
     * Default BinaryCodec to use to serialize the Value object as a binary
     * representation.
     *
     * @return codec class or interface, indicating no default codec
     */
    Class<? extends BinaryCodec> binCodec() default BinaryCodec.class;
}
