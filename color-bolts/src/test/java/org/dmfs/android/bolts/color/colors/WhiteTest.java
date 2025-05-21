package org.dmfs.android.bolts.color.colors;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


/**
 * @author Marten Gajda
 */
public class WhiteTest
{
    @Test
    public void testArgb() throws Exception
    {
        assertThat(White.INSTANCE.argb(), is(0xffffffff));
        assertThat(new White().argb(), is(0xffffffff));
    }

}