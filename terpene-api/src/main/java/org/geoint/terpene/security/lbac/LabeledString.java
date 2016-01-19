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

import java.util.Objects;
import org.geoint.terpene.domain.Value;

/**
 * An LBAC controlled String.
 *
 * @author steve_siebert
 */
@Value(name = "labeledString", desc = "LBAC controlled string")
public final class LabeledString implements Labeled<String> {

    private final String lbac;
    private final String value;

    public LabeledString(String lbac, String value) {
        this.lbac = lbac;
        this.value = value;
    }

    @Override
    public String getLabel() {
        return lbac;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return asString();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.lbac);
        hash = 97 * hash + Objects.hashCode(this.value);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LabeledString other = (LabeledString) obj;
        if (!Objects.equals(this.lbac, other.lbac)) {
            return false;
        }
        if (!Objects.equals(this.value, other.value)) {
            return false;
        }
        return true;
    }

}
