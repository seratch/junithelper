package org.junithelper.core.config;

import static org.junit.Assert.*;

import org.junit.Test;

public class JUnitVersionTest {

    @Test
    public void type() throws Exception {
        assertNotNull(JUnitVersion.class);
    }

    @Test
    public void toString_A$() throws Exception {
        String actual = JUnitVersion.version3.toString();
        String expected = "version3";
        assertEquals(expected, actual);
    }

}
