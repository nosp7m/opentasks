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

import org.dmfs.jems.iterable.elementary.Seq;
import org.dmfs.iterators.AbstractBaseIterator;
import org.dmfs.iterators.EmptyIterator;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static java.util.Arrays.asList;
import static java.util.Collections.singleton;
import static org.dmfs.jems.hamcrest.matchers.iterator.IteratorMatcher.emptyIterator;
import static org.dmfs.jems.hamcrest.matchers.iterator.IteratorMatcher.iteratorOf;
import static org.dmfs.jems.hamcrest.matchers.matcher.MatcherMatcher.describesAs;
import static org.dmfs.jems.hamcrest.matchers.matcher.MatcherMatcher.matches;
import static org.dmfs.jems.hamcrest.matchers.matcher.MatcherMatcher.mismatches;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;


/**
 * Unit test for {@link IteratorMatcher}.
 *
 * @author Marten Gajda
 */
public class IteratorMatcherTest
{
    @Test
    public void testEmptyIterator()
    {
        assertThat(emptyIterator(), matches(() -> new Unremovable<>(Collections.emptyIterator())));
        assertThat(emptyIterator(), mismatches(Collections::emptyIterator, "remove() threw wrong exception after last element"));
        assertThat(IteratorMatcher.<String>emptyIterator(), mismatches(() -> singleton("a").iterator(), "had more than 0 elements"));
        assertThat(emptyIterator(), mismatches(() -> new Flipping<>(Collections.emptyIterator(), 30), "hasNext() flipped after the last element"));
        assertThat(emptyIterator(), mismatches(() -> new NonThrowing<>(Collections.emptyIterator()), "next() did not throw after hasNext() returned false"));
        assertThat(emptyIterator(), mismatches(() -> new ReThrowing<>(Collections.emptyIterator(), new IllegalStateException()),
                "next() threw wrong exception after hasNext() returned false"));
        assertThat(emptyIterator(), describesAs("iterator of []"));
    }


    @Test
    public void iterate1Element()
    {
        assertThat(iteratorOf("a"), matches(() -> singleton("a").iterator()));
        assertThat(iteratorOf("a"), mismatches(EmptyIterator::new, "had only 0 elements"));
        assertThat(iteratorOf("a"), mismatches(() -> new Flipping<>(singleton("a").iterator(), 20), "hasNext() flipped at index 0"));
        assertThat(iteratorOf("a"), mismatches(() -> new Flipping<>(singleton("a").iterator(), 120), "hasNext() flipped after the last element"));
        assertThat(iteratorOf("a"), mismatches(() -> singleton("b").iterator(), "was \"b\" at index 0"));
        assertThat(iteratorOf("a"), mismatches(() -> new Unremovable<>(asList("a", "b").iterator()), "had more than 1 elements"));
        assertThat(iteratorOf("a"), mismatches(() -> new ArrayList<>(asList("a", "b")).iterator(), "remove() threw wrong exception at index 0"));
        assertThat(iteratorOf("a"), mismatches(() -> new NonThrowing<>(singleton("a").iterator()), "next() did not throw after hasNext() returned false"));
        assertThat(iteratorOf("a"), mismatches(() -> new ReThrowing<>(singleton("a").iterator(), new IllegalStateException()),
                "next() threw wrong exception after hasNext() returned false"));
        assertThat(iteratorOf("a"), describesAs("iterator of [\"a\"]"));
    }


    @Test
    public void iterate3Elements()
    {
        assertThat(iteratorOf("a", "b", "c"), matches(() -> new Unremovable<>(asList("a", "b", "c").iterator())));
        assertThat(iteratorOf("a", "b", "c"), mismatches(() -> new Unremovable<>(Collections.emptyIterator()), "had only 0 elements"));
        assertThat(iteratorOf("a", "b", "c"), mismatches(() -> new Flipping<>(asList("a", "b", "c").iterator(), 20), "hasNext() flipped at index 0"));
        assertThat(iteratorOf("a", "b", "c"), mismatches(() -> new Flipping<>(asList("a", "b", "c").iterator(), 220), "hasNext() flipped at index 2"));
        assertThat(iteratorOf("a", "b", "c"),
                mismatches(() -> new Flipping<>(asList("a", "b", "c").iterator(), 320), "hasNext() flipped after the last element"));
        assertThat(iteratorOf("a", "b", "c"), mismatches(() -> new ArrayList<>(asList("a", "b")).iterator(), "remove() threw wrong exception at index 0"));
        assertThat(iteratorOf("a", "b", "c"), mismatches(() -> new UnremovableFirst<>(asList("a", "b").iterator()), "remove() did not throw at index 0"));
        assertThat(iteratorOf("a", "b", "c"), mismatches(() -> new RemovableLast<>(asList("a", "b").iterator()), "remove() did not throw after last element"));
        assertThat(iteratorOf("a", "b", "c"), mismatches(() -> new Unremovable<>(asList("a", "b").iterator()), "had only 2 elements"));
        assertThat(iteratorOf("a", "b", "c"), mismatches(() -> new Unremovable<>(asList("a", "c", "b").iterator()), "was \"c\" at index 1"));
        assertThat(iteratorOf("a", "b", "c"), mismatches(() -> new Unremovable<>(asList("a", "b", "b").iterator()), "was \"b\" at index 2"));
        assertThat(iteratorOf("a", "b", "c"), mismatches(() -> new Unremovable<>(asList("b", "b", "c").iterator()), "was \"b\" at index 0"));
        assertThat(iteratorOf("a", "b", "c"), mismatches(() -> new Unremovable<>(asList("a", "b", "c", "c").iterator()), "had more than 3 elements"));
        assertThat(iteratorOf("a", "b", "c"),
                mismatches(() -> new NonThrowing<>(asList("a", "b", "c").iterator()), "next() did not throw after hasNext() returned false"));
        assertThat(iteratorOf("a", "b", "c"),
                mismatches(() -> new ReThrowing<>(asList("a", "b", "c").iterator(), new IllegalStateException()),
                        "next() threw wrong exception after hasNext() returned false"));
        assertThat(iteratorOf("a", "b", "c"), describesAs("iterator of [\"a\", \"b\", \"c\"]"));
    }


    @Test
    public void variants()
    {
        assertThat(iteratorOf(equalTo("a"), equalTo("b"), equalTo("c")), matches(() -> new Unremovable<>(asList("a", "b", "c").iterator())));
        assertThat(iteratorOf(new Seq<>(equalTo("a"), equalTo("b"), equalTo("c"))), matches(() -> new Unremovable<>(asList("a", "b", "c").iterator())));
    }


    /**
     * {@link Iterator} decorator which doesn't throw when calling next after the last element.
     */
    private static final class NonThrowing<T> extends AbstractBaseIterator<T>
    {
        private final Iterator<T> mDelegate;


        private NonThrowing(Iterator<T> delegate)
        {
            mDelegate = delegate;
        }


        @Override
        public boolean hasNext()
        {
            return mDelegate.hasNext();
        }


        @Override
        public T next()
        {
            try
            {
                return mDelegate.next();
            }
            catch (NoSuchElementException e)
            {
                return null;
            }
        }
    }


    /*
     * {@link Iterator} decorator which throws the wrong exception in next();
     */
    private static final class ReThrowing<T> extends AbstractBaseIterator<T>
    {
        private final Iterator<T> mDelegate;
        private final RuntimeException mException;


        private ReThrowing(Iterator<T> delegate, RuntimeException exception)
        {
            mDelegate = delegate;
            mException = exception;
        }


        @Override
        public boolean hasNext()
        {
            return mDelegate.hasNext();
        }


        @Override
        public T next()
        {
            try
            {
                return mDelegate.next();
            }
            catch (NoSuchElementException e)
            {
                throw mException;
            }
        }
    }


    /*
     * {@link Iterator} decorator which has a broken hasNext() implementation which doesn't always return the same result.
     */
    private static final class Flipping<T> extends AbstractBaseIterator<T>
    {
        private final Iterator<T> mDelegate;
        private int mFlipAfter;


        private Flipping(Iterator<T> delegate, int flipAfter)
        {
            mDelegate = delegate;
            mFlipAfter = flipAfter;
        }


        @Override
        public boolean hasNext()
        {
            if (mFlipAfter-- > 0)
            {
                return mDelegate.hasNext();
            }
            else
            {
                return !mDelegate.hasNext();
            }
        }


        @Override
        public T next()
        {
            return mDelegate.next();
        }
    }


    /**
     * {@link Iterator} decorator which does not allow removing elements from the delegate.
     */
    private static final class Unremovable<T> implements Iterator<T>
    {
        private final Iterator<T> mDelegate;


        private Unremovable(Iterator<T> delegate)
        {
            mDelegate = delegate;
        }


        @Override
        public boolean hasNext()
        {
            return mDelegate.hasNext();
        }


        @Override
        public T next()
        {
            return mDelegate.next();
        }


        @Override
        public void remove()
        {
            throw new UnsupportedOperationException("Remove not supported");
        }
    }


    /**
     * {@link Iterator} decorator which pretends to allow removing the first element of the delegate.
     */
    private static final class UnremovableFirst<T> implements Iterator<T>
    {
        private final Iterator<T> mDelegate;
        private int count;


        private UnremovableFirst(Iterator<T> delegate)
        {
            mDelegate = delegate;
        }


        @Override
        public boolean hasNext()
        {
            return mDelegate.hasNext();
        }


        @Override
        public T next()
        {
            return mDelegate.next();
        }


        @Override
        public void remove()
        {
            if (count++ < 1)
            {
                throw new UnsupportedOperationException("Remove not supported");
            }
        }
    }


    /**
     * {@link Iterator} decorator which pretends to allow removing the last element of the delegate.
     */
    private static final class RemovableLast<T> implements Iterator<T>
    {
        private final Iterator<T> mDelegate;


        private RemovableLast(Iterator<T> delegate)
        {
            mDelegate = delegate;
        }


        @Override
        public boolean hasNext()
        {
            return mDelegate.hasNext();
        }


        @Override
        public T next()
        {
            return mDelegate.next();
        }


        @Override
        public void remove()
        {
            if (mDelegate.hasNext())
            {
                throw new UnsupportedOperationException("Remove not supported");
            }
        }
    }
}