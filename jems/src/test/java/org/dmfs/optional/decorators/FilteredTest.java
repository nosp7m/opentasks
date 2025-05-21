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

package org.dmfs.optional.decorators;

import org.dmfs.iterators.Filter;
import org.dmfs.optional.Absent;
import org.dmfs.optional.Optional;
import org.dmfs.optional.Present;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


/**
 * Unit test for {@link Filtered}.
 *
 * @author Gabor Keszthelyi
 */
public class FilteredTest
{
    private static final Filter<Integer> SMALLER_THAN_TEN = new Filter<Integer>()
    {
        @Override
        public boolean iterate(Integer argument)
        {
            return argument < 10;
        }
    };


    @Test
    public void test_whenDelegatePresentAndFulfilsCondition_resultShouldBePresent()
    {
        Optional<Integer> result = new Filtered<>(SMALLER_THAN_TEN, new Present<>(8));

        assertTrue(result.isPresent());
        assertEquals(8, (int) result.value(77));
        assertEquals(8, (int) result.value());
    }


    @Test
    public void test_whenDelegatePresentAndNotFulfilsCondition_resultShouldBeAbsent()
    {
        Optional<Integer> result = new Filtered<>(SMALLER_THAN_TEN, new Present<>(12));

        assertFalse(result.isPresent());
        assertEquals(77, (int) result.value(77));
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
    public void test_whenDelegateAbsent_resultShouldBeAbsent()
    {
        Optional<Integer> result = new Filtered<>(SMALLER_THAN_TEN, new Absent<Integer>());

        assertFalse(result.isPresent());
        assertEquals(77, (int) result.value(77));
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