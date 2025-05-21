package org.dmfs.android.carrot.bindings;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;

import au.com.codeka.carrot.Bindings;


/**
 * Generic {@link Bindings} for {@link SharedPreferences}. This serves as a "factory" for concrete {@link SharedPreferencesBindings}.
 *
 * @author Marten Gajda
 */
public final class AppPreferencesBindings implements Bindings
{
    private final Context mContext;


    public AppPreferencesBindings(Context context)
    {
        mContext = context;
    }


    @Override
    public Object resolve(@NonNull String key)
    {
        SharedPreferences prefs = mContext.getSharedPreferences(key, 0);
        return new SharedPreferencesBindings(prefs);
    }


    @Override
    public boolean isEmpty()
    {
        return false;
    }
}
