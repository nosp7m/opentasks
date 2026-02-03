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

import android.content.Context;
import androidx.annotation.NonNull;

import au.com.codeka.carrot.Bindings;
import au.com.codeka.carrot.bindings.Composite;
import au.com.codeka.carrot.bindings.SingletonBindings;


/**
 * {@link Bindings} which provide the most common bindings for an Android app.
 *
 * @author Marten Gajda
 */
public final class AndroidBindings implements Bindings
{
    private final Bindings mDelegate;


    public AndroidBindings(Context context)
    {
        mDelegate = new Composite(
                new SingletonBindings("$app", new AppBindings(context)),
                new SingletonBindings("$R",
                        new Composite(
                                new SingletonBindings("string", new StringResourceBindings(context)),
                                new SingletonBindings("integer", new IntegerResourceBindings(context))
                        )
                ),
                new SingletonBindings("$prefs", new AppPreferencesBindings(context)),
                new SingletonBindings("$locale", new LocaleBindings()),
                new SingletonBindings("$device", new DeviceBindings())
        );
    }


    @Override
    public Object resolve(@NonNull String key)
    {
        return mDelegate.resolve(key);
    }


    @Override
    public boolean isEmpty()
    {
        // no need to delegate, we know for sure that this is not empty.
        return false;
    }
}
