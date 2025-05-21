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

import java.util.LinkedList;

import org.dmfs.xmlobjects.ElementDescriptor;


/**
 * A simple XML Path.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public class XmlPath implements Cloneable
{
	public LinkedList<ElementDescriptor<?>> mPathElements;


	public XmlPath(ElementDescriptor<?>... path)
	{
		LinkedList<ElementDescriptor<?>> pathElements = mPathElements = new LinkedList<ElementDescriptor<?>>();
		if (path != null)
		{
			for (ElementDescriptor<?> element : path)
			{
				pathElements.addFirst(element);
			}
		}
	}


	@SuppressWarnings("unchecked")
	private XmlPath(XmlPath path)
	{
		mPathElements = (LinkedList<ElementDescriptor<?>>) path.mPathElements.clone();
	}


	public boolean matches(XmlPath path)
	{
		return mPathElements.equals(path.mPathElements);
	}


	public XmlPath clone()
	{
		return new XmlPath(this);
	}


	public int length()
	{
		// TODO: check if this is inefficient and we need to optimize
		return mPathElements.size();
	}


	void append(ElementDescriptor<?> element)
	{
		mPathElements.addFirst(element);
	}


	ElementDescriptor<?> peek()
	{
		return mPathElements.peek();
	}


	ElementDescriptor<?> peekLast()
	{
		return mPathElements.peekLast();
	}


	ElementDescriptor<?> pop()
	{
		return mPathElements.removeFirst();
	}

}
