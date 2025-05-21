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

package org.dmfs.android.contentpal.tools;

import android.database.Cursor;
import android.os.RemoteException;
import android.provider.BaseColumns;

import org.dmfs.android.contentpal.Predicate;
import org.dmfs.android.contentpal.View;
import org.dmfs.android.contentpal.predicates.AnyOf;
import org.dmfs.android.contentpal.projections.SingleColProjection;
import org.dmfs.android.contentpal.tools.uriparams.EmptyUriParams;
import org.dmfs.jems.single.Single;

import androidx.annotation.NonNull;

import static org.dmfs.jems.optional.elementary.Absent.absent;


/**
 * The number of rows in a {@link View} that matches the given {@link Predicate}.
 *
 * @author Gabor Keszthelyi
 */
public final class RowCount<T> implements Single<Integer>
{
    private final View<T> mView;
    private final Predicate<? super T> mPredicate;


    public RowCount(@NonNull View<T> view, @NonNull Predicate<? super T> predicate)
    {
        mView = view;
        mPredicate = predicate;
    }


    public RowCount(@NonNull View<T> view)
    {
        this(view, new AnyOf<>());
    }


    @NonNull
    @Override
    public Integer value()
    {
        try (Cursor cursor = mView.rows(EmptyUriParams.INSTANCE, new SingleColProjection<>(BaseColumns._ID), mPredicate, absent()))
        {
            return cursor.getCount();
        }
        catch (RemoteException e)
        {
            throw new RuntimeException("Query failed", e);
        }
    }
}
