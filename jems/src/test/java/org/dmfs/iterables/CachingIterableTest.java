/*
 * Copyright 2017 dmfs GmbH
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dmfs.iterables;

import org.dmfs.iterators.EmptyIterator;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

import static org.junit.Assert.*;


@SuppressWarnings("deprecation")
public class CachingIterableTest
{

    @Test
    public void testEmptyIterator()
    {
        Iterable<Object> i = new CachingIterable<Object>(EmptyIterator.instance());
        assertFalse(i.iterator().hasNext());
        assertFalse(i.iterator().hasNext());
        assertFalse(i.iterator().hasNext());
        assertFalse(i.iterator().hasNext());
    }


    @Test
    public void testSingletonIterator()
    {
        Iterable<String> i = new CachingIterable<String>(Collections.singletonList("Testing").iterator());
        Iterator<String> i1 = i.iterator();
        assertTrue(i1.hasNext());
        assertEquals("Testing", i1.next());
        assertFalse(i1.hasNext());
        Iterator<String> i2 = i.iterator();
        assertTrue(i2.hasNext());
        assertEquals("Testing", i2.next());
        assertFalse(i2.hasNext());
        Iterator<String> i3 = i.iterator();
        assertTrue(i3.hasNext());
        assertEquals("Testing", i3.next());
        assertFalse(i3.hasNext());
        Iterator<String> i4 = i.iterator();
        assertTrue(i4.hasNext());
        assertEquals("Testing", i4.next());
        assertFalse(i4.hasNext());
    }


    /**
     * Test that all iterators return correct results when iterating them till the end before creating a new one.
     */
    @Test
    public void testSimpleIterator()
    {
        Iterable<String> i = new CachingIterable<String>(
                Arrays.asList(new String[] { "a", "1", null, "x", "Testing" }).iterator());
        Iterator<String> i1 = i.iterator();
        assertTrue(i1.hasNext());
        assertEquals("a", i1.next());
        assertTrue(i1.hasNext());
        assertEquals("1", i1.next());
        assertTrue(i1.hasNext());
        assertNull(i1.next());
        assertTrue(i1.hasNext());
        assertEquals("x", i1.next());
        assertTrue(i1.hasNext());
        assertEquals("Testing", i1.next());
        assertFalse(i1.hasNext());

        Iterator<String> i2 = i.iterator();
        assertTrue(i2.hasNext());
        assertEquals("a", i2.next());
        assertTrue(i2.hasNext());
        assertEquals("1", i2.next());
        assertTrue(i2.hasNext());
        assertNull(i2.next());
        assertTrue(i2.hasNext());
        assertEquals("x", i2.next());
        assertTrue(i2.hasNext());
        assertEquals("Testing", i2.next());
        assertFalse(i2.hasNext());

        Iterator<String> i3 = i.iterator();
        assertTrue(i3.hasNext());
        assertEquals("a", i3.next());
        assertTrue(i3.hasNext());
        assertEquals("1", i3.next());
        assertTrue(i3.hasNext());
        assertNull(i3.next());
        assertTrue(i3.hasNext());
        assertEquals("x", i3.next());
        assertTrue(i3.hasNext());
        assertEquals("Testing", i3.next());
        assertFalse(i3.hasNext());

        Iterator<String> i4 = i.iterator();
        assertTrue(i4.hasNext());
        assertEquals("a", i4.next());
        assertTrue(i4.hasNext());
        assertEquals("1", i4.next());
        assertTrue(i4.hasNext());
        assertNull(i4.next());
        assertTrue(i4.hasNext());
        assertEquals("x", i4.next());
        assertTrue(i4.hasNext());
        assertEquals("Testing", i4.next());
        assertFalse(i4.hasNext());
    }


    /**
     * Test that all iterators return correct results when creating and iterating a new one before the others have been
     * fully iterated.
     */
    @Test
    public void testSimpleIteratorAlternating()
    {
        Iterable<String> i = new CachingIterable<String>(
                Arrays.asList(new String[] { "a", "1", null, "x", "Testing" }).iterator());
        Iterator<String> i4 = i.iterator();
        Iterator<String> i3 = i.iterator();
        Iterator<String> i2 = i.iterator();
        Iterator<String> i1 = i.iterator();

        assertTrue(i1.hasNext());
        assertEquals("a", i1.next());
        assertTrue(i2.hasNext());
        assertEquals("a", i2.next());
        assertTrue(i2.hasNext());
        assertEquals("1", i2.next());
        assertTrue(i1.hasNext());
        assertEquals("1", i1.next());
        assertTrue(i1.hasNext());
        assertTrue(i3.hasNext());
        assertEquals("a", i3.next());
        assertTrue(i3.hasNext());
        assertEquals("1", i3.next());
        assertTrue(i3.hasNext());
        assertNull(i3.next());
        assertNull(i1.next());
        assertTrue(i1.hasNext());
        assertEquals("x", i1.next());
        assertTrue(i1.hasNext());
        assertTrue(i4.hasNext());
        assertEquals("a", i4.next());
        assertTrue(i4.hasNext());
        assertEquals("1", i4.next());
        assertTrue(i4.hasNext());
        assertNull(i4.next());
        assertTrue(i4.hasNext());
        assertEquals("x", i4.next());
        assertTrue(i4.hasNext());
        assertEquals("Testing", i4.next());
        assertFalse(i4.hasNext());
        assertEquals("Testing", i1.next());
        assertFalse(i1.hasNext());
        assertTrue(i2.hasNext());
        assertNull(i2.next());
        assertTrue(i3.hasNext());
        assertEquals("x", i3.next());
        assertTrue(i3.hasNext());
        assertEquals("Testing", i3.next());
        assertFalse(i3.hasNext());
        assertTrue(i2.hasNext());
        assertEquals("x", i2.next());
        assertTrue(i2.hasNext());
        assertEquals("Testing", i2.next());
        assertFalse(i2.hasNext());

    }
}
