/*
 * Copyright 2018 dmfs GmbH
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

package org.dmfs.iterables.elementary;

import org.dmfs.iterators.EmptyIterator;
import org.dmfs.jems.optional.Optional;

import java.util.Iterator;


/**
 * An {@link Iterable} which iterates the elements of an {@link Optional} {@link Iterable} or nothing if the Optional is not present.
 *
 * @author Marten Gajda
 */
public final class OptionalIterable<T> implements Iterable<T>
{
    private final Optional<? extends Iterable<T>> mDelegate;


    public OptionalIterable(Optional<? extends Iterable<T>> delegate)
    {
        mDelegate = delegate;
    }


    @Override
    public Iterator<T> iterator()
    {
        return mDelegate.isPresent() ? mDelegate.value().iterator() : EmptyIterator.instance();
    }
}
