package org.dmfs.android.carrot.locaters;

import android.content.Context;
import android.content.res.Resources;
import androidx.annotation.Nullable;

import au.com.codeka.carrot.resource.ResourceName;


/**
 * A {@link ResourceName} of a raw resource identified by its name (like it would be used in a template).
 *
 * @author Marten Gajda
 */
public final class RawResourceName implements ResourceName
{
    private final Context mContext;
    private final String mName;


    RawResourceName(Context context, String name)
    {
        mContext = context;
        mName = name;
    }


    @Override
    public String getName()
    {
        // try to resolve the name to an id
        Resources resources = mContext.getResources();
        return String.valueOf(resources.getIdentifier(mName, "raw", mContext.getPackageName()));
    }


    @Nullable
    @Override
    public ResourceName getParent()
    {
        return null;
    }
}
