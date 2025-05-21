/*
 * Copyright 2019 dmfs GmbH
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
import org.dmfs.android.contentpal.RowSnapshot;
import org.dmfs.android.contentpal.testing.table.Contract;
import org.dmfs.android.contentpal.tools.FakeClosable;
import org.dmfs.iterators.EmptyIterator;
import org.dmfs.jems.iterator.elementary.Seq;
import org.junit.Test;

import static org.dmfs.jems.hamcrest.matchers.IterableMatcher.iteratesTo;
import static org.dmfs.jems.mockito.doubles.TestDoubles.dummy;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.emptyIterable;
import static org.junit.Assert.assertThat;


/**
 * @author Marten Gajda
 */
public class FrozenTest
{
    @Test
    public void testEmpty()
    {
        ClosableIterator<RowSnapshot<Contract>> delegate = new FakeClosable<>(new EmptyIterator<>());
        assertThat(new Frozen<>(() -> delegate), allOf(emptyIterable(), emptyIterable(), emptyIterable()));
    }


    @Test
    public void testSingleRow()
    {
        RowSnapshot<Contract> dummy1 = dummy(RowSnapshot.class);
        ClosableIterator<RowSnapshot<Contract>> delegate = new FakeClosable<>(new Seq<>(dummy1));
        assertThat(new Frozen<>(() -> delegate), allOf(iteratesTo(dummy1), iteratesTo(dummy1), iteratesTo(dummy1)));
    }


    @Test
    public void test2Rows()
    {
        RowSnapshot<Contract> dummy1 = dummy(RowSnapshot.class);
        RowSnapshot<Contract> dummy2 = dummy(RowSnapshot.class);
        ClosableIterator<RowSnapshot<Contract>> delegate = new FakeClosable<>(new Seq<>(dummy1, dummy2));
        assertThat(new Frozen<>(() -> delegate), allOf(iteratesTo(dummy1, dummy2), iteratesTo(dummy1, dummy2), iteratesTo(dummy1, dummy2)));
    }
}