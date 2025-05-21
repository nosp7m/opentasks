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

package org.dmfs.jems.generatable.elementary;

import org.dmfs.jems.function.Function;
import org.dmfs.jems.generatable.Generatable;
import org.dmfs.jems.generator.Generator;


/**
 * A sequence {@link Generatable}. It generates a sequence by passing the previous result (or initial value) into a given {@link Function}.
 *
 * @author Marten Gajda
 */
public final class Sequence<T> implements Generatable<T>
{
    private final Generator<T> mInitialValues;
    private final Function<T, T> mFunction;


    public Sequence(Generator<T> initialValues, Function<T, T> function)
    {
        mInitialValues = initialValues;
        mFunction = function;
    }


    public Sequence(T initialValue, Function<T, T> function)
    {
        this(() -> initialValue, function);
    }


    @Override
    public Generator<T> generator()
    {
        return new org.dmfs.jems.generator.elementary.Sequence<>(mInitialValues.next(), mFunction);
    }
}
