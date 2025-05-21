package org.dmfs.android.carrot.bindings;

import android.content.Context;
import android.content.res.Resources;
import androidx.annotation.NonNull;

import au.com.codeka.carrot.Bindings;


/**
 * {@link Bindings} for string resources.
 *
 * @author Marten Gajda
 */
public final class StringResourceBindings implements Bindings
{
    private final Context mContext;


    public StringResourceBindings(Context context)
    {
        mContext = context;
    }


    @Override
    public Object resolve(@NonNull String key)
    {
        Resources resources = mContext.getResources();
        int id = resources.getIdentifier(key, "string", mContext.getPackageName());
        return id == 0 ? null : resources.getString(id);
    }


    @Override
    public boolean isEmpty()
    {
        // we presume there is always at least one string resource.
        return false;
    }
}
