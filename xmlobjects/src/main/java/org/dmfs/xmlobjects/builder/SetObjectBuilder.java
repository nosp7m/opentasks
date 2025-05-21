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
import java.util.HashSet;
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
 * A builder for sets of elements. All child elements must be of the same type and have the same {@link ElementDescriptor} or the same {@link IObjectBuilder}.
 * Child elements with different types will be ignored.
 * <p>
 * Example:
 * </p>
 * 
 * <pre>
 * &lt;set>
 *    &lt;element>element 1&lt;/element>
 *    &lt;element>element 2&lt;/element>
 *    &lt;element>element 3&lt;/element>
 * &lt;/set>
 * </pre>
 * 
 * <p>
 * <strong>Note:</strong> Serialization requires that either an {@link ElementDescriptor} is provided or the set elements are {@link QualifiedName}s.
 * </p>
 * 
 * @author Marten Gajda <marten@dmfs.org>
 * 
 * @see ListObjectBuilder
 * @see ElementMapObjectBuilder
 */
public class SetObjectBuilder<T> extends AbstractObjectBuilder<Set<T>>
{
	private final static int DEFAULT_INITIAL_CAPACITY = 16;

	private final ElementDescriptor<T> mSetElementDescriptor;
	private final IObjectBuilder<T> mSetElementBuilder;

	private final int mInitialCapacity;
	private final boolean mStoreNull;


	public SetObjectBuilder(ElementDescriptor<T> setElementDescriptor)
	{
		this(setElementDescriptor, DEFAULT_INITIAL_CAPACITY, true);
	}


	public SetObjectBuilder(ElementDescriptor<T> setElementDescriptor, boolean storeNull)
	{
		this(setElementDescriptor, DEFAULT_INITIAL_CAPACITY, storeNull);
	}


	public SetObjectBuilder(ElementDescriptor<T> setElementDescriptor, int initialCapacity)
	{
		this(setElementDescriptor, initialCapacity, true);
	}


	public SetObjectBuilder(ElementDescriptor<T> setElementDescriptor, int initialCapacity, boolean storeNull)
	{
		mSetElementDescriptor = setElementDescriptor;
		mSetElementBuilder = null;
		mInitialCapacity = initialCapacity;
		mStoreNull = storeNull;
	}


	public SetObjectBuilder(IObjectBuilder<T> setElementBuilder)
	{
		this(setElementBuilder, DEFAULT_INITIAL_CAPACITY, true);
	}


	public SetObjectBuilder(IObjectBuilder<T> setElementBuilder, boolean storeNull)
	{
		this(setElementBuilder, DEFAULT_INITIAL_CAPACITY, storeNull);
	}


	public SetObjectBuilder(IObjectBuilder<T> setElementBuilder, int initialCapacity)
	{
		this(setElementBuilder, initialCapacity, true);
	}


	public SetObjectBuilder(IObjectBuilder<T> setElementBuilder, int initialCapacity, boolean storeNull)
	{
		mSetElementDescriptor = null;
		mSetElementBuilder = setElementBuilder;
		mInitialCapacity = initialCapacity;
		mStoreNull = storeNull;
	}


	@Override
	public Set<T> get(ElementDescriptor<Set<T>> descriptor, Set<T> recycle, ParserContext context)
	{
		if (recycle != null)
		{
			recycle.clear();
			return recycle;
		}
		else
		{
			return new HashSet<T>(mInitialCapacity);
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	public <V> Set<T> update(ElementDescriptor<Set<T>> descriptor, Set<T> object, ElementDescriptor<V> childDescriptor, V child, ParserContext context)
		throws XmlObjectPullParserException
	{
		if (childDescriptor == mSetElementDescriptor || mSetElementDescriptor == null && childDescriptor != null
			&& mSetElementBuilder == childDescriptor.builder)
		{
			if (child != null || mStoreNull)
			{
				object.add((T) child);
			}
		}
		return object;
	}


	@Override
	public void writeChildren(ElementDescriptor<Set<T>> descriptor, Set<T> object, IXmlChildWriter childWriter, SerializerContext context)
		throws SerializerException, IOException
	{
		if (object != null)
		{
			XmlContext xmlContext = context.getXmlContext();
			for (T element : object)
			{
				if (mSetElementDescriptor == null && element instanceof QualifiedName)
				{
					@SuppressWarnings("unchecked")
					ElementDescriptor<T> childDescriptor = (ElementDescriptor<T>) ElementDescriptor.get((QualifiedName) element, descriptor, xmlContext);
					childWriter.writeChild(childDescriptor, element, context);
				}
				else
				{
					childWriter.writeChild(mSetElementDescriptor, element, context);
				}
			}
		}
	}

}
