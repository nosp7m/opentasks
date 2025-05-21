package org.dmfs.jems.predicate.composite;

import org.dmfs.jems.predicate.Predicate;
import org.junit.Test;

import static org.dmfs.jems.hamcrest.matchers.predicate.PredicateMatcher.satisfiedBy;
import static org.dmfs.jems.mockito.doubles.TestDoubles.failingMock;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;


/**
 * Test {@link Not}.
 *
 * @author Marten Gajda
 */
public class NotTest
{
    @Test
    public void testSatisfiedBy()
    {
        Predicate<String> mockPredicate = failingMock(Predicate.class);
        doReturn(true).when(mockPredicate).satisfiedBy("match");
        doReturn(false).when(mockPredicate).satisfiedBy("mismatch");

        assertThat(new Not<>(mockPredicate), is(not(satisfiedBy("match"))));
        assertThat(new Not<>(mockPredicate), is(satisfiedBy("mismatch")));
    }


    @Test
    public void testEqualsCtor()
    {
        assertThat(new Not<>("a"), is(not(satisfiedBy("a"))));
        assertThat(new Not<>("a"), is(satisfiedBy("b")));
    }

}