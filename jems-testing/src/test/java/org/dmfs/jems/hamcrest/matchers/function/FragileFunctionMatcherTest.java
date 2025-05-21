/*
 * Copyright 2020 dmfs GmbH
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

package org.dmfs.jems.hamcrest.matchers.function;

import org.junit.Test;

import java.io.IOException;

import static org.dmfs.jems.hamcrest.matchers.function.FragileFunctionMatcher.associates;
import static org.dmfs.jems.hamcrest.matchers.function.FragileFunctionMatcher.throwing;
import static org.dmfs.jems.hamcrest.matchers.matcher.MatcherMatcher.describesAs;
import static org.dmfs.jems.hamcrest.matchers.matcher.MatcherMatcher.matches;
import static org.dmfs.jems.hamcrest.matchers.matcher.MatcherMatcher.mismatches;
import static org.dmfs.jems.hamcrest.matchers.throwable.ThrowableMatcher.throwable;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


/**
 * Unit test for {@link FragileFunctionMatcher}.
 */
public class FragileFunctionMatcherTest
{
    @Test
    public void test()
    {
        assertThat(new FragileFunctionMatcher<>(10, is(100)),
            allOf(
                matches(x -> x * x),
                mismatches(x -> x, "result for argument 10 was <10>"),
                mismatches(x -> {throw new RuntimeException();}, "threw java.lang.RuntimeException for argument 10"),
                describesAs("result for argument 10 is <100>")));

        assertThat(associates(10, is(100)),
            allOf(
                matches(x -> x * x),
                mismatches(x -> x, "result for argument 10 was <10>"),
                mismatches(x -> {throw new RuntimeException();}, "threw java.lang.RuntimeException for argument 10"),
                describesAs("result for argument 10 is <100>")));

        assertThat(associates(10, 100),
            allOf(
                matches(x -> x * x),
                mismatches(x -> x, "result for argument 10 was <10>"),
                mismatches(x -> {throw new RuntimeException();}, "threw java.lang.RuntimeException for argument 10"),
                describesAs("result for argument 10 is <100>")));
    }


    @Test
    public void testThrowing()
    {
        assertThat(throwing(10, throwable(instanceOf(IOException.class))),
            allOf(
                matches(x -> {throw new IOException();}),
                mismatches(x -> x, "value Fragile Fragile was not broken"),
                describesAs("value Fragile is broken Fragile throwing (an instance of java.io.IOException)")));
    }

}