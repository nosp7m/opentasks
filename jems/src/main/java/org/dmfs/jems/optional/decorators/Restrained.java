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

package org.dmfs.jems.optional.decorators;

import org.dmfs.jems.optional.Optional;
import org.dmfs.jems.single.Single;

import java.util.NoSuchElementException;


/**
 * {@link Optional} decorator restrains the presence of another {@link Optional} with a specific condition.
 *
 * @author Marten Gajda
 */
public final class Restrained<T> implements Optional<T>
{
    private final Single<Boolean> mCondition;
    private final Optional<T> mDelegate;


    public Restrained(Single<Boolean> condition, Optional<T> delegate)
    {
        mCondition = condition;
        mDelegate = delegate;
    }


    @Override
    public boolean isPresent()
    {
        return mCondition.value() && mDelegate.isPresent();
    }


    @Override
    public T value() throws NoSuchElementException
    {
        if (!mCondition.value())
        {
            throw new NoSuchElementException("Optional restrained by condition.");
        }
        return mDelegate.value();
    }
}
