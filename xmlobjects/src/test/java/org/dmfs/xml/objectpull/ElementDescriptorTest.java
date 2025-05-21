package org.dmfs.xml.objectpull;

import static org.junit.Assert.assertEquals;

import org.dmfs.xmlobjects.ElementDescriptor;
import org.dmfs.xmlobjects.QualifiedName;
import org.dmfs.xmlobjects.XmlContext;
import org.dmfs.xmlobjects.builder.IntegerObjectBuilder;
import org.dmfs.xmlobjects.builder.StringObjectBuilder;
import org.junit.Before;
import org.junit.Test;


public class ElementDescriptorTest
{

	XmlContext context;


	@Before
	public void setUp() throws Exception
	{
		context = new XmlContext();
	}


	/**
	 * This test checks if {@link ElementDescriptor#get(QualifiedName, XmlContext)} and
	 * {@link ElementDescriptor#get(QualifiedName, ElementDescriptor, XmlContext)} always retun the correct value.
	 */
	@Test
	public void testGet()
	{
		ElementDescriptor<String> testDescriptor1 = ElementDescriptor.register("test", StringObjectBuilder.INSTANCE, context);
		assertEquals(testDescriptor1, ElementDescriptor.get(QualifiedName.get("test"), context));

		ElementDescriptor<String> testDescriptor2 = ElementDescriptor.register("test2", StringObjectBuilder.INSTANCE, context);
		assertEquals(testDescriptor2, ElementDescriptor.get(QualifiedName.get("test2"), context));

		// get test1 again
		assertEquals(testDescriptor1, ElementDescriptor.get(QualifiedName.get("test"), context));

		// register test2 again, but with a different type and as a child of test1
		ElementDescriptor<Integer> testDescriptor2b = ElementDescriptor.registerWithParents("test2", IntegerObjectBuilder.INSTANCE, testDescriptor1);

		// make sure we get the right element for the given context
		assertEquals(testDescriptor2, ElementDescriptor.get(QualifiedName.get("test2"), context));
		assertEquals(testDescriptor2b, ElementDescriptor.get(QualifiedName.get("test2"), testDescriptor1, context));
	}

}
