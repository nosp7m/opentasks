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

package org.dmfs.xmlobjects.pull;

/**
 * An {@link Exception} that's thrown whenever an unrecoverable error occurred while parsing an XML stream.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public class XmlObjectPullParserException extends Exception
{

	/**
	 * Generated serial ID.
	 */
	private static final long serialVersionUID = 7963991276367967034L;


	public XmlObjectPullParserException()
	{
		super();
	}


	public XmlObjectPullParserException(String message)
	{
		super(message);
	}


	public XmlObjectPullParserException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
