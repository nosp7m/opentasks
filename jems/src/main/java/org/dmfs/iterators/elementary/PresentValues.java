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

package org.dmfs.iterators.elementary;

import org.dmfs.iterators.AbstractBaseIterator;
import org.dmfs.iterators.SingletonIterator;
import org.dmfs.iterators.decorators.Filtered;
import org.dmfs.jems.iterator.elementary.Seq;
import org.dmfs.jems.optional.Optional;

import java.util.Iterator;


/**
 * {@link Iterator} that iterates over the present values from the input {@link Iterator} of {@link Optional}s of {@code E}.
 *
 * @author Marten Gajda
 */
public final class PresentValues<E> extends AbstractBaseIterator<E>
{
    private final Iterator<Optional<E>> mDelegate;


    public PresentValues(Optional<E> optional)
    {
        this(new SingletonIterator<>(optional));
    }


    @SafeVarargs
    public PresentValues(Optional<E>... optionals)
    {
        this(new Seq<>(optionals));
    }


    public PresentValues(Iterator<Optional<E>> optionals)
    {
        mDelegate = new Filtered<>(optionals, Optional::isPresent);
    }


    @Override
    public boolean hasNext()
    {
        return mDelegate.hasNext();
    }


    @Override
    public E next()
    {
        return mDelegate.next().value();
    }
}
