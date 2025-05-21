/*
 * Copyright 2019 dmfs GmbH
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
import org.dmfs.jems.iterable.elementary.Seq;

import androidx.annotation.NonNull;


/**
 * A {@link Predicate} which selects rows by their id.
 *
 * @author Marten Gajda
 */
public final class IdIn<Contract> extends DelegatingPredicate<Contract>
{
    public IdIn(@NonNull String id)
    {
        super(new EqArg<>(BaseColumns._ID, id));
    }


    public IdIn(@NonNull String... ids)
    {
        super(new In<>(BaseColumns._ID, new Seq<>(ids)));
    }


    public IdIn(long id)
    {
        super(new EqArg<>(BaseColumns._ID, id));
    }


    public IdIn(@NonNull Long... ids)
    {
        this(new Seq<>(ids));
    }


    public IdIn(@NonNull Iterable<Long> ids)
    {
        super(new In<>(BaseColumns._ID, ids));
    }
}
