package org.dmfs.android.carrot.bindings;

import android.os.Bundle;
import androidx.annotation.NonNull;

import org.dmfs.iterators.Function;
import org.dmfs.iterators.decorators.Mapped;

import java.util.Iterator;
import java.util.Map;

import au.com.codeka.carrot.Bindings;
import au.com.codeka.carrot.bindings.EntryBindings;


/**
 * {@link Bindings} which provide access to a given {@link Bundle}. Iterating over {@link BundleBindings} will iterate {@link Map.Entry}s of all elements.
 *
 * @author Marten Gajda
 */
public final class BundleBindings implements Bindings, Iterable<EntryBindings>
{
    private final Bundle mBundle;


    public BundleBindings(Bundle bundle)
    {
        mBundle = bundle;
    }


    @Override
    public Object resolve(@NonNull String key)
    {
        return decoratedValue(key);
    }


    @Override
    public boolean isEmpty()
    {
        return mBundle.isEmpty();
    }


    @Override
    public Iterator<EntryBindings> iterator()
    {
        return new Mapped<>(mBundle.keySet().iterator(), new Function<String, EntryBindings>()
        {
            @Override
            public EntryBindings apply(String argument)
            {
                return new EntryBindings(argument, decoratedValue(argument));
            }
        });
    }


    /**
     * Decorate the value of the given key if it's a nested Bundle.
     *
     * @param key
     *
     * @return
     */
    private Object decoratedValue(String key)
    {
        Object result = mBundle.get(key);
        // wrap result in BundleBindings if it's a bundle to allow access to nested values
        return result instanceof Bundle ? new BundleBindings((Bundle) result) : result;
    }
}
