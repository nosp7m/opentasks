/*
 * Copyright (C) 2014 Marten Gajda <marten@dmfs.org>
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
 * 
 */

package org.dmfs.xmlobjects.builder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.dmfs.xmlobjects.ElementDescriptor;
import org.dmfs.xmlobjects.QualifiedName;
import org.dmfs.xmlobjects.XmlContext;
import org.dmfs.xmlobjects.pull.ParserContext;
import org.dmfs.xmlobjects.pull.XmlObjectPullParserException;
import org.dmfs.xmlobjects.serializer.SerializerContext;
import org.dmfs.xmlobjects.serializer.SerializerException;
import org.dmfs.xmlobjects.serializer.XmlObjectSerializer.IXmlChildWriter;


/**
 * A builder for lists of elements. All child elements must be of the same type and have the same {@link ElementDescriptor} or the same {@link IObjectBuilder}.
 * Child elements with different types will be ignored. Check out {@link SetObjectBuilder} to build {@link Set}s.
 * <p>
 * Example:
 * </p>
 * 
 * <pre>
 * &lt;list>
 *    &lt;element>element 1&lt;/element>
 *    &lt;element>element 2&lt;/element>
 *    &lt;element>element 3&lt;/element>
 * &lt;/list>
 * </pre>
 * 
 * <p>
 * <strong>Note:</strong> Serialization requires that either an {@link ElementDescriptor} is provided or the list elements are {@link QualifiedName}s.
 * </p>
 * 
 * @author Marten Gajda <marten@dmfs.org>
 * 
 * @see SetObjectBuilder
 * @see ElementMapObjectBuilder
 * 
 */
public class ListObjectBuilder<T> extends AbstractObjectBuilder<List<T>>
{
	private final static int DEFAULT_INITIAL_CAPACITY = 16;

	private final ElementDescriptor<T> mListElementDescriptor;
	private final IObjectBuilder<T> mListElementBuilder;

	private final int mInitialCapacity;
	private final boolean mStoreNull;


	public ListObjectBuilder(ElementDescriptor<T> listElementDescriptor)
	{
		this(listElementDescriptor, DEFAULT_INITIAL_CAPACITY, true);
	}


	public ListObjectBuilder(ElementDescriptor<T> listElementDescriptor, boolean storeNull)
	{
		this(listElementDescriptor, DEFAULT_INITIAL_CAPACITY, storeNull);
	}


	public ListObjectBuilder(ElementDescriptor<T> listElementDescriptor, int initialCapacity)
	{
		this(listElementDescriptor, initialCapacity, true);
	}


	public ListObjectBuilder(ElementDescriptor<T> listElementDescriptor, int initialCapacity, boolean storeNull)
	{
		mListElementDescriptor = listElementDescriptor;
		mListElementBuilder = null;
		mInitialCapacity = initialCapacity;
		mStoreNull = storeNull;
	}


	public ListObjectBuilder(IObjectBuilder<T> listElementBuilder)
	{
		this(listElementBuilder, DEFAULT_INITIAL_CAPACITY, true);
	}


	public ListObjectBuilder(IObjectBuilder<T> listElementBuilder, boolean storeNull)
	{
		this(listElementBuilder, DEFAULT_INITIAL_CAPACITY, storeNull);
	}


	public ListObjectBuilder(IObjectBuilder<T> listElementBuilder, int initialCapacity)
	{
		this(listElementBuilder, initialCapacity, true);
	}


	public ListObjectBuilder(IObjectBuilder<T> listElementBuilder, int initialCapacity, boolean storeNull)
	{
		mListElementDescriptor = null;
		mListElementBuilder = listElementBuilder;
		mInitialCapacity = initialCapacity;
		mStoreNull = storeNull;
	}


	@Override
	public List<T> get(ElementDescriptor<List<T>> descriptor, List<T> recycle, ParserContext context)
	{
		if (recycle != null)
		{
			// we have a list that we can recycle
			recycle.clear();
			return recycle;
		}
		else
		{
			// return a new list
			return new ArrayList<T>(mInitialCapacity);
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	public <V> List<T> update(ElementDescriptor<List<T>> descriptor, List<T> object, ElementDescriptor<V> childDescriptor, V child, ParserContext context)
		throws XmlObjectPullParserException
	{
		if (childDescriptor == mListElementDescriptor || mListElementDescriptor == null && childDescriptor != null
			&& mListElementBuilder == childDescriptor.builder)
		{
			if (child != null || mStoreNull)
			{
				object.add((T) child);
			}
		}
		return object;
	}


	@Override
	public void writeChildren(ElementDescriptor<List<T>> descriptor, List<T> object, IXmlChildWriter childWriter, SerializerContext context)
		throws SerializerException, IOException
	{
		if (object != null)
		{
			XmlContext xmlContext = context.getXmlContext();
			for (T element : object)
			{
				if (mListElementDescriptor == null && element instanceof QualifiedName)
				{
					@SuppressWarnings("unchecked")
					ElementDescriptor<T> childDescriptor = (ElementDescriptor<T>) ElementDescriptor.get((QualifiedName) element, descriptor, xmlContext);
					childWriter.writeChild(childDescriptor, element, context);
				}
				else
				{
					childWriter.writeChild(mListElementDescriptor, element, context);
				}
			}
		}
	}

}
