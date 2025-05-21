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
import org.dmfs.iterators.EmptyIterator;
import org.dmfs.jems.function.Function;
import org.dmfs.jems.optional.Optional;

import java.util.Iterator;

import androidx.annotation.NonNull;

import static org.dmfs.jems.optional.elementary.Absent.absent;


/**
 * {@link RowDataSnapshot} without any values;
 *
 * @author Marten Gajda
 */
public final class EmptyRowDataSnapshot<Contract> implements RowDataSnapshot<Contract>
{
    public final static RowDataSnapshot INSTANCE = new EmptyRowDataSnapshot();


    @NonNull
    @Override
    public <ValueType> Optional<ValueType> data(@NonNull String key, @NonNull Function<String, ValueType> mapFunction)
    {
        return absent();
    }


    @NonNull
    @Override
    public Optional<byte[]> byteData(@NonNull String key)
    {
        return absent();
    }


    @NonNull
    @Override
    public Iterator<String> iterator()
    {
        return EmptyIterator.instance();
    }
}
