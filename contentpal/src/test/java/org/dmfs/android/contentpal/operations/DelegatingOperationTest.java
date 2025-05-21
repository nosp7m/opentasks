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

import org.dmfs.android.contentpal.Operation;
import org.dmfs.android.contentpal.SoftRowReference;
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.jems.optional.Optional;
import org.junit.Test;

import static org.dmfs.jems.mockito.doubles.TestDoubles.dummy;
import static org.dmfs.jems.mockito.doubles.TestDoubles.failingMock;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;


/**
 * Unit test for {@link DelegatingOperation}.
 *
 * @author Gabor Keszthelyi
 */
public final class DelegatingOperationTest
{

    @Test
    public void test()
    {
        Operation<Object> mockDelegate = failingMock(Operation.class);
        Optional<SoftRowReference<Object>> dummyRowReference = dummy(Optional.class);
        ContentProviderOperation.Builder dummyBuilder = dummy(ContentProviderOperation.Builder.class);
        TransactionContext dummyTContext = dummy(TransactionContext.class);

        doReturn(dummyRowReference).when(mockDelegate).reference();
        doReturn(dummyBuilder).when(mockDelegate).contentOperationBuilder(dummyTContext);

        assertThat(new TestDelegatingOperation<>(mockDelegate).reference(), sameInstance(dummyRowReference));
        assertThat(new TestDelegatingOperation<>(mockDelegate).contentOperationBuilder(dummyTContext), sameInstance(dummyBuilder));
    }


    private static final class TestDelegatingOperation<T> extends DelegatingOperation<T>
    {

        private TestDelegatingOperation(Operation<T> delegate)
        {
            super(delegate);
        }
    }

}