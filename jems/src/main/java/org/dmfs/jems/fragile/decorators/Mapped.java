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

package org.dmfs.jems.fragile.decorators;

import org.dmfs.jems.fragile.Fragile;
import org.dmfs.jems.function.FragileFunction;


/**
 * A {@link Fragile} decorator which maps the value using a given {@link FragileFunction}.
 */
public final class Mapped<From, To, E extends Throwable> implements Fragile<To, E>
{
    private final FragileFunction<? super From, ? extends To, ? extends E> mMapFunction;
    private final Fragile<? extends From, ? extends E> mDelegate;


    public Mapped(
        FragileFunction<? super From, ? extends To, ? extends E> mapFunction,
        Fragile<? extends From, ? extends E> delegate)
    {
        mMapFunction = mapFunction;
        mDelegate = delegate;
    }


    @Override
    public To value() throws E
    {
        return mMapFunction.value(mDelegate.value());
    }
}
