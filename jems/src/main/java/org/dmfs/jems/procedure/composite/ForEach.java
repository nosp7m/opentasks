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

package org.dmfs.jems.procedure.composite;

import org.dmfs.iterables.elementary.PresentValues;
import org.dmfs.iterables.elementary.SingleIterable;
import org.dmfs.jems.optional.Optional;
import org.dmfs.jems.procedure.Procedure;
import org.dmfs.jems.single.Single;


/**
 * The Object Oriented version of a for-each loop. It executes a given {@link Procedure} once for each of the given arguments.
 * <p>
 * This is somewhat orthogonal to the {@link Batch} {@link Procedure}.
 *
 * @author Marten Gajda
 */
public final class ForEach<T> implements Procedure<Procedure<? super T>>
{
    private final Iterable<T> mArguments;


    public ForEach(Iterable<T> arguments)
    {
        mArguments = arguments;
    }


    public ForEach(Optional<T> argument)
    {
        this(new PresentValues<T>(argument));
    }


    public ForEach(Single<T> argument)
    {
        this(new SingleIterable<>(argument));
    }


    @Override
    public void process(Procedure<? super T> arg)
    {
        for (T item : mArguments)
        {
            arg.process(item);
        }
    }
}
