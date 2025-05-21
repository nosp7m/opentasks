package org.dmfs.android.carrot.bindings;

import android.content.Context;
import androidx.annotation.NonNull;

import au.com.codeka.carrot.Bindings;
import au.com.codeka.carrot.bindings.Composite;
import au.com.codeka.carrot.bindings.SingletonBindings;


/**
 * {@link Bindings} which provide the most common bindings for an Android app.
 *
 * @author Marten Gajda
 */
public final class AndroidBindings implements Bindings
{
    private final Bindings mDelegate;


    public AndroidBindings(Context context)
    {
        mDelegate = new Composite(
                new SingletonBindings("$app", new AppBindings(context)),
                new SingletonBindings("$R",
                        new Composite(
                                new SingletonBindings("string", new StringResourceBindings(context)),
                                new SingletonBindings("integer", new IntegerResourceBindings(context))
                        )
                ),
                new SingletonBindings("$prefs", new AppPreferencesBindings(context)),
                new SingletonBindings("$locale", new LocaleBindings()),
                new SingletonBindings("$device", new DeviceBindings())
        );
    }


    @Override
    public Object resolve(@NonNull String key)
    {
        return mDelegate.resolve(key);
    }


    @Override
    public boolean isEmpty()
    {
        // no need to delegate, we know for sure that this is not empty.
        return false;
    }
}
