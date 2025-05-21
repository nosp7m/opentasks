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

package org.dmfs.android.contentpal.rowdata;

import android.content.ContentProviderOperation;

import org.dmfs.android.contentpal.RowData;
import org.dmfs.android.contentpal.TransactionContext;
import org.junit.Test;

import static org.dmfs.jems.mockito.doubles.TestDoubles.dummy;
import static org.dmfs.jems.mockito.doubles.TestDoubles.failingMock;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;


/**
 * @author Marten Gajda
 */
public class DelegatingRowDataTest
{
    @Test
    public void testUpdatedBuilder()
    {
        ContentProviderOperation.Builder dummyResultBuilder = dummy(ContentProviderOperation.Builder.class);
        ContentProviderOperation.Builder dummyParamBuilder = dummy(ContentProviderOperation.Builder.class);
        TransactionContext dummyTransactionContext = dummy(TransactionContext.class);
        // the delegate returns a different builder so we can check that we actually receive the result of the delegate
        RowData<Object> mockDelegate = failingMock(RowData.class);
        doReturn(dummyResultBuilder).when(mockDelegate).updatedBuilder(dummyTransactionContext, dummyParamBuilder);

        assertThat(new TestDecorator<>(mockDelegate).updatedBuilder(dummyTransactionContext, dummyParamBuilder), is(dummyResultBuilder));
    }


    private final class TestDecorator<T> extends DelegatingRowData<T>
    {

        public TestDecorator(RowData<T> delegate)
        {
            super(delegate);
        }
    }

}