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

package org.dmfs.optional.adapters;

import org.dmfs.jems.single.Single;
import org.dmfs.optional.Optional;

import java.util.NoSuchElementException;


/**
 * A present {@link Optional} that takes a {@link Single} for the value.
 *
 * @author Gabor Keszthelyi
 * @deprecated in favour of {@link org.dmfs.jems.optional.adapters.SinglePresent}.
 */
@Deprecated
public final class SinglePresent<T> implements Optional<T>
{
    private final Single<T> mSingle;


    public SinglePresent(Single<T> single)
    {
        mSingle = single;
    }


    @Override
    public boolean isPresent()
    {
        return true;
    }


    @Override
    public T value(T defaultValue)
    {
        return mSingle.value();
    }


    @Override
    public T value() throws NoSuchElementException
    {
        return mSingle.value();
    }
}
