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

import org.dmfs.android.contentpal.InsertOperation;
import org.dmfs.android.contentpal.SoftRowReference;
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.jems.optional.Optional;
import org.junit.Test;

import androidx.annotation.NonNull;

import static org.dmfs.jems.mockito.doubles.TestDoubles.dummy;
import static org.dmfs.jems.mockito.doubles.TestDoubles.failingMock;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;


/**
 * Unit test for {@link DelegatingInsertOperation}.
 *
 * @author Gabor Keszthelyi
 */
public final class DelegatingInsertOperationTest
{

    @Test
    public void test()
    {
        InsertOperation<Object> mockInsertOperation = failingMock(InsertOperation.class);
        Optional<SoftRowReference<Object>> dummyRowReference = dummy(Optional.class);
        ContentProviderOperation.Builder dummyBuilder = dummy(ContentProviderOperation.Builder.class);

        doReturn(dummyRowReference).when(mockInsertOperation).reference();
        doReturn(dummyBuilder).when(mockInsertOperation).contentOperationBuilder(any(TransactionContext.class));

        TestInsertOperation<Object> underTest = new TestInsertOperation<>(mockInsertOperation);
        assertThat(underTest.reference(), sameInstance(dummyRowReference));
        assertThat(underTest.contentOperationBuilder(dummy(TransactionContext.class)), sameInstance(dummyBuilder));
    }


    private static final class TestInsertOperation<T> extends DelegatingInsertOperation<T>
    {
        public TestInsertOperation(@NonNull InsertOperation<T> delegate)
        {
            super(delegate);
        }
    }

}