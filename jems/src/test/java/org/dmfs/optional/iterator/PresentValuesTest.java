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

package org.dmfs.optional.iterator;

import org.dmfs.iterators.EmptyIterator;
import org.dmfs.iterators.SingletonIterator;
import org.dmfs.jems.iterator.elementary.Seq;
import org.dmfs.optional.Absent;
import org.dmfs.optional.Optional;
import org.dmfs.optional.Present;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Unit test for {@link PresentValues}.
 *
 * @author Gabor Keszthelyi
 */
public final class PresentValuesTest
{

    private static final Absent<String> ABSENT = Absent.absent();


    @Test
    public void test_whenEmptyInput_noElement()
    {
        assertFalse(new PresentValues<>(EmptyIterator.<Optional<String>>instance()).hasNext());
    }


    @Test
    public void test_whenOnePresentValue()
    {
        Iterator<Optional<String>> iterator = new SingletonIterator<Optional<String>>(new Present<>("hello"));

        PresentValues<String> result = new PresentValues<>(iterator);

        assertTrue(result.hasNext());
        assertEquals("hello", result.next());
        assertFalse(result.hasNext());
    }


    @Test
    public void test_whenOneAbsentValue()
    {
        Iterator<Optional<String>> iterator = new SingletonIterator<Optional<String>>(Absent.<String>absent());

        PresentValues<String> result = new PresentValues<>(iterator);

        assertFalse(result.hasNext());
    }


    @Test
    public void test_various()
    {
        Iterator<Optional<String>> iterator = new Seq<>(
                new Present<>("1"),
                ABSENT,
                ABSENT,
                new Present<>("2"),
                new Present<>("3"),
                ABSENT
        );

        PresentValues<String> result = new PresentValues<>(iterator);

        assertTrue(result.hasNext());
        assertEquals("1", result.next());
        assertTrue(result.hasNext());
        assertEquals("2", result.next());
        assertTrue(result.hasNext());
        assertEquals("3", result.next());

        assertFalse(result.hasNext());
    }


    public void test_Ctors()
    {
        assertFalse(new PresentValues<>().hasNext());
        assertFalse(new PresentValues<>(Absent.absent()).hasNext());
        assertFalse(new PresentValues<>(Absent.absent(), Absent.absent()).hasNext());
    }


    @Test
    public void test_whenOnePresentValue_ctors()
    {
        PresentValues<String> result = new PresentValues<>(new Present<>("hello"));

        assertTrue(result.hasNext());
        assertEquals("hello", result.next());
        assertFalse(result.hasNext());
    }


    @Test
    public void test_various_Ctors()
    {
        PresentValues<String> result = new PresentValues<>(new Present<>("1"),
                ABSENT,
                ABSENT,
                new Present<>("2"),
                new Present<>("3"),
                ABSENT);

        assertTrue(result.hasNext());
        assertEquals("1", result.next());
        assertTrue(result.hasNext());
        assertEquals("2", result.next());
        assertTrue(result.hasNext());
        assertEquals("3", result.next());

        assertFalse(result.hasNext());
    }

}