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

package org.dmfs.iterators;

import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;


public class UnquotedSplitTest
{

    @Test
    public void testEmptyString()
    {
        // there is always at least one element
        Iterator<CharSequence> iterator = new UnquotedSplit("", ';');
        assertTrue(iterator.hasNext());
        assertEquals("", iterator.next());
        assertFalse(iterator.hasNext());
    }


    @Test
    public void testTrivialString()
    {
        Iterator<CharSequence> iterator = new UnquotedSplit("abcde", ';');
        assertTrue(iterator.hasNext());
        assertEquals("abcde", iterator.next());
        assertFalse(iterator.hasNext());
    }


    @Test
    public void testSimpleString()
    {
        Iterator<CharSequence> iterator = new UnquotedSplit("abc;de", ';');
        assertTrue(iterator.hasNext());
        assertEquals("abc", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("de", iterator.next());
        assertFalse(iterator.hasNext());
    }


    @Test
    public void testSimpleSeparatorString()
    {
        Iterator<CharSequence> iterator = new UnquotedSplit(";", ';');
        assertTrue(iterator.hasNext());
        assertEquals("", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("", iterator.next());
        assertFalse(iterator.hasNext());
    }


    @Test
    public void testSimpleSeparatorString2()
    {
        Iterator<CharSequence> iterator = new UnquotedSplit(";;", ';');
        assertTrue(iterator.hasNext());
        assertEquals("", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("", iterator.next());
        assertFalse(iterator.hasNext());
    }


    @Test
    public void testSeparatorString()
    {
        Iterator<CharSequence> iterator = new UnquotedSplit("abcde;fghi;jkl", ';');
        assertTrue(iterator.hasNext());
        assertEquals("abcde", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("fghi", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("jkl", iterator.next());
        assertFalse(iterator.hasNext());
    }


    @Test
    public void testSeparatorString2()
    {
        Iterator<CharSequence> iterator = new UnquotedSplit(";abcde;fghi;jkl", ';');
        assertTrue(iterator.hasNext());
        assertEquals("", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("abcde", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("fghi", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("jkl", iterator.next());
        assertFalse(iterator.hasNext());
    }


    @Test
    public void testSeparatorString3()
    {
        Iterator<CharSequence> iterator = new UnquotedSplit(";abcde;fghi;jkl;", ';');
        assertTrue(iterator.hasNext());
        assertEquals("", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("abcde", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("fghi", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("jkl", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("", iterator.next());
        assertFalse(iterator.hasNext());
    }


    @Test(expected = NoSuchElementException.class)
    public void testSeparatorString4()
    {
        Iterator<CharSequence> iterator = new UnquotedSplit(";abcde;fghi;jkl;", ';');
        assertTrue(iterator.hasNext());
        assertEquals("", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("abcde", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("fghi", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("jkl", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("", iterator.next());
        iterator.next();
    }


    @Test
    public void testSimpleQuotedString()
    {
        Iterator<CharSequence> iterator = new UnquotedSplit("\"abc;de\"", ';', '"');
        assertTrue(iterator.hasNext());
        assertEquals("\"abc;de\"", iterator.next());
        assertFalse(iterator.hasNext());
    }


    @Test
    public void testSimpleQuotedSeparatorString()
    {
        Iterator<CharSequence> iterator = new UnquotedSplit("\";\"", ';', '"');
        assertTrue(iterator.hasNext());
        assertEquals("\";\"", iterator.next());
        assertFalse(iterator.hasNext());
    }


    @Test
    public void testSimpleQuotedSeparatorString2()
    {
        Iterator<CharSequence> iterator = new UnquotedSplit(";\";\"", ';', '"');
        assertTrue(iterator.hasNext());
        assertEquals("", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("\";\"", iterator.next());
        assertFalse(iterator.hasNext());
    }


    @Test
    public void testSimpleQuotedSeparatorString3()
    {
        Iterator<CharSequence> iterator = new UnquotedSplit("\";\";", ';', '"');
        assertTrue(iterator.hasNext());
        assertEquals("\";\"", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("", iterator.next());
        assertFalse(iterator.hasNext());
    }


    @Test
    public void testSimpleQuotedSeparatorString3_no_hasNext()
    {
        Iterator<CharSequence> iterator = new UnquotedSplit("\";\";", ';', '"');
        assertEquals("\";\"", iterator.next());
        assertEquals("", iterator.next());
        assertFalse(iterator.hasNext());
    }


    @Test
    public void testQuotedSeparatorString()
    {
        Iterator<CharSequence> iterator = new UnquotedSplit("\"abcde;fghi\";jkl", ';', '"');
        assertTrue(iterator.hasNext());
        assertEquals("\"abcde;fghi\"", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("jkl", iterator.next());
        assertFalse(iterator.hasNext());
    }


    @Test
    public void testQuotedSeparatorString_no_hasNext()
    {
        Iterator<CharSequence> iterator = new UnquotedSplit("\"abcde;fghi\";jkl", ';', '"');
        assertEquals("\"abcde;fghi\"", iterator.next());
        assertEquals("jkl", iterator.next());
        assertFalse(iterator.hasNext());
    }
}
