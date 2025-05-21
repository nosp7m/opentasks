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

package org.dmfs.jems.iterator.adapters;

import org.dmfs.jems.generator.Generator;

import java.util.Iterator;


/**
 * An {@link Generator} to {@link Iterator} adapter which iterates infinitely. Be careful when using this with a {@code while} loop and make sure there is
 * another exit point.
 *
 * @author Marten Gajda
 */
public final class Infinite<T> implements Iterator<T>
{
    private final Generator<T> mDelegate;


    public Infinite(Generator<T> generator)
    {
        mDelegate = generator;
    }


    @Override
    public boolean hasNext()
    {
        return true;
    }


    @Override
    public T next()
    {
        return mDelegate.next();
    }
}
