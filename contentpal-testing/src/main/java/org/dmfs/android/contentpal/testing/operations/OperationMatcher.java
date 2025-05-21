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

package org.dmfs.android.contentpal.testing.operations;

import android.content.ContentProviderOperation;

import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.android.contentpal.testing.dummies.BounceTransactionContext;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import androidx.annotation.NonNull;

import static org.hamcrest.Matchers.allOf;


/**
 * A {@link Matcher} which matches the outcome of an {@link org.dmfs.android.contentpal.Operation}.
 * <p>
 * TODO: allow to pass a custom {@link TransactionContext}.
 *
 * @author Marten Gajda
 */
public final class OperationMatcher extends TypeSafeDiagnosingMatcher<org.dmfs.android.contentpal.Operation>
{
    private final Matcher<ContentProviderOperation.Builder> mBuilderMatcher;
    private final TransactionContext mTransactionContext;


    @NonNull
    @SafeVarargs
    public static OperationMatcher builds(@NonNull Matcher<ContentProviderOperation.Builder>... builderMatchers)
    {
        return new OperationMatcher(new BounceTransactionContext(), allOf(builderMatchers));
    }


    public OperationMatcher(@NonNull TransactionContext transactionContext, @NonNull Matcher<ContentProviderOperation.Builder> builderMatchers)
    {
        mTransactionContext = transactionContext;
        mBuilderMatcher = builderMatchers;
    }


    @Override
    protected boolean matchesSafely(@NonNull org.dmfs.android.contentpal.Operation item, @NonNull Description mismatchDescription)
    {
        ContentProviderOperation.Builder builder = item.contentOperationBuilder(mTransactionContext);
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
