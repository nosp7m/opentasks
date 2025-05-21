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
import org.dmfs.jems.predicate.Predicate;

import java.util.NoSuchElementException;


/**
 * {@link Optional} decorator which is present if the delegate is present and satisfies a specific {@link Predicate}.
 *
 * @author Marten Gajda
 */
public final class Sieved<T> implements Optional<T>
{
    private final Predicate<? super T> mPredicate;
    private final Optional<T> mDelegate;


    public Sieved(Predicate<? super T> predicate, Optional<T> delegate)
    {
        mPredicate = predicate;
        mDelegate = delegate;
    }


    @Override
    public boolean isPresent()
    {
        return mDelegate.isPresent() && mPredicate.satisfiedBy(mDelegate.value());
    }


    @Override
    public T value() throws NoSuchElementException
    {
        T value = mDelegate.value();
        if (!mPredicate.satisfiedBy(value))
        {
            throw new NoSuchElementException("Delegate sieved.");
        }
        return value;
    }
}
