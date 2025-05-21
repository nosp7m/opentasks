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

package org.dmfs.android.contentpal.batches;

import org.dmfs.android.contentpal.Operation;
import org.dmfs.android.contentpal.RowSet;
import org.dmfs.android.contentpal.RowSnapshot;
import org.dmfs.android.contentpal.testing.rowset.TestRowSet;
import org.dmfs.android.contentpal.testing.table.Contract;
import org.dmfs.iterables.decorators.DelegatingIterable;
import org.dmfs.jems.iterable.decorators.Mapped;
import org.dmfs.jems.iterable.elementary.Seq;
import org.dmfs.jems.mocks.MockFunction;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.junit.Test;

import static org.dmfs.jems.hamcrest.matchers.IterableMatcher.iteratesTo;
import static org.dmfs.jems.mockito.doubles.TestDoubles.dummy;
import static org.junit.Assert.assertThat;


/**
 * Test for {@link MappedRowSetBatch}.
 *
 * @author Gabor Keszthelyi
 */
public final class MappedRowSetBatchTest
{

    @Test
    public void test()
    {
        RowSet<Contract> rowSet = new TestRowSet<Contract>(dummy(RowSnapshot.class), dummy(RowSnapshot.class), dummy(RowSnapshot.class));
        Iterable<Operation<?>> dummyOperations = new Seq<Operation<?>>(dummy(Operation.class), dummy(Operation.class), dummy(Operation.class));

        // TODO: method reference when we have a MockFunction implementing the new Function interface
        assertThat(new MappedRowSetBatch<>(rowSet, new MockFunction<>(new InstanceMatching<>(rowSet), dummyOperations)::apply),
                iteratesTo(new InstanceMatching<>(dummyOperations)));
    }


    // TODO Use from jems:test-utils when available
    private static final class InstanceMatching<T> extends DelegatingIterable<Matcher<T>>
    {
        InstanceMatching(Iterable<T> delegate)
        {
            super(new Mapped<>(CoreMatchers::sameInstance, delegate));
        }
    }

}