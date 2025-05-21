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

package org.dmfs.iterators.composite;

import org.dmfs.iterators.AbstractBaseIterator;
import org.dmfs.jems.function.BiFunction;

import java.util.Iterator;


/**
 * An {@link Iterator} combining the elements of two given {@link Iterator}s using a {@link BiFunction}.
 * <p>
 * The result has as many elements as the shorter of both {@link Iterator}s.
 *
 * @author Marten Gajda
 */
public final class Zipped<Left, Right, Result> extends AbstractBaseIterator<Result>
{
    private final Iterator<? extends Left> mLeft;
    private final Iterator<? extends Right> mRight;
    private final BiFunction<? super Left, ? super Right, ? extends Result> mFunction;


    public Zipped(
        Iterator<? extends Left> left,
        Iterator<? extends Right> right,
        BiFunction<? super Left, ? super Right, ? extends Result> function)
    {
        mLeft = left;
        mRight = right;
        mFunction = function;
    }


    @Override
    public boolean hasNext()
    {
        return mLeft.hasNext() && mRight.hasNext();
    }


    @Override
    public Result next()
    {
        return mFunction.value(mLeft.next(), mRight.next());
    }
}
