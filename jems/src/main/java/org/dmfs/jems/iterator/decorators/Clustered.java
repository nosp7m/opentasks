/*
 * Copyright 2019 dmfs GmbH
 *
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

package org.dmfs.jems.iterator.decorators;

import org.dmfs.jems.optional.Optional;
import org.dmfs.jems.optional.adapters.Next;
import org.dmfs.jems.optional.elementary.Present;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static org.dmfs.jems.optional.elementary.Absent.absent;


/**
 * An {@link Iterator} decorator which clusters consecutive elements of another {@link Iterator} by the result of a {@link Comparator}. Consecutive elements
 * which compare to {@code 0} go into the same cluster. The original order of the elements remains the same.
 *
 * <pre>{@code
 * Clustered(Comparator.naturalOrder(), [5, 5, 5, 3, 3, 4, 4, 4, 5, 5, 2, 2]) ->
 * [
 *   [5, 5, 5],
 *   [3, 3],
 *   [4, 4, 4],
 *   [5, 5],
 *   [2, 2]
 * ]
 * }</pre>
 *
 * @author Marten Gajda
 */
public final class Clustered<T> implements Iterator<Iterable<T>>
{
    private final Iterator<T> mDelegate;
    private final Comparator<? super T> mComparator;
    private Optional<T> mNext;


    public Clustered(Comparator<? super T> comparator, Iterator<T> delegate)
    {
        mDelegate = delegate;
        mComparator = comparator;
        mNext = new Next<>(mDelegate);
    }


    @Override
    public boolean hasNext()
    {
        return mNext.isPresent();
    }


    @Override
    public Iterable<T> next()
    {
        T first = mNext.value();

        List<T> result = new LinkedList<>();
        result.add(first);
        boolean hasMore;
        T next = null;

        // add elements as long as they are equal to the first one
        while ((hasMore = mDelegate.hasNext()) && mComparator.compare(first, next = mDelegate.next()) == 0)
        {
            result.add(next);
        }

        mNext = hasMore ? new Present<>(next) : absent();
        return result;
    }
}
