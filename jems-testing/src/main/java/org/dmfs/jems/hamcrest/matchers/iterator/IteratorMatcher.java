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

package org.dmfs.jems.hamcrest.matchers.iterator;

import org.dmfs.iterables.EmptyIterable;
import org.dmfs.jems.iterable.elementary.Seq;
import org.dmfs.jems.generator.Generator;
import org.dmfs.jems.iterable.decorators.Mapped;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.Iterator;
import java.util.Locale;
import java.util.NoSuchElementException;


/**
 * A {@link Matcher} for {@link Iterator}s. It checks the iterated elements as well as the compliance with the iterator contract.
 *
 * @author Marten Gajda
 */
public final class IteratorMatcher<T> extends TypeSafeDiagnosingMatcher<Generator<? extends Iterator<? extends T>>>
{
    public static final int HAS_NEXT_TEST_COUNT = 100;
    public static final int EXCEPTION_TEST_COUNT = 100;
    private final Iterable<Matcher<? super T>> mElementMatchers;


    public static <T> Matcher<Generator<? extends Iterator<? extends T>>> emptyIterator()
    {
        return new IteratorMatcher<>(new EmptyIterable<>());
    }


    @SafeVarargs
    public static <T> Matcher<Generator<? extends Iterator<? extends T>>> iteratorOf(T... elements)
    {
        return new IteratorMatcher<>(new Mapped<>(Matchers::equalTo, new Seq<>(elements)));
    }


    public static <T> Matcher<Generator<? extends Iterator<? extends T>>> iteratorOf(Iterable<Matcher<? super T>> elementMatchers)
    {
        return new IteratorMatcher<>(elementMatchers);
    }


    @SafeVarargs
    public static <T> Matcher<Generator<? extends Iterator<? extends T>>> iteratorOf(Matcher<? super T>... elementMatchers)
    {
        return new IteratorMatcher<T>(new Seq<>(elementMatchers));
    }


    public IteratorMatcher(Iterable<Matcher<? super T>> mElementMatchers)
    {
        this.mElementMatchers = mElementMatchers;
    }


    @Override
    protected boolean matchesSafely(Generator<? extends Iterator<? extends T>> item, Description mismatchDescription)
    {
        Iterator<Matcher<? super T>> matcherIterator = mElementMatchers.iterator();
        Iterator<? extends T> testee = item.next();
        int index = 0;
        while (testee.hasNext() && matcherIterator.hasNext())
        {
            for (int i = 0; i < HAS_NEXT_TEST_COUNT; ++i)
            {
                //noinspection ConstantConditions
                if (!testee.hasNext())
                {
                    mismatchDescription.appendText(String.format(Locale.ENGLISH, "hasNext() flipped at index %d", index));
                    return false;
                }
                try
                {
                    testee.remove();
                    mismatchDescription.appendText(String.format(Locale.ENGLISH, "remove() did not throw at index %d", index));
                    return false;
                }
                catch (UnsupportedOperationException e)
                {
                    // pass
                }
                catch (Exception e)
                {
                    mismatchDescription.appendText(String.format(Locale.ENGLISH, "remove() threw wrong exception at index %d", index));
                    return false;
                }
            }
            T next = testee.next();
            Matcher<? super T> nextMatcher = matcherIterator.next();
            if (!nextMatcher.matches(next))
            {
                nextMatcher.describeMismatch(next, mismatchDescription);
                mismatchDescription.appendText(String.format(Locale.ENGLISH, " at index %d", index));
                return false;
            }
            index += 1;
        }
        if (testee.hasNext())
        {
            mismatchDescription.appendText(String.format(Locale.ENGLISH, "had more than %d elements", index));
            return false;
        }
        for (int i = 0; i < HAS_NEXT_TEST_COUNT; ++i)
        {
            //noinspection ConstantConditions
            if (testee.hasNext())
            {
                mismatchDescription.appendText("hasNext() flipped after the last element");
                return false;
            }
            try
            {
                testee.remove();
                mismatchDescription.appendText("remove() did not throw after last element");
                return false;
            }
            catch (UnsupportedOperationException e)
            {
                // pass
            }
            catch (Exception e)
            {
                mismatchDescription.appendText("remove() threw wrong exception after last element");
                return false;
            }
        }
        if (matcherIterator.hasNext())
        {
            mismatchDescription.appendText(String.format(Locale.ENGLISH, "had only %d elements", index));
            return false;
        }

        for (int i = 0; i < EXCEPTION_TEST_COUNT; ++i)
        {
            try
            {
                testee.next();
                mismatchDescription.appendText("next() did not throw after hasNext() returned false");
                return false;
            }
            catch (NoSuchElementException e)
            {
                // pass
            }
            catch (Exception e)
            {
                mismatchDescription.appendText("next() threw wrong exception after hasNext() returned false");
                return false;
            }
        }

        return true;
    }


    @Override
    public void describeTo(Description description)
    {
        description.appendText("iterator of [");
        boolean first = true;
        for (Matcher<?> matcher : mElementMatchers)
        {
            if (first)
            {
                first = false;
            }
            else
            {
                description.appendText(", ");
            }
            matcher.describeTo(description);
        }
        description.appendText("]");
    }
}
