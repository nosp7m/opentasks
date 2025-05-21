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

package org.dmfs.android.contentpal.testing.contentvalues;

import android.content.ContentValues;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.Locale;

import androidx.annotation.NonNull;


/**
 * A {@link Matcher} which matches the expected size of {@link ContentValues}.
 *
 * @author Marten Gajda
 */
public final class Size extends TypeSafeDiagnosingMatcher<ContentValues>
{
    private final int mExpectedValueCount;


    @NonNull
    public static Size withValueCount(int expectedValueCount)
    {
        return new Size(expectedValueCount);
    }


    public Size(int expectedValueCount)
    {
        mExpectedValueCount = expectedValueCount;
    }


    @Override
    protected boolean matchesSafely(@NonNull ContentValues values, @NonNull Description mismatchDescription)
    {
        if (values.size() != mExpectedValueCount)
        {
            mismatchDescription.appendText(String.format(Locale.ENGLISH, "had %d values", values.size()));
            return false;
        }
        return true;
    }


    @Override
    public void describeTo(@NonNull Description description)
    {
        description.appendText(String.format(Locale.ENGLISH, "has %d values", mExpectedValueCount));
    }
}
