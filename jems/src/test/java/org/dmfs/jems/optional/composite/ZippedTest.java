/*
 * Copyright 2018 dmfs GmbH
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

package org.dmfs.jems.optional.composite;

import org.dmfs.jems.function.BiFunction;
import org.dmfs.jems.hamcrest.matchers.optional.AbsentMatcher;
import org.dmfs.jems.optional.elementary.Present;
import org.junit.Test;

import static org.dmfs.jems.hamcrest.matchers.optional.PresentMatcher.present;
import static org.dmfs.jems.mockito.doubles.TestDoubles.failingMock;
import static org.dmfs.jems.optional.elementary.Absent.absent;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;


/**
 * Test for {@link Zipped}.
 *
 * @author Marten Gajda
 */
public class ZippedTest
{
    @Test
    public void test() throws Exception
    {
        assertThat(new Zipped<>(new Present<>("val1"), new Present<>("val2"), stringBiFunction("val1", "val2", "result")), is(present("result")));
        assertThat(new Zipped<Object,Object,Object>(new Present<>("val1"), absent(), failingMock(BiFunction.class)), is(AbsentMatcher.absent()));
        assertThat(new Zipped<Object,Object,Object>(absent(), new Present<>("val2"), failingMock(BiFunction.class)), is(AbsentMatcher.absent()));
        assertThat(new Zipped<Object,Object,Object>(absent(), absent(), failingMock(BiFunction.class)), is(AbsentMatcher.absent()));
    }


    private <Result> BiFunction<String, String, Result> stringBiFunction(String s1, String s2, Result result)
    {
        BiFunction<String, String, Result> mockFunction = mock(BiFunction.class);
        doReturn(result).when(mockFunction).value(s1, s2);
        return mockFunction;
    }

}