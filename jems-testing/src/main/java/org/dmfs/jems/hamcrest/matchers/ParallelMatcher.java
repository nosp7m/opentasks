/*
 * Copyright 2019 dmfs GmbH
 *
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

package org.dmfs.jems.hamcrest.matchers;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 * A {@link Matcher} which runs another matcher multiple times in concurrent threads. This can help in finding potential race conditions and other concurrency
 * issues. However, due to the nature of such issues there is no guarantee that all, if any, existing issues will be reported. So take the results with a grain
 * of salt.
 * <p>
 * Note, when running time consuming tests make sure you reduce the number of threads to ensure the test will terminate in a timely manner.
 * <p>
 * Also note, due to the way hamcrest matchers work and the nature of concurrency issues, this test can not report the mismatch description of a failing
 * matcher.
 *
 * @author Marten Gajda
 */
public final class ParallelMatcher<T> extends TypeSafeDiagnosingMatcher<T>
{
    private final Matcher<T> mDelegate;
    private final int mNumThreads;


    public static <T> Matcher<T> parallel(int threads, Matcher<T> delegate)
    {
        return new ParallelMatcher<>(threads, delegate);
    }


    public static <T> Matcher<T> parallel(Matcher<T> delegate)
    {
        return new ParallelMatcher<>(delegate);
    }


    public ParallelMatcher(Matcher<T> delegate)
    {
        this(1000, delegate);
    }


    public ParallelMatcher(int threads, Matcher<T> delegate)
    {
        mDelegate = delegate;
        mNumThreads = threads;
    }


    @Override
    protected boolean matchesSafely(T item, Description mismatchDescription)
    {
        ExecutorService executor = Executors.newFixedThreadPool(mNumThreads);

        Set<Boolean> results = Collections.synchronizedSet(new HashSet<>());
        for (int i = 0; i < mNumThreads; ++i)
        {
            executor.execute(() -> {
                if (!mDelegate.matches(item))
                {
                    // note it's pointless to try calling describeMismatch because chances are low the same issue will appear
                    results.add(false);
                }
            });
        }
        executor.shutdown();
        try
        {
            executor.awaitTermination(1, TimeUnit.DAYS);
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
            return false;
        }
        if (results.size() > 0)
        {
            mismatchDescription.appendText("at least one matcher failed");
            return false;
        }
        return true;
    }


    @Override
    public void describeTo(Description description)
    {
        description.appendDescriptionOf(mDelegate);
        description.appendText(String.format(Locale.ENGLISH, " every time when run %d times in parallel", mNumThreads));
    }
}
