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
public class SerialIteratorIteratorTest
{

    @Test
    public void test()
    {
        List<Iterator<String>> emptyList = Collections.emptyList();
        List<String> emptyStringList = Collections.emptyList();
        List<String> list1 = Arrays.asList("1", "2", "3", "4");
        List<String> list2 = Arrays.asList("a", "b", "c", "d");

        // test trivial cases with empty iterators
        assertIterateSame(emptyStringList.iterator(), new SerialIteratorIterator<String>(emptyList.iterator()));

        // trivial case, only one iterator
        assertIterateSame(list1.iterator(),
                new SerialIteratorIterator<String>(new Seq<Iterator<String>>(list1.iterator())));

        // test various combinations of empty and non-empty iterators
        assertIterateSame(Arrays.asList("1", "2", "3", "4", "1", "2", "3", "4").iterator(),
                new SerialIteratorIterator<String>(
                        new Seq<Iterator<String>>(list1.iterator(), list1.iterator())));
        assertIterateSame(Arrays.asList(
                "1", "2", "3", "4", "a", "b", "c", "d", "1", "2", "3", "4", "a", "b", "c", "d")
                        .iterator(),
                new SerialIteratorIterator<String>(
                        new Seq<Iterator<String>>(list1.iterator(), list2.iterator(), list1.iterator(),
                                list2.iterator())));

        assertIterateSame(Arrays.asList("1", "2", "3", "4", "a", "b", "c", "d").iterator(),
                new SerialIteratorIterator<String>(
                        new Seq<Iterator<String>>(emptyStringList.iterator(), list1.iterator(),
                                list2.iterator())));

        assertIterateSame(Arrays.asList("1", "2", "3", "4", "a", "b", "c", "d").iterator(),
                new SerialIteratorIterator<String>(
                        new Seq<Iterator<String>>(list1.iterator(), emptyStringList.iterator(),
                                list2.iterator())));

        assertIterateSame(Arrays.asList("1", "2", "3", "4", "a", "b", "c", "d").iterator(),
                new SerialIteratorIterator<String>(
                        new Seq<Iterator<String>>(list1.iterator(), list2.iterator(),
                                emptyStringList.iterator())));

        assertIterateSame(Arrays.asList("a", "b", "c", "d", "1", "2", "3", "4").iterator(),
                new SerialIteratorIterator<String>(
                        new Seq<Iterator<String>>(list2.iterator(), list1.iterator(),
                                emptyStringList.iterator())));
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
        new SerialIteratorIterator<String>(
                new SingletonIterator<Iterator<String>>(
                        EmptyIterator.<String>instance()))
                .next();
    }
}
