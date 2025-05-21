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

package org.dmfs.jems.stack.decorators;

import org.dmfs.jems.optional.Optional;
import org.dmfs.jems.stack.Stack;

import java.util.NoSuchElementException;


/**
 * A {@link Stack} based on another Stack and a new top element.
 *
 * @param <Element>
 *         The type of the elements on the Stack.
 *
 * @author Marten Gajda
 */
public final class Topped<Element> implements Stack<Element>
{
    private final Optional<StackTop<Element>> mTop;


    /**
     * Creates a new {@link Stack} with the given element on top and the given {@link Stack} underneath.
     *
     * @param element
     *         The  top element of the {@link Stack}.
     * @param bottom
     *         The {@link Stack} underneath the top element.
     */
    public Topped(Element element, Stack<Element> bottom)
    {
        this(new PresentTop<>(element, bottom));
    }


    /**
     * Creates a new Stack with the given {@link Optional} {@link StackTop}.
     * <p>
     * Note, this constructor expects the {@link StackTop} to be present. It's made private to guarantee this.
     *
     * @param top
     *         {@link StackTop}
     */
    private Topped(Optional<StackTop<Element>> top)
    {
        mTop = top;
    }


    @Override
    public Optional<StackTop<Element>> top()
    {
        return mTop;
    }


    /**
     * A present {@link Optional} {@link StackTop}.
     *
     * @param <T>
     */
    private final static class PresentTop<T> implements Optional<StackTop<T>>, StackTop<T>
    {
        private final T mElement;
        private final Stack<T> mBottom;


        private PresentTop(T element, Stack<T> bottom)
        {
            mElement = element;
            mBottom = bottom;
        }


        @Override
        public T element()
        {
            return mElement;
        }


        @Override
        public Stack<T> bottom()
        {
            return mBottom;
        }


        @Override
        public boolean isPresent()
        {
            return true;
        }


        @Override
        public StackTop<T> value() throws NoSuchElementException
        {
            return this;
        }

    }
}
