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

import org.dmfs.iterators.Filter;
import org.dmfs.optional.Optional;

import java.util.NoSuchElementException;


/**
 * {@link Optional} decorator that is present if the delegate is present and its value fulfils the filter condition.
 *
 * @author Gabor Keszthelyi
 */
public final class Filtered<T> implements Optional<T>
{
    private final Filter<T> mFilter;
    private final Optional<T> mDelegate;


    public Filtered(Filter<T> filter, Optional<T> delegate)
    {
        mFilter = filter;
        mDelegate = delegate;
    }


    @Override
    public boolean isPresent()
    {
        return mDelegate.isPresent() && mFilter.iterate(mDelegate.value());
    }


    @Override
    public T value(T defaultValue)
    {
        return isPresent() ? mDelegate.value() : defaultValue;
    }


    @Override
    public T value() throws NoSuchElementException
    {
        if (isPresent())
        {
            return mDelegate.value();
        }
        throw new NoSuchElementException("Filtered value not present");
    }
}
