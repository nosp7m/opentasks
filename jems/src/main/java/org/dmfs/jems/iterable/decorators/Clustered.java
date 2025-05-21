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

package org.dmfs.jems.iterable.decorators;

import java.util.Comparator;
import java.util.Iterator;


/**
 * An {@link Iterable} decorator which clusters consecutive elements of another {@link Iterable} by the result of a {@link Comparator}. Consecutive elements
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
public final class Clustered<T> implements Iterable<Iterable<T>>
{
    private final Iterable<T> mDelegate;
    private final Comparator<? super T> mComparator;


    public Clustered(Comparator<? super T> comparator, Iterable<T> delegate)
    {
        mDelegate = delegate;
        mComparator = comparator;
    }


    @Override
    public Iterator<Iterable<T>> iterator()
    {
        return new org.dmfs.jems.iterator.decorators.Clustered<T>(mComparator, mDelegate.iterator());
    }
}
