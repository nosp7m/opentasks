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

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;


public class SingletonIteratorTest
{

    @Test
    public void test()
    {
        List<String> list1 = Collections.singletonList("1");
        List<String> list2 = Collections.singletonList("a");
        List<String> list3 = Collections.singletonList((String) null);

        // test trivial cases without or with empty iterators
        assertIterateSame(list1.iterator(), new SingletonIterator<String>("1"));
        assertIterateSame(list2.iterator(), new SingletonIterator<String>("a"));
        assertIterateSame(list3.iterator(), new SingletonIterator<String>(null));
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
        Iterator<String> iterator = new SingletonIterator("X");
        try
        {
            iterator.next();
        }
        catch (NoSuchElementException e)
        {
            fail("Thrown Exception for wrong call to next()");
        }
        iterator.next();
    }

}
