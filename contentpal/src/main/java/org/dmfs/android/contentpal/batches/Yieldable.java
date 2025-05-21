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

package org.dmfs.android.contentpal.batches;

import org.dmfs.android.contentpal.Operation;
import org.dmfs.iterators.AbstractBaseIterator;

import java.util.Iterator;

import androidx.annotation.NonNull;


/**
 * An {@link Iterable} of {@link Operation}s decorator that allows yielding the database to other transactions after the given {@link Operation}s have been
 * executed.
 * <p>
 * This doesn't affect any other yield point no any of the {@link Operation}s.
 *
 * @author Marten Gajda
 * @see <a href="https://developer.android.com/guide/topics/providers/contacts-provider.html#Transactions">ContactsProvider - Batch modification</a>
 */
public final class Yieldable<T> implements Iterable<Operation<T>>
{
    private final Iterable<Operation<T>> mDelegate;


    /**
     * Creates an {@link Iterable} of {@link Operation}s which allows yielding the database to other transactions after the given {@link Operation}s have been
     * executed.
     *
     * @param delegate
     *         The {@link Iterable} of {@link Operation}s.
     */
    public Yieldable(@NonNull Iterable<Operation<T>> delegate)
    {
        mDelegate = delegate;
    }


    @NonNull
    @Override
    public Iterator<Operation<T>> iterator()
    {
        final Iterator<Operation<T>> delegate = mDelegate.iterator();
        // return an iterator which decorates the last Operation with Yieldable.
        return new AbstractBaseIterator<Operation<T>>()
        {
            private boolean mFirst = true;


            @Override
            public boolean hasNext()
            {
                return delegate.hasNext();
            }


            @NonNull
            @Override
            public Operation<T> next()
            {
                Operation<T> next = mFirst ? new org.dmfs.android.contentpal.operations.Yieldable<>(delegate.next()) : delegate.next();
                mFirst = false;
                return next;
            }
        };
    }
}
