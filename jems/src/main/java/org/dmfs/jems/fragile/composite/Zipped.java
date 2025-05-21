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

package org.dmfs.jems.fragile.composite;

import org.dmfs.jems.fragile.Fragile;
import org.dmfs.jems.function.BiFunction;


/**
 * A {@link Fragile} which combines two other {@link Fragile}s with a {@link BiFunction}.
 *
 * @author Marten Gajda
 */
public final class Zipped<Left, Right, Result, E extends Throwable> implements Fragile<Result, E>
{
    private final BiFunction<Left, Right, Result> mZipFunction;
    private final Fragile<Left, ? extends E> mLeft;
    private final Fragile<Right, ? extends E> mRight;


    public Zipped(Fragile<Left, ? extends E> left, Fragile<Right, ? extends E> right, BiFunction<Left, Right, Result> zipFunction)
    {
        mZipFunction = zipFunction;
        mLeft = left;
        mRight = right;
    }


    @Override
    public Result value() throws E
    {
        return mZipFunction.value(mLeft.value(), mRight.value());
    }
}
