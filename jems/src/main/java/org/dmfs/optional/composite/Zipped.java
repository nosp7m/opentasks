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

package org.dmfs.optional.composite;

import org.dmfs.jems.function.BiFunction;
import org.dmfs.optional.Optional;

import java.util.NoSuchElementException;


/**
 * An {@link Optional} which combines two other {@link Optional}s with a {@link BiFunction} if they are both present and is absent otherwise
 *
 * @author Marten Gajda
 * @deprecated in favour of {@link org.dmfs.jems.optional.composite.Zipped}.
 */
@Deprecated
public final class Zipped<Left, Right, Result> implements Optional<Result>
{
    private final Optional<Left> mLeft;
    private final Optional<Right> mRight;
    private final BiFunction<Left, Right, Result> mFunction;


    public Zipped(Optional<Left> left, Optional<Right> right, BiFunction<Left, Right, Result> function)
    {
        mLeft = left;
        mRight = right;
        mFunction = function;
    }


    @Override
    public boolean isPresent()
    {
        return mLeft.isPresent() && mRight.isPresent();
    }


    @Override
    public Result value(Result defaultValue)
    {
        return isPresent() ? value() : defaultValue;
    }


    @Override
    public Result value() throws NoSuchElementException
    {
        return mFunction.value(mLeft.value(), mRight.value());
    }
}
