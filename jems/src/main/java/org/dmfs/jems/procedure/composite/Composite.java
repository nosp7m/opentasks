/*
 * Copyright 2020 dmfs GmbH
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

import org.dmfs.jems.iterable.elementary.Seq;
import org.dmfs.jems.procedure.Procedure;


/**
 * A {@link Procedure} composed of other procedures.
 *
 * @author Marten Gajda
 */
public final class Composite<T> implements Procedure<T>
{
    private final Iterable<? extends Procedure<? super T>> mDelegates;


    @SafeVarargs
    public Composite(Procedure<? super T>... delegates)
    {
        this(new Seq<>(delegates));
    }


    public Composite(Iterable<? extends Procedure<? super T>> delegates)
    {
        mDelegates = delegates;
    }


    @Override
    public void process(T arg)
    {
        new ForEach<>(mDelegates).process(delegate -> delegate.process(arg));
    }
}
