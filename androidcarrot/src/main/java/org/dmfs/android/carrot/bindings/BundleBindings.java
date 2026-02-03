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
 * 
 * This file has been changed from the original.
 */

package org.dmfs.android.carrot.bindings;

import android.os.Bundle;
import androidx.annotation.NonNull;

import org.dmfs.iterators.Function;
import org.dmfs.iterators.decorators.Mapped;

import java.util.Iterator;
import java.util.Map;

import au.com.codeka.carrot.Bindings;
import au.com.codeka.carrot.bindings.EntryBindings;


/**
 * {@link Bindings} which provide access to a given {@link Bundle}. Iterating over {@link BundleBindings} will iterate {@link Map.Entry}s of all elements.
 *
 * @author Marten Gajda
 */
public final class BundleBindings implements Bindings, Iterable<EntryBindings>
{
    private final Bundle mBundle;


    public BundleBindings(Bundle bundle)
    {
        mBundle = bundle;
    }


    @Override
    public Object resolve(@NonNull String key)
    {
        return decoratedValue(key);
    }


    @Override
    public boolean isEmpty()
    {
        return mBundle.isEmpty();
    }


    @Override
    public Iterator<EntryBindings> iterator()
    {
        return new Mapped<>(mBundle.keySet().iterator(), new Function<String, EntryBindings>()
        {
            @Override
            public EntryBindings apply(String argument)
            {
                return new EntryBindings(argument, decoratedValue(argument));
            }
        });
    }


    /**
     * Decorate the value of the given key if it's a nested Bundle.
     *
     * @param key
     *
     * @return
     */
    private Object decoratedValue(String key)
    {
        Object result = mBundle.get(key);
        // wrap result in BundleBindings if it's a bundle to allow access to nested values
        return result instanceof Bundle ? new BundleBindings((Bundle) result) : result;
    }
}
