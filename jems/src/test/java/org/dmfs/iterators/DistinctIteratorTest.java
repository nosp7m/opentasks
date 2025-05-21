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

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


@SuppressWarnings("deprecation")
public class DistinctIteratorTest
{

    @Test
    public void test()
    {
        List<String> emptyList = Collections.emptyList();
        List<String> list1 = Arrays.asList("1", "2", "3", "4");
        List<String> list2 = Arrays.asList("1", "2", "3", "4", "3", "2", "1");
        List<String> list3 = Arrays.asList("1", "2", "3", "4", "3", "2", "1", "3", "3", "5");
        List<String> list4 = Arrays.asList("1", "2", "3", "4", "5");

        // test trivial case with empty iterator
        assertIterateSame(emptyList.iterator(), new DistinctIterator<String>(emptyList.iterator()));

        // no duplicates
        assertIterateSame(list1.iterator(), new DistinctIterator<String>(list1.iterator()));

        // some duplicates
        assertIterateSame(list1.iterator(), new DistinctIterator<String>(list2.iterator()));

        // some more duplicates
        assertIterateSame(list4.iterator(), new DistinctIterator<String>(list3.iterator()));
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
}
