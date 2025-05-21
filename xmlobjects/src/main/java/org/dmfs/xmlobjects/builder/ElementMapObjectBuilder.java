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
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.dmfs.xmlobjects.ElementDescriptor;
import org.dmfs.xmlobjects.pull.ParserContext;
import org.dmfs.xmlobjects.pull.XmlObjectPullParserException;
import org.dmfs.xmlobjects.serializer.SerializerContext;
import org.dmfs.xmlobjects.serializer.SerializerException;
import org.dmfs.xmlobjects.serializer.XmlObjectSerializer.IXmlChildWriter;


/**
 * Builder for {@link Map}s of {@link ElementDescriptor}s to values. It's meant to hold variable sets of elements of different types (of which each can occur
 * only once) like in the following example.
 * 
 * <p>
 * Example:
 * </p>
 * 
 * <pre>
 * &lt;values>
 *     &lt;element-x>value-1&lt;/element-x>
 *     &lt;element-y>value-2&lt;/element-y>
 *     &lt;element-z>value-3&lt;/element-z>
 * &lt;/values>
 * </pre>
 * 
 * @author Marten Gajda <marten@dmfs.org>
 * 
 * @see SetObjectBuilder
 * @see ListObjectBuilder
 */
public class ElementMapObjectBuilder extends AbstractObjectBuilder<Map<ElementDescriptor<?>, Object>>
{
	/**
	 * Instance which uses {@link #DEFAULT_INITIAL_CAPACITY} as the initial capacity.
	 */
	public final static ElementMapObjectBuilder INSTANCE = new ElementMapObjectBuilder();

	/**
	 * The initial capacity when creating instances with {@link #ElementMapObjectBuilder()}. Use {@link #ElementMapObjectBuilder(int)} to specify a different
	 * initial capacity.
	 */
	public final static int DEFAULT_INITIAL_CAPACITY = 16;

	/**
	 * The actual initial capacity for new instances.
	 */
	private final int mInitialCapacity;


	public ElementMapObjectBuilder()
	{
		mInitialCapacity = DEFAULT_INITIAL_CAPACITY;
	}


	public ElementMapObjectBuilder(int initialCapacity)
	{
		mInitialCapacity = initialCapacity;
	}


	@Override
	public Map<ElementDescriptor<?>, Object> get(ElementDescriptor<Map<ElementDescriptor<?>, Object>> descriptor, Map<ElementDescriptor<?>, Object> recycle,
		ParserContext context)
	{
		if (recycle != null)
		{
			recycle.clear();
			return recycle;
		}
		else
		{
			return new HashMap<ElementDescriptor<?>, Object>(mInitialCapacity);
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	public <V> Map<ElementDescriptor<?>, Object> update(ElementDescriptor<Map<ElementDescriptor<?>, Object>> descriptor,
		Map<ElementDescriptor<?>, Object> object, ElementDescriptor<V> childDescriptor, V child, ParserContext context) throws XmlObjectPullParserException
	{
		if (childDescriptor != null)
		{
			object.put((ElementDescriptor<Object>) childDescriptor, child);
		}
		return object;
	}


	@SuppressWarnings("unchecked")
	@Override
	public void writeChildren(ElementDescriptor<Map<ElementDescriptor<?>, Object>> descriptor, Map<ElementDescriptor<?>, Object> object,
		IXmlChildWriter childWriter, SerializerContext context) throws SerializerException, IOException
	{
		if (object != null)
		{
			for (Entry<ElementDescriptor<?>, Object> element : object.entrySet())
			{
				childWriter.writeChild((ElementDescriptor<Object>) element.getKey(), element.getValue(), context);
			}
		}
	}
}
