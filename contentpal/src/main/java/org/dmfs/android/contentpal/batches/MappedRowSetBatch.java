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

package org.dmfs.android.contentpal.batches;

import org.dmfs.android.contentpal.Operation;
import org.dmfs.android.contentpal.RowSet;
import org.dmfs.android.contentpal.RowSnapshot;
import org.dmfs.iterables.decorators.DelegatingIterable;
import org.dmfs.jems.function.Function;
import org.dmfs.jems.iterable.decorators.Mapped;

import androidx.annotation.NonNull;


/**
 * An {@link Iterable} of {@link Operation}s which contains {@link Operation}s derived from the {@link RowSnapshot}s of a given {@link RowSet}.
 * <p>
 * Example use case:
 * <p>
 * Assign a random UID to all unsynced events.
 * <pre><code>
 * operationsQueue.enqueue(
 *   new MappedRowSetBatch&lt;&gt;(
 *     new Unsynced(eventsTable),
 *     new Function&lt;CalendarContract.Events, Operation&lt;?&gt;&gt;()
 *     {
 *       public Operation&lt;&gt; apply(RowSnapshot&lt;CalendarContract.Events&gt; rowSnapwhot)
 *       {
 *         return new Populated&lt;&gt;(new Put&lt;&gt;(rowSnapshot), new RowData()
 *         {
 *          {@literal @}NonNull
 *          {@literal @}Override
 *           public ContentProviderOperation.Builder updatedBuilder(@NonNull ContentProviderOperation.Builder builder)
 *           {
 *             return mDelegate.updatedBuilder(builder).withValue(CalendarContract.Events.UID_2445, UUID.randomUUID().toString());
 *           }
 *         }
 *       }
 *     }
 *   )
 * );
 * </code></pre>
 *
 * @param <T>
 *         the contract of the table of the {@link RowSet}.
 *
 * @author Marten Gajda
 */
public final class MappedRowSetBatch<T> extends DelegatingIterable<Operation<?>>
{
    /**
     * Creates an {@link Iterable} of {@link Operation}s based on the {@link RowSnapshot}s of the given {@link RowSet}.
     *
     * @param rowSet
     *         The {@link RowSet} which contains the {@link RowSnapshot}s to operate on.
     * @param function
     *         A {@link Function} to apply to all {@link RowSnapshot}s in the given {@link RowSet}.
     */
    public MappedRowSetBatch(@NonNull RowSet<T> rowSet, @NonNull Function<RowSnapshot<T>, Operation<?>> function)
    {
        super(new Mapped<>(function, rowSet));
    }

}
