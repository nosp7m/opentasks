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

package org.dmfs.android.contentpal.batches;

import android.content.ContentProviderOperation;
import android.net.Uri;

import org.dmfs.android.contentpal.InsertOperation;
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.android.contentpal.rowdata.CharSequenceRowData;
import org.dmfs.android.contentpal.rowdata.Composite;
import org.dmfs.jems.iterable.elementary.Seq;
import org.dmfs.jems.optional.elementary.Absent;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.stubbing.Answer;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.dmfs.android.contentpal.testing.contentoperationbuilder.OperationType.insertOperation;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.TargetMatcher.targets;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithExpectedCount.withoutExpectedCount;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithValues.withValuesOnly;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithYieldAllowed.withYieldNotAllowed;
import static org.dmfs.android.contentpal.testing.contentvalues.Containing.containing;
import static org.dmfs.android.contentpal.testing.operations.OperationMatcher.builds;
import static org.dmfs.jems.mockito.doubles.TestDoubles.dummy;
import static org.hamcrest.Matchers.emptyIterable;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * @author Marten Gajda
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class MultiInsertBatchTest
{
    @Test
    public void testEmpty()
    {
        InsertOperation<Object> mockOp = mock(InsertOperation.class);
        when(mockOp.contentOperationBuilder(any(TransactionContext.class))).then(
                (Answer<ContentProviderOperation.Builder>) invocation -> ContentProviderOperation.newInsert(Uri.EMPTY));

        assertThat(new MultiInsertBatch<>(mockOp, new Seq<>()), emptyIterable());
        assertThat(new MultiInsertBatch<>(mockOp, Absent.absent()), emptyIterable());
        assertThat(new MultiInsertBatch<>(mockOp, Absent.absent(), Absent.absent()), emptyIterable());
    }


    @Test
    public void testSingle()
    {
        InsertOperation<Object> mockOp = mock(InsertOperation.class);
        final Uri dummyUri = dummy(Uri.class);
        when(mockOp.contentOperationBuilder(any(TransactionContext.class))).then(
                (Answer<ContentProviderOperation.Builder>) invocation -> ContentProviderOperation.newInsert(dummyUri));

        assertThat(new MultiInsertBatch<>(mockOp, new CharSequenceRowData<>("key", "value")),
                Matchers.contains(
                        builds(
                                targets(sameInstance(dummyUri)),
                                insertOperation(),
                                withValuesOnly(containing("key", "value")),
                                withoutExpectedCount(),
                                withYieldNotAllowed())));
    }


    @Test
    public void testSingleComposite()
    {
        InsertOperation<Object> mockOp = mock(InsertOperation.class);
        final Uri dummyUri = dummy(Uri.class);

        when(mockOp.contentOperationBuilder(any(TransactionContext.class))).then(
                (Answer<ContentProviderOperation.Builder>) invocation -> ContentProviderOperation.newInsert(dummyUri));

        assertThat(new MultiInsertBatch<>(mockOp,
                        new Composite<>(
                                new CharSequenceRowData<>("key", "value"),
                                new CharSequenceRowData<>("key2", "value2"),
                                new CharSequenceRowData<>("key3", "value3"))),
                Matchers.contains(
                        builds(
                                targets(sameInstance(dummyUri)),
                                insertOperation(),
                                withValuesOnly(
                                        containing("key", "value"),
                                        containing("key2", "value2"),
                                        containing("key3", "value3")),
                                withoutExpectedCount(),
                                withYieldNotAllowed())));
    }


    @Test
    public void testMulti()
    {
        InsertOperation<Object> mockOp = mock(InsertOperation.class);
        final Uri dummyUri = dummy(Uri.class);
        when(mockOp.contentOperationBuilder(any(TransactionContext.class))).then(
                (Answer<ContentProviderOperation.Builder>) invocation -> ContentProviderOperation.newInsert(dummyUri));

        assertThat(new MultiInsertBatch<>(mockOp,
                        new CharSequenceRowData<>("key", "value"),
                        new CharSequenceRowData<>("key", "value2"),
                        new CharSequenceRowData<>("key", "value3")),
                Matchers.contains(
                        builds(
                                targets(sameInstance(dummyUri)),
                                insertOperation(),
                                withValuesOnly(containing("key", "value")),
                                withoutExpectedCount(),
                                withYieldNotAllowed()),
                        builds(
                                targets(sameInstance(dummyUri)),
                                insertOperation(),
                                withValuesOnly(containing("key", "value2")),
                                withoutExpectedCount(),
                                withYieldNotAllowed()),
                        builds(
                                targets(sameInstance(dummyUri)),
                                insertOperation(),
                                withValuesOnly(containing("key", "value3")),
                                withoutExpectedCount(),
                                withYieldNotAllowed())));
    }


    @Test
    public void testMultiComposite()
    {
        InsertOperation<Object> mockOp = mock(InsertOperation.class);
        final Uri dummyUri = dummy(Uri.class);
        when(mockOp.contentOperationBuilder(any(TransactionContext.class))).then(
                (Answer<ContentProviderOperation.Builder>) invocation -> ContentProviderOperation.newInsert(dummyUri));

        assertThat(new MultiInsertBatch<>(mockOp,
                        new Composite<>(
                                new CharSequenceRowData<>("key1a", "value1a"),
                                new CharSequenceRowData<>("key1b", "value1b"),
                                new CharSequenceRowData<>("key1c", "value1c")),
                        new Composite<>(
                                new CharSequenceRowData<>("key2a", "value2a"),
                                new CharSequenceRowData<>("key2b", "value2b"),
                                new CharSequenceRowData<>("key2c", "value2c")),
                        new Composite<>(
                                new CharSequenceRowData<>("key3a", "value3a"),
                                new CharSequenceRowData<>("key3b", "value3b"),
                                new CharSequenceRowData<>("key3c", "value3c"))),
                Matchers.contains(
                        builds(
                                targets(sameInstance(dummyUri)),
                                insertOperation(),
                                withValuesOnly(
                                        containing("key1a", "value1a"),
                                        containing("key1b", "value1b"),
                                        containing("key1c", "value1c")),
                                withoutExpectedCount(),
                                withYieldNotAllowed()),
                        builds(
                                targets(sameInstance(dummyUri)),
                                insertOperation(),
                                withValuesOnly(
                                        containing("key2a", "value2a"),
                                        containing("key2b", "value2b"),
                                        containing("key2c", "value2c")),
                                withoutExpectedCount(),
                                withYieldNotAllowed()),
                        builds(
                                targets(sameInstance(dummyUri)),
                                insertOperation(),
                                withValuesOnly(
                                        containing("key3a", "value3a"),
                                        containing("key3b", "value3b"),
                                        containing("key3c", "value3c")),
                                withoutExpectedCount(),
                                withYieldNotAllowed())));

    }

}