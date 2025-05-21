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

package org.dmfs.iterators.decorators;

import org.dmfs.iterators.EmptyIterator;
import org.dmfs.iterators.SingletonIterator;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;


public class SerializedTest
{

    @Test
    public void test()
    {
        List<String> emptyList = Collections.emptyList();
        List<String> list1 = Arrays.asList("1", "2", "3", "4");
        List<String> list2 = Arrays.asList("a", "b", "c", "d");

        // test trivial cases without or with empty iterators
        assertIterateSame(emptyList.iterator(), new Serialized<String>());
        assertIterateSame(emptyList.iterator(), new Serialized<>(emptyList.iterator()));
        assertIterateSame(emptyList.iterator(),
                new Serialized<>(emptyList.iterator(), emptyList.iterator(), emptyList.iterator()));
        assertIterateSame(emptyList.iterator(), new Serialized<>(
                Arrays.asList(emptyList.iterator(), emptyList.iterator(), emptyList.iterator()).iterator()));

        // trivial case, only one iterator
        assertIterateSame(list1.iterator(), new Serialized<>(list1.iterator()));
        assertIterateSame(list1.iterator(), new Serialized<>(new SingletonIterator<>(list1.iterator())));

        // test various combinations of empty and non-empty iterators
        assertIterateSame(Arrays.asList("1", "2", "3", "4", "1", "2", "3", "4").iterator(),
                new Serialized<>(list1.iterator(), list1.iterator()));
        assertIterateSame(Arrays.asList("1", "2", "3", "4", "1", "2", "3", "4").iterator(),
                new Serialized<>(Arrays.asList(list1.iterator(), list1.iterator()).iterator()));
        assertIterateSame(Arrays.asList(
                "1", "2", "3", "4", "a", "b", "c", "d", "1", "2", "3", "4", "a", "b", "c", "d")
                        .iterator(),
                new Serialized<>(list1.iterator(), list2.iterator(), list1.iterator(), list2.iterator()));

        assertIterateSame(Arrays.asList("1", "2", "3", "4", "a", "b", "c", "d").iterator(),
                new Serialized<>(emptyList.iterator(),
                        list1.iterator(), list2.iterator()));

        assertIterateSame(Arrays.asList("1", "2", "3", "4", "a", "b", "c", "d").iterator(),
                new Serialized<>(list1.iterator(),
                        emptyList.iterator(), list2.iterator()));

        assertIterateSame(Arrays.asList("1", "2", "3", "4", "a", "b", "c", "d").iterator(),
                new Serialized<>(list1.iterator(), list2.iterator(), emptyList.iterator()));

        assertIterateSame(Arrays.asList("a", "b", "c", "d", "1", "2", "3", "4").iterator(),
                new Serialized<>(list2.iterator(), list1.iterator(), emptyList.iterator()));
    }


    @Test
    public void testOveriterate()
    {
        List<String> list1 = Arrays.asList("1", "2");
        List<String> list2 = Arrays.asList("a", "b");

        Iterator<String> iterator = new Serialized<>(list1.iterator(), list2.iterator());
        assertEquals("1", iterator.next());
        assertEquals("2", iterator.next());
        assertEquals("a", iterator.next());
        assertEquals("b", iterator.next());
        try
        {
            iterator.next();
            fail("Did not throw NoSuchElementException");
        }
        catch (NoSuchElementException e)
        {
            // passed
        }
        catch (Exception e)
        {
            fail(String.format("Wrong Exception Type %s thrown", e.getClass().getName()));
        }
    }


    /**
     * Assert that two iterators return equal results.
     *
     * @param iterator1
     * @param iterator2
     */
    private <E> void assertIterateSame(Iterator<E> iterator1, Iterator<E> iterator2)
    {
        while (iterator1.hasNext())
        {
            assertEquals(iterator1.next(), iterator2.next());
        }

        assertFalse(iterator2.hasNext());
    }


    @Test(expected = NoSuchElementException.class)
    public void testNextThrows()
    {
        new Serialized<>(EmptyIterator.instance()).next();
    }
}
