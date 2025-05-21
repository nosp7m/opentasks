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
import org.dmfs.xmlobjects.pull.ParserContext;
import org.dmfs.xmlobjects.pull.XmlObjectPullParserException;
import org.dmfs.xmlobjects.serializer.SerializerContext;
import org.dmfs.xmlobjects.serializer.SerializerException;
import org.dmfs.xmlobjects.serializer.XmlObjectSerializer.IXmlChildWriter;


/**
 * A builder for elements that enclose a text value like so:
 * 
 * <pre>
 * &lt;description>An example of a text value.&lt;/description>
 * </pre>
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public class StringObjectBuilder extends AbstractObjectBuilder<String>
{
	/**
	 * An {@link StringObjectBuilder} instance.
	 */
	public final static StringObjectBuilder INSTANCE = new StringObjectBuilder();


	private StringObjectBuilder()
	{
	}


	@Override
	public String update(ElementDescriptor<String> descriptor, String object, String text, ParserContext context) throws XmlObjectPullParserException
	{
		return text;
	}


	@Override
	public void writeChildren(ElementDescriptor<String> descriptor, String object, IXmlChildWriter childWriter, SerializerContext context)
		throws SerializerException, IOException
	{
		childWriter.writeText(object, context);
	}
}
