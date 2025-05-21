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

package org.dmfs.iterables;

import org.dmfs.jems.iterable.decorators.Frozen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


/**
 * An {@link Iterable} that stores the result of a given {@link Iterator} and allows to re-test the values. This is
 * meant to speed up repeated access to slow iterators.
 * <p>
 * Note that {@link CachingIterable} needs to synchronize access to the original iterator (and an internal list), which
 * causes some overhead. So only use this if reiterating the original source is impossible or expensive (compared to the
 * number of times you need to reiterate).
 *
 * @param <T>
 *         The type of the iterated elements.
 *
 * @author Marten Gajda
 * @deprecated in favor of {@link Frozen}, to be removed in version 2.0.
 */
@Deprecated
public final class CachingIterable<T> implements Iterable<T>
{
    private final List<T> mCache;
    private final Iterator<T> mSourceIterator;
    private boolean mComplete;


    public CachingIterable(Iterator<T> iterator)
    {
        mCache = new ArrayList<T>(64);
        mSourceIterator = iterator;
    }


    @Override
    public Iterator<T> iterator()
    {
        // check if we can skip to synchronize because we already know that the cache is populated
        if (!mComplete)
        {
            synchronized (mSourceIterator)
            {
                if (mSourceIterator.hasNext())
                {
                    // we're still in the process of populating the cache
                    return new SynchronizedCachingIterator<T>(mSourceIterator, mCache, mCache.size());
                }

                // store that the iterator was completely iterated, so we don't need to check next time
                mComplete = true;
            }
        }
        // The cache is completely populated. That means we don't need the synchronized iterator anymore.
        // Just return an iterator on the cache. Make sure the consumer can't modify our cache.
        return Collections.unmodifiableList(mCache).iterator();
    }


    /**
     * An iterator that stores all iterated values in a "cache" {@link List} for further reiteration.
     *
     * @param <T>
     *         The type of the iterated elements.
     *
     * @author Marten Gajda
     */
    private final static class SynchronizedCachingIterator<T> implements Iterator<T>
    {
        private final Iterator<T> mOriginalIterator;
        private final List<T> mCache;
        private final int mSafeElements;
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
                // TODO: does it make sense to update mSafeElements in here?
                return mPos < mCache.size() || mOriginalIterator.hasNext();
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

                // this needs to be synchronized as well since the add operation might leave the list in an invalid state for a short time (like during a
                // resize of the backing array).
                return mCache.get(mPos++);
            }
        }


        @Override
        public void remove()
        {
            throw new UnsupportedOperationException("Remove is not supported by this Iterator.");
        }
    }
}
