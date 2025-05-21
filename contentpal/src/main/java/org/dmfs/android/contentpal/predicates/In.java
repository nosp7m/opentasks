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

package org.dmfs.android.contentpal.predicates;

import android.provider.BaseColumns;

import org.dmfs.android.contentpal.Predicate;
import org.dmfs.android.contentpal.RowSet;
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.android.contentpal.predicates.arguments.ValueArgument;
import org.dmfs.jems.iterable.decorators.Mapped;
import org.dmfs.jems.iterable.elementary.Seq;

import androidx.annotation.NonNull;


/**
 * The conditional {@code IN} operator. Validates to {@code true} if a given column has any of the given values (their string representation to be more
 * precise).
 * <p>
 * Note, {@code null} is not a supported value.
 *
 * @author Marten Gajda
 */
public final class In<Contract> implements Predicate<Contract>
{
    private final String mColumnName;
    private final Iterable<?> mArguments;


    public In(@NonNull String columnName, @NonNull RowSet<? extends BaseColumns> arguments)
    {
        // note, every row must have a non-null _ID column
        this(columnName, new Mapped<>(row -> row.values().data(BaseColumns._ID, Long::parseLong).value(), arguments));
    }


    public In(@NonNull String columnName, @NonNull Object... arguments)
    {
        this(columnName, new Seq<>(arguments));
    }


    public In(@NonNull String columnName, @NonNull Iterable<?> arguments)
    {
        mColumnName = columnName;
        mArguments = arguments;
    }


    @NonNull
    @Override
    public CharSequence selection(@NonNull TransactionContext transactionContext)
    {
        StringBuilder sb = new StringBuilder(128);
        sb.append(mColumnName);
        sb.append(" in ( ");
        boolean first = true;
        for (Object ignored : mArguments)
        {
            if (first)
            {
                first = false;
            }
            else
            {
                sb.append(", ");
            }
            sb.append("?");
        }
        sb.append(" ) ");
        return sb;
    }


    @NonNull
    @Override
    public Iterable<Argument> arguments(@NonNull TransactionContext transactionContext)
    {
        return new Mapped<>(ValueArgument::new, mArguments);
    }
}
