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

package org.dmfs.jems.hamcrest.matchers;

import org.dmfs.jems.pair.Pair;
import org.hamcrest.Description;
import org.hamcrest.StringDescription;
import org.junit.Test;

import static org.dmfs.jems.hamcrest.matchers.PairMatcher.pair;
import static org.dmfs.jems.mockito.doubles.TestDoubles.failingMock;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;


/**
 * Unit test for {@link PairMatcher}.
 *
 * @author Gabor Keszthelyi
 */
public final class PairMatcherTest
{

    @Test
    public void test_pairWithAndWithoutMatcher_withDifferentArgPermutations()
    {
        assertThat(pair(equalTo(new String("a")), equalTo(new Integer(1)))
                .matches(mockPair(new String("a"), new Integer(1))), is(true));
        assertThat(pair(new String("a"), new Integer(1))
                .matches(mockPair(new String("a"), new Integer(1))), is(true));

        assertThat(pair(equalTo(new String("b")), equalTo(new Integer(1)))
                .matches(mockPair(new String("a"), new Integer(1))), is(false));
        assertThat(pair(new String("b"), new Integer(1))
                .matches(mockPair(new String("a"), new Integer(1))), is(false));

        assertThat(pair(equalTo(new String("a")), equalTo(new Integer(2)))
                .matches(mockPair(new String("a"), new Integer(1))), is(false));
        assertThat(pair(new String("a"), new Integer(2))
                .matches(mockPair(new String("a"), new Integer(1))), is(false));

        assertThat(pair(equalTo(new String("b")), equalTo(new Integer(2)))
                .matches(mockPair(new String("a"), new Integer(1))), is(false));
        assertThat(pair(new String("b"), new Integer(2))
                .matches(mockPair(new String("a"), new Integer(1))), is(false));
    }


    @Test
    public void test_describeMismatch()
    {
        Description mismatchMsg = new StringDescription();
        pair("a", 1).describeMismatch(mockPair("b", 1), mismatchMsg);
        assertThat(mismatchMsg.toString(), is("Left value doesn't match: was \"b\""));

        mismatchMsg = new StringDescription();
        pair("a", 1).describeMismatch(mockPair("a", 2), mismatchMsg);
        assertThat(mismatchMsg.toString(), is("Right value doesn't match: was <2>"));
    }


    @Test
    public void test_describeTo()
    {
        Description describeTo = new StringDescription();
        pair("a", 1).describeTo(describeTo);
        assertThat(describeTo.toString(), is("is Pair with left value: \"a\" , and right value: <1>"));
    }


    private <L, R> Pair<L, R> mockPair(L left, R right)
    {
        Pair<L, R> mockPair = failingMock(Pair.class);
        doReturn(left).when(mockPair).left();
        doReturn(right).when(mockPair).right();
        return mockPair;
    }

}