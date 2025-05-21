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

import org.dmfs.android.contentpal.RowSnapshot;
import org.dmfs.android.contentpal.SoftRowReference;
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.android.contentpal.rowdata.CharSequenceRowData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.dmfs.android.contentpal.testing.contentoperationbuilder.OperationType.updateOperation;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.TargetMatcher.targets;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithExpectedCount.withoutExpectedCount;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithValues.withValuesOnly;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithValues.withoutValues;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithYieldAllowed.withYieldNotAllowed;
import static org.dmfs.android.contentpal.testing.contentvalues.Containing.containing;
import static org.dmfs.android.contentpal.testing.operations.OperationMatcher.builds;
import static org.dmfs.jems.hamcrest.matchers.optional.PresentMatcher.present;
import static org.dmfs.jems.mockito.doubles.TestDoubles.dummy;
import static org.dmfs.jems.mockito.doubles.TestDoubles.failingMock;
import static org.hamcrest.Matchers.sameInstance;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;


/**
 * @author Marten Gajda
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class PutTest
{

    @Test
    public void testVirtualReference()
    {
        SoftRowReference<Object> dummyReference = dummy(SoftRowReference.class);
        RowSnapshot<Object> mockRowSnapshot = failingMock(RowSnapshot.class);

        doReturn(dummyReference).when(mockRowSnapshot).reference();
        doReturn(ContentProviderOperation.newUpdate(Uri.EMPTY)).when(dummyReference).putOperationBuilder(any(TransactionContext.class));

        assertThat(new Put<>(mockRowSnapshot).reference(), is(present(sameInstance(dummyReference))));
    }


    @Test
    public void testContentOperationBuilder()
    {
        RowSnapshot<Object> mockRowSnapshot = failingMock(RowSnapshot.class);
        SoftRowReference<Object> mockRowReference = failingMock(SoftRowReference.class);

        Uri dummyUri = dummy(Uri.class);
        doReturn(mockRowReference).when(mockRowSnapshot).reference();
        doReturn(ContentProviderOperation.newUpdate(dummyUri)).when(mockRowReference).putOperationBuilder(any(TransactionContext.class));

        assertThat(new Put<>(mockRowSnapshot),
                builds(
                        targets(sameInstance(dummyUri)),
                        updateOperation(),
                        withYieldNotAllowed(),
                        withoutExpectedCount(),
                        withoutValues()
                )
        );
    }


    @Test
    public void testContentOperationBuilderWithData()
    {
        RowSnapshot<Object> mockRowSnapshot = failingMock(RowSnapshot.class);
        SoftRowReference<Object> mockRowReference = failingMock(SoftRowReference.class);

        Uri dummyUri = dummy(Uri.class);
        doReturn(mockRowReference).when(mockRowSnapshot).reference();
        doReturn(ContentProviderOperation.newUpdate(dummyUri)).when(mockRowReference).putOperationBuilder(any(TransactionContext.class));

        assertThat(new Put<>(mockRowSnapshot, new CharSequenceRowData<>("x", "y")),
                builds(
                        targets(sameInstance(dummyUri)),
                        updateOperation(),
                        withYieldNotAllowed(),
                        withoutExpectedCount(),
                        withValuesOnly(
                                containing("x", "y"))));
    }
}