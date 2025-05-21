package org.dmfs.android.carrot.bindings;

import android.os.Build;
import androidx.annotation.NonNull;

import au.com.codeka.carrot.Bindings;


/**
 * {@link Bindings} for certain device info values.
 *
 * @author Marten Gajda
 */
public final class DeviceBindings implements Bindings
{
    public DeviceBindings()
    {
    }


    @Override
    public Object resolve(@NonNull String key)
    {
        switch (key)
        {
            case "manufacturer":
                return Build.MANUFACTURER;
            case "model":
                return Build.MODEL;
            case "device":
                return Build.DEVICE;
            case "product":
                return Build.PRODUCT;
            case "sdk_level":
                return Build.VERSION.SDK_INT;
            case "version":
                return Build.VERSION.RELEASE;
            case "build_id":
                return Build.DISPLAY;
            default:
                return null;
        }
    }


    @Override
    public boolean isEmpty()
    {
        return false;
    }
}
