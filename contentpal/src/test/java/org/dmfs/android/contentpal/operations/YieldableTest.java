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
import org.dmfs.android.contentpal.SoftRowReference;
import org.dmfs.android.contentpal.operations.internal.RawInsert;
import org.dmfs.jems.optional.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.dmfs.android.contentpal.testing.contentoperationbuilder.OperationType.insertOperation;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.TargetMatcher.targets;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithExpectedCount.withoutExpectedCount;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithValues.withoutValues;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithYieldAllowed.withYieldAllowed;
import static org.dmfs.android.contentpal.testing.operations.OperationMatcher.builds;
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
public class YieldableTest
{
    @Test
    public void testReference()
    {
        Optional<SoftRowReference<Object>> dummyReference = dummy(Optional.class);
        Operation<Object> mockOperation = failingMock(Operation.class);

        doReturn(dummyReference).when(mockOperation).reference();

        assertThat(new Yieldable<>(mockOperation).reference(), sameInstance(dummyReference));
    }


    @Test
    public void testContentOperationBuilder()
    {
        Uri dummyUri = dummy(Uri.class);
        assertThat(
                new Yieldable<>(new RawInsert<>(dummyUri)),
                builds(
                        targets(sameInstance(dummyUri)),
                        insertOperation(),
                        withYieldAllowed(),
                        withoutExpectedCount(),
                        withoutValues()));
    }
}