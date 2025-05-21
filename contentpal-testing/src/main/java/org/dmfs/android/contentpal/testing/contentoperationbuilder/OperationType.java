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

package org.dmfs.android.contentpal.testing.contentoperationbuilder;

import android.content.ContentProviderOperation;
import android.net.Uri;

import org.dmfs.android.contentpal.testing.tools.Field;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.Locale;

import androidx.annotation.NonNull;

import static org.hamcrest.Matchers.any;


/**
 * A {@link ContentProviderOperation.Builder} {@link Matcher} which matches the operation type.
 * <p>
 *
 * @author Marten Gajda
 */
public final class OperationType extends TypeSafeDiagnosingMatcher<ContentProviderOperation.Builder>
{
    /**
     * The supported operation types.
     * <p>
     * Note, the order of elements is chosen to match the type values specified in ContentProviderOperation. So don't change it.
     */
    private enum Type
    {
        INSERT,
        UPDATE,
        DELETE,
        ASSERT
    }


    private final Type mExpectedType;
    private final Matcher<Uri> mUriMatcher;


    @NonNull
    public static OperationType insertOperation()
    {
        return new OperationType(any(Uri.class), Type.INSERT);
    }


    @NonNull
    public static OperationType updateOperation()
    {
        return new OperationType(any(Uri.class), Type.UPDATE);
    }


    @NonNull
    public static OperationType deleteOperation()
    {
        return new OperationType(any(Uri.class), Type.DELETE);
    }


    @NonNull
    public static OperationType assertOperation()
    {
        return new OperationType(any(Uri.class), Type.ASSERT);
    }


    private OperationType(@NonNull Matcher<Uri> uriMatcher, @NonNull Type expectedType)
    {
        mUriMatcher = uriMatcher;
        mExpectedType = expectedType;
    }


    @Override
    protected boolean matchesSafely(@NonNull ContentProviderOperation.Builder builder, @NonNull Description mismatchDescription)
    {
        Integer typeValue = new Field<Integer>(builder, "mType").value();
        if (mExpectedType.ordinal() + 1 /* 1-based */ != typeValue)
        {
            mismatchDescription.appendText(String.format(Locale.ENGLISH, "was an %s operation", Type.values()[typeValue - 1]));
            return false;
        }
        return true;
    }


    @Override
    public void describeTo(@NonNull Description description)
    {
        description.appendText(String.format(Locale.ENGLISH, "is a %s operation", mExpectedType.toString()));
    }
}
