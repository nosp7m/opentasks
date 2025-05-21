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

import org.dmfs.jems.iterator.elementary.Seq;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


@SuppressWarnings("deprecation")
public class SerialIterableIteratorTest
{

    @Test
    public void test()
    {
        List<Iterable<String>> emptyList = Collections.emptyList();
        List<String> emptyStringList = Collections.emptyList();
        List<String> list1 = Arrays.asList("1", "2", "3", "4");
        List<String> list2 = Arrays.asList("a", "b", "c", "d");

        // test trivial cases with empty iterators
        assertIterateSame(emptyStringList.iterator(), new SerialIterableIterator<String>(emptyList.iterator()));

        // trivial case, only one iterator
        assertIterateSame(list1.iterator(),
                new SerialIterableIterator<String>(new Seq<Iterable<String>>(list1)));

        // test various combinations of empty and non-empty iterators
        assertIterateSame(Arrays.asList("1", "2", "3", "4", "1", "2", "3", "4").iterator(),
                new SerialIterableIterator<String>(
                        new Seq<Iterable<String>>(list1, list1)));
        assertIterateSame(Arrays.asList(
                "1", "2", "3", "4", "a", "b", "c", "d", "1", "2", "3", "4", "a", "b", "c", "d")
                        .iterator(),
                new SerialIterableIterator<String>(new Seq<Iterable<String>>(list1, list2, list1, list2)));

        assertIterateSame(Arrays.asList("1", "2", "3", "4", "a", "b", "c", "d").iterator(),
                new SerialIterableIterator<String>(
                        new Seq<Iterable<String>>(emptyStringList, list1, list2)));

        assertIterateSame(Arrays.asList("1", "2", "3", "4", "a", "b", "c", "d").iterator(),
                new SerialIterableIterator<String>(
                        new Seq<Iterable<String>>(list1, emptyStringList, list2)));

        assertIterateSame(Arrays.asList("1", "2", "3", "4", "a", "b", "c", "d").iterator(),
                new SerialIterableIterator<String>(
                        new Seq<Iterable<String>>(list1, list2, emptyStringList)));

        assertIterateSame(Arrays.asList("a", "b", "c", "d", "1", "2", "3", "4").iterator(),
                new SerialIterableIterator<String>(
                        new Seq<Iterable<String>>(list2, list1, emptyStringList)));
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
        new SerialIterableIterator<String>(
                new SingletonIterator<Iterable<String>>(
                        Collections.<String>emptyList()))
                .next();
    }
}
