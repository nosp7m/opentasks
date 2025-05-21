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

package org.dmfs.optional;

import org.dmfs.iterators.EmptyIterator;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


/**
 * Unit test for {@link Next}.
 *
 * @author Gabor Keszthelyi
 */
public class NextTest
{
    @Test
    public void test_whenIteratorIsEmpty_resultShouldBeAbsent()
    {
        Next<String> result = new Next<>(EmptyIterator.<String>instance());

        assertFalse(result.isPresent());
        assertEquals("hi", result.value("hi"));
        try
        {
            result.value();
            fail();
        }
        catch (NoSuchElementException e)
        {
        }
    }


    @Test
    public void test_whenIteratorHasNextValue_resultShouldBeThat()
    {
        Next<String> result = new Next<>(Arrays.asList("a", "b", "c").iterator());

        assertTrue(result.isPresent());
        assertEquals("a", result.value("hi"));
        assertEquals("a", result.value());
    }


    @Test
    public void test_whenIteratorHasMovedAlready_resultShouldBeNext()
    {
        Iterator<String> iterator = Arrays.asList("a", "b", "c").iterator();
        iterator.next();
        Next<String> result = new Next<>(iterator);

        assertTrue(result.isPresent());
        assertEquals("b", result.value("hi"));
        assertEquals("b", result.value());
    }


    @Test
    public void test_whenIteratorReachedTheEnd_resultShouldBeAbsent()
    {
        Iterator<String> iterator = Arrays.asList("a", "b", "c").iterator();
        iterator.next();
        iterator.next();
        iterator.next();
        Next<String> result = new Next<>(iterator);

        assertFalse(result.isPresent());
        assertEquals("hi", result.value("hi"));
        try
        {
            result.value();
            fail();
        }
        catch (NoSuchElementException e)
        {
        }
    }

}