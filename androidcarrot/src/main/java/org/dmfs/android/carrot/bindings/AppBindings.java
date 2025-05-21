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
