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

package org.dmfs.jems.iterable.adapters;

import org.dmfs.jems.generatable.Generatable;

import java.util.Iterator;


/**
 * A {@link Generatable} to {@link Iterable} adapter which continues iterating forever. Be careful when using this in a {@code for} loop and make sure there is
 * another exit point.
 *
 * @author Marten Gajda
 */
public final class Infinite<T> implements Iterable<T>
{
    private final Generatable<T> mDelegate;


    public Infinite(Generatable<T> generatable)
    {
        mDelegate = generatable;
    }


    @Override
    public Iterator<T> iterator()
    {
        return new org.dmfs.jems.iterator.adapters.Infinite<>(mDelegate.generator());
    }
}
