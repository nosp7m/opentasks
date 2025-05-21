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

package org.dmfs.iterables.decorators;

import org.dmfs.iterators.Function;

import java.util.Iterator;


/**
 * {@link Iterable} decorator that applies {@link org.dmfs.iterators.decorators.Mapped} to the returned {@link Iterator}.
 *
 * @author Gabor Keszthelyi
 * @deprecated use {@link org.dmfs.jems.iterable.decorators.Mapped} instead
 */
@Deprecated
public final class Mapped<OriginalType, ResultType> implements Iterable<ResultType>
{
    private final Iterable<OriginalType> mDelegate;
    private final Function<OriginalType, ResultType> mFunction;


    public Mapped(Iterable<OriginalType> delegate, Function<OriginalType, ResultType> function)
    {
        mDelegate = delegate;
        mFunction = function;
    }


    @Override
    public Iterator<ResultType> iterator()
    {
        return new org.dmfs.iterators.decorators.Mapped<>(mDelegate.iterator(), mFunction);
    }
}
