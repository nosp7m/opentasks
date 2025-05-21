/*
 * Copyright 2019 dmfs GmbH
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

package org.dmfs.jems.iterable.decorators;

import org.dmfs.iterators.AbstractBaseIterator;
import org.dmfs.jems.generator.Generator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;


/**
 * An {@link Iterable} stores the elements of the given {@link Iterator} or {@link Iterable} in order to allow re-iterating them.
 * <p>
 * This is meant to speed up repeated access to slow iterators.
 * <p>
 * Note that {@link Frozen} needs to synchronize access to the original iterator (and an internal list), which causes some overhead. So only use this if
 * reiterating the original source is impossible or expensive (compared to the number of times you need to reiterate).
 *
 * @param <T>
 *         The type of the iterated elements.
 *
 * @author Marten Gajda
 */
public final class Frozen<T> implements Iterable<T>
{

    private Generator<Iterator<T>> mIteratorGenerator;


    public Frozen(Iterator<T> delegate)
    {
        this(() -> delegate);
    }


    public Frozen(Iterable<T> delegate)
    {
        mIteratorGenerator = new Generator<Iterator<T>>()
        {
            private final List<T> mCache = new ArrayList<>(64);
            private final Iterator<T> mIterator = delegate.iterator();


            @Override
            public Iterator<T> next()
            {
                synchronized (mIterator)
                {
                    if (mIterator.hasNext())
                    {
                        // we're still in the process of populating the cache
                        return new SynchronizedCachingIterator<T>(mIterator, mCache, mCache.size());
                    }
                    // store that the iterator was completely iterated, so we don't need to check next time
                    mIteratorGenerator = () -> new ListIterator<>(mCache);
                }
                return new ListIterator<>(mCache);
            }
        };
    }


    @Override
    public Iterator<T> iterator()
    {
        return mIteratorGenerator.next();
    }


    private final static class ListIterator<T> extends AbstractBaseIterator<T>
    {
        private final List<T> mList;
        private int mPos;


        private ListIterator(List<T> list)
        {
            mList = list;
        }


        @Override
        public boolean hasNext()
        {
            return mPos < mList.size();
        }


        @Override
        public T next()
        {
            if (!hasNext())
            {
                throw new NoSuchElementException("no more elements to iterate");
            }
            return mList.get(mPos++);
        }
    }


    /**
     * An iterator that stores all iterated values in a "cache" {@link List} for further reiteration.
     *
     * @param <T>
     *         The type of the iterated elements.
     */
    private final static class SynchronizedCachingIterator<T> extends AbstractBaseIterator<T>
    {
        private final Iterator<T> mOriginalIterator;
        private final List<T> mCache;
        private int mSafeElements;
        private int mPos;


        public SynchronizedCachingIterator(Iterator<T> originalIterator, List<T> cache, int safeElements)
        {
            mOriginalIterator = originalIterator;
            mCache = cache;
            mSafeElements = safeElements;
        }


        @Override
        public boolean hasNext()
        {
            if (mPos < mSafeElements)
            {
                // we already know that there are this many elements in the cache, so no need to enter the expensive synchronized block
                return true;
            }

            synchronized (mOriginalIterator)
            {
                mSafeElements = mCache.size();
                return mPos < mSafeElements || mOriginalIterator.hasNext();
            }
        }


        @Override
        public T next()
        {
            synchronized (mOriginalIterator)
            {
                if (mPos == mCache.size())
                {
                    // the list has no more elements, try to append one from the original iterator
                    T next = mOriginalIterator.next();
                    mCache.add(next);
                    mPos++;
                    return next;
                }

                // TODO: does it make sense to update mSafeElements in here?

                // this needs to be synchronized (even if mPos<mSafeElements) since the add operation might leave the list in an invalid state for a short time (like during a
                // resize of the backing array).
                return mCache.get(mPos++);
            }
        }
    }
}
