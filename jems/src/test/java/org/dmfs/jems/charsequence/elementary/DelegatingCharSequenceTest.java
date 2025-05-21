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

package org.dmfs.jems.charsequence.elementary;

import org.junit.Test;

import static org.dmfs.jems.mockito.doubles.TestDoubles.dummy;
import static org.dmfs.jems.mockito.doubles.TestDoubles.failingMock;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;


/**
 * Unit test for {@link DelegatingCharSequence}.
 *
 * @author Gabor Keszthelyi
 */
public final class DelegatingCharSequenceTest
{

    @Test
    public void test_length()
    {
        CharSequence mockDelegate = failingMock(CharSequence.class);
        doReturn(3).when(mockDelegate).length();
        assertThat(new TestDCS(mockDelegate).length(), is(3));
    }


    @Test
    public void test_charAt()
    {
        CharSequence mockDelegate = failingMock(CharSequence.class);
        doReturn('c').when(mockDelegate).charAt(4);
        assertThat(new TestDCS(mockDelegate).charAt(4), is('c'));
    }


    @Test
    public void test_subSequence()
    {
        CharSequence mockDelegate = failingMock(CharSequence.class);
        CharSequence subSequencDummy = dummy(CharSequence.class);
        doReturn(subSequencDummy).when(mockDelegate).subSequence(2, 5);
        assertThat(new TestDCS(mockDelegate).subSequence(2, 5), sameInstance(subSequencDummy));
    }


    @Test
    public void test_toString()
    {
        CharSequence mockDelegate = failingMock(CharSequence.class);
        doReturn("test").when(mockDelegate).toString();
        assertThat(new TestDCS(mockDelegate).toString(), is("test"));
    }


    private static final class TestDCS extends DelegatingCharSequence
    {

        TestDCS(CharSequence delegate)
        {
            super(delegate);
        }
    }

}