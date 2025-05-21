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

package org.dmfs.optional.composite;

import org.dmfs.jems.function.BiFunction;
import org.dmfs.optional.Absent;
import org.dmfs.optional.Present;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.dmfs.jems.mockito.doubles.TestDoubles.dummy;
import static org.dmfs.optional.Absent.absent;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;


/**
 * @author Marten Gajda
 */
public class ZippedTest
{
    @Test
    public void testIsPresent() throws Exception
    {
        assertThat(new Zipped<>(new Present<>("val1"), new Present<>("val2"), dummy(BiFunction.class)).isPresent(), is(true));
        assertThat(new Zipped<>(absent(), new Present<>("val2"), dummy(BiFunction.class)).isPresent(), is(false));
        assertThat(new Zipped<>(new Present<>("val1"), absent(), dummy(BiFunction.class)).isPresent(), is(false));
        assertThat(new Zipped<>(absent(), absent(), dummy(BiFunction.class)).isPresent(), is(false));
    }


    @Test
    public void testValue() throws Exception
    {
        assertThat(new Zipped<>(new Present<>("val1"), new Present<>("val2"), stringBiFunction("val1", "val2", "result")).value("fallback"), is("result"));
        assertThat(new Zipped<>(Absent.<String>absent(), new Present<>("val2"), stringBiFunction("val1", "val2", "result")).value("fallback"), is("fallback"));
        assertThat(new Zipped<>(new Present<>("val1"), Absent.<String>absent(), stringBiFunction("val1", "val2", "result")).value("fallback"), is("fallback"));
        assertThat(new Zipped<>(Absent.<String>absent(), Absent.<String>absent(), stringBiFunction("val1", "val2", "result")).value("fallback"),
                is("fallback"));
    }


    @Test
    public void testValue1() throws Exception
    {
        assertThat(new Zipped<>(new Present<>("val1"), new Present<>("val2"), stringBiFunction("val1", "val2", "result")).value(), is("result"));
    }


    @Test(expected = NoSuchElementException.class)
    public void testValueFailAbsentRight() throws Exception
    {
        new Zipped<>(new Present<>("val1"), Absent.<String>absent(), anyStringBiFunction("result")).value();
    }


    @Test(expected = NoSuchElementException.class)
    public void testValueFailAbsentLeft() throws Exception
    {
        new Zipped<>(Absent.<String>absent(), new Present<>("val2"), anyStringBiFunction("result")).value();
    }


    @Test(expected = NoSuchElementException.class)
    public void testValueFailAbsentBoth() throws Exception
    {
        new Zipped<>(Absent.<String>absent(), Absent.<String>absent(), anyStringBiFunction("result")).value();
    }


    private <Result> BiFunction<String, String, Result> anyStringBiFunction(Result result)
    {
        BiFunction<String, String, Result> mockFunction = mock(BiFunction.class);
        doReturn(result).when(mockFunction).value(anyString(), anyString());
        return mockFunction;
    }


    private <Result> BiFunction<String, String, Result> stringBiFunction(String s1, String s2, Result result)
    {
        BiFunction<String, String, Result> mockFunction = mock(BiFunction.class);
        doReturn(result).when(mockFunction).value(s1, s2);
        return mockFunction;
    }

}