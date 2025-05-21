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

package org.dmfs.jems.generator;

import org.dmfs.jems.single.Single;

import java.util.Iterator;


/**
 * A Generator is able to generate an infinite sequence of values. It's similar to {@link Iterator} with the difference that a generator always has a next
 * element, hence there is no {@code hasNext()} method.
 * <p>
 * A Generator also serves as an equivalent to {@link Single} for mutable values. A {@link Single} should not be used with mutable values because mutation of
 * the value would affect all other uses of the {@link Single} because other callers might receive the mutated value. A {@link Generator} on the other hand is
 * expected to return (generate) a new instance on each call if the results are mutable.
 *
 * @author Marten Gajda
 */
public interface Generator<T>
{
    /**
     * Generates and returns another value.
     *
     * @return Another generated value.
     */
    T next();
}
