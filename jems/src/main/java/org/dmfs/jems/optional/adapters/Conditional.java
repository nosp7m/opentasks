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
import org.dmfs.jems.optional.elementary.Present;
import org.dmfs.jems.predicate.Predicate;
import org.dmfs.jems.single.Single;
import org.dmfs.jems.single.elementary.ValueSingle;

import java.util.NoSuchElementException;

import static org.dmfs.optional.Absent.absent;


/**
 * {@link Optional} that is present with the value of the provided target if it satisfies the given {@link Predicate}, otherwise it is absent.
 *
 * @author Gabor Keszthelyi
 */
public final class Conditional<T> implements org.dmfs.optional.Optional<T>
{
    private final Predicate<? super T> mPredicate;
    private final Single<T> mTargetSingle;

    private Optional<T> mCachedDelegate;


    public Conditional(Predicate<? super T> predicate, Single<T> targetSingle)
    {
        mPredicate = predicate;
        mTargetSingle = targetSingle;
    }


    public Conditional(Predicate<T> predicate, T targetValue)
    {
        this(predicate, new ValueSingle<>(targetValue));
    }


    @Override
    public boolean isPresent()
    {
        return cachedDelegate().isPresent();
    }


    @Override
    public T value() throws NoSuchElementException
    {
        return cachedDelegate().value();
    }


    private Optional<T> cachedDelegate()
    {
        if (mCachedDelegate == null)
        {
            T targetValue = mTargetSingle.value();
            mCachedDelegate = mPredicate.satisfiedBy(targetValue) ? new Present<>(targetValue) : absent();
        }
        return mCachedDelegate;
    }


    @Override
    public T value(T defaultValue)
    {
        return isPresent() ? value() : defaultValue;
    }
}
