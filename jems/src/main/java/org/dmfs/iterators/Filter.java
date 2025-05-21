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

package org.dmfs.iterators;

/**
 * Represents a filter function.
 * <p>
 * Note that filters may be mutable.
 *
 * @param <E>
 *         The type of the arguments.
 *
 * @author Marten Gajda
 */
public interface Filter<E>
{
    /**
     * Filters the given argument.
     *
     * @param argument
     *         The input Argument.
     *
     * @return <code>true</code> if the input argument iterate the filter, <code>false</code> otherwise.
     */
    boolean iterate(E argument);
}
