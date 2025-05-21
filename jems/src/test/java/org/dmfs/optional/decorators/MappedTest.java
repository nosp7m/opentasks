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

import org.dmfs.iterators.Function;
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
 * Unit test for {@link Mapped}
 *
 * @author Gabor Keszthelyi
 */
public class MappedTest
{
    private static final Function<Boolean, Integer> CONVERSION = new Function<Boolean, Integer>()
    {
        @Override
        public Integer apply(Boolean bool)
        {
            return bool ? 1 : 0;
        }
    };


    @Test
    public void test_whenValueIsAbsent()
    {
        Optional<Integer> result = new Mapped<>(CONVERSION, Absent.<Boolean>absent());

        assertFalse(result.isPresent());
        assertEquals(5, (int) result.value(5));
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
    public void test_whenValueIsPresent()
    {
        Optional<Integer> result = new Mapped<>(CONVERSION, new Present<>(true));

        assertTrue(result.isPresent());
        assertEquals(1, (int) result.value(5));
        assertEquals(1, (int) result.value());
    }

}