/*
 * Copyright 2017 dmfs GmbH
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

package org.dmfs.iterators.decorators;

import org.dmfs.iterators.AbstractBaseIterator;
import org.dmfs.iterators.Function;

import java.util.Iterator;


/**
 * An {@link Iterator} that maps the elements of another {@link Iterator} using a {@link Function} before iterating
 * them.
 *
 * @param <OriginalType>
 *         The original type of the values iterated by the decorated {@link Iterator}.
 * @param <ResultType>
 *         The type of the values iterated by this {@link Iterator}.
 *
 * @author Marten Gajda
 * @deprecated use {@link org.dmfs.jems.iterator.decorators.Mapped} instead
 */
@Deprecated
public final class Mapped<OriginalType, ResultType> extends AbstractBaseIterator<ResultType>
{
    private final Iterator<OriginalType> mDelegate;
    private final Function<OriginalType, ResultType> mFunction;


    /**
     * Creates a {@link Mapped} {@link Iterator} that iterates the elements of the given {@link Iterator} after mapping
     * them using the given {@link Function}.
     *
     * @param delegate
     *         The {@link Iterator} to be mapped.
     * @param function
     *         The {@link Function} to apply to all elements.
     */
    public Mapped(final Iterator<OriginalType> delegate, final Function<OriginalType, ResultType> function)
    {
        mDelegate = delegate;
        mFunction = function;
    }


    @Override
    public final boolean hasNext()
    {
        return mDelegate.hasNext();
    }


    @Override
    public final ResultType next()
    {
        return mFunction.apply(mDelegate.next());
    }

}
