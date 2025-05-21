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
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


public class ArrayIteratorTest
{

    @Test
    public void test()
    {
        List<String> emptyList = Arrays.asList(new String[] {});
        List<String> list1 = Arrays.asList("1");
        List<String> list2 = Arrays.asList("a", "1", "3");
        List<String> list3 = Arrays.asList("a", "1", null, "3");

        assertIterateSame(emptyList.iterator(), new ArrayIterator<String>());
        assertIterateSame(list1.iterator(), new ArrayIterator<String>("1"));
        assertIterateSame(list2.iterator(), new ArrayIterator<String>("a", "1", "3"));
        assertIterateSame(list3.iterator(), new ArrayIterator<String>("a", "1", null, "3"));

        assertIterateSame(list1.iterator(), new ArrayIterator<String>("1"));
        assertIterateSame(list2.iterator(), new ArrayIterator<String>("a", "1", "3"));
        assertIterateSame(list3.iterator(), new ArrayIterator<String>("a", "1", null, "3"));
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
