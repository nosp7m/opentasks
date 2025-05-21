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

package org.dmfs.android.contentpal;

import android.content.ContentProvider;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;

import org.dmfs.jems.optional.Optional;

import androidx.annotation.NonNull;


/**
 * Represents a view on a content provider table.
 *
 * @param <T>
 *         The contract of this view.
 *
 * @author Marten Gajda
 */
public interface View<T>
{
    /**
     * Query this view for rows matching certain {@link Predicate}s.
     * <p>
     * Note, in most cases you don't want to call this directly, but create dedicated {@link RowSet} instance.
     *
     * @param uriParams
     *         Additional {@link Uri} parameters to append to the query {@link Uri}.
     * @param projection
     *         The projection to request from the cursor.
     * @param predicate
     *         The {@link Predicate} to use as the selection.
     * @param sorting
     *         An {@link Optional} sorting column name.
     *
     * @return A {@link Cursor} as returned by the {@link ContentProvider}.
     */
    @NonNull
    Cursor rows(@NonNull UriParams uriParams, @NonNull Projection<? super T> projection, @NonNull Predicate<? super T> predicate, @NonNull Optional<String> sorting) throws RemoteException;

    /**
     * Returns a {@link Table} to write to.
     *
     * @return The {@link Table} of this view.
     */
    @NonNull
    Table<T> table();
}
