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
package org.geoint.terpene.hash;

/**
 * Service to generate a hash.
 * 
 * @author steve_siebert
 */
public interface HashService {
    
    /**
     * Add content to checksum.
     * 
     * @param bytes checksum content
     */
    public void update(byte[] bytes);
    
    /**
     * Create checksum.
     * 
     * @return checksum bytes
     */
    public byte[] checksum();
    
    /**
     * Create checksum as human-readable string.
     * 
     * @return checksum string
     */
    public String checksumString();
    
}
