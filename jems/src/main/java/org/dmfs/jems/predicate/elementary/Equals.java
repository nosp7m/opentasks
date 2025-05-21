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

package org.dmfs.jems.predicate.elementary;

import org.dmfs.jems.predicate.Predicate;


/**
 * A trivial {@link Predicate} which delegates to {@link Object#equals(Object)} and matches if it returns {@code true}.
 *
 * @author Marten Gajda
 */
public final class Equals<T> implements Predicate<T>
{
    private final T mValue;


    public Equals(T value)
    {
        mValue = value;
    }


    @Override
    public boolean satisfiedBy(T testedInstance)
    {
        return mValue.equals(testedInstance);
    }
}
