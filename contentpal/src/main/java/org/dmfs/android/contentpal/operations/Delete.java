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

package org.dmfs.android.contentpal.operations;

import android.content.ContentProviderOperation;

import org.dmfs.android.contentpal.Operation;
import org.dmfs.android.contentpal.RowReference;
import org.dmfs.android.contentpal.RowSnapshot;
import org.dmfs.android.contentpal.SoftRowReference;
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.android.contentpal.references.RowSnapshotReference;
import org.dmfs.jems.optional.Optional;

import androidx.annotation.NonNull;

import static org.dmfs.jems.optional.elementary.Absent.absent;


/**
 * An {@link Operation} which deletes a row belonging to a {@link RowSnapshot} or {@link RowReference}.
 *
 * @author Marten Gajda
 */
public final class Delete<T> implements Operation<T>
{
    private final RowReference<T> mRowReference;


    /**
     * Creates a {@link Delete} {@link Operation} for the row of the given {@link RowSnapshot}.
     *
     * @param rowSnapshot
     *         A {@link RowSnapshot} of the row to delete.
     */
    public Delete(@NonNull RowSnapshot<T> rowSnapshot)
    {
        this(new RowSnapshotReference<>(rowSnapshot));
    }


    /**
     * Creates a {@link Delete} {@link Operation} for the row of the given {@link RowReference}.
     *
     * @param rowReference
     *         A {@link RowReference} to the row to delete.
     */
    public Delete(@NonNull RowReference<T> rowReference)
    {
        mRowReference = rowReference;
    }


    @NonNull
    @Override
    public Optional<SoftRowReference<T>> reference()
    {
        return absent();
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder contentOperationBuilder(@NonNull TransactionContext transactionContext)
    {
        return mRowReference.deleteOperationBuilder(transactionContext);
    }
}
