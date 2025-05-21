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

package org.dmfs.jems.optional.decorators;

import org.dmfs.jems.optional.Optional;
import org.dmfs.jems.optional.elementary.Absent;
import org.dmfs.jems.optional.elementary.Present;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.dmfs.jems.hamcrest.matchers.optional.AbsentMatcher.absent;
import static org.dmfs.jems.hamcrest.matchers.optional.PresentMatcher.present;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;


/**
 * Unit test for {@link Frozen}.
 *
 * @author Marten Gajda
 */
public class FrozenTest
{

    @Test
    public void test()
    {
        assertThat(new Frozen<>(new Present<>("abc")), is(present("abc")));
        assertThat(new Frozen<>(new Absent<>()), is(absent()));
    }


    @Test
    public void testRevaluationPresent()
    {
        Optional<String> optional = mock(Optional.class);
        doReturn(true).when(optional).isPresent();
        doReturn("abc").when(optional).value();
        Optional<String> testee = new Frozen<>(optional);
        testee.isPresent();
        testee.value();
        testee.isPresent();
        testee.value();
        testee.isPresent();
        testee.value();
        verify(optional).isPresent();
        verify(optional).value();
        verifyNoMoreInteractions(optional);
    }


    @Test
    public void testRevaluationAbsent()
    {
        Optional<String> optional = mock(Optional.class);
        doReturn(false).when(optional).isPresent();
        doThrow(new NoSuchElementException()).when(optional).value();
        Optional<String> testee = new Frozen<>(optional);
        testee.isPresent();
        testee.isPresent();
        testee.isPresent();
        verify(optional).isPresent();
        verifyNoMoreInteractions(optional);
    }

}