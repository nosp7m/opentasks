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
import org.dmfs.jems.optional.elementary.Present;
import org.dmfs.jems.single.Single;

import java.util.NoSuchElementException;

import static org.dmfs.jems.optional.elementary.Absent.absent;


/**
 * An {@link Optional} decorator which avoids frequent (potentially expensive) evaluation of another {@link Optional} by retaining the result of the first
 * evaluation.
 *
 * @author Marten Gajda
 */
public final class Frozen<T> implements Optional<T>
{
    private final Single<Optional<T>> mDelegate;


    public Frozen(Optional<T> delegate)
    {
        mDelegate = new org.dmfs.jems.single.elementary.Frozen<>(() -> delegate.isPresent() ? new Present<>(delegate.value()) : absent());
    }


    @Override
    public boolean isPresent()
    {
        return mDelegate.value().isPresent();
    }


    @Override
    public T value() throws NoSuchElementException
    {
        return mDelegate.value().value();
    }
}
