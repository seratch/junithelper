package org.junithelper.core.constant;

import static org.junit.Assert.*;

import org.junit.Test;

public class StringValueTest {

    @Test
    public void type() throws Exception {
        assertNotNull(StringValue.class);
    }

    @Test
    public void instantiation() throws Exception {
        StringValue target = new StringValue();
        assertNotNull(target);
    }

}
