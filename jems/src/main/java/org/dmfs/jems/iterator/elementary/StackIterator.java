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

package org.dmfs.jems.iterator.elementary;

import org.dmfs.iterators.AbstractBaseIterator;
import org.dmfs.jems.stack.Stack;

import java.util.Iterator;


/**
 * An {@link Iterator} iterating the elements of a {@link Stack}.
 *
 * @param <Element>
 *         The type of the elements on the {@link Stack}.
 *
 * @author Marten Gajda
 */
public final class StackIterator<Element> extends AbstractBaseIterator<Element>
{
    private Stack<Element> mStack;


    public StackIterator(Stack<Element> stack)
    {
        mStack = stack;
    }


    @Override
    public boolean hasNext()
    {
        return mStack.top().isPresent();
    }


    @Override
    public Element next()
    {
        Stack.StackTop<Element> topElement = mStack.top().value();
        mStack = topElement.bottom();
        return topElement.element();
    }
}
