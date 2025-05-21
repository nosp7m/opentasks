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

package org.dmfs.iterables.decorators;

import org.dmfs.jems.predicate.Predicate;

import java.util.Iterator;


/**
 * {@link Iterable} decorator which iterates all elements of the delegate which satisfy a given {@link Predicate}.
 *
 * @author Marten Gajda
 */
public final class Sieved<E> implements Iterable<E>
{
    private final Predicate<? super E> mPredicate;
    private Iterable<E> mDelegate;


    public Sieved(Predicate<? super E> predicate, Iterable<E> delegate)
    {
        mDelegate = delegate;
        mPredicate = predicate;
    }


    @Override
    public Iterator<E> iterator()
    {
        return new org.dmfs.iterators.decorators.Sieved<>(mPredicate, mDelegate.iterator());
    }
}
