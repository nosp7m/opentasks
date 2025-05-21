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
