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


/**
 * An {@link Iterable} decorator which returns the elements of the delegate in their natural descending order.
 *
 * @author Marten Gajda
 */
public final class Descending<T extends Comparable<? super T>> extends DelegatingIterable<T>
{
    public Descending(Iterable<T> delegate)
    {
        // note Comparator.reverseOrder doesn't exist on Android SDK Level < 24 hence we don't use it here
        super(new Sorted<>((l, r) -> r.compareTo(l), delegate));
    }
}
