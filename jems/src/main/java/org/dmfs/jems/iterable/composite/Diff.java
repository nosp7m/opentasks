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

package org.dmfs.jems.iterable.composite;

import org.dmfs.jems.function.BiFunction;
import org.dmfs.jems.optional.Optional;
import org.dmfs.jems.pair.Pair;

import java.util.Iterator;


/**
 * An {@link Iterable} of the differences of two given {@link Iterable}s. The {@link Iterable} must return their elements sorted by the comparison criterion.
 *
 * @author Marten Gajda
 */
public final class Diff<Left, Right> implements Iterable<Pair<Optional<Left>, Optional<Right>>>
{
    private final Iterable<? extends Left> mLefts;
    private final Iterable<? extends Right> mRights;
    private final BiFunction<? super Left, ? super Right, Integer> mComparatorFunction;


    public Diff(
        Iterable<? extends Left> lefts,
        Iterable<? extends Right> rights,
        BiFunction<? super Left, ? super Right, Integer> comparatorFunction)
    {
        mLefts = lefts;
        mRights = rights;
        mComparatorFunction = comparatorFunction;
    }


    @Override
    public Iterator<Pair<Optional<Left>, Optional< Right>>> iterator()
    {
        return new org.dmfs.jems.iterator.composite.Diff<>(mLefts.iterator(), mRights.iterator(), mComparatorFunction);
    }

}
