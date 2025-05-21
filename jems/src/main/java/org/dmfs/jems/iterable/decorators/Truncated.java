/*
 * Copyright 2019 dmfs GmbH
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

package org.dmfs.jems.iterable.decorators;

import org.dmfs.iterables.decorators.DelegatingIterable;
import org.dmfs.jems.generatable.Generatable;


/**
 * An {@link Iterable} decorator which returns only the first few elements of the delegate.
 *
 * @author Marten Gajda
 */
public final class Truncated<T> extends DelegatingIterable<T>
{
    public Truncated(int limit, Generatable<T> delegate)
    {
        super(() -> new org.dmfs.jems.iterator.decorators.Truncated<>(limit, delegate.generator()));
    }


    public Truncated(int limit, Iterable<T> delegate)
    {
        super(() -> new org.dmfs.jems.iterator.decorators.Truncated<>(limit, delegate.iterator()));
    }
}
