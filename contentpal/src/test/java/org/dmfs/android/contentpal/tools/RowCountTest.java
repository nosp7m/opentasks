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

package org.dmfs.android.contentpal.tools;

import android.annotation.TargetApi;
import android.database.Cursor;
import android.os.Build;
import android.os.RemoteException;

import org.dmfs.android.contentpal.Predicate;
import org.dmfs.android.contentpal.Projection;
import org.dmfs.android.contentpal.View;
import org.dmfs.android.contentpal.tools.uriparams.EmptyUriParams;
import org.dmfs.jems.mockito.answers.FailAnswer;
import org.junit.Test;

import static org.dmfs.android.contentpal.testing.predicates.PredicateArgumentMatcher.predicateWithSelection;
import static org.dmfs.jems.mockito.doubles.TestDoubles.dummy;
import static org.dmfs.jems.mockito.doubles.TestDoubles.failingMock;
import static org.dmfs.jems.optional.elementary.Absent.absent;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


/**
 * Test for {@link RowCount}.
 *
 * @author Gabor Keszthelyi
 */
public final class RowCountTest
{

    @Test
    public void test_thatCursorGetCountIsReturned() throws Exception
    {
        View<Object> mockView = failingMock(View.class);
        Predicate<Object> dummyPredicate = dummy(Predicate.class);
        Cursor mockCursor = failingMock(Cursor.class);

        doReturn(mockCursor).when(mockView).rows(same(EmptyUriParams.INSTANCE), any(Projection.class), same(dummyPredicate), same(absent()));
        doReturn(4).when(mockCursor).getCount();
        doNothing().when(mockCursor).close();

        assertThat(new RowCount<>(mockView, dummyPredicate).value(), is(4));
        verify(mockCursor).close();
    }


    @Test
    public void test_ctorWithoutPredicate_predicateHasSelection1() throws Exception
    {
        View<Object> mockView = failingMock(View.class);
        Cursor mockCursor = failingMock(Cursor.class);

        doReturn(mockCursor).when(mockView)
                .rows(same(EmptyUriParams.INSTANCE), any(Projection.class), predicateWithSelection("1"), same(absent()));
        doReturn(5).when(mockCursor).getCount();
        doNothing().when(mockCursor).close();

        assertThat(new RowCount<>(mockView).value(), is(5));
        verify(mockCursor).close();
    }


    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    @Test(expected = RuntimeException.class)
    public void test_whenRowsThrows_ExceptionIsThrown() throws Exception
    {
        View<Object> mockView = failingMock(View.class);
        Predicate<Object> dummyPredicate = dummy(Predicate.class);

        doThrow(new RemoteException("msg")).when(mockView)
                .rows(same(EmptyUriParams.INSTANCE), any(Projection.class), same(dummyPredicate), same(absent()));

        new RowCount<>(mockView, dummyPredicate).value();
    }


    @Test
    public void test_whenCursorGetCountThrows_CursorIsClosed() throws Exception
    {
        View<Object> mockView = mock(View.class, new FailAnswer());
        Predicate<Object> dummyPredicate = mock(Predicate.class, new FailAnswer());
        Cursor mockCursor = mock(Cursor.class, new FailAnswer());

        doReturn(mockCursor).when(mockView).rows(same(EmptyUriParams.INSTANCE), any(Projection.class), same(dummyPredicate), same(absent()));
        doThrow(new RuntimeException("msg")).when(mockCursor).getCount();
        doNothing().when(mockCursor).close();

        try
        {
            new RowCount<>(mockView, dummyPredicate).value();
            fail();
        }
        catch (RuntimeException e)
        {
            // expected
        }
        verify(mockCursor).close();
    }

}