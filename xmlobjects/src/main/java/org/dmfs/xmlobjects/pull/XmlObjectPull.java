/*
 * Copyright (C) 2015 Marten Gajda <marten@dmfs.org>
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

import java.io.IOException;
import java.util.LinkedList;

import org.dmfs.xmlobjects.ElementDescriptor;
import org.dmfs.xmlobjects.QualifiedName;
import org.dmfs.xmlobjects.XmlContext;
import org.dmfs.xmlobjects.builder.IObjectBuilder;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;


/**
 * XML Parser that allows to pull parsed objects from an XML stream.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public class XmlObjectPull
{

	/**
	 * The current {@link XmlPullParser}.
	 */
	private final XmlPullParser mParser;

	private final XmlPath mCurrentElementDescriptorPath = new XmlPath();
	private final LinkedList<Object> mObjectStack = new LinkedList<Object>();

	private IObjectBuilder<?> mCurrentBuilder;
	private XmlContext mContext;
	private ParserContext mParserContext;


	public XmlObjectPull(XmlPullParser parser) throws XmlPullParserException, IOException
	{
		this(parser, new ParserContext());
	}


	public XmlObjectPull(XmlPullParser parser, ParserContext parserContext) throws XmlPullParserException, IOException
	{
		mParser = parser;
		mParserContext = parserContext;
		mParserContext.setXmlPullParser(parser);
		mParserContext.setObjectPullParser(this);
		parser.next();
	}


	public void setContext(XmlContext context)
	{
		mContext = context;
	}


	/**
	 * Moves forward to the start of the next element that matches the given type and path.
	 * 
	 * @return <code>true</code> if there is such an element, false otherwise.
	 * @throws XmlPullParserException
	 * @throws XmlObjectPullParserException
	 * @throws IOException
	 */
	public <T> boolean moveToNext(ElementDescriptor<T> type, XmlPath path) throws XmlPullParserException, XmlObjectPullParserException, IOException
	{
		pullInternal(type, null, path, true, false);
		return !isEndOfDocument();
	}


	/**
	 * Moves forward to the start of the next element that matches the given type and path without leaving the current sub-tree. If there is no other element of
	 * that type in the current sub-tree, this mehtod will stop at the closing tab current sub-tree. Calling this methods with the same parameters won't get you
	 * any further.
	 * 
	 * @return <code>true</code> if there is such an element, false otherwise.
	 * @throws XmlPullParserException
	 * @throws XmlObjectPullParserException
	 * @throws IOException
	 */
	public <T> boolean moveToNextSibling(ElementDescriptor<T> type, XmlPath path) throws XmlPullParserException, XmlObjectPullParserException, IOException
	{
		return pullInternal(type, null, path, true, true) != null || mParser.getDepth() == path.length() + 1;
	}


	/**
	 * Return whether the end of the document has been reached.
	 * 
	 * @return <code>true</code> if the end of the document has been reached.
	 * @throws XmlPullParserException
	 */
	public boolean isEndOfDocument() throws XmlPullParserException
	{
		return mParser.getEventType() == XmlPullParser.END_DOCUMENT;
	}


	/**
	 * Returns the qualified name of the current element.
	 * 
	 * @return
	 */
	public QualifiedName getCurrentElementQualifiedName()
	{
		XmlPullParser parser = mParser;
		return QualifiedName.get(parser.getNamespace(), parser.getName());
	}


	/**
	 * Returns an {@link ElementDescriptor} for the current element. When called from any method of an {@link IObjectBuilder} this is guaranteed to be the same
	 * as the passed {@link ElementDescriptor}.
	 * 
	 * @return The {@link ElementDescriptor} that will be used to build an object for this element.
	 */
	public ElementDescriptor<?> getCurrentElementDescriptor()
	{
		return mCurrentElementDescriptorPath.peek();
	}


	/**
	 * Returns the current depth in the XML element tree. When called from any method of an {@link IObjectBuilder} this is guaranteed to be the depth of the
	 * current element.
	 * 
	 * @return The current depth.
	 */
	public int getCurrentDepth()
	{
		return mCurrentElementDescriptorPath.length();
	}


	/**
	 * Pull the next object of the given type from the XML stream. If the current position is within such an object the current object is returned.
	 * 
	 * @param type
	 * @param recycle
	 * @param path
	 * @return
	 * @throws XmlPullParserException
	 * @throws IOException
	 * @throws XmlObjectPullParserException
	 */
	public <T> T pull(ElementDescriptor<T> type, T recycle, XmlPath path) throws XmlPullParserException, IOException, XmlObjectPullParserException
	{
		return pullInternal(type, recycle, path, false, false);
	}


	@SuppressWarnings("unchecked")
	private <T, U, V> T pullInternal(ElementDescriptor<T> type, T recycle, XmlPath path, boolean stopOnStartTag, boolean stopOnLeaveSubTree)
		throws XmlPullParserException, IOException, XmlObjectPullParserException
	{
		if (type.getContext() != mContext && type.getContext() != ElementDescriptor.DEFAULT_CONTEXT)
		{
			throw new IllegalArgumentException("type is from an invalid context");
		}

		// cache some fields locally
		ParserContext parserContext = mParserContext;
		XmlPullParser parser = mParser;
		IObjectBuilder<?> currentBuilder = mCurrentBuilder;
		XmlPath currentPath = mCurrentElementDescriptorPath;
		LinkedList<Object> objectStack = mObjectStack;
		ElementDescriptor<?> currentElementDescriptor = mCurrentElementDescriptorPath.peek();

		// we ignore all elements below this depth
		int ignoreDepth = Integer.MAX_VALUE;

		// the object we're going to recycle
		Object recycled = recycle;

		// the current object we're working with
		Object currentObject = objectStack.peek();

		while (true)
		{
			int next = parser.getEventType();
			int currentDepth = parser.getDepth();
			switch (next)
			{
				case XmlPullParser.START_TAG:
				{
					if (currentDepth < ignoreDepth)
					{
						ElementDescriptor<?> nextClass = ElementDescriptor.get(QualifiedName.get(parser.getNamespace(), parser.getName()),
							currentElementDescriptor, mContext);
						if (nextClass != null)
						{
							currentElementDescriptor = nextClass;
							if (stopOnStartTag && type == currentElementDescriptor && path.matches(currentPath))
							{
								return null;
							}

							if (recycled == null)
							{
								// try to get an object to recycle from the parser context
								recycled = parserContext.getRecycled(currentElementDescriptor);
							}

							// append the descriptor to the current path to ensure it's already present when we get the currentObject
							currentPath.append(currentElementDescriptor);

							currentBuilder = mCurrentBuilder = currentElementDescriptor.builder;
							currentObject = ((IObjectBuilder<V>) currentBuilder).get((ElementDescriptor<V>) currentElementDescriptor, (V) recycled,
								parserContext);

							recycled = null;

							// pass all attributes to the builder
							for (int i = 0, count = parser.getAttributeCount(); i < count; ++i)
							{
								currentObject = ((IObjectBuilder<V>) currentBuilder).update((ElementDescriptor<V>) currentElementDescriptor, (V) currentObject,
									QualifiedName.get(parser.getAttributeNamespace(i), parser.getAttributeName(i)), parser.getAttributeValue(i), parserContext);
							}

							objectStack.addFirst(currentObject);
						}
						else
						{
							ignoreDepth = currentDepth;
						}
					}
					break;
				}
				case XmlPullParser.END_TAG:
				{
					if (currentDepth < ignoreDepth)
					{
						if (stopOnLeaveSubTree && currentDepth - 1 < path.length())
						{
							// we're about to leave the current sub-tree, stop right now
							return null;
						}

						// finalize the current object, which now becomes the child object of it's parent
						V childObject = ((IObjectBuilder<V>) currentBuilder).finish((ElementDescriptor<V>) currentElementDescriptor, (V) currentObject,
							parserContext);
						ElementDescriptor<V> childClass = (ElementDescriptor<V>) currentElementDescriptor;

						// remove child from the stack
						currentPath.pop();
						objectStack.removeFirst();

						// get parent object
						currentElementDescriptor = currentPath.peek();
						currentObject = objectStack.peek();

						if (currentElementDescriptor != null)
						{
							currentBuilder = mCurrentBuilder = currentElementDescriptor.builder;
						}

						if (type == childClass && !stopOnStartTag && currentPath.matches(path))
						{
							parser.next();
							return (T) childObject;
						}
						else
						{
							Object old = currentObject;
							currentObject = ((IObjectBuilder<U>) currentBuilder).update((ElementDescriptor<U>) currentElementDescriptor, (U) currentObject,
								childClass, childObject, parserContext);
							if (currentObject != old)
							{
								// update the object stack with the new instance
								objectStack.set(0, currentObject);
							}
						}
					}
					else if (currentDepth == ignoreDepth)
					{
						// we're back at the depth of the current object
						ignoreDepth = Integer.MAX_VALUE;
					}
					break;
				}
				case XmlPullParser.TEXT:
				{
					if (currentDepth < ignoreDepth)
					{
						// update current object with text value
						Object old = currentObject;
						currentObject = ((IObjectBuilder<V>) currentBuilder).update((ElementDescriptor<V>) currentElementDescriptor, (V) currentObject,
							parser.getText(), parserContext);
						if (currentObject != old)
						{
							// update the object stack with the new instance
							objectStack.set(0, currentObject);
						}
					}
					break;
				}
				case XmlPullParser.END_DOCUMENT:
				{
					return null;
				}
			}
			parser.next();
		}
	}
}
