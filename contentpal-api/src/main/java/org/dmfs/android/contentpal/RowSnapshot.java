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

import androidx.annotation.NonNull;


/**
 * Represents the snapshot of a content provider row at a specific point in time. {@link RowSnapshot}s are immutable and they are not updated when the
 * underlying row is updated.
 *
 * @param <T>
 *         The contract of the view this {@link RowSnapshot} belongs to.
 *
 * @author Marten Gajda
 */
public interface RowSnapshot<T>
{

    /**
     * Returns a {@link SoftRowReference} to this row.
     *
     * @return
     */
    @NonNull
    SoftRowReference<T> reference();

    /**
     * Returns the values of this {@link RowSnapshot}.
     * <p>
     * Note, for new {@link RowSnapshot}s this will return an empty {@link RowDataSnapshot}.
     *
     * @return A {@link RowDataSnapshot} that provides access to the values of this snapshot of the row.
     */
    @NonNull
    RowDataSnapshot<T> values();
}
