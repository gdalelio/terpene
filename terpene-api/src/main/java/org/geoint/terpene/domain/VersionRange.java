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

import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A version range with a an optional start and end version.
 *
 * @author steve_siebert
 */
public final class VersionRange {

    private final boolean startInclusive;
    private final Optional<Version> startVersion;
    private final boolean endInclusive;
    private final Optional<Version> endVersion;
    private final String stringRepresentation;

    private static final Logger LOGGER = Logger.getLogger(VersionRange.class.getName());
    private static final String START_INCLUSIVE = "[";
    private static final String START_EXCLUSIVE = "(";
    private static final String END_INCLUSIVE = "]";
    private static final String END_EXCLUSIVE = ")";
    private static final String RANGE_SEPARATOR = ",";
    private static final Pattern VERSION_PATTERN = Pattern.compile(
            "([\\" + START_INCLUSIVE + "\\" + START_EXCLUSIVE + "])?" //[1] start inclusive/exclusive
            + Version.VERSION_PATTERN
            + "(\\" + RANGE_SEPARATOR + "(" + Version.VERSION_PATTERN + "))?"
            + "([\\" + END_INCLUSIVE + "\\" + END_EXCLUSIVE + "])?");//[15] end inclusive/eclusive; complete optional end version

    private VersionRange(String stringRepresentation,
            boolean startInclusive, Version startVersion,
            boolean endInclusive, Version endVersion) {
        this.startInclusive = startInclusive;
        this.startVersion = Optional.ofNullable(startVersion);
        this.endInclusive = endInclusive;
        this.endVersion = Optional.ofNullable(endVersion);
        this.stringRepresentation = stringRepresentation;
    }

    public VersionRange(boolean startInclusive, Version startVersion,
            boolean endInclusive, Version endVersion) {
        this(format(startInclusive, startVersion, endInclusive, endVersion),
                startInclusive, startVersion, endInclusive, endVersion);
    }

    /**
     * Returns the version or null if the string is not a valid format.
     *
     * @param versionString string formatted version
     * @return version or null if not a valid version string format
     */
    public static VersionRange valueOf(final String versionString) {
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
            Version startVersion = Version.valueOf(m.group(2));
            Version endVersion = Version.valueOf(m.group(8));
            boolean endInclusive = (m.group(15) == null)
                    ? true //default is inclusive
                    : m.group(15).contentEquals(END_INCLUSIVE);

            return new VersionRange(versionString, startInclusive, startVersion,
                    endInclusive, endVersion);

        } catch (NullPointerException | IllegalArgumentException ex) {
            //invalid version format, log and return null
            LOGGER.log(Level.FINER, () -> String.format("%s is not a valid "
                    + "string formatted version range", versionString));
            return null;
        }
    }

    /**
     * Test to see if this version falls within the provided version.
     *
     * @param version test version
     * @return true if this version falls within the version of the provided
     * version, otherwise false
     */
    public boolean isWithin(Version version) {
        Optional<Integer> startCompare = this.startVersion.map((v) -> v.compareTo(version));
        if (startCompare.isPresent()) {
            int maxCompareReq = (startInclusive) ? 0 : -1;
            if (startCompare.get() >= maxCompareReq) {
                return false;
            }
        }

        Optional<Integer> endCompare = this.endVersion.map((v) -> v.compareTo(version));
        if (endCompare.isPresent()) {
            int minCompareReq = (endInclusive) ? 0 : 1;
            if (endCompare.get() <= minCompareReq) {
                return false;
            }
        }
        return true;
    }

    private static String format(boolean startInclusive, Version startVersion,
            boolean endInclusive, Version endVersion) {

        StringBuilder sb = new StringBuilder();
        sb.append((startInclusive) ? START_INCLUSIVE : START_EXCLUSIVE);

        if (startVersion != null) {
            sb.append(startVersion.asString());
        }
        if (startVersion != null) {
            sb.append(RANGE_SEPARATOR)
                    .append(endVersion.asString());
        }

        sb.append((endInclusive) ? END_INCLUSIVE : END_EXCLUSIVE);
        return sb.toString();
    }
}
