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

import org.dmfs.android.contentpal.RowData;
import org.dmfs.android.contentpal.RowSnapshot;
import org.dmfs.android.contentpal.SoftRowReference;
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.android.contentpal.rowdata.CharSequenceRowData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.dmfs.android.contentpal.testing.contentoperationbuilder.OperationType.assertOperation;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.TargetMatcher.targets;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithExpectedCount.withoutExpectedCount;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithValues.withValuesOnly;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithYieldAllowed.withYieldNotAllowed;
import static org.dmfs.android.contentpal.testing.contentvalues.Containing.containing;
import static org.dmfs.android.contentpal.testing.operations.OperationMatcher.builds;
import static org.dmfs.jems.hamcrest.matchers.optional.AbsentMatcher.absent;
import static org.dmfs.jems.mockito.doubles.TestDoubles.dummy;
import static org.dmfs.jems.mockito.doubles.TestDoubles.failingMock;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;


/**
 * Unit test for {@link Assert}.
 *
 * @author Gabor Keszthelyi
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public final class AssertTest
{

    @Test
    public void testReference()
    {
        assertThat(new Assert<Object>(dummy(RowSnapshot.class), dummy(RowData.class)).reference(), absent());
    }


    @Test
    public void testContentOperationBuilder()
    {
        RowSnapshot<Object> mockRowSnapshot = failingMock(RowSnapshot.class);
        SoftRowReference<Object> dummyRowReference = dummy(SoftRowReference.class);
        doReturn(dummyRowReference).when(mockRowSnapshot).reference();
        Uri dummyUri = dummy(Uri.class);
        doReturn(ContentProviderOperation.newAssertQuery(dummyUri)).when(dummyRowReference).assertOperationBuilder(any(TransactionContext.class));

        assertThat(new Assert<>(mockRowSnapshot, new CharSequenceRowData<>("key", "value")),
                builds(
                        targets(sameInstance(dummyUri)),
                        assertOperation(),
                        withoutExpectedCount(),
                        withYieldNotAllowed(),
                        withValuesOnly(
                                containing("key", "value"))));
    }

}