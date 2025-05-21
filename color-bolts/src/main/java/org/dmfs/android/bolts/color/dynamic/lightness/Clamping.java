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

package org.dmfs.android.bolts.color.dynamic.lightness;

import org.dmfs.jems.function.Function;


/**
 * {@link Function} that 'clamps' the given original value between the provided min and max values.
 *
 * @author Gabor Keszthelyi
 * @deprecated use it from jems when available
 */
@Deprecated
final class Clamping<T extends Comparable<T>> implements Function<T, T>
{
    private final T mMinValue;
    private final T mMaxValue;
    private final Function<T, T> mOriginalFunction;


    public Clamping(T minValue, T maxValue, Function<T, T> originalFunction)
    {
        mMinValue = minValue;
        mMaxValue = maxValue;
        mOriginalFunction = originalFunction;
    }


    public Clamping(T minValue, T maxValue)
    {
        this(minValue, maxValue, v -> v);
    }


    @Override
    public T value(T argument)
    {
        T originalResult = mOriginalFunction.value(argument);
        if (originalResult.compareTo(mMinValue) < 0)
        {
            return mMinValue;
        }
        if (originalResult.compareTo(mMaxValue) > 0)
        {
            return mMaxValue;
        }
        return originalResult;
    }
}
