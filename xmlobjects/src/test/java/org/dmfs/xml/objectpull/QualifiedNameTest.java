package org.dmfs.xml.objectpull;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.dmfs.xmlobjects.QualifiedName;
import org.junit.Test;


/**
 * Test {@link QualifiedName}s.
 * <p>
 * The test makes sure that {@link QualifiedName#get(String)} and {@link QualifiedName#get(String, String)} always return the same instance for the same
 * parameters and that the instances are different for different parameters.
 * </p>
 * <p>
 * The test assumes that the results are independed of the actual parameter values, since it's impossible to test with every combination of characters.
 * </p>
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public class QualifiedNameTest
{

	@Test(expected = IllegalArgumentException.class)
	public void testGetNull1()
	{
		QualifiedName.get(null);
	}


	@Test(expected = IllegalArgumentException.class)
	public void testGetNull2()
	{
		QualifiedName.get("namespace", null);
	}


	@Test(expected = IllegalArgumentException.class)
	public void testGetNull4()
	{
		QualifiedName.get(null, null);
	}


	@Test
	public void testGet()
	{
		// get a qualified name without namespace and verify name and namespace
		QualifiedName n1 = QualifiedName.get("test1");
		assertEquals("test1", n1.name);
		assertNull(n1.namespace);

		// get a qualified name with namespace and verify name and namespace
		QualifiedName n2 = QualifiedName.get("namespace2", "test2");
		assertEquals("test2", n2.name);
		assertEquals("namespace2", n2.namespace);

		// verify equals() returns false for different qualified names
		assertFalse(n1.equals(n2));
		assertFalse(n2.equals(n1));

		// verify equals() returns true for itself
		assertTrue(n1.equals(n1));
		assertTrue(n2.equals(n2));

		// get the same qualified name again
		QualifiedName n3 = QualifiedName.get("test1");
		// verfiy name and namespace
		assertEquals("test1", n3.name);
		assertNull(n3.namespace);
		// verify equals() returns true
		assertTrue(n3.equals(n1));
		assertTrue(n1.equals(n3));
		// verify it's actually the same object
		assertSame(n1, n3);

		// get the same qualified name again, but use the other get method
		QualifiedName n3b = QualifiedName.get(null, "test1");
		// verfiy name and namespace
		assertEquals("test1", n3b.name);
		assertNull(n3b.namespace);
		// verify equals() returns true
		assertTrue(n3b.equals(n1));
		assertTrue(n1.equals(n3b));
		// verify it's actually the same object
		assertSame(n1, n3b);

		// get the same qualified name again
		QualifiedName n4 = QualifiedName.get("namespace2", "test2");
		// verfiy name and namespace
		assertEquals("test2", n4.name);
		assertEquals("namespace2", n4.namespace);
		// verify equals() returns true
		assertTrue(n4.equals(n2));
		assertTrue(n2.equals(n4));
		// verify it's actually the same object
		assertSame(n2, n4);

		// get qualified names that are different in name or namespace
		// also check that they don't equal the provious ones
		QualifiedName n5 = QualifiedName.get("test2");
		assertEquals("test2", n5.name);
		assertNull(n5.namespace);
		QualifiedName n6 = QualifiedName.get("namespace3", "test2");
		assertEquals("test2", n6.name);
		assertEquals("namespace3", n6.namespace);
		QualifiedName n7 = QualifiedName.get("namespace2", "test3");
		assertEquals("test3", n7.name);
		assertEquals("namespace2", n7.namespace);
		QualifiedName n8 = QualifiedName.get("namespace3", "test3");
		assertEquals("test3", n8.name);
		assertEquals("namespace3", n8.namespace);

		assertNotSame(n1, n5);
		assertNotSame(n1, n6);
		assertNotSame(n1, n7);
		assertNotSame(n1, n8);
		assertNotSame(n2, n5);
		assertNotSame(n2, n6);
		assertNotSame(n2, n7);
		assertNotSame(n2, n8);
		assertNotSame(n5, n6);
		assertNotSame(n5, n7);
		assertNotSame(n5, n8);
		assertNotSame(n6, n7);
		assertNotSame(n6, n8);
		assertNotSame(n7, n8);

		assertFalse(n1.equals(n5));
		assertFalse(n1.equals(n6));
		assertFalse(n1.equals(n7));
		assertFalse(n1.equals(n8));
		assertFalse(n2.equals(n5));
		assertFalse(n2.equals(n6));
		assertFalse(n2.equals(n7));
		assertFalse(n2.equals(n8));
		assertFalse(n5.equals(n6));
		assertFalse(n5.equals(n7));
		assertFalse(n5.equals(n8));
		assertFalse(n6.equals(n7));
		assertFalse(n6.equals(n8));
		assertFalse(n7.equals(n8));
	}
}
