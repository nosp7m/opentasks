/*
 * Copyright 2018 dmfs GmbH
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dmfs.jems.optional.adapters;

import org.dmfs.jems.optional.Optional;

import java.util.Map;
import java.util.NoSuchElementException;


/**
 * An {@link Optional} element of a {@link Map}. This is present if the given {@link Map} contains a non-{@code null} value for the given key.
 *
 * @author Marten Gajda
 */
public final class MapEntry<K, V> implements Optional<V>
{
    private final Map<K, V> mMap;
    private final K mKey;


    public MapEntry(Map<K, V> map, K key)
    {
        mMap = map;
        mKey = key;
    }


    @Override
    public boolean isPresent()
    {
        return mMap.get(mKey) != null;
    }


    @Override
    public V value() throws NoSuchElementException
    {
        V value = mMap.get(mKey);
        if (value == null)
        {
            throw new NoSuchElementException(String.format("No element for key \"%s\" found", mKey.toString()));
        }
        return value;
    }
}
