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

import android.content.ContentProviderOperation;
import android.net.Uri;

import org.dmfs.android.contentpal.Operation;
import org.dmfs.android.contentpal.Predicate;
import org.dmfs.android.contentpal.RowData;
import org.dmfs.android.contentpal.Table;
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.android.contentpal.rowdata.CharSequenceRowData;
import org.dmfs.android.contentpal.tools.uriparams.EmptyUriParams;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.dmfs.android.contentpal.testing.contentoperationbuilder.OperationType.assertOperation;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.TargetMatcher.targets;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithExpectedCount.withoutExpectedCount;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithValues.withValuesOnly;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithValues.withoutValues;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithYieldAllowed.withYieldNotAllowed;
import static org.dmfs.android.contentpal.testing.contentvalues.Containing.containing;
import static org.dmfs.android.contentpal.testing.operations.OperationMatcher.builds;
import static org.dmfs.android.contentpal.testing.predicates.PredicateArgumentMatcher.predicateWithSelection;
import static org.dmfs.jems.hamcrest.matchers.optional.AbsentMatcher.absent;
import static org.dmfs.jems.mockito.doubles.TestDoubles.dummy;
import static org.dmfs.jems.mockito.doubles.TestDoubles.failingMock;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.doReturn;


/**
 * Unit test for {@link BulkAssert}.
 *
 * @author Gabor Keszthelyi
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public final class BulkAssertTest
{

    @Test
    public void testReference()
    {
        assertThat(new BulkAssert<Object>(dummy(Table.class)).reference(), absent());

        assertThat(new BulkAssert<Object>(dummy(Table.class), dummy(Predicate.class)).reference(), absent());

        assertThat(new BulkAssert<Object>(dummy(Table.class), dummy(RowData.class)).reference(), absent());

        assertThat(new BulkAssert<Object>(dummy(Table.class), dummy(RowData.class), dummy(Predicate.class)).reference(), absent());
    }


    @Test
    public void testContentOperationBuilder_ctor_table()
    {
        Table<Object> mockTable = failingMock(Table.class);
        Operation<Object> mockAssertOperation = failingMock(Operation.class);

        doReturn(mockAssertOperation).when(mockTable).assertOperation(same(EmptyUriParams.INSTANCE), predicateWithSelection("1"));
        Uri dummyUri = dummy(Uri.class);
        doReturn(ContentProviderOperation.newAssertQuery(dummyUri)).when(mockAssertOperation).contentOperationBuilder(any(TransactionContext.class));

        assertThat(new BulkAssert<>(mockTable),
                builds(
                        targets(sameInstance(dummyUri)),
                        assertOperation(),
                        withoutExpectedCount(),
                        withYieldNotAllowed(),
                        withoutValues()));
    }


    @Test
    public void testContentOperationBuilder_ctor_table_predicate()
    {
        Table<Object> mockTable = failingMock(Table.class);
        Predicate<Object> dummyPredicate = dummy(Predicate.class);
        Operation<Object> mockAssertOperation = failingMock(Operation.class);

        doReturn(mockAssertOperation).when(mockTable).assertOperation(EmptyUriParams.INSTANCE, dummyPredicate);
        Uri dummyUri = dummy(Uri.class);
        doReturn(ContentProviderOperation.newAssertQuery(dummyUri)).when(mockAssertOperation).contentOperationBuilder(any(TransactionContext.class));

        assertThat(new BulkAssert<>(mockTable, dummyPredicate),
                builds(
                        targets(sameInstance(dummyUri)),
                        assertOperation(),
                        withoutExpectedCount(),
                        withYieldNotAllowed(),
                        withoutValues()));
    }


    @Test
    public void testContentOperationBuilder_ctor_table_data()
    {
        Table<Object> mockTable = failingMock(Table.class);
        Operation<Object> mockAssertOperation = failingMock(Operation.class);

        doReturn(mockAssertOperation).when(mockTable).assertOperation(same(EmptyUriParams.INSTANCE), predicateWithSelection("1"));
        Uri dummyUri = dummy(Uri.class);
        doReturn(ContentProviderOperation.newAssertQuery(dummyUri)).when(mockAssertOperation).contentOperationBuilder(any(TransactionContext.class));

        assertThat(new BulkAssert<>(mockTable, new CharSequenceRowData<>("key", "value")),
                builds(
                        targets(sameInstance(dummyUri)),
                        assertOperation(),
                        withoutExpectedCount(),
                        withYieldNotAllowed(),
                        withValuesOnly(
                                containing("key", "value"))));
    }


    @Test
    public void testContentOperationBuilder_ctor_table_data_predicate()
    {
        Table<Object> mockTable = failingMock(Table.class);
        Predicate<Object> dummyPredicate = dummy(Predicate.class);
        Operation<Object> mockAssertOperation = failingMock(Operation.class);

        doReturn(mockAssertOperation).when(mockTable).assertOperation(EmptyUriParams.INSTANCE, dummyPredicate);
        Uri dummyUri = dummy(Uri.class);
        doReturn(ContentProviderOperation.newAssertQuery(dummyUri)).when(mockAssertOperation).contentOperationBuilder(any(TransactionContext.class));

        assertThat(new BulkAssert<>(mockTable, new CharSequenceRowData<>("key", "value"), dummyPredicate),
                builds(
                        targets(sameInstance(dummyUri)),
                        assertOperation(),
                        withoutExpectedCount(),
                        withYieldNotAllowed(),
                        withValuesOnly(
                                containing("key", "value"))));
    }

}