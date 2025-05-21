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

package org.dmfs.android.contentpal.testing.projection;

import org.dmfs.android.contentpal.Projection;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.Arrays;

import androidx.annotation.NonNull;

import static org.hamcrest.Matchers.contains;


/**
 * @author Marten Gajda
 */
public final class ProjectionMatcher extends TypeSafeDiagnosingMatcher<Projection<?>>
{
    private final Matcher<Iterable<? extends String>> mDelegate;


    @NonNull
    public static Matcher<Projection<?>> projectsEmpty()
    {
        return new ProjectionMatcher(Matchers.emptyIterable());
    }


    @NonNull
    public static Matcher<Projection<?>> projects(@NonNull String... expectedColumns)
    {
        return new ProjectionMatcher(contains(expectedColumns));
    }


    public ProjectionMatcher(@NonNull Matcher<Iterable<? extends String>> delegate)
    {
        mDelegate = delegate;
    }


    @Override
    protected boolean matchesSafely(@NonNull Projection<?> item, @NonNull Description mismatchDescription)
    {
        if (!mDelegate.matches(Arrays.asList(item.toArray())))
        {
            // check that we get the correct elements
            mDelegate.describeMismatch(Arrays.asList(item.toArray()), mismatchDescription);
            return false;
        }
        // also check if the projection is immutable, i.e. if me modify the result the next result should not be affected
        String[] projection = item.toArray();
        if (projection.length > 0)
        {
            String testString = new String("bogus");
            String[] copy = projection.clone();
            projection[0] = testString;
            if (item.toArray()[0] == testString)
            {
                mismatchDescription.appendText("projection is not immutable");
                return false;
            }
        }
        return true;
    }


    @Override
    public void describeTo(@NonNull Description description)
    {
        mDelegate.describeTo(description);
    }
}
