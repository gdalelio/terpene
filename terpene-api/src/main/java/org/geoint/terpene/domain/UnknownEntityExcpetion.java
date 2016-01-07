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

import org.geoint.terpene.util.GUID;

/**
 * Thrown when an operation could not complete because an addressed Entity
 * instance is unknown to terpene.
 *
 * @author steve_siebert
 */
public class UnknownEntityExcpetion extends EntityException {

    private final GUID entityId;

    public UnknownEntityExcpetion(GUID entityId) {
        this.entityId = entityId;
    }

    public UnknownEntityExcpetion(GUID entityId, String message) {
        super(message);
        this.entityId = entityId;
    }

    public UnknownEntityExcpetion(GUID entityId, String message, Throwable cause) {
        super(message, cause);
        this.entityId = entityId;
    }

    public UnknownEntityExcpetion(GUID entityId, Throwable cause) {
        super(cause);
        this.entityId = entityId;
    }

    public GUID getEntityId() {
        return entityId;
    }

}
