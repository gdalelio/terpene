/*
 * Copyright 2015 geoint.org.
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
package org.geoint.terpene.component.container;

import org.geoint.terpene.component.TerpeneComponent;

/**
 *
 * @author steve_siebert
 */
public class UnsupportedComponentException extends Exception {

    private final TerpeneComponent component;

    public UnsupportedComponentException(TerpeneComponent component) {
        this.component = component;
    }

    public UnsupportedComponentException(TerpeneComponent component, String message) {
        super(message);
        this.component = component;
    }

    public UnsupportedComponentException(TerpeneComponent component, String message, Throwable cause) {
        super(message, cause);
        this.component = component;
    }

    public UnsupportedComponentException(TerpeneComponent component, Throwable cause) {
        super(cause);
        this.component = component;
    }

    public TerpeneComponent getComponent() {
        return component;
    }

}
