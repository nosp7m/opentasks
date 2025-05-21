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

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


/**
 * A {@link Filter} that evaluates to {@code true} if the tested elements are not in a given list of elements.
 *
 * @param <E>
 *         The type of the arguments.
 *
 * @author Marten Gajda
 */
public final class NoneOf<E> implements Filter<E>
{
    private final Set<E> mExcludes;


    /**
     * Creates a {@link NoneOf} {@link Filter} from an array of elements that will fail the test.
     *
     * @param excluded
     *         The elements that are supposed to fail the test.
     */
    @SafeVarargs
    public NoneOf(E... excluded)
    {
        this(Arrays.asList(excluded));
    }


    /**
     * Creates a {@link NoneOf} {@link Filter} from a {@link Collection} of elements that will fail the test.
     *
     * @param excluded
     *         The {@link Collection} of elements that are supposed to fail the test.
     */
    public NoneOf(Collection<E> excluded)
    {
        this(new HashSet<E>(excluded));
    }


    private NoneOf(Set<E> excludes)
    {
        mExcludes = excludes;
    }


    @Override
    public boolean iterate(E element)
    {
        return !mExcludes.contains(element);
    }
}
