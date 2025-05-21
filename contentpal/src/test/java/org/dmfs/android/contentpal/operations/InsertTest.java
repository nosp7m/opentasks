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

package org.dmfs.android.contentpal.operations;

import android.net.Uri;

import org.dmfs.android.contentpal.RowData;
import org.dmfs.android.contentpal.Table;
import org.dmfs.android.contentpal.operations.internal.RawInsert;
import org.dmfs.android.contentpal.rowdata.CharSequenceRowData;
import org.dmfs.android.contentpal.tools.uriparams.EmptyUriParams;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.dmfs.android.contentpal.testing.contentoperationbuilder.OperationType.insertOperation;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.TargetMatcher.targets;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithExpectedCount.withoutExpectedCount;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithValues.withValuesOnly;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithValues.withoutValues;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithYieldAllowed.withYieldNotAllowed;
import static org.dmfs.android.contentpal.testing.contentvalues.Containing.containing;
import static org.dmfs.android.contentpal.testing.operations.OperationMatcher.builds;
import static org.dmfs.jems.hamcrest.matchers.optional.AbsentMatcher.absent;
import static org.dmfs.jems.mockito.doubles.TestDoubles.dummy;
import static org.dmfs.jems.mockito.doubles.TestDoubles.failingMock;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;


/**
 * @author Marten Gajda
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class InsertTest
{
    @Test
    public void testReference()
    {
        Table<Object> mockTable = failingMock(Table.class);

        assertThat(new Insert<>(mockTable).reference(),
                is(absent()));

        assertThat(new Insert<Object>(mockTable, dummy(RowData.class)).reference(),
                is(absent()));
    }


    @Test
    public void testContentOperationBuilder()
    {
        Table<Object> mockTable = failingMock(Table.class);
        Uri dummyUri = dummy(Uri.class);
        doReturn(new RawInsert<>(dummyUri)).when(mockTable).insertOperation(EmptyUriParams.INSTANCE);

        assertThat(new Insert<>(mockTable),
                builds(
                        targets(sameInstance(dummyUri)),
                        insertOperation(),
                        withoutExpectedCount(),
                        withYieldNotAllowed(),
                        withoutValues()));

        assertThat(new Insert<>(mockTable, new CharSequenceRowData<>("key", "value")),
                builds(
                        targets(sameInstance(dummyUri)),
                        insertOperation(),
                        withoutExpectedCount(),
                        withYieldNotAllowed(),
                        withValuesOnly(
                                containing("key", "value"))));
    }
}