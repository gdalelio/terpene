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

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author steve_siebert
 */
public class VersionRangeTest {

    /**
     * Test parsing a version string which contains every possible version
     * component.
     *
     * @throws Exception
     */
    @Test
    public void testParseCompleteVersionComponents() throws Exception {
        final String str = "[1.0.0-DEV,2.1.15-PROD]";
        Version v = Version.valueOf(str);

        assertNotNull(v);
        assertTrue(v.isStartInclusive());
        assertEquals(Integer.valueOf(1), v.getStartMajor());
        assertEquals(Integer.valueOf(0), v.getStartMinor());
        assertEquals(Integer.valueOf(0), v.getStartIncrement().get());
        assertEquals(VersionQualifier.DEV, v.getStartQualifier());
        assertTrue(v.isEndInclusive());
        assertEquals(Integer.valueOf(2), v.getEndMajor());
        assertEquals(Integer.valueOf(1), v.getEndMinor());
        assertEquals(Integer.valueOf(15), v.getEndIncrement().get());
        assertEquals(VersionQualifier.PROD, v.getEndQualifier());

    }

    @Test
    public void testParseNoConstraints() throws Exception {
        final String str = "1.0-DEV";
        Version v = Version.valueOf(str);

        assertNotNull(v);
        assertFalse(v.isStartOpen());
        assertTrue(v.isStartInclusive());
        assertEquals(Integer.valueOf(1), v.getStartMajor());
        assertEquals(Integer.valueOf(0), v.getStartMinor());
        assertFalse(v.getStartIncrement().isPresent());
        assertEquals(VersionQualifier.DEV, v.getStartQualifier());
        assertTrue(v.isEndInclusive());
        assertEquals(Integer.valueOf(1), v.getEndMajor());
        assertEquals(Integer.valueOf(0), v.getEndMinor());
        assertFalse(v.getEndIncrement().isPresent());
        assertEquals(VersionQualifier.DEV, v.getEndQualifier());
    }

    @Test
    public void testParseExact() throws Exception {
        final String str = "[1.0-DEV]";
        Version v = Version.valueOf(str);

        assertNotNull(v);
        assertFalse(v.isStartOpen());
        assertTrue(v.isStartInclusive());
        assertEquals(Integer.valueOf(1), v.getStartMajor());
        assertEquals(Integer.valueOf(0), v.getStartMinor());
        assertFalse(v.getStartIncrement().isPresent());
        assertEquals(VersionQualifier.DEV, v.getStartQualifier());
        assertTrue(v.isEndInclusive());
        assertEquals(Integer.valueOf(1), v.getEndMajor());
        assertEquals(Integer.valueOf(0), v.getEndMinor());
        assertFalse(v.getEndIncrement().isPresent());
        assertEquals(VersionQualifier.DEV, v.getEndQualifier());
    }

    @Test
    public void testParseInclusiveRange() throws Exception {
        final String str = "[1.2-DEV,1.3-DEV]";
        Version v = Version.valueOf(str);

        assertNotNull(v);
        assertFalse(v.isStartOpen());
        assertTrue(v.isStartInclusive());
        assertEquals(Integer.valueOf(1), v.getStartMajor());
        assertEquals(Integer.valueOf(2), v.getStartMinor());
        assertFalse(v.getStartIncrement().isPresent());
        assertEquals(VersionQualifier.DEV, v.getStartQualifier());
        assertTrue(v.isEndInclusive());
        assertEquals(Integer.valueOf(1), v.getEndMajor());
        assertEquals(Integer.valueOf(3), v.getEndMinor());
        assertFalse(v.getEndIncrement().isPresent());
        assertEquals(VersionQualifier.DEV, v.getEndQualifier());
    }

    @Test
    public void testParseStartOpen() throws Exception {
        final String str = "(,1.0-DEV]";
        Version v = Version.valueOf(str);

        assertNotNull(v);
        assertTrue(v.isStartOpen());
        assertNull(v.getStartMajor());
        assertNull(v.getStartMinor());
        assertFalse(v.getStartIncrement().isPresent());
        assertNull(v.getStartQualifier());
        assertTrue(v.isEndInclusive());
        assertFalse(v.isEndOpen());
        assertEquals(Integer.valueOf(1), v.getEndMajor());
        assertEquals(Integer.valueOf(0), v.getEndMinor());
        assertFalse(v.getEndIncrement().isPresent());
        assertEquals(VersionQualifier.DEV, v.getEndQualifier());
    }

    @Test
    public void testParseEndExclusive() throws Exception {
        final String str = "[1.0-DEV,2.0-DEV)";
        Version v = Version.valueOf(str);

        assertNotNull(v);
        assertFalse(v.isStartOpen());
        assertTrue(v.isStartInclusive());
        assertEquals(Integer.valueOf(1), v.getStartMajor());
        assertEquals(Integer.valueOf(0), v.getStartMinor());
        assertFalse(v.getStartIncrement().isPresent());
        assertEquals(VersionQualifier.DEV, v.getStartQualifier());
        assertFalse(v.isEndInclusive());
        assertEquals(Integer.valueOf(2), v.getEndMajor());
        assertEquals(Integer.valueOf(0), v.getEndMinor());
        assertFalse(v.getEndIncrement().isPresent());
        assertEquals(VersionQualifier.DEV, v.getEndQualifier());

    }

    @Test
    public void testParseEndOpen() throws Exception {
        final String str = "[1.5-DEV,)";
        Version v = Version.valueOf(str);

        assertNotNull(v);
        assertFalse(v.isStartOpen());
        assertTrue(v.isStartInclusive());
        assertEquals(Integer.valueOf(1), v.getStartMajor());
        assertEquals(Integer.valueOf(5), v.getStartMinor());
        assertFalse(v.getStartIncrement().isPresent());
        assertEquals(VersionQualifier.DEV, v.getStartQualifier());
        assertFalse(v.isEndInclusive());
        assertTrue(v.isEndOpen());

    }

}
