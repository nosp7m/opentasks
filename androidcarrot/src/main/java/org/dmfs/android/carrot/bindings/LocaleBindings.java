package org.dmfs.android.carrot.bindings;

import android.os.Build;
import androidx.annotation.NonNull;

import java.util.Locale;

import au.com.codeka.carrot.Bindings;


/**
 * {@link Bindings} for the current locale.
 *
 * @author Marten Gajda
 */
public final class LocaleBindings implements Bindings
{
    public LocaleBindings()
    {
    }


    @Override
    public Object resolve(@NonNull String key)
    {
        switch (key)
        {
            case "code":
                return Build.VERSION.SDK_INT >= 21 ? Locale.getDefault().toLanguageTag() : Locale.getDefault().toString().replace("_", "-");
            case "language":
                return Locale.getDefault().getDisplayLanguage();
            case "language_en":
                return Locale.getDefault().getDisplayLanguage(Locale.ENGLISH);
            case "language_code":
                return Locale.getDefault().getLanguage();
            case "country":
                return Locale.getDefault().getDisplayCountry();
            case "country_en":
                return Locale.getDefault().getDisplayCountry(Locale.ENGLISH);
            case "country_code":
                return Locale.getDefault().getCountry();
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
