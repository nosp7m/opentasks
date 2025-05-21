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
public final class IntegerResourceBindings implements Bindings
{
    private final Context mContext;


    public IntegerResourceBindings(Context context)
    {
        mContext = context;
    }


    @Override
    public Object resolve(@NonNull String key)
    {
        Resources resources = mContext.getResources();
        int id = resources.getIdentifier(key, "integer", mContext.getPackageName());
        return id == 0 ? null : resources.getInteger(id);
    }


    @Override
    public boolean isEmpty()
    {
        // we presume there is always at least one integer resource.
        return false;
    }
}
