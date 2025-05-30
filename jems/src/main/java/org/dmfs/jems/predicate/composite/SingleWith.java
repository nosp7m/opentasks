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

package org.dmfs.jems.predicate.composite;

import org.dmfs.jems.predicate.Predicate;
import org.dmfs.jems.single.Single;


/**
 * A {@link Predicate} to match the value of a {@link Single}.
 *
 * @author Marten Gajda
 */
public final class SingleWith<V, T extends Single<? extends V>> implements Predicate<T>
{
    private final Predicate<? super V> mDelegate;


    public SingleWith(Predicate<? super V> delegate)
    {
        mDelegate = delegate;
    }


    @Override
    public boolean satisfiedBy(T testedInstance)
    {
        return mDelegate.satisfiedBy(testedInstance.value());
    }
}
