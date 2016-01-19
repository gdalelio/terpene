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
package org.geoint.terpene.security.lbac;

/**
 * An LBAC-controlled domain component.
 *
 * @author steve_siebert
 * @param <T>
 */
public interface Labeled<T> {

    /**
     * The LBAC label.
     *
     * @return lbac label
     */
    String getLabel();

    /**
     * The LBAC label value.
     *
     * @return value of the LBAC controlled component
     */
    T getValue();

    /**
     * Default String representation of the LBAC controlled domain component.
     * <p>
     * This method assumes that the domain component class implements a
     * reasonable {@link #toString() } method.
     *
     * @return labeled component as string
     */
    public default String asString() {
        return String.format("(%s) %s",
                this.getLabel(),
                this.getValue().toString());
    }
    
}
