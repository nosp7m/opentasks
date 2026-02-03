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
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import androidx.annotation.NonNull;

import au.com.codeka.carrot.Bindings;


/**
 * {@link Bindings} for app meta data.
 *
 * @author Marten Gajda
 */
public final class AppBindings implements Bindings
{
    private final Context mContext;


    public AppBindings(Context context)
    {
        mContext = context;
    }


    @Override
    public Object resolve(@NonNull String key)
    {
        PackageManager pm = mContext.getPackageManager();
        String packageName = mContext.getPackageName();
        try
        {
            PackageInfo pInfo = pm.getPackageInfo(packageName, 0);
            switch (key)
            {
                case "package":
                    return mContext.getPackageName();
                case "version":
                    return pInfo.versionName;
                case "version_code":
                    return pInfo.versionCode;
                case "title":
                    CharSequence label = pm.getApplicationLabel(mContext.getApplicationInfo());
                    return label == null ? packageName : label.toString();
                default:
                    return null;
            }
        }
        catch (PackageManager.NameNotFoundException e)
        {
            throw new RuntimeException("Own package not found!? o_O");
        }
    }


    @Override
    public boolean isEmpty()
    {
        return false;
    }
}
