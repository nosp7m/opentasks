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

package org.dmfs.jems.iterable.adapters;

import org.dmfs.iterables.SingletonIterable;
import org.dmfs.iterables.decorators.DelegatingIterable;
import org.dmfs.iterables.decorators.Sieved;
import org.dmfs.jems.iterable.decorators.Mapped;
import org.dmfs.jems.iterable.elementary.Seq;
import org.dmfs.jems.optional.Optional;


/**
 * {@link Iterable} which iterates over the present values from the input {@link Iterable} of {@link Optional}s extending {@code E}.
 *
 * @author Marten Gajda
 */
public final class PresentValues<E> extends DelegatingIterable<E>
{
    public PresentValues(Optional<? extends E> optional)
    {
        this(new SingletonIterable<>(optional));
    }


    @SafeVarargs
    public PresentValues(Optional<? extends E>... optionals)
    {
        this(new Seq<>(optionals));
    }


    public PresentValues(Iterable<? extends Optional<? extends E>> optionals)
    {
        super(new Mapped<>(Optional::value, new Sieved<>(Optional::isPresent, optionals)));
    }
}
