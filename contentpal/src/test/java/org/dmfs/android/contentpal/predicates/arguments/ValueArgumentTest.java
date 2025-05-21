/*
 * Copyright 2017 dmfs GmbH
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

package org.dmfs.android.contentpal.predicates.arguments;

import org.dmfs.android.contentpal.Predicate;
import org.junit.Test;

import static org.dmfs.jems.hamcrest.matchers.optional.AbsentMatcher.absent;
import static org.dmfs.jems.mockito.doubles.TestDoubles.failingMock;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;


/**
 * Unit test for {@link ValueArgument}.
 *
 * @author Gabor Keszthelyi
 */
public final class ValueArgumentTest
{

    @Test
    public void test()
    {
        Object mockValue = failingMock(Object.class);
        doReturn("val").when(mockValue).toString();

        Predicate.Argument underTest = new ValueArgument(mockValue);
        assertThat(underTest.value(), is("val"));
        assertThat(underTest.backReference(), is(absent()));
    }

}