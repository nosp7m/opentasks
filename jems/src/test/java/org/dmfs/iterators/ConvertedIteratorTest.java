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

import org.dmfs.iterators.AbstractConvertedIterator.Converter;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


@SuppressWarnings("deprecation")
public class ConvertedIteratorTest
{

    @Test
    public void test()
    {
        List<String> emptyList = Collections.emptyList();
        List<String> list1 = Arrays.asList("1", "2", "3", "4", "3", "2", "1");

        Converter<Integer, String> converter = new Converter<Integer, String>()
        {
            @Override
            public Integer convert(String element)
            {
                return Integer.parseInt(element);
            }
        };

        assertIterateSame(Arrays.asList(new Integer[] {}).iterator(),
                new ConvertedIterator<Integer, String>(emptyList.iterator(), converter));
        assertIterateSame(Arrays.asList(new Integer[] { 1, 2, 3, 4, 3, 2, 1 }).iterator(),
                new ConvertedIterator<Integer, String>(list1.iterator(), converter));
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
