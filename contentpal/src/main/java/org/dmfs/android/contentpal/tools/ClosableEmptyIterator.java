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

package org.dmfs.android.contentpal.tools;

import org.dmfs.android.contentpal.ClosableIterator;
import org.dmfs.iterators.AbstractBaseIterator;

import java.io.Closeable;
import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * An empty {@link Iterator} which is also {@link Closeable}.
 *
 * @author Marten Gajda
 */
public final class ClosableEmptyIterator<T> extends AbstractBaseIterator<T> implements ClosableIterator<T>
{
    private final static ClosableEmptyIterator<?> INSTANCE = new ClosableEmptyIterator<>();


    @SuppressWarnings("unchecked")
    public static <T> ClosableEmptyIterator<T> instance()
    {
        return (ClosableEmptyIterator<T>) INSTANCE;
    }


    @Override
    public void close()
    {
        // nothing to do
    }


    @Override
    public boolean hasNext()
    {
        return false;
    }


    @Override
    public T next()
    {
        throw new NoSuchElementException("No next element found");
    }
}
