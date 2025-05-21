/*
 * Copyright 2018 dmfs GmbH
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

package org.dmfs.android.contentpal.transactions.contexts;

import org.dmfs.android.contentpal.RowReference;
import org.dmfs.android.contentpal.SoftRowReference;
import org.dmfs.android.contentpal.TransactionContext;
import org.junit.Test;

import static org.dmfs.jems.mockito.doubles.TestDoubles.dummy;
import static org.dmfs.jems.mockito.doubles.TestDoubles.failingMock;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;


/**
 * Test {@link QuickTest}.
 *
 * @author Marten Gajda
 */
public class QuickTest
{
    /**
     * Test that {@link Quick} returns non-virtual references as is.
     */
    @Test
    public void testNonVirtual()
    {
        SoftRowReference<Object> ref = dummy(SoftRowReference.class);
        doReturn(false).when(ref).isVirtual();
        TransactionContext delegate = failingMock(TransactionContext.class);
        assertThat(new Quick(delegate).resolved(ref), is(sameInstance(ref)));
    }


    /**
     * Test that {@link Quick} delegates resolution of virtual references.
     */
    @Test
    public void testVirtual()
    {
        SoftRowReference<Object> ref = dummy(SoftRowReference.class);
        RowReference<Object> ref2 = dummy(RowReference.class);
        doReturn(true).when(ref).isVirtual();
        TransactionContext delegate = failingMock(TransactionContext.class);
        doReturn(ref2).when(delegate).resolved(ref);
        assertThat(new Quick(delegate).resolved(ref), is(sameInstance(ref2)));
    }

}