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

import org.dmfs.android.contentpal.Operation;
import org.dmfs.android.contentpal.RowSnapshot;
import org.dmfs.android.contentpal.SoftRowReference;
import org.dmfs.android.contentpal.operations.internal.RawInsert;
import org.dmfs.android.contentpal.references.RowUriReference;
import org.dmfs.jems.optional.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import static org.dmfs.jems.mockito.doubles.TestDoubles.failingMock;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;


/**
 * @author Marten Gajda
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class ReferringTest
{
    @Test
    public void testReference()
    {
        RowSnapshot<Object> mockSnapshot = failingMock(RowSnapshot.class);
        Operation<Object> mockOperation = failingMock(Operation.class);
        Optional<SoftRowReference<Object>> dummyOptional = dummy(Optional.class);
        doReturn(dummyOptional).when(mockOperation).reference();

        assertThat(new Referring<>(mockSnapshot, "column", mockOperation).reference(), is(dummyOptional));
    }


    @Test
    public void testContentOperationBuilder()
    {
        RowSnapshot<Object> mockSnapshot = failingMock(RowSnapshot.class);
        doReturn(new RowUriReference<>(Uri.parse("content://abc/xyz/123"))).when(mockSnapshot).reference();

        Uri dummyUri = dummy(Uri.class);
        assertThat(new Referring<>(mockSnapshot, "column", new RawInsert<>(dummyUri)),
                builds(
                        targets(sameInstance(dummyUri)),
                        insertOperation(),
                        withoutExpectedCount(),
                        withYieldNotAllowed(),
                        withValuesOnly(
                                containing("column", 123L))));
    }
}