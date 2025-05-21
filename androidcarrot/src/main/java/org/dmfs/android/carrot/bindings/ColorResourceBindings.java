package org.dmfs.android.carrot.bindings;

import android.content.Context;
import android.content.res.Resources;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import au.com.codeka.carrot.Bindings;


/**
 * {@link Bindings} for string resources.
 * <p>
 * TODO: support color attributes like `colorPrimary`
 *
 * @author Marten Gajda
 */
public final class ColorResourceBindings implements Bindings
{
    private final Context mContext;


    public ColorResourceBindings(Context context)
    {
        mContext = context;
    }


    @Override
    public Object resolve(@NonNull String key)
    {
        Resources resources = mContext.getResources();
        int id = resources.getIdentifier(key, "color", mContext.getPackageName());
        return id == 0 ? null : ContextCompat.getColor(mContext, id);
    }


    @Override
    public boolean isEmpty()
    {
        // we presume there is always at least one color resource.
        return false;
    }
}
