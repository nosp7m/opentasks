/*
 * Copyright (C) 2015 Marten Gajda <marten@dmfs.org>
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

import org.dmfs.xmlobjects.ElementDescriptor;
import org.dmfs.xmlobjects.pull.ParserContext;
import org.dmfs.xmlobjects.pull.XmlObjectPullParserException;
import org.dmfs.xmlobjects.serializer.SerializerContext;
import org.dmfs.xmlobjects.serializer.SerializerException;
import org.dmfs.xmlobjects.serializer.XmlObjectSerializer.IXmlChildWriter;


/**
 * An {@link IObjectBuilder} that builds a {@link Map} from the child elements of a specific type. The index is determined by a callback. If the callback
 * returns <code>null</code> the value is not stored in the map.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 * 
 * @param <T>
 *            The index type.
 * @param <V>
 *            The value type.
 */
public class MapObjectBuilder<T, V> extends AbstractObjectBuilder<Map<T, V>>
{
	/**
	 * The default size of maps created by this builder.
	 */
	public final static int DEFAULT_INITIAL_MAP_SIZE = 16;

	/**
	 * The interface of a class that knows how to determine the index of a specific value.
	 * 
	 * @param <T>
	 *            The index type.
	 * @param <V>
	 *            The value type.
	 */
	public interface Mapper<T, V>
	{
		/**
		 * Returns the index value for the given value of the given {@link ElementDescriptor}.
		 * 
		 * @param descriptor
		 *            The {@link ElementDescriptor} of the child element.
		 * @param value
		 *            The value of the child element.
		 * @return The index value or <code>null</code> to skip this value.
		 */
		public T getIndex(ElementDescriptor<V> descriptor, V value);
	}

	private final Mapper<T, V> mMapper;
	private final ElementDescriptor<V> mChildElementDescriptor;
	private final int mInitialMapSize;


	/**
	 * Create a new MapObjectBilder for Elements of the given {@link ElementDescriptor} using the given {@link Mapper}. This uses
	 * {@link #DEFAULT_INITIAL_MAP_SIZE} as the initial size of created maps.
	 * 
	 * @param mapper
	 *            The {@link Mapper} to build an index.
	 * @param childElementDescriptor
	 *            The {@link ElementDescriptor} of valid child elements.
	 */
	public MapObjectBuilder(Mapper<T, V> mapper, ElementDescriptor<V> childElementDescriptor)
	{
		this(mapper, childElementDescriptor, DEFAULT_INITIAL_MAP_SIZE);
	}


	/**
	 * Create a new MapObjectBilder for Elements of the given {@link ElementDescriptor} using the given {@link Mapper} and the given initial map size.
	 * 
	 * @param mapper
	 *            The {@link Mapper} to build an index.
	 * @param childElementDescriptor
	 *            The {@link ElementDescriptor} of valid child elements.
	 * @param initialMapSize
	 *            The initial size of the created maps.
	 */
	public MapObjectBuilder(Mapper<T, V> mapper, ElementDescriptor<V> childElementDescriptor, int initialMapSize)
	{
		mMapper = mapper;
		mChildElementDescriptor = childElementDescriptor;
		mInitialMapSize = initialMapSize;
	}


	@Override
	public Map<T, V> get(ElementDescriptor<Map<T, V>> descriptor, Map<T, V> recycle, ParserContext context) throws XmlObjectPullParserException
	{
		if (recycle != null)
		{
			recycle.clear();
			return recycle;
		}

		return new HashMap<T, V>(mInitialMapSize);
	}


	@Override
	public <W extends Object> Map<T, V> update(ElementDescriptor<Map<T, V>> descriptor, Map<T, V> object, ElementDescriptor<W> childDescriptor, W child,
		ParserContext context) throws XmlObjectPullParserException
	{
		if (childDescriptor == mChildElementDescriptor)
		{
			@SuppressWarnings("unchecked")
			V childElement = (V) child;
			T index = mMapper.getIndex(mChildElementDescriptor, childElement);
			if (index != null)
			{
				object.put(index, childElement);
			}
		}
		return object;
	};


	@Override
	public void writeChildren(ElementDescriptor<Map<T, V>> descriptor, Map<T, V> object, IXmlChildWriter childWriter, SerializerContext context)
		throws SerializerException, IOException
	{
		if (object != null)
		{
			for (V child : object.values())
			{
				childWriter.writeChild(mChildElementDescriptor, child, context);
			}
		}
	}
}
