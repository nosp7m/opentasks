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

package org.dmfs.jems.single.adapters;

import org.dmfs.jems.fragile.Fragile;
import org.dmfs.jems.function.Function;
import org.dmfs.jems.single.Single;


/**
 * A {@link Fragile} to {@link Single} adapter which throws an unchecked Exception if the fragile delegate is broken.
 *
 * @author Marten Gajda
 */
public final class Unchecked<T, E extends Exception> implements Single<T>
{
    private final Fragile<T, E> mDelegate;
    private final Function<Exception, ? extends RuntimeException> mExceptionFactory;


    public Unchecked(Fragile<T, E> delegate)
    {
        this("Broken fragile delegate", delegate);
    }


    public Unchecked(String message, Fragile<T, E> delegate)
    {
        this(e -> new RuntimeException(message, e), delegate);
    }


    public Unchecked(Function<Exception, ? extends RuntimeException> exceptionFactory, Fragile<T, E> delegate)
    {
        mDelegate = delegate;
        mExceptionFactory = exceptionFactory;
    }


    @Override
    public T value()
    {
        try
        {
            return mDelegate.value();
        }
        catch (Exception e)
        {
            throw mExceptionFactory.value(e);
        }
    }
}
