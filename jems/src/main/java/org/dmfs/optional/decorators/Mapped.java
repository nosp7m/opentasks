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

package org.dmfs.optional.decorators;

import org.dmfs.iterators.Function;
import org.dmfs.optional.Optional;

import java.util.NoSuchElementException;


/**
 * {@link Optional} that maps an {@link Optional} if it's present using the given {@link Function}.
 *
 * @author Gabor Keszthelyi
 * @deprecated use {@link org.dmfs.jems.optional.decorators.Mapped} instead
 */
@Deprecated
public final class Mapped<From, To> implements Optional<To>
{
    private final Optional<From> mFromValue;
    private final Function<From, To> mConversion;


    public Mapped(Function<From, To> conversion, Optional<From> fromValue)
    {
        mConversion = conversion;
        mFromValue = fromValue;
    }


    @Override
    public boolean isPresent()
    {
        return mFromValue.isPresent();
    }


    @Override
    public To value(To defaultValue)
    {
        return mFromValue.isPresent() ? value() : defaultValue;
    }


    @Override
    public To value() throws NoSuchElementException
    {
        return mConversion.apply(mFromValue.value());
    }
}