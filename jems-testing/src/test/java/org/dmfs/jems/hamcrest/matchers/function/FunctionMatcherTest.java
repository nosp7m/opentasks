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

import static org.dmfs.jems.hamcrest.matchers.function.FunctionMatcher.associates;
import static org.dmfs.jems.hamcrest.matchers.matcher.MatcherMatcher.describesAs;
import static org.dmfs.jems.hamcrest.matchers.matcher.MatcherMatcher.matches;
import static org.dmfs.jems.hamcrest.matchers.matcher.MatcherMatcher.mismatches;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


/**
 * Unit test for {@link FunctionMatcher}.
 *
 * @author Marten Gajda
 */
public class FunctionMatcherTest
{
    @Test
    public void test()
    {
        assertThat(new FunctionMatcher<>(10, is(100)),
                allOf(
                        matches(x -> x * x),
                        mismatches(x -> x, "result for argument 10 was <10>"),
                        describesAs("result for argument 10 is <100>")));

        assertThat(associates(10, is(100)),
                allOf(
                        matches(x -> x * x),
                        mismatches(x -> x, "result for argument 10 was <10>"),
                        describesAs("result for argument 10 is <100>")));

        assertThat(associates(10, 100),
                allOf(
                        matches(x -> x * x),
                        mismatches(x -> x, "result for argument 10 was <10>"),
                        describesAs("result for argument 10 is <100>")));
    }
}