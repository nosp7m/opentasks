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

package org.dmfs.iterators;

import java.util.Iterator;


/**
 * An abstract {@link Iterator} that converts the elements of another {@link Iterator} using a {@link Converter} before
 * returning them.
 *
 * @param <ResultType>
 *         The type of the values iterated by this Iterator.
 * @param <OriginalType>
 *         The original type of the values iterated by the decorated Iterator.
 *
 * @author Marten Gajda
 * @deprecated to be removed in version 2.0.
 */
@Deprecated
public abstract class AbstractConvertedIterator<ResultType, OriginalType> extends AbstractBaseIterator<ResultType>
{
    public interface Converter<ResultType, OriginalType>
    {
        ResultType convert(OriginalType element);
    }


    private final Iterator<OriginalType> mIterator;
    private final Converter<ResultType, OriginalType> mConverter;


    /**
     * Creates a converting {@link Iterator} that iterates the elements of the given {@link Iterator} after converting
     * them using the given {@link Converter}.
     *
     * @param iterator
     *         The {@link Iterator} to be converted.
     * @param converter
     *         The {@link Converter}.
     */
    public AbstractConvertedIterator(final Iterator<OriginalType> iterator, final Converter<ResultType, OriginalType> converter)
    {
        mIterator = iterator;
        mConverter = converter;
    }


    @Override
    public final boolean hasNext()
    {
        return mIterator.hasNext();
    }


    @Override
    public final ResultType next()
    {
        return mConverter.convert(mIterator.next());
    }
}
