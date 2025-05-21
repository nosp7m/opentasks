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

import java.util.Set;

import org.dmfs.xmlobjects.XmlContext;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;


/**
 * The context of the {@link XmlObjectSerializer}. Subclass it to provide additional information to your serializers.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public class SerializerContext
{
	/**
	 * The {@link XmlContext}.
	 */
	XmlContext xmlContext;

	/**
	 * The actual serializer.
	 */
	XmlSerializer serializer;

	/**
	 * A set of known name spaces.
	 */
	Set<String> knownNamespaces;


	public SerializerContext(XmlContext xmlContext) throws SerializerException
	{
		this.xmlContext = xmlContext;
		try
		{
			this.serializer = XmlPullParserFactory.newInstance().newSerializer();
		}
		catch (XmlPullParserException e)
		{
			throw new SerializerException("can't get serializer", e);
		}
	}


	/**
	 * Returns the current {@link XmlContext}.
	 * 
	 * @return The {@link XmlContext}, never <code>null</code>.
	 */
	public XmlContext getXmlContext()
	{
		return xmlContext;
	}
}
