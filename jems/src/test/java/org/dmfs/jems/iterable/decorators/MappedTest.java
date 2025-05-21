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

package org.dmfs.jems.iterable.decorators;

import org.dmfs.iterables.EmptyIterable;
import org.dmfs.jems.iterable.elementary.Seq;
import org.dmfs.jems.function.Function;
import org.dmfs.jems.mockito.doubles.TestDoubles;
import org.hamcrest.Matchers;
import org.junit.Test;

import static org.dmfs.jems.hamcrest.matchers.IterableMatcher.iteratesTo;
import static org.junit.Assert.assertThat;


/**
 * Unit test for {@link Mapped}.
 *
 * @author Gabor Keszthelyi
 */
public final class MappedTest
{

    @Test
    public void test_emptyIterable()
    {
        Function<Object, Object> dummyFunc = TestDoubles.dummy(Function.class);
        assertThat(new Mapped<>(dummyFunc, new EmptyIterable<>()), Matchers.emptyIterable());
    }


    @Test
    public void test_nonEmptyIterable()
    {
        assertThat(new Mapped<>(new Function<String, Integer>()
        {
            @Override
            public Integer value(String argument)
            {
                return argument.length();
            }
        }, new Seq<>("a", "bb", "ccc")), iteratesTo(1, 2, 3));
    }

}