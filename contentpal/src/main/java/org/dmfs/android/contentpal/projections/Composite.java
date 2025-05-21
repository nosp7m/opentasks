/*
 * Copyright 2017 dmfs GmbH
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

package org.dmfs.android.contentpal.projections;

import org.dmfs.android.contentpal.Projection;
import org.dmfs.jems.iterable.composite.Joined;
import org.dmfs.jems.iterable.decorators.Mapped;
import org.dmfs.jems.iterable.elementary.Seq;
import org.dmfs.jems.single.elementary.Collected;

import java.util.ArrayList;
import java.util.Arrays;

import androidx.annotation.NonNull;


/**
 * A {@link Projection} which combines other Projections by concatenating the column names into a single array.
 *
 * @author Marten Gajda
 */
public final class Composite<T> implements Projection<T>
{
    private final Iterable<? extends Projection<? super T>> mDelegates;


    @SafeVarargs
    public Composite(@NonNull Projection<? super T>... delegates)
    {
        this(new Seq<>(delegates));
    }


    public Composite(@NonNull Iterable<? extends Projection<? super T>> delegates)
    {
        mDelegates = delegates;
    }


    @NonNull
    @Override
    public String[] toArray()
    {
        return new Collected<>(
                ArrayList::new,
                new Joined<>(
                        new Mapped<>(
                                Arrays::asList,
                                new Mapped<>(
                                        Projection::toArray,
                                        mDelegates))))
                .value().toArray(new String[0]);
    }
}
