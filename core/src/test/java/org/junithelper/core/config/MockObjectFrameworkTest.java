package org.junithelper.core.config;

import static org.junit.Assert.*;

import org.junit.Test;

public class MockObjectFrameworkTest {

    @Test
    public void type() throws Exception {
        assertNotNull(MockObjectFramework.class);
    }

    @Test
    public void toString_A$() throws Exception {
        String actual = MockObjectFramework.EasyMock.toString();
        String expected = "EasyMock";
        assertEquals(expected, actual);
    }

}
