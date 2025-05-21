package org.dmfs.android.carrot.locaters;

import androidx.annotation.Nullable;

import au.com.codeka.carrot.resource.ResourceName;


/**
 * A {@link ResourceName} of a raw resource identified by its ID.
 *
 * @author Marten Gajda
 */
public final class RawResourceIdName implements ResourceName
{
    private final long mId;


    RawResourceIdName(long id)
    {
        mId = id;
    }


    @Override
    public String getName()
    {
        return String.valueOf(mId);
    }


    @Nullable
    @Override
    public ResourceName getParent()
    {
        return null;
    }
}
