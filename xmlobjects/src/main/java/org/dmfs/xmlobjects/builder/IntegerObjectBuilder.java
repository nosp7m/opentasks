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
 * A builder for elements that enclose an Integer value like so:
 * 
 * <pre>
 * &lt;some-integer>42&lt;/some-integer>
 * </pre>
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public class IntegerObjectBuilder extends AbstractObjectBuilder<Integer>
{

	/**
	 * A strict {@link IntegerObjectBuilder}. If the value can not be parsed as an Integer it will throw an {@link XmlObjectPullParserException}.
	 */
	public final static IntegerObjectBuilder INSTANCE_STRICT = new IntegerObjectBuilder(true);

	/**
	 * A tolerant {@link IntegerObjectBuilder}. Instead of throwing an exception it will return a <code>null</code> value.
	 */
	public final static IntegerObjectBuilder INSTANCE = new IntegerObjectBuilder(false);

	private final boolean mStrict;


	private IntegerObjectBuilder(boolean strict)
	{
		mStrict = strict;
	}


	@Override
	public Integer update(ElementDescriptor<Integer> descriptor, Integer object, String text, ParserContext context) throws XmlObjectPullParserException
	{
		if (text == null && !mStrict)
		{
			return null;
		}

		try
		{
			return Integer.parseInt(mStrict ? text : text.trim());
		}
		catch (NumberFormatException e)
		{
			if (!mStrict)
			{
				return null;
			}

			throw new XmlObjectPullParserException("could not parse integer in '" + text + "'", e);
		}
	}


	@Override
	public void writeChildren(ElementDescriptor<Integer> descriptor, Integer object, IXmlChildWriter childWriter, SerializerContext context)
		throws SerializerException, IOException
	{
		if (object != null)
		{
			childWriter.writeText(object.toString(), context);
		}
		else if (mStrict)
		{
			throw new IllegalStateException("Integer value is null");
		}
	}

}
