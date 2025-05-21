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
import org.dmfs.android.contentpal.RowSet;
import org.dmfs.android.contentpal.RowSnapshot;
import org.dmfs.android.contentpal.tools.FakeClosable;
import org.dmfs.jems.single.Single;
import org.dmfs.jems.single.adapters.Unchecked;

import java.util.LinkedList;
import java.util.List;

import androidx.annotation.NonNull;


/**
 * A {@link RowSet} which eagerly evaluates another {@link RowSet}. Iterating over this {@link RowSet} more than once will not cause additional content provider
 * queries.
 *
 * @author Marten Gajda
 */
public final class Frozen<T> implements RowSet<T>
{
    private final Single<Iterable<RowSnapshot<T>>> mDelegate;


    public Frozen(@NonNull RowSet<T> delegate)
    {
        mDelegate = new org.dmfs.jems.single.elementary.Frozen<>(new Unchecked<>(() -> {
            try (ClosableIterator<RowSnapshot<T>> iterator = delegate.iterator())
            {
                List<RowSnapshot<T>> result = new LinkedList<>();

                while (iterator.hasNext())
                {
                    result.add(iterator.next());
                }
                return result;
            }
        }));
    }


    @NonNull
    @Override
    public ClosableIterator<RowSnapshot<T>> iterator()
    {
        return new FakeClosable<>(mDelegate.value().iterator());
    }
}
