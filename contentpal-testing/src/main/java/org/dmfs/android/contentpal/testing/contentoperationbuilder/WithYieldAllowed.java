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

import org.dmfs.android.contentpal.testing.tools.Field;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.Locale;

import androidx.annotation.NonNull;


/**
 * A {@link ContentProviderOperation.Builder} {@link Matcher} which matches the "yield allowed" flag of an operation builder.
 *
 * @author Marten Gajda
 */
public final class WithYieldAllowed extends TypeSafeDiagnosingMatcher<ContentProviderOperation.Builder>
{

    private final boolean mYieldable;


    @NonNull
    public static WithYieldAllowed withYieldAllowed()
    {
        return new WithYieldAllowed(true);
    }


    @NonNull
    public static WithYieldAllowed withYieldNotAllowed()
    {
        return new WithYieldAllowed(false);
    }


    public WithYieldAllowed(boolean yieldable)
    {
        mYieldable = yieldable;
    }


    @Override
    protected boolean matchesSafely(@NonNull ContentProviderOperation.Builder builder, @NonNull Description mismatchDescription)
    {
        boolean yieldable = new Field<Boolean>(builder, "mYieldAllowed").value();
        if (mYieldable != yieldable)
        {
            mismatchDescription.appendText(String.format(Locale.ENGLISH, "yieldable was %s", Boolean.toString(yieldable)));
            return false;
        }
        return true;
    }


    @Override
    public void describeTo(@NonNull Description description)
    {
        description.appendText(String.format(Locale.ENGLISH, "yieldable is %s", Boolean.toString(mYieldable)));
    }
}
