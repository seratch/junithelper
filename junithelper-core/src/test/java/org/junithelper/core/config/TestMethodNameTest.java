package org.junithelper.core.config;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestMethodNameTest {

    @Test
    public void type() throws Exception {
        assertNotNull(TestMethodName.class);
    }

    @Test
    public void instantiation() throws Exception {
        TestMethodName target = new TestMethodName();
        assertNotNull(target);
    }

}
