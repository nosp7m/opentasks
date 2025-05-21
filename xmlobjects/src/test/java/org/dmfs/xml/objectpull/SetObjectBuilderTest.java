package org.dmfs.xml.objectpull;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.dmfs.xmlobjects.ElementDescriptor;
import org.dmfs.xmlobjects.QualifiedName;
import org.dmfs.xmlobjects.XmlContext;
import org.dmfs.xmlobjects.builder.SetObjectBuilder;
import org.dmfs.xmlobjects.builder.StringObjectBuilder;
import org.dmfs.xmlobjects.pull.ParserContext;
import org.dmfs.xmlobjects.pull.XmlObjectPullParserException;
import org.junit.Test;


public class SetObjectBuilderTest
{
	XmlContext testContext = new XmlContext();

	ElementDescriptor<String> childElement = ElementDescriptor.register("child", StringObjectBuilder.INSTANCE, testContext);
	ElementDescriptor<Set<String>> setElement = ElementDescriptor.register("set", new SetObjectBuilder<String>(childElement, true), testContext);
	ElementDescriptor<Set<String>> setElementNonNull = ElementDescriptor.register("set-non-null", new SetObjectBuilder<String>(childElement, false),
		testContext);


	@Test
	public void testElementDescriptorNull() throws XmlObjectPullParserException
	{
		SetObjectBuilder<String> builder = new SetObjectBuilder<String>(childElement, true);
		ParserContext pc = new ParserContext();

		// test get without recycled set
		assertEquals(makeSet(), builder.get(setElement, null, pc));

		// test get with recycled set, that's not empty
		assertEquals(makeSet(), builder.get(setElement, makeSet("1", "2", "3"), pc));

		// test adding an attribute
		assertEquals(makeSet(), builder.update(setElement, makeSet(), QualifiedName.get("testattr"), "testvalue", pc));

		// test adding a text
		assertEquals(makeSet(), builder.update(setElement, makeSet(), "text value", pc));
		assertEquals(makeSet("1"), builder.update(setElement, makeSet("1"), "text value", pc));
		assertEquals(makeSet("1", "abc"), builder.update(setElement, makeSet("1", "abc"), "text value", pc));

		// test adding child elements
		assertEquals(makeSet("1"), builder.update(setElement, makeSet(), childElement, "1", pc));
		assertEquals(makeSet("1", "2"), builder.update(setElement, makeSet("1"), childElement, "2", pc));
		assertEquals(makeSet("1", "abc", "text value"), builder.update(setElement, makeSet("1", "abc"), childElement, "text value", pc));

		// test adding null elements
		assertEquals(makeSet((String) null), builder.update(setElement, makeSet(), childElement, null, pc));
		assertEquals(makeSet("1", null), builder.update(setElement, makeSet("1"), childElement, null, pc));
		assertEquals(makeSet("1", "abc", null), builder.update(setElement, makeSet("1", "abc"), childElement, null, pc));
	}


	@Test
	public void testElementDescriptorNonNull() throws XmlObjectPullParserException
	{
		SetObjectBuilder<String> builder = new SetObjectBuilder<String>(childElement, false);
		ParserContext pc = new ParserContext();

		// test get without recycled set
		assertEquals(makeSet(), builder.get(setElementNonNull, null, pc));

		// test get with recycled set, that's not empty
		assertEquals(makeSet(), builder.get(setElementNonNull, makeSet("1", "2", "3"), pc));

		// test adding an attribute
		assertEquals(makeSet(), builder.update(setElementNonNull, makeSet(), QualifiedName.get("testattr"), "testvalue", pc));

		// test adding a text
		assertEquals(makeSet(), builder.update(setElementNonNull, makeSet(), "text value", pc));
		assertEquals(makeSet("1"), builder.update(setElementNonNull, makeSet("1"), "text value", pc));
		assertEquals(makeSet("1", "abc"), builder.update(setElementNonNull, makeSet("1", "abc"), "text value", pc));

		// test adding child elements
		assertEquals(makeSet("1"), builder.update(setElementNonNull, makeSet(), childElement, "1", pc));
		assertEquals(makeSet("1", "2"), builder.update(setElementNonNull, makeSet("1"), childElement, "2", pc));
		assertEquals(makeSet("1", "abc", "text value"), builder.update(setElementNonNull, makeSet("1", "abc"), childElement, "text value", pc));

		// test adding null elements
		assertEquals(makeSet(), builder.update(setElementNonNull, makeSet(), childElement, null, pc));
		assertEquals(makeSet("1"), builder.update(setElementNonNull, makeSet("1"), childElement, null, pc));
		assertEquals(makeSet("1", "abc"), builder.update(setElementNonNull, makeSet("1", "abc"), childElement, null, pc));
	}


	private static Set<String> makeSet(String... strings)
	{
		return new HashSet<String>(Arrays.asList(strings));
	}
}
