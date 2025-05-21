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

package org.dmfs.xmlobjects.serializer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.HashSet;

import org.dmfs.xmlobjects.ElementDescriptor;
import org.dmfs.xmlobjects.QualifiedName;


/**
 * High-level XML serializer. The serializer itself is stateless and threadsafe. You have to provide a {@link SerializerContext} to all methods to store the
 * current state. Be aware that {@link SerializerContext}s are not threadsafe.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public class XmlObjectSerializer
{
	/**
	 * Characters we use to build prefixes.
	 */
	private final static char[] PREFIX_CHARS = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
		'W', 'X', 'Y', 'Z' };

	/**
	 * A writer for attributes.
	 */
	public interface IXmlAttributeWriter
	{
		/**
		 * Adds the given attribute to the current element.
		 * 
		 * @param name
		 *            The name of the attribute.
		 * @param value
		 *            The value of the attribute.
		 * @param serializerContext
		 *            The current {@link SerializerContext}.
		 * @throws SerializerException
		 * @throws IOException
		 */
		public void writeAttribute(QualifiedName name, String value, SerializerContext serializerContext) throws SerializerException, IOException;
	}

	/**
	 * A writer for child elements, including text nodes.
	 */
	public interface IXmlChildWriter
	{
		/**
		 * Add the given child element to the current element.
		 * 
		 * @param descriptor
		 *            The descriptor of the child to add.
		 * @param child
		 *            The child object.
		 * @param serializerContext
		 *            The current {@link SerializerContext}.
		 * @throws SerializerException
		 * @throws IOException
		 */
		public <T> void writeChild(ElementDescriptor<T> descriptor, T child, SerializerContext serializerContext) throws SerializerException, IOException;


		/**
		 * Add a text node to the current element.
		 * 
		 * @param text
		 *            The text to write.
		 * @param serializerContext
		 *            The current {@link SerializerContext}.
		 * @throws SerializerException
		 * @throws IOException
		 */
		public void writeText(String text, SerializerContext serializerContext) throws SerializerException, IOException;
	}

	/**
	 * The actual instance of {@link IXmlAttributeWriter}.
	 */
	private final IXmlAttributeWriter mAttributeWriter = new IXmlAttributeWriter()
	{

		@Override
		public void writeAttribute(QualifiedName name, String value, SerializerContext serializerContext) throws SerializerException, IOException
		{
			try
			{
				serializerContext.serializer.attribute(name.namespace, name.name, value);
			}
			catch (IllegalArgumentException e)
			{
				throw new SerializerException("can not serialize attribute " + name, e);
			}
			catch (IllegalStateException e)
			{
				throw new SerializerException("can not serialize attribute " + name, e);
			}
		}
	};

	/**
	 * The actual instance if {@link IXmlChildWriter}.
	 */
	private final IXmlChildWriter mChildWriter = new IXmlChildWriter()
	{

		@Override
		public <T> void writeChild(ElementDescriptor<T> descriptor, T child, SerializerContext serializerContext) throws SerializerException, IOException
		{
			QualifiedName name = descriptor.qualifiedName;

			if (descriptor.builder == null)
			{
				throw new SerializerException(name + " is not serializable");
			}
			else
			{
				try
				{
					serializerContext.serializer.startTag(name.namespace, name.name);
					descriptor.builder.writeAttributes(descriptor, child, mAttributeWriter, serializerContext);
					descriptor.builder.writeChildren(descriptor, child, mChildWriter, serializerContext);
					serializerContext.serializer.endTag(name.namespace, name.name);
				}
				catch (IllegalArgumentException e)
				{
					throw new SerializerException("can not serialize element " + name, e);
				}
				catch (IllegalStateException e)
				{
					throw new SerializerException("can not serialize element " + name, e);
				}
			}
		}


		@Override
		public void writeText(String text, SerializerContext serializerContext) throws SerializerException, IOException
		{
			if (text != null)
			{
				try
				{
					serializerContext.serializer.text(text);
				}
				catch (IllegalArgumentException e)
				{
					throw new SerializerException("can not serialize text '" + text + "'", e);
				}
				catch (IllegalStateException e)
				{
					throw new SerializerException("can not serialize text '" + text + "'", e);
				}
			}
		}

	};


	/**
	 * Set the output of the serializer.
	 * 
	 * @param serializerContext
	 *            A {@link SerializerContext}.
	 * @param out
	 *            The {@link Writer} to write to.
	 * @return
	 * @throws SerializerException
	 * @throws IOException
	 */
	public XmlObjectSerializer setOutput(SerializerContext serializerContext, Writer out) throws SerializerException, IOException
	{
		try
		{
			serializerContext.serializer.setOutput(out);
		}
		catch (IllegalArgumentException e)
		{
			throw new SerializerException("can't configure serializer", e);
		}
		catch (IllegalStateException e)
		{
			throw new SerializerException("can't configure serializer", e);
		}
		return this;
	}


	/**
	 * Set the output of the serializer.
	 * 
	 * @param serializerContext
	 *            A {@link SerializerContext}.
	 * @param out
	 *            The {@link OutputStream} to write to.
	 * @param charset
	 *            The charset. The serializer will use "UTF-8" if this is <code>null</code>.
	 * @return
	 * @throws SerializerException
	 * @throws IOException
	 */
	public XmlObjectSerializer setOutput(SerializerContext serializerContext, OutputStream out, String charset) throws SerializerException, IOException
	{
		try
		{
			serializerContext.serializer.setOutput(out, charset == null ? "UTF-8" : charset);
		}
		catch (IllegalArgumentException e)
		{
			throw new SerializerException("can't configure serializer", e);
		}
		catch (IllegalStateException e)
		{
			throw new SerializerException("can't configure serializer", e);
		}
		return this;
	}


	/**
	 * Inform the serializer that the given namespace will be used. This allows the serializer to bind a prefix early.
	 * 
	 * @param namespace
	 *            The namespace that will be used.
	 */
	public void useNamespace(SerializerContext serializerContext, String namespace)
	{
		if (namespace != null && namespace.length() > 0)
		{
			if (serializerContext.knownNamespaces == null)
			{
				serializerContext.knownNamespaces = new HashSet<String>(8);
			}
			serializerContext.knownNamespaces.add(namespace);
		}
	}


	/**
	 * Inform the serializer that the given {@link QualifiedName} will be used. This allows the serializer to bind a prefix early.
	 * 
	 * @param name
	 *            The {@link QualifiedName} that will be used.
	 */
	public void useNamespace(SerializerContext serializerContext, QualifiedName name)
	{
		useNamespace(serializerContext, name.namespace);
	}


	/**
	 * Inform the serializer that the given {@link ElementDescriptor} will be used. This allows the serializer to bind a prefix early.
	 * 
	 * @param elementDescriptor
	 *            The {@link ElementDescriptor} that will be used.
	 */
	public void useNamespace(SerializerContext serializerContext, ElementDescriptor<?> elementDescriptor)
	{
		useNamespace(serializerContext, elementDescriptor.qualifiedName.namespace);
	}


	/**
	 * Serialize the given root object with the given {@link ElementDescriptor}.
	 * 
	 * @param descriptor
	 *            The descriptor of the object.
	 * @param rootObject
	 *            The root object itself.
	 * @throws SerializerException
	 * @throws IOException
	 */
	public <T> void serialize(SerializerContext serializerContext, ElementDescriptor<T> descriptor, T rootObject) throws SerializerException, IOException
	{
		serializerContext.serializer.startDocument(null, null);
		useNamespace(serializerContext, descriptor.qualifiedName);
		bindNamespaces(serializerContext);
		mChildWriter.writeChild(descriptor, rootObject, serializerContext);
		serializerContext.serializer.endDocument();
	}


	/**
	 * Ensure all known namespaces have been bound to a prefix.
	 * 
	 * @throws IOException
	 */
	private void bindNamespaces(SerializerContext serializerContext) throws IOException
	{
		if (serializerContext.knownNamespaces == null)
		{
			return;
		}

		StringBuilder nsBuilder = new StringBuilder(8);
		int count = 0;

		for (String ns : serializerContext.knownNamespaces)
		{
			int num = count;
			do
			{
				nsBuilder.append(PREFIX_CHARS[num % PREFIX_CHARS.length]);
				num /= PREFIX_CHARS.length;
			} while (num > 0);

			serializerContext.serializer.setPrefix(nsBuilder.toString(), ns);
			nsBuilder.setLength(0);
			++count;
		}
	}
}
