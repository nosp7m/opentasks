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

package org.dmfs.jems.optional.adapters;

import org.dmfs.jems.optional.Optional;

import java.util.NoSuchElementException;


/**
 * Collapses the given {@link Optional} of {@link Optional} into {@link Optional}.
 *
 * @author Marten Gajda
 * @author Gabor Keszthelyi
 */
public final class Collapsed<T> implements org.dmfs.optional.Optional<T>
{
    private final Optional<Optional<T>> mDelegate;


    public Collapsed(Optional<Optional<T>> delegate)
    {
        mDelegate = delegate;
    }


    @Override
    public boolean isPresent()
    {
        return mDelegate.isPresent() && mDelegate.value().isPresent();
    }


    @Override
    public T value() throws NoSuchElementException
    {
        return mDelegate.value().value();
    }


    @Override
    public T value(T defaultValue)
    {
        return isPresent() ? value() : defaultValue;
    }
}
