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

package org.dmfs.jems.single.elementary;

import org.dmfs.jems.function.BiFunction;
import org.dmfs.jems.generator.Generator;
import org.dmfs.jems.single.Single;


/**
 * The {@link Single} value of a reduced {@link Iterable}.
 * <p>
 * Note, this is the single threaded version of a "reduce" operation.
 *
 * @author Marten Gajda
 */
public final class Reduced<Value, Result> implements Single<Result>
{
    private final Generator<Result> mInitialValue;
    private final Iterable<Value> mIterable;
    private final BiFunction<Result, Value, Result> mFunction;


    /**
     * @deprecated in favor of {@link Reduced#Reduced(Generator, BiFunction, Iterable)}.
     */
    @Deprecated
    public Reduced(Result initialValue, BiFunction<Result, Value, Result> accumulatorFunction, Iterable<Value> iterable)
    {
        this(() -> initialValue, accumulatorFunction, iterable);
    }


    public Reduced(Generator<Result> initialValueGenerator, BiFunction<Result, Value, Result> accumulatorFunction, Iterable<Value> iterable)
    {
        mInitialValue = initialValueGenerator;
        mIterable = iterable;
        mFunction = accumulatorFunction;
    }


    @Override
    public Result value()
    {
        Result result = mInitialValue.next();
        for (Value value : mIterable)
        {
            result = mFunction.value(result, value);
        }
        return result;
    }
}
