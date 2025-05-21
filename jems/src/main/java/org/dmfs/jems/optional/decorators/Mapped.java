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

package org.dmfs.jems.optional.decorators;

import org.dmfs.jems.function.Function;
import org.dmfs.jems.optional.Optional;

import java.util.NoSuchElementException;


/**
 * {@link Optional} that maps an {@link Optional} if it's present using the given {@link Function}.
 *
 * @author Gabor Keszthelyi
 */
public final class Mapped<From, To> implements org.dmfs.optional.Optional<To>
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
    public To value() throws NoSuchElementException
    {
        return mConversion.value(mFromValue.value());
    }


    @Override
    public To value(To defaultValue)
    {
        return isPresent() ? value() : defaultValue;
    }
}