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
import org.dmfs.xmlobjects.pull.ParserContext;
import org.dmfs.xmlobjects.pull.XmlObjectPullParserException;
import org.dmfs.xmlobjects.serializer.SerializerContext;
import org.dmfs.xmlobjects.serializer.SerializerException;
import org.dmfs.xmlobjects.serializer.XmlObjectSerializer.IXmlAttributeWriter;
import org.dmfs.xmlobjects.serializer.XmlObjectSerializer.IXmlChildWriter;


/**
 * The interface of a builder that knows how to build an object from an XML element and how to serialize an object to XML.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 * 
 * @param <T>
 *            The type of the objects this builder builds and serializes.
 */
public interface IObjectBuilder<T>
{

	/**
	 * Return a new Object of type T for the given descriptor while trying to recycle the given instance of T if possible. This method is called when the start
	 * tag of an element has been parsed. A builder is free to return <code>null</code> if the occurrence of a start tag doesn't give enough information to
	 * build an instance of T.
	 * 
	 * @param descriptor
	 *            The {@link ElementDescriptor} of the element that has been parsed.
	 * @param recycle
	 *            An instance of T to recycle, can be <code>null</code>.
	 * @param context
	 *            A {@link ParserContext}.
	 * @return An instance of T or <code>null</code>.
	 * @throws XmlObjectPullParserException
	 */
	public T get(ElementDescriptor<T> descriptor, T recycle, ParserContext context) throws XmlObjectPullParserException;


	/**
	 * Update an object of type T with the value of an attribute. This method must be prepared to accept any value that can be returned by
	 * {@link #get(ElementDescriptor, Object, ParserContext)} or {@link #update(ElementDescriptor, Object, QualifiedName, String, ParserContext)}, including
	 * <code>null</code>.
	 * 
	 * @param descriptor
	 *            The {@link ElementDescriptor} of the element that has been parsed.
	 * @param object
	 *            The object to update, may be <code>null</code>.
	 * @param attribute
	 *            The {@link QualifiedName} of the attribute.
	 * @param value
	 *            The value of the attribute.
	 * @param context
	 *            A {@link ParserContext}.
	 * @return The updated instance of T, which might be a completely new instance or <code>null</code>.
	 * @throws XmlObjectPullParserException
	 */
	public T update(ElementDescriptor<T> descriptor, T object, QualifiedName attribute, String value, ParserContext context)
		throws XmlObjectPullParserException;


	/**
	 * Update an object of type T with a text value. This method must be prepared to accept any value that can be returned by
	 * {@link #get(ElementDescriptor, Object, ParserContext)} or one of the <code>update</code> methods, including <code>null</code>.
	 * 
	 * @param descriptor
	 *            The {@link ElementDescriptor} of the element that has been parsed.
	 * @param object
	 *            The object to update, may be <code>null</code>.
	 * @param text
	 *            The text that has been parsed.
	 * @param context
	 *            A {@link ParserContext}.
	 * @return The updated instance of T, which might be a completely new instance or <code>null</code>.
	 * @throws XmlObjectPullParserException
	 */
	public T update(ElementDescriptor<T> descriptor, T object, String text, ParserContext context) throws XmlObjectPullParserException;


	/**
	 * Update an object of type T with a child element. This method must be prepared to accept any value that can be returned by
	 * {@link #get(ElementDescriptor, Object, ParserContext)} or one of the <code>update</code> methods, including <code>null</code>.
	 * 
	 * @param descriptor
	 *            The {@link ElementDescriptor} of the element that has been parsed.
	 * @param object
	 *            The object to update, may be <code>null</code>.
	 * @param childDescriptor
	 *            The {@link ElementDescriptor} of the child element to add.
	 * @param child
	 *            The child element to add.
	 * @param context
	 *            A {@link ParserContext}.
	 * @return The updated instance of T, which might be a completely new instance or <code>null</code>.
	 * @throws XmlObjectPullParserException
	 */
	public <V> T update(ElementDescriptor<T> descriptor, T object, ElementDescriptor<V> childDescriptor, V child, ParserContext context)
		throws XmlObjectPullParserException;


	/**
	 * Update an object of type T with an anonymous child element. This method must be prepared to accept any value that can be returned by
	 * {@link #get(ElementDescriptor, Object, ParserContext)} or one of the <code>update</code> methods, including <code>null</code>.
	 * <p>
	 * An anonymous child is a child element for which no {@link ElementDescriptor} exists, but for which the {@link ElementDescriptor} of the parent element
	 * returns a builder.
	 * </p>
	 * 
	 * @param descriptor
	 *            The {@link ElementDescriptor} of the element that has been parsed.
	 * @param object
	 *            The object to update, may be <code>null</code>.
	 * @param anonymousChildName
	 *            The {@link QualifiedName} of the child element.
	 * @param anonymousChildBuilder
	 *            An {@link IObjectBuilder} that has built the child object.
	 * @param anonymousChild
	 *            The child object.
	 * @param context
	 *            A {@link ParserContext}.
	 * @return The updated instance of T, which might be a completely new instance or <code>null</code>.
	 * @throws XmlObjectPullParserException
	 */
	public <V> T update(ElementDescriptor<T> descriptor, T object, QualifiedName anonymousChildName, IObjectBuilder<V> anonymousChildBuilder, V anonymousChild,
		ParserContext context) throws XmlObjectPullParserException;


	/**
	 * This method is called when the end tag of an element has been parsed. It may perform additional processing of the parsed values. This method must be
	 * prepared to accept any value that can be returned by {@link #get(ElementDescriptor, Object, ParserContext)} or one of the <code>update</code> methods,
	 * including <code>null</code>.
	 * 
	 * @param descriptor
	 *            The {@link ElementDescriptor} of the element that has been parsed.
	 * @param object
	 *            The object to finish, may be <code>null</code>.
	 * @param context
	 *            A {@link ParserContext}.
	 * @return The finished instance of T, which might be a completely new instance or <code>null</code>.
	 * @throws XmlObjectPullParserException
	 */
	public T finish(ElementDescriptor<T> descriptor, T object, ParserContext context) throws XmlObjectPullParserException;


	/**
	 * Called when the attributes of an element needs to be serialized.
	 * 
	 * @param descriptor
	 *            The {@link ElementDescriptor} of the current element.
	 * @param object
	 *            The object to serialize.
	 * @param attributeWriter
	 *            An {@link IXmlAttributeWriter} to write to.
	 * @param context
	 *            The current {@link SerializerContext}.
	 * @throws SerializerException
	 * @throws IOException
	 */
	public void writeAttributes(ElementDescriptor<T> descriptor, T object, IXmlAttributeWriter attributeWriter, SerializerContext context)
		throws SerializerException, IOException;


	/**
	 * Called when the children of an element needs to be serialized.
	 * 
	 * @param descriptor
	 *            The {@link ElementDescriptor} of the current element.
	 * @param object
	 *            The object to serialize.
	 * @param childWriter
	 *            An {@link IXmlChildWriter} to write to.
	 * @param context
	 *            The current {@link SerializerContext}.
	 * @throws SerializerException
	 * @throws IOException
	 */
	public void writeChildren(ElementDescriptor<T> descriptor, T object, IXmlChildWriter childWriter, SerializerContext context) throws SerializerException,
		IOException;
}
