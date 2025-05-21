/*
 * Copyright 2018 dmfs GmbH
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

import androidx.annotation.NonNull;

import static org.hamcrest.CoreMatchers.is;


/**
 * A {@link Matcher} which matches the {@link Uri} of a {@link ContentProviderOperation.Builder}.
 *
 * @author Marten Gajda
 */
public final class TargetMatcher extends TypeSafeDiagnosingMatcher<ContentProviderOperation.Builder>
{
    private final Matcher<Uri> mValueMatcher;


    @NonNull
    public static TargetMatcher targets(@NonNull String uri)
    {
        return new TargetMatcher(is(Uri.parse(uri)));
    }


    @NonNull
    public static TargetMatcher targets(@NonNull Uri uri)
    {
        return new TargetMatcher(is(uri));
    }


    @NonNull
    public static TargetMatcher targets(@NonNull Matcher<Uri> uriMatcher)
    {
        return new TargetMatcher(uriMatcher);
    }


    public TargetMatcher(@NonNull Matcher<Uri> valueMatchers)
    {
        mValueMatcher = valueMatchers;
    }


    @Override
    protected boolean matchesSafely(@NonNull ContentProviderOperation.Builder builder, @NonNull Description mismatchDescription)
    {
        Uri uri = new Field<Uri>(builder, "mUri").value();

        if (!mValueMatcher.matches(uri))
        {
            mismatchDescription.appendText("Operation target ");
            mValueMatcher.describeMismatch(uri, mismatchDescription);
            return false;
        }
        return true;
    }


    @Override
    public void describeTo(@NonNull Description description)
    {
        description.appendText("Operation target ");
        mValueMatcher.describeTo(description);
    }
}
