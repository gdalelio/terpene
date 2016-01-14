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
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiPredicate;
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

    private final boolean startInclusive;
    private final Integer startMajor;
    private final Integer startMinor;
    private final Optional<Integer> startIncrement;
    private final VersionQualifier startQualifier;
    private final boolean endInclusive;
    private final Integer endMajor;
    private final Integer endMinor;
    private final Optional<Integer> endIncrement;
    private final VersionQualifier endQualifier;
    private final String stringRepresentation; //this object is frequently converted to/from a String, so we'll cache it here

    private static final Logger LOGGER = Logger.getLogger(Version.class.getName());
    private static final String START_INCLUSIVE = "[";
    private static final String START_EXCLUSIVE = "(";
    private static final String END_INCLUSIVE = "]";
    private static final String END_EXCLUSIVE = ")";
    private static final String INCREMENT_SEPARATOR = ".";
    private static final String QUALIFIER_SEPARATOR = "-";
    private static final String RANGE_SEPARATOR = ",";
    private static final Pattern VERSION_PATTERN = Pattern.compile(
            "([\\" + START_INCLUSIVE + "\\" + START_EXCLUSIVE + "])?" //[1] start inclusive/exclusive
            + "((\\d+)\\" + INCREMENT_SEPARATOR + "(\\d+)" //[2] begin optional start; [3] major and [4] minor
            + "(\\" + INCREMENT_SEPARATOR + "(\\d+))?" //start [5] optional [6] increment
            + "\\" + QUALIFIER_SEPARATOR + "(\\w+))?" //[7] start qualifier; complete optional start version
            + "(" + RANGE_SEPARATOR + ")?" //optional [8] range separator
            + "((\\d+)\\" + INCREMENT_SEPARATOR + "(\\d+)" //optional [9] end version; end [10] major and [11] minor
            + "(\\" + INCREMENT_SEPARATOR + "(\\d+))?" //end [12] optional [13]increment
            + "\\" + QUALIFIER_SEPARATOR + "(\\w+))?" //[14] end qualifier
            + "([\\" + END_INCLUSIVE + "\\" + END_EXCLUSIVE + "])?");//[15] end inclusive/eclusive; complete optional end version

    private Version(String stringRepresentation, boolean startInclusive,
            Integer startMajor, Integer startMinor, Integer startIncrement,
            VersionQualifier startQualifier, boolean endInclusive,
            Integer endMajor, Integer endMinor, Integer endIncrement,
            VersionQualifier endQualifier) {
        this.stringRepresentation = stringRepresentation;
        this.startInclusive = startInclusive;
        this.startMajor = startMajor;
        this.startMinor = startMinor;
        this.startIncrement = Optional.ofNullable(startIncrement);
        this.startQualifier = startQualifier;
        this.endInclusive = endInclusive;
        this.endMajor = endMajor;
        this.endMinor = endMinor;
        this.endIncrement = Optional.ofNullable(endIncrement);
        this.endQualifier = endQualifier;
    }

    /**
     *
     * @param startInclusive
     * @param startMajor
     * @param startMinor
     * @param startIncrement
     * @param startQualifier
     * @param endInclusive
     * @param endMajor
     * @param endMinor
     * @param endIncrement
     * @param endQualifier
     * @throws NullPointerException if required version components are null
     */
    public Version(boolean startInclusive, Integer startMajor,
            Integer startMinor, Integer startIncrement,
            VersionQualifier startQualifier, boolean endInclusive,
            Integer endMajor, Integer endMinor, Integer endIncrement,
            VersionQualifier endQualifier) {
        this(format(startInclusive, startMajor,
                startMinor, startIncrement, startQualifier, endInclusive,
                endMajor, endMinor, endIncrement, endQualifier),
                startInclusive, startMajor, startMinor, startIncrement,
                startQualifier, endInclusive, endMajor, endMinor, endIncrement,
                endQualifier);
    }

    /**
     * Returns the version or null if the string is not a valid format.
     *
     * @param versionString string formatted version
     * @return version or null if not a valid version string format
     */
    public static Version valueOf(final String versionString) {
        Matcher m = VERSION_PATTERN.matcher(versionString);
        if (!m.find()) {
            return null;
        }

        int count = m.groupCount();
        for (int i = 0; i <= count; i++) {
            System.out.println(String.format("%d\t%s", i, m.group(i)));
        }

        try {

            boolean startInclusive = (m.group(1) == null)
                    ? true //default is inclusive
                    : m.group(1).contentEquals(START_INCLUSIVE);
            Integer startMajor = null;
            Integer startMinor = null;
            Integer startIncrement = null;
            VersionQualifier startQualifier = null;
            boolean endInclusive = (m.group(15) == null)
                    ? true //default is inclusive
                    : m.group(15).contentEquals(END_INCLUSIVE);
            Integer endMajor = null;
            Integer endMinor = null;
            Integer endIncrement = null;
            VersionQualifier endQualifier = null;

            if (m.group(3) != null) {
                //start version is defined
                startMajor = Integer.valueOf(m.group(3));
                startMinor = Integer.valueOf(m.group(4));
                startIncrement = (m.group(6) != null)
                        ? Integer.valueOf(m.group(6))
                        : null;
                startQualifier = VersionQualifier.valueOf(m.group(7));
            }

            if (m.group(8) != null) {
                //range is defined
                if (m.group(9) != null) {
                    //end version is defined
                    endMajor = Integer.valueOf(m.group(10));
                    endMinor = Integer.valueOf(m.group(11));
                    endIncrement = (m.group(13) != null)
                            ? Integer.valueOf(m.group(13))
                            : null;
                    endQualifier = VersionQualifier.valueOf(m.group(14));
                } else {
                    //range is defined, but no range number is specified, 
                    //so there is no upper limit
                }
            } else {
                //no range is specified at all, and end condition is inclusive, 
                //so this is exact match only
                endMajor = startMajor;
                endMinor = startMinor;
                endIncrement = startIncrement;
                endQualifier = startQualifier;
            }

            return new Version(versionString, startInclusive, startMajor,
                    startMinor, startIncrement, startQualifier, endInclusive,
                    endMajor, endMinor, endIncrement, endQualifier);

        } catch (NullPointerException | IllegalArgumentException ex) {
            //invalid version format, log and return null
            LOGGER.log(Level.FINER, () -> String.format("%s is not a valid "
                    + "string formatted version", versionString));
            return null;
        }
    }

    /**
     *
     * @return string
     */
    public String asString() {
        return stringRepresentation;
    }

    private static String format(boolean startInclusive, Integer startMajor,
            Integer startMinor, Integer startIncrement,
            VersionQualifier startQualifier, boolean endInclusive,
            Integer endMajor, Integer endMinor, Integer endIncrement,
            VersionQualifier endQualifier) {

        StringBuilder sb = new StringBuilder();
        sb.append((startInclusive) ? START_INCLUSIVE : START_EXCLUSIVE);

        if (startMajor != null) {
            format(sb, startMajor, startMinor, startIncrement, startQualifier);
        }
        if (endMajor != null) {
            sb.append(RANGE_SEPARATOR);
            format(sb, endMajor, endMinor, endIncrement, endQualifier);
        }

        sb.append((endInclusive) ? END_INCLUSIVE : END_EXCLUSIVE);
        return sb.toString();
    }

    private static void format(StringBuilder sb, Integer major, Integer minor,
            Integer increment, VersionQualifier qualifier) {

        sb.append(String.valueOf(major))
                .append(INCREMENT_SEPARATOR)
                .append(String.valueOf(minor));
        if (increment != null) {
            sb.append(INCREMENT_SEPARATOR)
                    .append(String.valueOf(increment));
        }
        sb.append(QUALIFIER_SEPARATOR)
                .append(qualifier.name());
    }

    public boolean isStartInclusive() {
        return startInclusive;
    }

    public boolean isStartOpen() {
        return startMajor == null;
    }

    public Integer getStartMajor() {
        return startMajor;
    }

    public Integer getStartMinor() {
        return startMinor;
    }

    public Optional<Integer> getStartIncrement() {
        return startIncrement;
    }

    public VersionQualifier getStartQualifier() {
        return startQualifier;
    }

    public boolean isEndInclusive() {
        return endInclusive;
    }

    public boolean isEndOpen() {
        return endMajor == null;
    }

    public Integer getEndMajor() {
        return endMajor;
    }

    public Integer getEndMinor() {
        return endMinor;
    }

    public Optional<Integer> getEndIncrement() {
        return endIncrement;
    }

    public VersionQualifier getEndQualifier() {
        return endQualifier;
    }

    public boolean isRange() {
        //iff there is a difference between the start and end, this is a range
        return !Objects.equals(startMajor, endMajor)
                || !Objects.equals(startMinor, endMinor)
                || (startIncrement.isPresent()
                && endIncrement.isPresent()
                && !Objects.equals(startIncrement.get(), endIncrement.get()))
                || startQualifier != endQualifier;
    }

    /**
     * Test to see if this version is greater than the provided version.
     * <p>
     * If the two versions overlap at all this method will return false.
     *
     * @param version test version
     * @return true if this version is greater than the the provided version,
     * otherwise false
     */
    public boolean isGreaterThan(Version version) {
//        return this.getEndMajor() > version.getEndMajor()
//                || this.getEndMinor() > version.getEndMinor()
//                || (this.getEndIncrement().isPresent() && !version.getEndIncrement().isPresent())
//                || (this.getEndIncrement().isPresent() && version.getEndIncrement().isPresent()
//                && this.getEndIncrement().get() > version.getEndIncrement().get())
//                || this.getEndQualifier().ordinal() > version.getEndQualifier().ordinal();
        return this.compare(this, version, (i1, i2) -> i1 > i2);
    }

    /**
     * Test to see if this version is less than the provided version.
     * <p>
     * If the two versions overlap at all this method will return false.
     *
     * @param version test version
     * @return true if this version is less than the provided version, otherwise
     * false
     */
    public boolean isLessThan(Version version) {
        return this.compare(this, version, (i1, i2) -> i1 < i2);
    }

    private boolean compareEnd(Version v1, Version v2,
            BiPredicate<Integer, Integer> comparator) {
        return comparator.test(v1.getEndMajor(), v2.getEndMajor())
                || comparator.test(v1.getEndMinor(), v2.getEndMinor())
                || (v1.getEndIncrement().isPresent() && !v2.getEndIncrement().isPresent())
                || (v1.getEndIncrement().isPresent() && v2.getEndIncrement().isPresent()
                && comparator.test(v1.getEndIncrement().get(), v2.getEndIncrement().get()))
                || comparator.test(v1.getEndQualifier().ordinal(), v2.getEndQualifier().ordinal());

    }

    /**
     * Test to see if this version falls within the provided version.
     *
     * @param version test version
     * @return true if this version falls within the version of the provided
     * version, otherwise false
     */
    public boolean isWithin(Version version) {
        
    }
    

    @Override
    public int compareTo(Version o) {
        if (this.isWithin(o)) {
            return 0;
        } else if (this.isLessThan(o)) {
            return -1;
        }
        return 1;
    }

    @Override
    public String toString() {
        return asString();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (this.startInclusive ? 1 : 0);
        hash = 89 * hash + Objects.hashCode(this.startMajor);
        hash = 89 * hash + Objects.hashCode(this.startMinor);
        hash = 89 * hash + Objects.hashCode(this.startIncrement);
        hash = 89 * hash + Objects.hashCode(this.startQualifier);
        hash = 89 * hash + (this.endInclusive ? 1 : 0);
        hash = 89 * hash + Objects.hashCode(this.endMajor);
        hash = 89 * hash + Objects.hashCode(this.endMinor);
        hash = 89 * hash + Objects.hashCode(this.endIncrement);
        hash = 89 * hash + Objects.hashCode(this.endQualifier);
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
        if (this.startInclusive != other.startInclusive) {
            return false;
        }
        if (this.endInclusive != other.endInclusive) {
            return false;
        }
        if (!Objects.equals(this.startMajor, other.startMajor)) {
            return false;
        }
        if (!Objects.equals(this.startMinor, other.startMinor)) {
            return false;
        }
        if (!Objects.equals(this.startIncrement, other.startIncrement)) {
            return false;
        }
        if (this.startQualifier != other.startQualifier) {
            return false;
        }
        if (!Objects.equals(this.endMajor, other.endMajor)) {
            return false;
        }
        if (!Objects.equals(this.endMinor, other.endMinor)) {
            return false;
        }
        if (!Objects.equals(this.endIncrement, other.endIncrement)) {
            return false;
        }
        if (this.endQualifier != other.endQualifier) {
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
