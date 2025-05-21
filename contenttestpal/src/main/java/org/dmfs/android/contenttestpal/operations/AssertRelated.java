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

package org.dmfs.android.contenttestpal.operations;

import org.dmfs.android.contentpal.Operation;
import org.dmfs.android.contentpal.Predicate;
import org.dmfs.android.contentpal.RowData;
import org.dmfs.android.contentpal.RowSnapshot;
import org.dmfs.android.contentpal.Table;
import org.dmfs.android.contentpal.operations.BulkAssert;
import org.dmfs.android.contentpal.operations.Counted;
import org.dmfs.android.contentpal.operations.DelegatingOperation;
import org.dmfs.android.contentpal.predicates.AllOf;
import org.dmfs.android.contentpal.predicates.AnyOf;
import org.dmfs.android.contentpal.predicates.ReferringTo;
import org.dmfs.android.contentpal.rowdata.EmptyRowData;

import androidx.annotation.NonNull;


/**
 * An assert {@link Operation} that can be used to check rows in a table <code>T</code> that has a value in the given foreign key column referring to the id of
 * a row from another table.
 *
 * @author Gabor Keszthelyi
 */
public final class AssertRelated<T> extends DelegatingOperation<T>
{

    public AssertRelated(@NonNull Table<T> table, @NonNull String foreignKeyColumn, RowSnapshot<?> rowSnapshot, @NonNull RowData<T> rowData, @NonNull Predicate<? super T> predicate)
    {
        super(new Counted<>(1, new BulkAssert<>(table, rowData, new AllOf<>(predicate, new ReferringTo<>(foreignKeyColumn, rowSnapshot)))));
    }


    public AssertRelated(@NonNull Table<T> table, @NonNull String foreignKeyColumn, @NonNull RowSnapshot<?> rowSnapshot, @NonNull RowData<T> rowData)
    {
        this(table, foreignKeyColumn, rowSnapshot, rowData, new AnyOf<>());
    }


    public AssertRelated(@NonNull Table<T> table, @NonNull String foreignKeyColumn, @NonNull RowSnapshot<?> rowSnapshot, @NonNull Predicate<? super T> predicate)
    {
        this(table, foreignKeyColumn, rowSnapshot, EmptyRowData.instance(), predicate);
    }


    public AssertRelated(@NonNull Table<T> table, @NonNull String foreignKeyColumn, @NonNull RowSnapshot<?> rowSnapshot)
    {
        this(table, foreignKeyColumn, rowSnapshot, EmptyRowData.instance(), new AnyOf<>());
    }
}
