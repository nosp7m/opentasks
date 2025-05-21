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

package org.dmfs.jems.iterable.decorators;

import org.dmfs.iterators.AbstractBaseIterator;
import org.dmfs.jems.iterator.elementary.Seq;
import org.junit.Test;

import java.util.Iterator;

import static org.dmfs.jems.hamcrest.matchers.IterableMatcher.iteratesTo;
import static org.dmfs.jems.hamcrest.matchers.ParallelMatcher.parallel;
import static org.dmfs.jems.hamcrest.matchers.iterator.IteratorMatcher.iteratorOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


/**
 * Unit test for {@link Frozen}.
 *
 * @author Marten Gajda
 */
public class FrozenTest
{

    @Test
    public void test()
    {
        assertThat(new Frozen<>(new Seq<>(1, 2, 3, 4, 5, 6, 7, 8, 9)), is(parallel(iteratesTo(1, 2, 3, 4, 5, 6, 7, 8, 9))));
        assertThat(new Frozen<>(new Slow<>(10, new Seq<>(1, 2, 3, 4, 5, 6, 7, 8, 9))), is(parallel(iteratesTo(1, 2, 3, 4, 5, 6, 7, 8, 9))));
        assertThat(new Frozen<>(new Slow<>(100, new Seq<>(1, 2, 3, 4, 5, 6, 7, 8, 9))), is(parallel(iteratesTo(1, 2, 3, 4, 5, 6, 7, 8, 9))));

        Iterable<Integer> i = new Frozen<>(new Seq<>(1, 2, 3, 4, 5, 6, 7, 8, 9));
        assertThat(i::iterator, is(parallel(iteratorOf(1, 2, 3, 4, 5, 6, 7, 8, 9))));

        Iterable<Integer> i2 = new Frozen<>(new Slow<>(2, new Seq<>(1, 2, 3, 4, 5, 6, 7, 8, 9)));
        assertThat(i2::iterator, is(parallel(iteratorOf(1, 2, 3, 4, 5, 6, 7, 8, 9))));
    }


    /**
     * An Iterator decorator which pauses before returning the next element.
     *
     * @param <E>
     */
    private final static class Slow<E> extends AbstractBaseIterator<E>
    {
        private final Iterator<E> mDelegate;
        private final int mDelay;


        private Slow(int delay, Iterator<E> delegate)
        {
            mDelegate = delegate;
            mDelay = delay;
        }


        @Override
        public boolean hasNext()
        {
            return mDelegate.hasNext();
        }


        @Override
        public E next()
        {
            try
            {
                Thread.sleep(mDelay);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            return mDelegate.next();
        }
    }

}