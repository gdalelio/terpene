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

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import org.geoint.terpene.domain.model.ValueModel;

/**
 * Instance of non-complex domain data.
 *
 * @author steve_siebert
 * @param <T> java class representing the domain value data
 */
public interface DomainValue<T> extends DomainData<T> {

    /**
     * Return the model of this domain value type.
     *
     * @return value model
     */
    ValueModel<T> getModel();

    /**
     * Writes the value as a UTF-8 string to the stream.
     *
     * @param out stream to write value as a UTF-8 string
     * @throws IOException thrown if there is a problem writing to the stream
     */
    void writeString(PrintStream out) throws IOException;

    /**
     * Writes the value as bytes to the output stream.
     *
     * @param out stream to write value as an array of bytes
     * @throws IOException thrown if there is a problem writing to the stream
     */
    void writeBytes(OutputStream out) throws IOException;
}
