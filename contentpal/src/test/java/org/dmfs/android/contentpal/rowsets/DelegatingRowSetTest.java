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

package org.dmfs.android.contentpal.rowsets;

import org.dmfs.android.contentpal.ClosableIterator;
import org.dmfs.android.contentpal.RowSet;
import org.junit.Test;

import static org.dmfs.jems.mockito.doubles.TestDoubles.dummy;
import static org.dmfs.jems.mockito.doubles.TestDoubles.failingMock;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;


/**
 * Unit test for {@link DelegatingRowSet}.
 *
 * @author Gabor Keszthelyi
 */
public final class DelegatingRowSetTest
{

    @Test
    public void test()
    {
        RowSet<Object> mockRowSet = failingMock(RowSet.class);
        ClosableIterator dummyIterator = dummy(ClosableIterator.class);
        doReturn(dummyIterator).when(mockRowSet).iterator();

        assertThat(new TestRowSet<>(mockRowSet).iterator(), sameInstance(dummyIterator));
    }


    private static final class TestRowSet<T> extends DelegatingRowSet<T>
    {
        TestRowSet(RowSet<T> delegate)
        {
            super(delegate);
        }
    }
}