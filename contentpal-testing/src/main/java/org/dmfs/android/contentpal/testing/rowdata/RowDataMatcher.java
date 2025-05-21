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

package org.dmfs.android.contentpal.testing.rowdata;

import android.content.ContentProviderOperation;
import android.net.Uri;

import org.dmfs.android.contentpal.RowData;
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.android.contentpal.testing.dummies.BounceTransactionContext;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import androidx.annotation.NonNull;

import static org.dmfs.android.contentpal.testing.contentoperationbuilder.OperationType.updateOperation;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithExpectedCount.withoutExpectedCount;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithYieldAllowed.withYieldNotAllowed;
import static org.hamcrest.Matchers.allOf;


/**
 * A {@link Matcher} which matches the operations performed by {@link RowData}.
 * <p>
 * TODO: allow passing a custom {@link TransactionContext}
 *
 * @author Marten Gajda
 */
public final class RowDataMatcher extends TypeSafeDiagnosingMatcher<RowData<?>>
{
    private final Matcher<ContentProviderOperation.Builder> mBuilderMatcher;
    private final TransactionContext mContext;


    @NonNull
    @SafeVarargs
    public static <T> RowDataMatcher builds(@NonNull Matcher<ContentProviderOperation.Builder>... builderMatchers)
    {
        // note RowData is not allowed to change anything but values, hence we add a few more matchers here
        return new RowDataMatcher(new BounceTransactionContext(),
                allOf(allOf(builderMatchers), updateOperation(), withYieldNotAllowed(), withoutExpectedCount()));
    }


    public RowDataMatcher(@NonNull TransactionContext context, @NonNull Matcher<ContentProviderOperation.Builder> builderMatchers)
    {
        mContext = context;
        mBuilderMatcher = builderMatchers;
    }


    @Override
    protected boolean matchesSafely(@NonNull RowData<?> item, @NonNull Description mismatchDescription)
    {
        // note the type of the operation we feed into `updateBuilder` should not matter for this test (as long as it's not a delete operation)
        ContentProviderOperation.Builder builder = item.updatedBuilder(mContext, ContentProviderOperation.newUpdate(Uri.EMPTY));
        if (!mBuilderMatcher.matches(builder))
        {
            mBuilderMatcher.describeMismatch(builder, mismatchDescription);
            return false;
        }
        return true;
    }


    @Override
    public void describeTo(@NonNull Description description)
    {
        description.appendDescriptionOf(mBuilderMatcher);
    }
}
