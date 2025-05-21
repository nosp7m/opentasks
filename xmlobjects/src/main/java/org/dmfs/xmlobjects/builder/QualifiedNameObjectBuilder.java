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

import org.dmfs.xmlobjects.QualifiedName;
import org.dmfs.xmlobjects.ElementDescriptor;
import org.dmfs.xmlobjects.pull.ParserContext;
import org.dmfs.xmlobjects.pull.XmlObjectPullParserException;


/**
 * A builder for XmlElements that are represented by their name like so:
 * 
 * <pre>
 * &lt; calendar-report />
 * </pre>
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public class QualifiedNameObjectBuilder extends AbstractObjectBuilder<QualifiedName>
{
	public final static QualifiedNameObjectBuilder INSTANCE = new QualifiedNameObjectBuilder();


	@Override
	public QualifiedName get(ElementDescriptor<QualifiedName> descriptor, QualifiedName recycle, ParserContext context) throws XmlObjectPullParserException
	{
		return descriptor.qualifiedName;
	}
}
