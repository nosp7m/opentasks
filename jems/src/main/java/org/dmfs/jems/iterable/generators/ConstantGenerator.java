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

package org.dmfs.jems.iterable.generators;

import org.dmfs.jems.single.Single;
import org.dmfs.jems.single.elementary.ValueSingle;

import java.util.Iterator;


/**
 * An {@link Iterable} which iterates the same value over and over again.
 *
 * @author Marten Gajda
 */
public final class ConstantGenerator<E> implements Iterable<E>
{
    private final Single<E> mValue;


    public ConstantGenerator(E value)
    {
        this(new ValueSingle<E>(value));
    }


    public ConstantGenerator(Single<E> value)
    {
        mValue = value;
    }


    @Override
    public Iterator<E> iterator()
    {
        return new org.dmfs.jems.iterator.generators.ConstantGenerator<>(mValue.value());
    }
}
