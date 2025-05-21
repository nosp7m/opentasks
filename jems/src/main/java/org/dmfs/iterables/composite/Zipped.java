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

package org.dmfs.iterables.composite;

import org.dmfs.jems.function.BiFunction;

import java.util.Iterator;


/**
 * An {@link Iterable} combining the elements of two given {@link Iterable}s using a {@link BiFunction}.
 * <p>
 * This iterates as many elements as the shorter of both {@link Iterable}s.
 *
 * @author Marten Gajda
 */
public final class Zipped<Left, Right, Result> implements Iterable<Result>
{
    private final Iterable<Left> mLeft;
    private final Iterable<Right> mRight;
    private final BiFunction<Left, Right, Result> mFunction;


    public Zipped(Iterable<Left> left, Iterable<Right> right, BiFunction<Left, Right, Result> function)
    {
        mLeft = left;
        mRight = right;
        mFunction = function;
    }


    @Override
    public Iterator<Result> iterator()
    {
        return new org.dmfs.iterators.composite.Zipped<>(mLeft.iterator(), mRight.iterator(), mFunction);
    }
}
