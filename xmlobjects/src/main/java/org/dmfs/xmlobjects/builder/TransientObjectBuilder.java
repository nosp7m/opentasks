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

import org.dmfs.xmlobjects.ElementDescriptor;
import org.dmfs.xmlobjects.QualifiedName;
import org.dmfs.xmlobjects.XmlContext;
import org.dmfs.xmlobjects.pull.ParserContext;
import org.dmfs.xmlobjects.pull.XmlObjectPullParserException;
import org.dmfs.xmlobjects.serializer.SerializerContext;
import org.dmfs.xmlobjects.serializer.SerializerException;
import org.dmfs.xmlobjects.serializer.XmlObjectSerializer.IXmlChildWriter;


/**
 * A builder that just returns one of its child elements. It always returns the last child with the given {@link ElementDescriptor} or {@link IObjectBuilder}.
 * It's handy in cases like this, when the parent element can have only one child element of a specific type:
 * 
 * <pre>
 * &lt;location>&lt;href>http://example.com&lt;/href>&lt;/location>
 * </pre>
 * 
 * <p>
 * <strong>Note:</strong> Serialization requires that either an {@link ElementDescriptor} is provided or the child element is a {@link QualifiedName}s.
 * </p>
 * 
 * <p>
 * Please be aware that the child object is not serialized if the object is <code>null</code>.
 * </p>
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public class TransientObjectBuilder<T> extends AbstractObjectBuilder<T>
{
	/**
	 * The descriptor of the child element to pass through.
	 */
	private final ElementDescriptor<T> mChildDescriptor;

	private final IObjectBuilder<T> mChildBuilder;


	public TransientObjectBuilder(ElementDescriptor<T> childDescriptor)
	{
		mChildDescriptor = childDescriptor;
		mChildBuilder = null;
	}


	public TransientObjectBuilder(IObjectBuilder<T> childBuilder)
	{
		mChildDescriptor = null;
		mChildBuilder = childBuilder;
	}


	@SuppressWarnings("unchecked")
	@Override
	public <V> T update(ElementDescriptor<T> descriptor, T object, ElementDescriptor<V> childDescriptor, V child, ParserContext context)
		throws XmlObjectPullParserException
	{
		if (childDescriptor == mChildDescriptor || mChildDescriptor == null && childDescriptor != null && mChildBuilder == childDescriptor.builder)
		{
			return (T) child;
		}
		return object;
	}


	@Override
	public void writeChildren(ElementDescriptor<T> descriptor, T object, IXmlChildWriter childWriter, SerializerContext context) throws SerializerException,
		IOException
	{
		if (object != null)
		{
			XmlContext xmlContext = context.getXmlContext();
			if (mChildDescriptor == null && object instanceof QualifiedName)
			{
				@SuppressWarnings("unchecked")
				ElementDescriptor<T> childDescriptor = (ElementDescriptor<T>) ElementDescriptor.get((QualifiedName) object, descriptor, xmlContext);
				childWriter.writeChild(childDescriptor, object, context);
			}
			else
			{
				childWriter.writeChild(mChildDescriptor, object, context);
			}
		}
	}
}
