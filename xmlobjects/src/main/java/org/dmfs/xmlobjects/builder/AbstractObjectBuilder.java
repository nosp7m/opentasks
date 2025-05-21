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

import org.dmfs.xmlobjects.QualifiedName;
import org.dmfs.xmlobjects.ElementDescriptor;
import org.dmfs.xmlobjects.pull.ParserContext;
import org.dmfs.xmlobjects.pull.XmlObjectPullParserException;
import org.dmfs.xmlobjects.serializer.SerializerContext;
import org.dmfs.xmlobjects.serializer.SerializerException;
import org.dmfs.xmlobjects.serializer.XmlObjectSerializer.IXmlAttributeWriter;
import org.dmfs.xmlobjects.serializer.XmlObjectSerializer.IXmlChildWriter;


/**
 * A basic implementation of an {@link IObjectBuilder}. This class provides default implementations for all methods.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 * 
 * @param <T>
 *            The type of the objects being built by this builder.
 */
public abstract class AbstractObjectBuilder<T> implements IObjectBuilder<T>
{

	@Override
	public T get(ElementDescriptor<T> descriptor, T recycle, ParserContext context) throws XmlObjectPullParserException
	{
		return null;
	}


	@Override
	public T update(ElementDescriptor<T> descriptor, T object, QualifiedName attribute, String value, ParserContext context)
		throws XmlObjectPullParserException
	{
		return object;
	}


	@Override
	public T update(ElementDescriptor<T> descriptor, T object, String text, ParserContext context) throws XmlObjectPullParserException
	{
		return object;
	}


	@Override
	public <V> T update(ElementDescriptor<T> descriptor, T object, ElementDescriptor<V> childDescriptor, V child, ParserContext context)
		throws XmlObjectPullParserException
	{
		return object;
	}


	@Override
	public <V> T update(ElementDescriptor<T> descriptor, T object, QualifiedName anonymousChildName, IObjectBuilder<V> anonymousChildBuilder,
		V anonymousChild, ParserContext context) throws XmlObjectPullParserException
	{
		return object;
	}


	@Override
	public T finish(ElementDescriptor<T> descriptor, T object, ParserContext context) throws XmlObjectPullParserException
	{
		return object;
	}


	@Override
	public void writeAttributes(ElementDescriptor<T> descriptor, T object, IXmlAttributeWriter attributeWriter, SerializerContext context)
		throws SerializerException, IOException
	{
		// do nothing
	}


	@Override
	public void writeChildren(ElementDescriptor<T> descriptor, T object, IXmlChildWriter childWriter, SerializerContext context) throws SerializerException,
		IOException
	{
		// do nothing
	}
}
