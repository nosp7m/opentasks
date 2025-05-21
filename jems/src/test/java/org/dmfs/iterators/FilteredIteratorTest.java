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
public class FilteredIteratorTest
{

    @Test
    public void test()
    {
        List<String> emptyList = Collections.emptyList();
        List<String> list1 = Arrays.asList("1", "2", "3", "4", "3", "2", "1");

        // test trivial cases with empty iterators
        assertIterateSame(emptyList.iterator(),
                new FilteredIterator<String>(emptyList.iterator(), new AbstractFilteredIterator.IteratorFilter<String>()
                {
                    @Override
                    public boolean iterate(String element)
                    {
                        return true;
                    }
                }));

        assertIterateSame(emptyList.iterator(),
                new FilteredIterator<String>(emptyList.iterator(), new AbstractFilteredIterator.IteratorFilter<String>()
                {
                    @Override
                    public boolean iterate(String element)
                    {
                        return false;
                    }
                }));

        // filter no elements
        assertIterateSame(list1.iterator(),
                new FilteredIterator<String>(list1.iterator(), new AbstractFilteredIterator.IteratorFilter<String>()
                {
                    @Override
                    public boolean iterate(String element)
                    {
                        return true;
                    }
                }));

        // filter all elements
        assertIterateSame(emptyList.iterator(),
                new FilteredIterator<String>(list1.iterator(), new AbstractFilteredIterator.IteratorFilter<String>()
                {
                    @Override
                    public boolean iterate(String element)
                    {
                        return false;
                    }
                }));

        // filter one element
        assertIterateSame(Arrays.asList(new String[] { "1", "2", "3", "3", "2", "1" }).iterator(),
                new FilteredIterator<String>(list1.iterator(),
                        new AbstractFilteredIterator.IteratorFilter<String>()
                        {
                            @Override
                            public boolean iterate(String element)
                            {
                                return !"4".equals(element);
                            }
                        }));

        // filter some elements
        assertIterateSame(Arrays.asList(new String[] { "2", "3", "3", "2" }).iterator(),
                new FilteredIterator<String>(list1.iterator(),
                        new AbstractFilteredIterator.IteratorFilter<String>()
                        {
                            @Override
                            public boolean iterate(String element)
                            {
                                return !"1".equals(element) && !"4".equals(element);
                            }
                        }));

        // filter most elements
        assertIterateSame(Arrays.asList(new String[] { "4" }).iterator(),
                new FilteredIterator<String>(list1.iterator(), new AbstractFilteredIterator.IteratorFilter<String>()
                {
                    @Override
                    public boolean iterate(String element)
                    {
                        return "4".equals(element);
                    }
                }));
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
