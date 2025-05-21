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

import org.dmfs.android.contentpal.SoftRowReference;
import org.junit.Test;

import static org.dmfs.jems.mockito.doubles.TestDoubles.dummy;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;


/**
 * Test {@link EmptyTransactionContext}.
 *
 * @author Marten Gajda
 */
public class EmptyTransactionContextTest
{
    @Test
    public void test()
    {
        SoftRowReference<Object> ref = dummy(SoftRowReference.class);
        assertThat(new EmptyTransactionContext().resolved(ref), is(sameInstance(ref)));
    }

}