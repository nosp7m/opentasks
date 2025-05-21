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

package org.dmfs.jems.generator.elementary;

import org.dmfs.jems.function.Function;
import org.dmfs.jems.generator.Generator;


/**
 * A sequence {@link Generator}. It generates a sequence by passing the previous result (or initial value) into a given {@link Function}.
 *
 * @author Marten Gajda
 */
public final class Sequence<T> implements Generator<T>
{
    private T mNext;
    private Function<T, T> mFunction;


    public Sequence(T first, Function<T, T> function)
    {
        mNext = first;
        mFunction = value -> {
            // the first call returns value as is, afterwards we use the given function
            mFunction = function;
            return value;
        };
    }


    @Override
    public T next()
    {
        return mNext = mFunction.value(mNext);
    }
}
