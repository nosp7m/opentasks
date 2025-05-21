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

package org.dmfs.jems.iterable.decorators;

import org.dmfs.jems.function.Function;

import java.util.Iterator;


/**
 * {@link Iterable} decorator maps the results of the delegate {@link Iterable} using a given {@link Function}.
 *
 * @author Gabor Keszthelyi
 */
public final class Mapped<OriginalType, ResultType> implements Iterable<ResultType>
{
    private final Iterable<? extends OriginalType> mOriginal;
    private final Function<? super OriginalType, ? extends ResultType> mMapFunction;


    public Mapped(Function<? super OriginalType, ? extends ResultType> mapFunction, Iterable<? extends OriginalType> original)
    {
        mOriginal = original;
        mMapFunction = mapFunction;
    }


    @Override
    public Iterator<ResultType> iterator()
    {
        return new org.dmfs.jems.iterator.decorators.Mapped<>(mMapFunction, mOriginal.iterator());
    }
}
