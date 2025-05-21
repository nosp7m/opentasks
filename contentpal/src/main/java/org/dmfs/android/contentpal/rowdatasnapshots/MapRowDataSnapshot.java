/*
 * Copyright 2017 dmfs GmbH
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

package org.dmfs.android.contentpal.rowdatasnapshots;

import org.dmfs.android.contentpal.RowDataSnapshot;
import org.dmfs.iterators.decorators.Serialized;
import org.dmfs.jems.function.Function;
import org.dmfs.jems.optional.Optional;
import org.dmfs.jems.optional.decorators.Mapped;
import org.dmfs.jems.optional.elementary.NullSafe;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import androidx.annotation.NonNull;


/**
 * A {@link RowDataSnapshot} backed by {@link Map}s.
 *
 * @author Marten Gajda
 */
public final class MapRowDataSnapshot<Contract> implements RowDataSnapshot<Contract>
{
    private final Map<String, String> mCharData;
    private final Map<String, byte[]> mByteData;


    public MapRowDataSnapshot(@NonNull Map<String, String> charData, @NonNull Map<String, byte[]> byteData)
    {
        mCharData = new HashMap<>(charData);
        mByteData = new HashMap<>(byteData);
    }


    @NonNull
    @Override
    public <ValueType> Optional<ValueType> data(@NonNull String key, @NonNull Function<String, ValueType> mapFunction)
    {
        return new Mapped<>(mapFunction, new NullSafe<>(mCharData.get(key)));
    }


    @NonNull
    @Override
    public Optional<byte[]> byteData(@NonNull String key)
    {
        return new NullSafe<>(mByteData.get(key));
    }


    @NonNull
    @Override
    public Iterator<String> iterator()
    {
        return new Serialized<>(mCharData.keySet().iterator(), mByteData.keySet().iterator());
    }
}