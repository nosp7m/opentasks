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

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;

import au.com.codeka.carrot.Bindings;
import au.com.codeka.carrot.bindings.EmptyBindings;


/**
 * {@link Bindings} which provide access to a given {@link Bundle}.
 *
 * @author Marten Gajda
 */
public final class IntentBindings implements Bindings
{
    private final Intent mIntent;


    public IntentBindings(Intent intent)
    {
        mIntent = intent;
    }


    @Override
    public Object resolve(@NonNull String key)
    {
        switch (key)
        {
            case "action":
                return mIntent.getAction();
            case "data":
                return mIntent.getDataString();
            case "extras":
                Bundle extras = mIntent.getExtras();
                return extras == null ? new EmptyBindings() : new BundleBindings(extras);
            case "categories":
                return mIntent.getCategories();
            default:
                return null;
        }
    }


    @Override
    public boolean isEmpty()
    {
        // never empty
        return false;
    }
}
