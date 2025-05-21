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

package org.dmfs.android.contentpal.projections;

import android.provider.ContactsContract;

import org.dmfs.android.contentpal.Projection;


/**
 * A {@link Projection} of columns joined from another table.
 * <p>
 * This can be used to create composite projections for views which join two or more tables. Instead of creating a new projection for the columns of the
 * original table in the view, this class allows it to use the original projection for the joined view.
 * <p>
 * A common example is the contact provider {@link ContactsContract.Data} table which also contains all columns of the {@link ContactsContract.RawContacts}
 * table.
 * <p>
 * Using {@link Joined}, it's possible to add all the {@link ContactsContract.RawContacts} columns to a projection on the {@link ContactsContract.Data} table.
 * <pre><code>
 * Projection&lt;Data&gt; projection =
 *     new Composite&lt;Data&gt;(
 *         new DataProjection(),
 *         new Joined&lt;RawContacts, Data&gt;(new RawContactsProjection()));
 * </code></pre>
 *
 * @param <Original>
 *         The table which the {@link Projection} originally belongs to
 * @param <JoinedView>
 *         The view which joins the original table and contains all its columns.
 *
 * @author Marten Gajda
 */
public final class Joined<Original, JoinedView> extends DelegatingProjection<JoinedView>
{

    @SuppressWarnings("unchecked")
    public Joined(Projection<Original> delegate)
    {
        super((Projection<JoinedView>) delegate);
    }
}
