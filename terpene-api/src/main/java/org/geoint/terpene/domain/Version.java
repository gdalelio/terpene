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
import java.io.PrintWriter;
import java.io.Reader;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.geoint.terpene.domain.Version.VersionStringCodec;
import org.geoint.terpene.domain.codec.CharCodec;

/**
 *
 * @author steve_siebert
 */
@Value(domain = "terpene", version = "[0.1,)",
        charCodec = VersionStringCodec.class)
public final class Version implements Comparable<Version> {

    private final Integer major;
    private final Integer minor;
    private final Integer increment;
    private final VersionQualifier qualifier;
    private final String stringRepresentation; //this object is frequently converted to/from a String, so we'll cache it here

    private static final Logger LOGGER = Logger.getLogger(Version.class.getName());
    private static final String INCREMENT_SEPARATOR = ".";
    private static final String QUALIFIER_SEPARATOR = "-";
    static final Pattern VERSION_PATTERN = Pattern.compile(
            "(\\d+)\\" + INCREMENT_SEPARATOR + "(\\d+)" //[1] major and [2] minor
            + "(\\" + INCREMENT_SEPARATOR + "(\\d+))?" //[3] optional [4] increment
            + "\\" + QUALIFIER_SEPARATOR + "(\\w+)"); //[4] qualifier

    private Version(String formatted, Integer major, Integer minor,
            Integer increment, VersionQualifier qualifier) {
        this.stringRepresentation = formatted;
        this.major = major;
        this.minor = minor;
        this.increment = increment;
        this.qualifier = qualifier;
    }

    public Version(Integer major, Integer minor,
            Integer increment, VersionQualifier qualifier) {
        this(format(major, minor, increment, qualifier),
                major, minor, increment, qualifier);
    }

    public static Version valueOf(String versionString) {
        if (versionString == null) {
            LOGGER.log(Level.FINER, () -> String.format("Version parsing failed, "
                    + "provided null value."));
            return null;
        }

        Matcher m = VERSION_PATTERN.matcher(versionString);
        if (!m.find()) {
            return null;
        }

        try {
            Integer major = Integer.valueOf(m.group(1));
            Integer minor = Integer.valueOf(m.group(2));
            Integer increment = (m.group(4) == null)
                    ? 0
                    : Integer.valueOf(m.group(4));
            VersionQualifier qualifier = VersionQualifier.valueOf(m.group(5));

            //call format, rather than pass provided string, because we want 
            //to normalize the format and the parser is more lax than the formatter
            return new Version(format(major, minor, increment, qualifier),
                    major, minor, increment, qualifier);

        } catch (NullPointerException | IllegalArgumentException ex) {
            LOGGER.log(Level.FINER, () -> String.format("%s is not a valid "
                    + "string formatted version", versionString));
            return null;
        }
    }

    public boolean isLessThan(Version other) {
        return compareTo(other) < 0;
    }

    public boolean isGreaterThan(Version other) {
        return compareTo(other) > 0;
    }

    public Integer getMajor() {
        return major;
    }

    public Integer getMinor() {
        return minor;
    }

    public Integer getIncrement() {
        return increment;
    }

    public VersionQualifier getQualifier() {
        return qualifier;
    }

    /**
     *
     * @return string
     */
    public String asString() {
        return stringRepresentation;
    }

    /*
     * Since all the version components are, or can be compared as, Integers 
     * we can greatly simplify the comparison logic by looping over all the 
     * component Integer representations.
     */
    private static Collection<Function<Version, Integer>> compareSuppliers
            = Arrays.asList(Version::getMajor, Version::getMinor,
                    Version::getIncrement, (v) -> v.getQualifier().ordinal());

    @Override
    public int compareTo(Version o) {
        int compare = 0;
        Iterator<Function<Version, Integer>> suppliers = compareSuppliers.iterator();
        while (suppliers.hasNext() && compare == 0) {
            Function<Version, Integer> supplier = suppliers.next();
            compare = supplier.apply(this).compareTo(supplier.apply(o));
        }
        return compare;
    }

    private static String format(Integer major, Integer minor,
            Integer increment, VersionQualifier qualifier) {
        StringBuilder sb = new StringBuilder();
        format(sb, major, minor, increment, qualifier);
        return sb.toString();
    }

    static void format(StringBuilder sb, Integer major, Integer minor,
            Integer increment, VersionQualifier qualifier) {

        sb.append(String.valueOf(major))
                .append(INCREMENT_SEPARATOR)
                .append(String.valueOf(minor))
                .append(INCREMENT_SEPARATOR)
                .append(String.valueOf(increment))
                .append(QUALIFIER_SEPARATOR)
                .append(qualifier.name());
    }

    @Override
    public String toString() {
        return asString();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.stringRepresentation);
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
        final Version other = (Version) obj;
        if (!Objects.equals(this.stringRepresentation, other.stringRepresentation)) {
            return false;
        }
        return true;
    }

    /**
     * Converts a Version instance to a String and back.
     *
     */
    public static class VersionStringCodec implements CharCodec<Version> {

        private static final int CHAR_BUFFER_SIZE = 12;

        @Override
        public void write(Version data, PrintWriter writer) throws IOException {
            //simply convert the version in-memory first, then push to stream
            //TODO check how much of a memory hog this is
            writer.write(data.toString());
        }

        @Override
        public Version read(Reader reader) throws IOException {
            final StringBuilder sb = new StringBuilder();
            final char[] buffer = new char[CHAR_BUFFER_SIZE];
            int read;
            while ((read = reader.read(buffer)) != -1) {
                sb.append(buffer, 0, read);
            }
            return Version.valueOf(sb.toString());
        }

    }
}
