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

import org.dmfs.iterables.SingletonIterable;
import org.dmfs.jems.iterable.elementary.Seq;
import org.dmfs.jems.optional.Optional;

import java.util.Iterator;


/**
 * {@link Iterable} which iterates over the present values from the input {@link Iterable} of {@link Optional}s of {@code E}.
 *
 * @author Gabor Keszthelyi
 * @author Marten Gajda
 * @deprecated in favour of {@link org.dmfs.jems.iterable.adapters.PresentValues}
 */
@Deprecated
public final class PresentValues<E> implements Iterable<E>
{
    private final Iterable<Optional<E>> mOptionals;


    public PresentValues(Optional<E> optional)
    {
        this(new SingletonIterable<>(optional));
    }


    @SafeVarargs
    public PresentValues(Optional<E>... optionals)
    {
        this(new Seq<>(optionals));
    }


    public PresentValues(Iterable<Optional<E>> optionals)
    {
        mOptionals = optionals;
    }


    @Override
    public Iterator<E> iterator()
    {
        return new org.dmfs.iterators.elementary.PresentValues<>(mOptionals.iterator());
    }
}
