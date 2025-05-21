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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dmfs.xmlobjects.ElementDescriptor;
import org.dmfs.xmlobjects.builder.IObjectBuilder;
import org.xmlpull.v1.XmlPullParser;


/**
 * Represents the current context of the parser. Subclass this to provide additional information to {@link IObjectBuilder} instances.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public class ParserContext
{

	/**
	 * A cache of recycled objects. We cache one object per {@link ElementDescriptor}.
	 */
	private final Map<ElementDescriptor<?>, Object> mRecycledObjects = new HashMap<ElementDescriptor<?>, Object>(32);

	private List<Map<ElementDescriptor<?>, Object>> mState;

	/**
	 * The current {@link XmlPullParser} instance.
	 */
	private XmlPullParser mParser;

	private XmlObjectPull mObjectPullParser;


	/**
	 * Set the current {@link XmlObjectPull} parser this instance belongs to.
	 * 
	 * @param objectPullParser
	 */
	void setObjectPullParser(XmlObjectPull objectPullParser)
	{
		mObjectPullParser = objectPullParser;
	}


	/**
	 * Set the current {@link XmlPullParser} instance.
	 * 
	 * @param parser
	 *            The current {@link XmlPullParser}.
	 */
	void setXmlPullParser(XmlPullParser parser)
	{
		mParser = parser;
	}


	/**
	 * Returns the current {@link XmlPullParser} instance. You MUST NOT change the state of the parser, so don't call {@link XmlPullParser#next()},
	 * {@link XmlPullParser#nextTag()}, {@link XmlPullParser#nextText()} or {@link XmlPullParser#nextToken()}.
	 * 
	 * @return The current {@link XmlPullParser} instance.
	 */
	public XmlPullParser getXmlPullParser()
	{
		return mParser;
	}


	/**
	 * Recycle the given object. The basic implementation will store only one recycled object instance per {@link ElementDescriptor} in the recycler.
	 * 
	 * @param descriptor
	 *            The {@link ElementDescriptor} of the XML element
	 * @param object
	 *            The object to recycle.
	 */
	public <T> void recycle(ElementDescriptor<T> descriptor, T object)
	{
		if (object != null)
		{
			mRecycledObjects.put(descriptor, object);
		}
	}


	/**
	 * Get a recycled instance. If there was no instance in the recycler this method returns <code>null</code>.
	 * <p>
	 * <stong>Note:</strong> The object is returned exactly as passed to {@link #recycle(ElementDescriptor, Object)}, so you need to ensure you reset the state
	 * yourself.
	 * </p>
	 * 
	 * @param descriptor
	 *            The {@link ElementDescriptor} for which to return a recycled object.
	 * @return a recycled object or <code>null</code> if there is none.
	 */
	@SuppressWarnings("unchecked")
	public <T> T getRecycled(ElementDescriptor<T> descriptor)
	{
		// we can safely cast here, because we know that recycle always puts the right type
		return (T) mRecycledObjects.remove(descriptor);
	}


	/**
	 * Stores a state object for the current element and depth. Only elements with the same {@link ElementDescriptor} at the same depth can retrieve this state
	 * object through {@link #getState()}.
	 * <p>
	 * Builders should recycle the state object whenever possible.
	 * </p>
	 * 
	 * @param object
	 *            The state object to store or <code>null</code> to free this object.
	 */
	public void setState(Object object)
	{
		Map<ElementDescriptor<?>, Object> depthStateMap = getDepthStateMap(mObjectPullParser.getCurrentDepth(), true);

		ElementDescriptor<?> currentDescriptor = mObjectPullParser.getCurrentElementDescriptor();
		depthStateMap.put(currentDescriptor, object);
	}


	/**
	 * Get the state object of the current element. The object must have been set by {@link #setState(Object)}.
	 * 
	 * @return An {@link Object} or <code>null</code> if there is no state object.
	 */
	public Object getState()
	{
		Map<ElementDescriptor<?>, Object> stateMap = getDepthStateMap(mObjectPullParser.getCurrentDepth(), false);
		if (stateMap == null)
		{
			return null;
		}

		ElementDescriptor<?> currentDescriptor = mObjectPullParser.getCurrentElementDescriptor();
		return stateMap.get(currentDescriptor);
	}


	/**
	 * Return the element state map for the given depth, creating non-existing maps if required.
	 * 
	 * @param depth
	 *            The depth for which to retrieve the state map.
	 * @param create
	 *            <code>true</code> to create a new map if it doesn't exist.
	 * @return
	 */
	private Map<ElementDescriptor<?>, Object> getDepthStateMap(int depth, boolean create)
	{
		if (mState == null)
		{
			mState = new ArrayList<Map<ElementDescriptor<?>, Object>>(Math.max(16, depth + 8));
		}

		// fill array list with null values up to the current depth
		while (depth > mState.size())
		{
			mState.add(null);
		}

		Map<ElementDescriptor<?>, Object> map = mState.get(depth - 1 /* depth is at least 1 */);

		if (!create || map != null)
		{
			return map;
		}

		// map is null and we shall create it
		map = new HashMap<ElementDescriptor<?>, Object>(8);
		mState.set(depth - 1, map);
		return map;
	}

}
