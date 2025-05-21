/*
 * Copyright 2020 dmfs GmbH
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

package org.dmfs.jems.hamcrest.matchers.mockito;

import org.dmfs.iterables.EmptyIterable;
import org.dmfs.jems.function.Function;
import org.dmfs.jems.iterable.elementary.Seq;
import org.dmfs.jems.procedure.Procedure;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.mockito.verification.VerificationMode;


/**
 * A Matcher for interactions with Mockito mocks.
 * <p>
 * Note that, by design this matcher mutates the mock.
 */
public final class MockInteractionMatcher<T> extends TypeSafeDiagnosingMatcher<T>
{

    private final Function<T, InOrder> mOrderFactory;
    private final Iterable<? extends Procedure<? super T>> mTestProcedures;
    private final String mDescription;


    public static <T> Matcher<T> notCalled()
    {
        return new MockInteractionMatcher<>(Mockito::inOrder, new EmptyIterable<>(), "mock is not called");
    }


    @SafeVarargs
    public static <T> Matcher<T> called(Procedure<? super T>... verifications)
    {
        return new MockInteractionMatcher<T>(NoOrder::new, new Seq<>(verifications), "mock is called in any order");
    }


    @SafeVarargs
    public static <T> Matcher<T> calledInOrder(Procedure<? super T>... verifications)
    {
        return new MockInteractionMatcher<T>(Mockito::inOrder, new Seq<>(verifications), "mock is called in order");
    }


    public MockInteractionMatcher(Function<T, InOrder> mOrderFactory, Iterable<? extends Procedure<? super T>> mTestProcedures, String mDescription)
    {
        this.mOrderFactory = mOrderFactory;
        this.mTestProcedures = mTestProcedures;
        this.mDescription = mDescription;
    }


    @Override
    protected boolean matchesSafely(T item, Description mismatchDescription)
    {
        InOrder inOrder = mOrderFactory.value(item);
        try
        {
            for (Procedure<? super T> verification : mTestProcedures)
            {
                verification.process(inOrder.verify(item));
            }
            inOrder.verifyNoMoreInteractions();
        }
        catch (AssertionError e)
        {
            mismatchDescription.appendText(e.getMessage());
            return false;
        }
        return true;
    }


    @Override
    public void describeTo(Description description)
    {
        description.appendText(mDescription);
    }


    /**
     * A simple implementation of {@link InOrder} which doesn't care about the order of calls of a single mock.
     */
    private final static class NoOrder<T> implements InOrder
    {

        private final T mMock;


        private NoOrder(T mock)
        {
            mMock = mock;
        }


        @Override
        public <T> T verify(T mock)
        {
            return Mockito.verify(mock);
        }


        @Override
        public <T> T verify(T mock, VerificationMode mode)
        {
            return Mockito.verify(mock, mode);
        }


        @Override
        public void verifyNoMoreInteractions()
        {
            Mockito.verifyNoMoreInteractions(mMock);
        }
    }
}
