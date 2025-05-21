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

package org.dmfs.iterators.filters;

import org.dmfs.iterators.Filter;

import java.util.HashSet;
import java.util.Set;


/**
 * A {@link Filter} that evaluates to {@code true} exactly once per element.
 * <p>
 * By design this class is neither immutable nor thread safe.
 *
 * @param <E>
 *         The type of the arguments.
 *
 * @author Marten Gajda
 */
public final class Distinct<E> implements Filter<E>
{
    private final Set<E> mIteratedElements = new HashSet<>(32);


    @Override
    public boolean iterate(E element)
    {
        // passes only if it hasn't been added before
        return mIteratedElements.add(element);
    }
}
