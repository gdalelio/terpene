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
        VersionRange v = VersionRange.valueOf(str);

        assertNotNull(v);
        assertTrue(v.isStartInclusive());
        assertFalse(v.isStartOpen());
        Version sv = v.getStartVersion().get();
        assertEquals(Integer.valueOf(1), sv.getMajor());
        assertEquals(Integer.valueOf(0), sv.getMinor());
        assertEquals(Integer.valueOf(0), sv.getIncrement());
        assertEquals(VersionQualifier.DEV, sv.getQualifier());
        assertTrue(v.isEndInclusive());
        assertFalse(v.isEndOpen());
        Version ev = v.getEndVersion().get();
        assertEquals(Integer.valueOf(2), ev.getMajor());
        assertEquals(Integer.valueOf(1), ev.getMinor());
        assertEquals(Integer.valueOf(15), ev.getIncrement());
        assertEquals(VersionQualifier.PROD, ev.getQualifier());
    }

    @Test
    public void testParseNoRange() throws Exception {
        final String str = "1.0-DEV";
       
        VersionRange v = VersionRange.valueOf(str);

        assertNull(v);
    }

    @Test
    public void testParseExact() throws Exception {
        final String str = "[1.0-DEV]";
        final Integer expectedMajor = 1;
        final Integer expectedMinor = 0;
        final Integer expectedIncrement = 0;
        final VersionQualifier expectedQualifier = VersionQualifier.DEV;

        VersionRange v = VersionRange.valueOf(str);

        assertNotNull(v);
        assertFalse(v.isStartOpen());
        assertTrue(v.isStartInclusive());
        Version sv = v.getStartVersion().get();
        assertEquals(expectedMajor, sv.getMajor());
        assertEquals(expectedMinor, sv.getMinor());
        assertEquals(expectedIncrement, sv.getIncrement());
        assertEquals(expectedQualifier, sv.getQualifier());
        assertTrue(v.isEndInclusive());
        assertFalse(v.isEndOpen());
        Version ev = v.getEndVersion().get();
        assertEquals(expectedMajor, ev.getMajor());
        assertEquals(expectedMinor, ev.getMinor());
        assertEquals(expectedIncrement, ev.getIncrement());
        assertEquals(expectedQualifier, ev.getQualifier());
    }

    @Test
    public void testParseInclusiveRange() throws Exception {
        final String str = "[1.2-DEV,1.3-DEV]";
        VersionRange v = VersionRange.valueOf(str);

        assertNotNull(v);
        assertFalse(v.isStartOpen());
        assertTrue(v.isStartInclusive());
        Version sv = v.getStartVersion().get();
        assertEquals(Integer.valueOf(1), sv.getMajor());
        assertEquals(Integer.valueOf(2), sv.getMinor());
        assertEquals(Integer.valueOf(0), sv.getIncrement());
        assertEquals(VersionQualifier.DEV, sv.getQualifier());
        assertTrue(v.isEndInclusive());
        assertFalse(v.isEndOpen());
        Version ev = v.getEndVersion().get();
        assertEquals(Integer.valueOf(1), ev.getMajor());
        assertEquals(Integer.valueOf(3), ev.getMinor());
        assertEquals(Integer.valueOf(0), ev.getIncrement());
        assertEquals(VersionQualifier.DEV, ev.getQualifier());
    }

    @Test
    public void testParseStartOpen() throws Exception {
        final String str = "(,1.0-DEV]";
        VersionRange v = VersionRange.valueOf(str);

        assertNotNull(v);
        assertTrue(v.isStartOpen());
        assertFalse(v.getStartVersion().isPresent());
        assertTrue(v.isEndInclusive());
        assertFalse(v.isEndOpen());
        Version ev = v.getEndVersion().get();
        assertEquals(Integer.valueOf(1), ev.getMajor());
        assertEquals(Integer.valueOf(0), ev.getMinor());
        assertEquals(Integer.valueOf(0), ev.getIncrement());
        assertEquals(VersionQualifier.DEV, ev.getQualifier());
    }

    @Test
    public void testParseEndExclusive() throws Exception {
        final String str = "[1.0-DEV,2.0-DEV)";
        VersionRange v = VersionRange.valueOf(str);

        assertNotNull(v);
        assertFalse(v.isStartOpen());
        assertTrue(v.isStartInclusive());
        Version sv = v.getStartVersion().get();
        assertEquals(Integer.valueOf(1), sv.getMajor());
        assertEquals(Integer.valueOf(0), sv.getMinor());
        assertEquals(Integer.valueOf(0), sv.getIncrement());
        assertEquals(VersionQualifier.DEV, sv.getQualifier());
        assertFalse(v.isEndInclusive());
        assertFalse(v.isEndOpen());
        Version ev = v.getEndVersion().get();
        assertEquals(Integer.valueOf(2), ev.getMajor());
        assertEquals(Integer.valueOf(0), ev.getMinor());
        assertEquals(Integer.valueOf(0), ev.getIncrement());
        assertEquals(VersionQualifier.DEV, ev.getQualifier());

    }

    @Test
    public void testParseEndOpen() throws Exception {
        final String str = "[1.5-DEV,)";
        VersionRange v = VersionRange.valueOf(str);

        assertNotNull(v);
        assertFalse(v.isStartOpen());
        assertTrue(v.isStartInclusive());
        Version sv = v.getStartVersion().get();
        assertEquals(Integer.valueOf(1), sv.getMajor());
        assertEquals(Integer.valueOf(5), sv.getMinor());
        assertEquals(Integer.valueOf(0), sv.getIncrement());
        assertEquals(VersionQualifier.DEV, sv.getQualifier());
        assertFalse(v.isEndInclusive());
        assertTrue(v.isEndOpen());
    }
}
