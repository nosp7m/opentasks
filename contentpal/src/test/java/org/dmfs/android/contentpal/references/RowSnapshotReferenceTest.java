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

package org.dmfs.android.contentpal.references;

import android.content.ContentProviderOperation;

import org.dmfs.android.contentpal.Predicate;
import org.dmfs.android.contentpal.RowReference;
import org.dmfs.android.contentpal.RowSnapshot;
import org.dmfs.android.contentpal.SoftRowReference;
import org.dmfs.android.contentpal.TransactionContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.dmfs.jems.mockito.doubles.TestDoubles.dummy;
import static org.dmfs.jems.mockito.doubles.TestDoubles.failingMock;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;


/**
 * @author Marten Gajda
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class RowSnapshotReferenceTest
{
    @Test
    public void testPutOperationBuilder()
    {
        ContentProviderOperation.Builder dummyResultBuilder = dummy(ContentProviderOperation.Builder.class);
        SoftRowReference<Object> dummyOriginalReference = dummy(SoftRowReference.class);
        RowReference<Object> mockResolvedReference = failingMock(RowReference.class);
        RowSnapshot<Object> mockSnapshot = failingMock(RowSnapshot.class);
        TransactionContext mockTransactionContext = failingMock(TransactionContext.class);
        doReturn(dummyOriginalReference).when(mockSnapshot).reference();
        doReturn(mockResolvedReference).when(mockTransactionContext).resolved(dummyOriginalReference);

        doReturn(dummyResultBuilder).when(mockResolvedReference).putOperationBuilder(mockTransactionContext);

        assertThat(new RowSnapshotReference<>(mockSnapshot).putOperationBuilder(mockTransactionContext), sameInstance(dummyResultBuilder));
    }


    @Test
    public void testDeleteOperationBuilder()
    {
        ContentProviderOperation.Builder dummyResultBuilder = dummy(ContentProviderOperation.Builder.class);
        SoftRowReference<Object> dummyOriginalReference = dummy(SoftRowReference.class);
        RowReference<Object> mockResolvedReference = failingMock(RowReference.class);
        RowSnapshot<Object> mockSnapshot = failingMock(RowSnapshot.class);
        TransactionContext mockTransactionContext = failingMock(TransactionContext.class);
        doReturn(dummyOriginalReference).when(mockSnapshot).reference();
        doReturn(mockResolvedReference).when(mockTransactionContext).resolved(dummyOriginalReference);

        doReturn(dummyResultBuilder).when(mockResolvedReference).deleteOperationBuilder(mockTransactionContext);

        assertThat(new RowSnapshotReference<>(mockSnapshot).deleteOperationBuilder(mockTransactionContext), sameInstance(dummyResultBuilder));

    }


    @Test
    public void testAssertOperationBuilder()
    {
        ContentProviderOperation.Builder dummyResultBuilder = dummy(ContentProviderOperation.Builder.class);
        SoftRowReference<Object> dummyOriginalReference = dummy(SoftRowReference.class);
        RowReference<Object> mockResolvedReference = failingMock(RowReference.class);
        RowSnapshot<Object> mockSnapshot = failingMock(RowSnapshot.class);
        TransactionContext mockTransactionContext = failingMock(TransactionContext.class);
        doReturn(dummyOriginalReference).when(mockSnapshot).reference();
        doReturn(mockResolvedReference).when(mockTransactionContext).resolved(dummyOriginalReference);

        doReturn(dummyResultBuilder).when(mockResolvedReference).assertOperationBuilder(mockTransactionContext);

        assertThat(new RowSnapshotReference<>(mockSnapshot).assertOperationBuilder(mockTransactionContext), sameInstance(dummyResultBuilder));

    }


    @Test
    public void testBuilderWithReferenceData()
    {
        ContentProviderOperation.Builder dummyResultBuilder = dummy(ContentProviderOperation.Builder.class);
        SoftRowReference<Object> dummyOriginalReference = dummy(SoftRowReference.class);
        RowReference<Object> mockResolvedReference = failingMock(RowReference.class);
        RowSnapshot<Object> mockSnapshot = failingMock(RowSnapshot.class);
        TransactionContext mockTransactionContext = failingMock(TransactionContext.class);
        doReturn(dummyOriginalReference).when(mockSnapshot).reference();
        doReturn(mockResolvedReference).when(mockTransactionContext).resolved(dummyOriginalReference);

        doReturn(dummyResultBuilder).when(mockResolvedReference).builderWithReferenceData(mockTransactionContext, dummyResultBuilder, "column");

        assertThat(new RowSnapshotReference<>(mockSnapshot).builderWithReferenceData(mockTransactionContext, dummyResultBuilder, "column"),
                sameInstance(dummyResultBuilder));
    }


    @Test
    public void testPredicate()
    {
        Predicate<Object> dummyResultPredicate = dummy(Predicate.class);
        SoftRowReference<Object> dummyOriginalReference = dummy(SoftRowReference.class);
        RowReference<Object> mockResolvedReference = failingMock(RowReference.class);
        RowSnapshot<Object> mockSnapshot = failingMock(RowSnapshot.class);
        TransactionContext mockTransactionContext = failingMock(TransactionContext.class);
        doReturn(dummyOriginalReference).when(mockSnapshot).reference();
        doReturn(mockResolvedReference).when(mockTransactionContext).resolved(dummyOriginalReference);

        doReturn(dummyResultPredicate).when(mockResolvedReference).predicate(mockTransactionContext, "column");

        assertThat(new RowSnapshotReference<>(mockSnapshot).predicate(mockTransactionContext, "column"),
                sameInstance(dummyResultPredicate));
    }
}