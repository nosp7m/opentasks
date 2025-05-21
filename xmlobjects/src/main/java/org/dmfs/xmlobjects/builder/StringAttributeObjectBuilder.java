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


/**
 * A builder for elements that are represented by the value of a specific attribute, like so:
 * 
 * <pre>
 * &lt;comp name="VEVENT" />
 * </pre>
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public class StringAttributeObjectBuilder extends AbstractObjectBuilder<String>
{
	public final QualifiedName attribute;


	public StringAttributeObjectBuilder(QualifiedName attribute)
	{
		this.attribute = attribute;
	}


	@Override
	public String update(ElementDescriptor<String> descriptor, String object, QualifiedName attribute, String value, ParserContext context)
		throws XmlObjectPullParserException
	{
		if (this.attribute == attribute)
		{
			return value;
		}
		else
		{
			return object;
		}
	}


	@Override
	public void writeAttributes(ElementDescriptor<String> descriptor, String object, IXmlAttributeWriter attributeWriter, SerializerContext context)
		throws SerializerException, IOException
	{
		attributeWriter.writeAttribute(attribute, object, context);
	}

}