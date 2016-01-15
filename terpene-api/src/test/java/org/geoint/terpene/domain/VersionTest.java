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
public class VersionTest {

    public VersionTest() {
    }

    @Test
    public void testParseComplete() {
        final String str = "1.2.3-DEV";
        Version v = Version.valueOf(str);

        assertNotNull(v);
        assertEquals(v.getMajor(), Integer.valueOf(1));
        assertEquals(v.getMinor(), Integer.valueOf(2));
        assertEquals(v.getIncrement(), Integer.valueOf(3));
        assertEquals(v.getQualifier(), VersionQualifier.DEV);
    }

    @Test
    public void testParseNoIncrement() {
        final String str = "1.2-DEV";
        Version v = Version.valueOf(str);

        assertNotNull(v);
        assertEquals(v.getMajor(), Integer.valueOf(1));
        assertEquals(v.getMinor(), Integer.valueOf(2));
        assertEquals(v.getIncrement(), Integer.valueOf(0));
        assertEquals(v.getQualifier(), VersionQualifier.DEV);
    }

    @Test
    public void testParseFailNoMinor() {
        final String str = "1-DEV";
        Version v = Version.valueOf(str);

        assertNull(v);
    }

    @Test
    public void testParseFailNoQualifier() {
        final String str = "1.2.3";
        Version v = Version.valueOf(str);

        assertNull(v);
    }

    @Test
    public void testParseFailNull() {
        Version v = Version.valueOf(null);

        assertNull(v);
    }

    @Test
    public void testParseFailInvalidString() {
        final String str = "foo";
        Version v = Version.valueOf(str);

        assertNull(v);
    }

    @Test
    public void testAsStringComplete() {
        final String str = "1.2.3-DEV";
        final Version parsed = Version.valueOf(str);
        assertEquals(str, parsed.asString());
    }

    @Test
    public void testAsStringNoIncrement() {
        final String str = "1.2-DEV";
        final String expected = "1.2.0-DEV";
        assertEquals(expected, Version.valueOf(str).asString());
    }

    @Test
    public void testIsLessThanMajor() {
        final Version lower = new Version(1, 2, 3, VersionQualifier.DEV);
        final Version higher = new Version(2, 2, 3, VersionQualifier.DEV);
        assertTrue(lower.isLessThan(higher));
        assertFalse(higher.isLessThan(lower));
    }

    @Test
    public void testIsLessThanMinor() {
        final Version lower = new Version(1, 2, 3, VersionQualifier.DEV);
        final Version higher = new Version(1, 3, 3, VersionQualifier.DEV);
        assertTrue(lower.isLessThan(higher));
        assertFalse(higher.isLessThan(lower));
    }

    @Test
    public void testIsLessThanIncrement() {
        final Version lower = new Version(1, 2, 3, VersionQualifier.DEV);
        final Version higher = new Version(1, 2, 4, VersionQualifier.DEV);
        assertTrue(lower.isLessThan(higher));
        assertFalse(higher.isLessThan(lower));
    }

    @Test
    public void testIsLessThanQualifier() {
        final Version lower = new Version(1, 2, 3, VersionQualifier.DEV);
        final Version higher = new Version(1, 2, 3, VersionQualifier.ALPHA);
        assertTrue(lower.isLessThan(higher));
        assertFalse(higher.isLessThan(lower));
    }

    @Test
    public void testIsGreaterThanMajor() {
        final Version lower = new Version(1, 2, 3, VersionQualifier.DEV);
        final Version higher = new Version(2, 2, 3, VersionQualifier.DEV);
        assertTrue(lower.isLessThan(higher));
        assertFalse(higher.isLessThan(lower));
    }

    @Test
    public void testIsGreaterThanMinor() {

    }

    @Test
    public void testIsGreaterThanIncrement() {

    }

    @Test
    public void testIsGreaterThanQualifier() {

    }

    @Test
    public void testGetMajor() {
    }

    @Test
    public void testGetMinor() {
    }

    @Test
    public void testGetIncrement() {
    }

    @Test
    public void testGetQualifier() {
    }

    @Test
    public void testCompareTo() {
    }

    @Test
    public void testToString() {
    }

    @Test
    public void testHashCode() {
    }

    @Test
    public void testEquals() {
    }

}
