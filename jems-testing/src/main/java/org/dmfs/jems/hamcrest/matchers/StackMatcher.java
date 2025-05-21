/*
 * Copyright 2017 dmfs GmbH
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

import org.dmfs.iterables.decorators.Mapped;
import org.dmfs.jems.iterable.elementary.Seq;
import org.dmfs.iterators.Function;
import org.dmfs.jems.iterable.elementary.StackIterable;
import org.dmfs.jems.optional.Optional;
import org.dmfs.jems.stack.Stack;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

import static org.dmfs.jems.hamcrest.matchers.optional.AbsentMatcher.absent;
import static org.hamcrest.Matchers.is;


/**
 * A {@link Matcher} which matches an entire {@link Stack}.
 *
 * @author Marten Gajda
 */
public final class StackMatcher<T> extends FeatureMatcher<Stack<T>, Iterable<T>>
{

    public static <E> Matcher<Stack<E>> emptyStack()
    {
        return new FeatureMatcher<Stack<E>, Optional<Stack.StackTop<E>>>(absent(), "StackTop", "StackTop")
        {
            @Override
            protected Optional<Stack.StackTop<E>> featureValueOf(Stack<E> actual)
            {
                return actual.top();
            }
        };
    }


    @SafeVarargs
    public static <E> Matcher<Stack<E>> stacked(E... elements)
    {
        return new StackMatcher<>(IterableMatcher.iteratesTo(
                new Mapped<>(
                        new Seq<>(elements),
                        new Function<E, Matcher<E>>()
                        {
                            @Override
                            public Matcher<E> apply(E argument)
                            {
                                return is(argument);
                            }
                        })));
    }


    @SafeVarargs
    public static <E> Matcher<Stack<E>> stacked(Matcher<E>... elements)
    {
        return new StackMatcher<>(IterableMatcher.iteratesTo(new Seq<>(elements)));
    }


    /**
     * @param stackIterableMatcher
     */
    public StackMatcher(Matcher<Iterable<? extends T>> stackIterableMatcher)
    {
        super(stackIterableMatcher,
                "Elements in Stack",
                "Elements in Stack");
    }


    @Override
    protected Iterable<T> featureValueOf(Stack<T> actualStack)
    {
        return new StackIterable<>(actualStack);
    }
}