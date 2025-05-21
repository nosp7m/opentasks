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

package org.dmfs.android.contentpal.testing.predicates;

import org.dmfs.android.contentpal.Predicate;
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.jems.optional.elementary.Present;
import org.mockito.ArgumentMatcher;
import org.mockito.hamcrest.MockitoHamcrest;

import androidx.annotation.NonNull;

import static org.dmfs.jems.optional.elementary.Absent.absent;


/**
 * Factory methods for Mockito argument matching {@link Predicate}s (Using {@link ArgumentMatcher}s).
 *
 * @author Gabor Keszthelyi
 */
public final class PredicateArgumentMatcher
{
    @NonNull
    public static <T> Predicate<? super T> predicateWithSelection(@NonNull CharSequence expectedSelection)
    {
        return MockitoHamcrest.argThat(new PredicateMatcher.Selection<>(absent(), expectedSelection));
    }


    @NonNull
    public static <T> Predicate<? super T> predicateWithSelection(@NonNull TransactionContext tc, @NonNull CharSequence expectedSelection)
    {
        return MockitoHamcrest.argThat(new PredicateMatcher.Selection<>(new Present<>(tc), expectedSelection));
    }


    private PredicateArgumentMatcher()
    {

    }

}
