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

package org.dmfs.jems.optional.elementary;

import org.dmfs.jems.optional.Optional;

import java.util.NoSuchElementException;


/**
 * An {@link Optional} that's not present if the given value is {@code null}.
 *
 * @author Marten Gajda
 */
public final class NullSafe<T> implements Optional<T>
{
    private final T mValue;


    public NullSafe(T value)
    {
        mValue = value;
    }


    @Override
    public boolean isPresent()
    {
        return mValue != null;
    }


    @Override
    public T value() throws NoSuchElementException
    {
        if (mValue == null)
        {
            throw new NoSuchElementException("The value of this Optional is not present.");
        }
        return mValue;
    }
}
