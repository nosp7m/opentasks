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
