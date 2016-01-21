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
package org.geoint.terpene.domain.codec;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;

/**
 * A piece of code that converts/inverts terpene domain data objects to
 * serialized string.
 *
 * @author steve_siebert
 * @param <T> java type representing a domain component
 */
public interface CharCodec<T> {

    /**
     * Writes the domain component data to a character stream.
     *
     * @param data domain component
     * @param writer output character stream
     * @throws IOException thrown if there was a problem reading from the stream
     */
    void write(T data, PrintWriter writer)
            throws IOException;

    /**
     * Converts the content of a character stream to an instance of a domain
     * component.
     *
     * @param reader character stream
     * @return domain component instance, may return null
     * @throws IOException thrown if there was a problem reading from the stream
     * @throws org.geoint.terpene.domain.codec.InvalidCodecException thrown if
     * the codec cannot convert the stream to an domain component instance
     */
    T read(Reader reader) throws IOException, InvalidCodecException;

}
