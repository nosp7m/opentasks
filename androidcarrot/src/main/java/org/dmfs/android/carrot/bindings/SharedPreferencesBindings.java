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

import android.content.SharedPreferences;
import androidx.annotation.NonNull;

import au.com.codeka.carrot.Bindings;


/**
 * {@link Bindings} for the given {@link SharedPreferences}.
 *
 * @author Marten Gajda
 */
public final class SharedPreferencesBindings implements Bindings
{
    private final SharedPreferences mPrefs;


    public SharedPreferencesBindings(SharedPreferences prefs)
    {
        mPrefs = prefs;
    }


    @Override
    public Object resolve(@NonNull String key)
    {
        return mPrefs.getAll().get(key);
    }


    @Override
    public boolean isEmpty()
    {
        return mPrefs.getAll().isEmpty();
    }
}
