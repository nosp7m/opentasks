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

package org.dmfs.iterables.elementary;

import org.dmfs.iterators.elementary.SingleIterator;
import org.dmfs.jems.single.Single;

import java.util.Iterator;


/**
 * An {@link Iterable} which iterates the sole value of a {@link Single}.
 *
 * @author Marten Gajda
 */
public final class SingleIterable<T> implements Iterable<T>
{
    private final Single<T> mSingle;


    public SingleIterable(Single<T> single)
    {
        mSingle = single;
    }


    @Override
    public Iterator<T> iterator()
    {
        return new SingleIterator<>(mSingle);
    }
}
