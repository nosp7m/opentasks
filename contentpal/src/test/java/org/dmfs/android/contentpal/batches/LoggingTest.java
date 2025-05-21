/*
 * Copyright 2019 dmfs GmbH
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

package org.dmfs.android.contentpal.batches;

import android.content.ContentProviderOperation;

import org.dmfs.android.contentpal.Operation;
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.iterables.EmptyIterable;
import org.dmfs.jems.iterable.elementary.Seq;
import org.dmfs.jems.procedure.Procedure;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.dmfs.jems.hamcrest.matchers.IterableMatcher.iteratesTo;
import static org.dmfs.jems.hamcrest.matchers.LambdaMatcher.having;
import static org.dmfs.jems.mockito.doubles.TestDoubles.failingMock;
import static org.hamcrest.Matchers.emptyIterable;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


/**
 * Test {@link Logging}.
 *
 * @author Marten Gajda
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class LoggingTest
{
    @Test
    public void testEmpty()
    {
        assertThat(new Logging<>(failingMock(Procedure.class), new EmptyIterable<>()), emptyIterable());
    }


    @Test
    public void testSingle()
    {
        // TODO: find a way to simplify this
        TransactionContext tc = failingMock(TransactionContext.class);
        Procedure<String> logProcedure = mock(Procedure.class);

        Operation<Object> mockOperation = failingMock(Operation.class);
        ContentProviderOperation.Builder cpob = failingMock(ContentProviderOperation.Builder.class);
        ContentProviderOperation cpo = failingMock(ContentProviderOperation.class);
        doReturn(cpob).when(mockOperation).contentOperationBuilder(tc);
        doReturn(cpo).when(cpob).build();
        doReturn("result").when(cpo).toString();

        assertThat(new Logging<>(logProcedure, new Seq<>(mockOperation)),
                iteratesTo(having("contentprovideroperationbuilder", i -> i.contentOperationBuilder(tc), sameInstance(cpob))));
        verify(logProcedure).process("result");
    }


    @Test
    public void testMulti()
    {
        // TODO this is too complicated
        TransactionContext tc = failingMock(TransactionContext.class);

        Operation<Object> mockOperation1 = failingMock(Operation.class);
        ContentProviderOperation.Builder cpob1 = failingMock(ContentProviderOperation.Builder.class);
        ContentProviderOperation cpo1 = failingMock(ContentProviderOperation.class);
        doReturn(cpob1).when(mockOperation1).contentOperationBuilder(tc);
        doReturn(cpo1).when(cpob1).build();
        doReturn("result1").when(cpo1).toString();

        Operation<Object> mockOperation2 = failingMock(Operation.class);
        ContentProviderOperation.Builder cpob2 = failingMock(ContentProviderOperation.Builder.class);
        ContentProviderOperation cpo2 = failingMock(ContentProviderOperation.class);
        doReturn(cpob2).when(mockOperation2).contentOperationBuilder(tc);
        doReturn(cpo2).when(cpob2).build();
        doReturn("result2").when(cpo2).toString();

        Procedure<String> logProcedure = mock(Procedure.class);

        assertThat(new Logging<>(logProcedure, new Seq<>(mockOperation1, mockOperation2)),
                iteratesTo(
                        having("contentprovideroperationbuilder", i -> i.contentOperationBuilder(tc), sameInstance(cpob1)),
                        having("contentprovideroperationbuilder", i -> i.contentOperationBuilder(tc), sameInstance(cpob2))));
        verify(logProcedure).process("result1");
        verify(logProcedure).process("result2");
    }

}