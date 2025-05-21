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

package org.dmfs.jems.mocks;

import org.dmfs.jems.iterable.elementary.Seq;
import org.dmfs.iterators.Function;
import org.dmfs.jems.pair.Pair;
import org.dmfs.jems.pair.elementary.ValuePair;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;


/**
 * Unit test for {@link MockFunction}.
 *
 * @author Gabor Keszthelyi
 */
public final class MockFunctionTest
{

    private static final Object RES_1 = new Object();
    private static final Object RES_2 = new Object();
    private static final Object RES_3 = new Object();

    @Rule
    public ExpectedException mException = ExpectedException.none();


    @Test
    public void test_priCtorIterablePairs_matchingArgs_pass()
    {
        MockFunction<Integer, Object> mockFunction = new MockFunction<>(
                new Seq<Pair<Matcher<Integer>, Object>>(
                        new ValuePair<>(equalTo(arg(1)), RES_1),
                        new ValuePair<>(equalTo(arg(2)), RES_2),
                        new ValuePair<>(equalTo(arg(3)), RES_3)
                ));

        assertThat(mockFunction.apply(arg(1)), sameInstance(RES_1));
        assertThat(mockFunction.apply(arg(2)), sameInstance(RES_2));
        assertThat(mockFunction.apply(arg(3)), sameInstance(RES_3));
    }


    @Test(expected = AssertionError.class)
    public void test_priCtorIterablePairs_differentArg_fail()
    {
        new MockFunction<>(
                new Seq<Pair<Matcher<Integer>, Object>>(
                        new ValuePair<>(equalTo(arg(1)), RES_1),
                        new ValuePair<>(equalTo(arg(2)), RES_2),
                        new ValuePair<>(equalTo(arg(3)), RES_3)
                ))
                .apply(arg(5));
    }


    @Test
    public void test_secCtorIterablePairsVarargs_matchingArgs_pass()
    {
        MockFunction<Integer, Object> mockFunction = new MockFunction<>(
                new ValuePair<>(equalTo(arg(1)), RES_1),
                new ValuePair<>(equalTo(arg(2)), RES_2),
                new ValuePair<>(equalTo(arg(3)), RES_3)
        );

        assertThat(mockFunction.apply(arg(1)), sameInstance(RES_1));
        assertThat(mockFunction.apply(arg(2)), sameInstance(RES_2));
        assertThat(mockFunction.apply(arg(3)), sameInstance(RES_3));
    }


    @Test
    public void test_secCtorIterableIterable_matchingArgs_pass()
    {
        Function<Integer, Object> mockFunction = new MockFunction<>(
                new Seq<>(equalTo(arg(1)), equalTo(arg(2)), equalTo(arg(3))),
                new Seq<>(RES_1, RES_2, RES_3));

        assertThat(mockFunction.apply(arg(1)), sameInstance(RES_1));
        assertThat(mockFunction.apply(arg(2)), sameInstance(RES_2));
        assertThat(mockFunction.apply(arg(3)), sameInstance(RES_3));
    }


    @Test(expected = AssertionError.class)
    public void test_secCtorIterableIterable_differentArgs_fail()
    {
        new MockFunction<>(
                new Seq<>(equalTo(arg(1)), equalTo(arg(2)), equalTo(arg(3))),
                new Seq<>(RES_1, RES_2, RES_3))
                .apply(arg(555));
    }


    @Test
    public void test_secCtorSingleMatcher_matchingArg_pass()
    {
        assertThat(new MockFunction<>(equalTo(arg(1)), RES_1).apply(arg(1)), sameInstance(RES_1));
    }


    @Test(expected = AssertionError.class)
    public void test_ctorSingleMatcher_differentArg_fail()
    {
        new MockFunction<>(equalTo(arg(1)), RES_1).apply(arg(2));
    }


    @Test
    public void test_secCtorSingle_sameInstanceArg_pass()
    {
        Integer arg = new Integer(1);
        assertThat(new MockFunction<>(arg, RES_1).apply(arg), sameInstance(RES_1));
    }


    @Test(expected = AssertionError.class)
    public void test_ctorSingle_equalInstanceArg_fail()
    {
        new MockFunction<>(arg(1), RES_1).apply(arg(1));
    }


    @Test
    public void test_exceptionMessage()
    {
        mException.expect(AssertionError.class);
        mException.expectMessage("unexpected argument");
        mException.expectMessage(arg(555).toString());
        new MockFunction<>(arg(1), RES_1).apply(arg(555));
    }


    // Shortcut for creating a value object:
    private Integer arg(int value)
    {
        return new Integer(value);
    }

}