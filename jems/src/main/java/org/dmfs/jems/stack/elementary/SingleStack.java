package org.dmfs.jems.stack.elementary;

import org.dmfs.jems.optional.Optional;
import org.dmfs.jems.single.Single;
import org.dmfs.jems.single.elementary.ValueSingle;
import org.dmfs.jems.stack.Stack;

import java.util.NoSuchElementException;


/**
 * A {@link Stack} of a single element.
 *
 * @param <Element>
 *         The type of the element on this {@link Stack}.
 *
 * @author Marten Gajda
 */
public final class SingleStack<Element> implements Stack<Element>
{
    private final Optional<StackTop<Element>> mStackTop;


    public SingleStack(Element value)
    {
        this(new ValueSingle<>(value));
    }


    public SingleStack(Single<Element> value)
    {
        this(new PresentStackTop<>(value));
    }


    private SingleStack(Optional<StackTop<Element>> stackTop)
    {
        mStackTop = stackTop;
    }


    @Override
    public Optional<StackTop<Element>> top()
    {
        return mStackTop;
    }


    private final static class PresentStackTop<Element> implements Optional<StackTop<Element>>, StackTop<Element>
    {
        private final Single<Element> mElement;


        private PresentStackTop(Single<Element> element)
        {
            mElement = element;
        }


        @Override
        public boolean isPresent()
        {
            return true;
        }


        @Override
        public StackTop<Element> value() throws NoSuchElementException
        {
            return this;
        }


        @Override
        public Element element()
        {
            return mElement.value();
        }


        @Override
        public Stack<Element> bottom()
        {
            return new EmptyStack<>();
        }
    }
}
