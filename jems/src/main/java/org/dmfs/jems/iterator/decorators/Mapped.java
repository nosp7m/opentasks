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

package org.dmfs.jems.iterator.decorators;

import org.dmfs.iterators.AbstractBaseIterator;
import org.dmfs.jems.function.Function;

import java.util.Iterator;


/**
 * An {@link Iterator} that maps the elements of another {@link Iterator} using a {@link Function} before iterating them.
 *
 * @param <OriginalType>
 *         The original type of the values iterated by the decorated {@link Iterator}.
 * @param <ResultType>
 *         The type of the values iterated by this {@link Iterator}.
 *
 * @author Marten Gajda
 * @author Gabor Keszthelyi
 */
public final class Mapped<OriginalType, ResultType> extends AbstractBaseIterator<ResultType>
{
    private final Iterator<? extends OriginalType> mOriginal;
    private final Function<? super OriginalType, ? extends ResultType> mFunction;


    /**
     * Creates a {@link Mapped} {@link Iterator} that iterates the elements of the given {@link Iterator} after mapping them using the given {@link Function}.
     *
     * @param mapFunction
     *         The {@link Function} to apply to all elements.
     * @param original
     *         The {@link Iterator} to be mapped.
     */
    public Mapped(Function<? super OriginalType, ? extends ResultType> mapFunction, Iterator<? extends OriginalType> original)
    {
        mOriginal = original;
        mFunction = mapFunction;
    }


    @Override
    public final boolean hasNext()
    {
        return mOriginal.hasNext();
    }


    @Override
    public final ResultType next()
    {
        return mFunction.value(mOriginal.next());
    }

}
