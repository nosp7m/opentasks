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

package org.dmfs.android.contentpal.testing.rowset;

import org.dmfs.android.contentpal.ClosableIterator;
import org.dmfs.android.contentpal.RowSet;
import org.dmfs.android.contentpal.RowSnapshot;
import org.dmfs.android.contentpal.testing.tools.FakeClosable;
import org.dmfs.jems.iterable.elementary.Seq;

import androidx.annotation.NonNull;


/**
 * {@link RowSet} that simply delegates to the provided {@link RowSnapshot}s.
 *
 * @author Gabor Keszthelyi
 */
public final class TestRowSet<T> implements RowSet<T>
{
    private final Iterable<RowSnapshot<T>> mDelegate;


    public TestRowSet(@NonNull Iterable<RowSnapshot<T>> delegate)
    {
        mDelegate = delegate;
    }


    @SafeVarargs
    public TestRowSet(@NonNull RowSnapshot<T>... rowSnapshots)
    {
        this(new Seq<>(rowSnapshots));
    }


    @NonNull
    @Override
    public ClosableIterator<RowSnapshot<T>> iterator()
    {
        return new FakeClosable<>(mDelegate.iterator());
    }
}
