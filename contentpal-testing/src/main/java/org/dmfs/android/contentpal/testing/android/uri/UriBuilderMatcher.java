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

package org.dmfs.android.contentpal.testing.android.uri;

import android.net.Uri;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import androidx.annotation.NonNull;


/**
 * Matcher for a {@link Uri.Builder}.
 * <p>
 * TODO: move to Bolts project
 *
 * @author Marten Gajda
 */
public final class UriBuilderMatcher extends TypeSafeDiagnosingMatcher<Uri.Builder>
{
    private final Matcher<Uri> mDelegate;


    /**
     * Verifies that a {@link Uri.Builder} builds a {@link Uri} matching certain criteria.
     *
     * @param delegate
     *         The {@link Matcher} to delegate to when testing the result of the builder.
     *
     * @return A {@link Matcher} for {@link Uri.Builder}s.
     */
    @NonNull
    public static Matcher<Uri.Builder> builds(@NonNull Matcher<Uri> delegate)
    {
        return new UriBuilderMatcher(delegate);
    }


    public UriBuilderMatcher(@NonNull Matcher<Uri> delegate)
    {
        mDelegate = delegate;
    }


    @Override
    protected boolean matchesSafely(@NonNull Uri.Builder item, @NonNull Description mismatchDescription)
    {
        if (!mDelegate.matches(item.build()))
        {
            mismatchDescription.appendText("built Uri ");
            mDelegate.describeMismatch(item.build(), mismatchDescription);
            return false;
        }
        return true;
    }


    @Override
    public void describeTo(@NonNull Description description)
    {
        description.appendText("builds a Uri with ");
        mDelegate.describeTo(description);
    }
}
