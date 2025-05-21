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

package org.dmfs.jems.iterator.generators;

import org.junit.Test;

import java.util.Iterator;

import static org.dmfs.jems.mockito.doubles.TestDoubles.dummy;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;


/**
 * @author Marten Gajda
 */
public class ConstantGeneratorTest
{
    @Test
    public void testNext() throws Exception
    {
        Object dummy = dummy(Object.class);
        Iterator<Object> testIterator = new ConstantGenerator<>(dummy);

        for (int i = 0; i < 100000; ++i)
        {
            assertThat(testIterator.hasNext(), is(true));
            assertThat(testIterator.hasNext(), is(true));
            assertThat(testIterator.hasNext(), is(true));
            assertThat(testIterator.next(), sameInstance(dummy));
        }
    }

}