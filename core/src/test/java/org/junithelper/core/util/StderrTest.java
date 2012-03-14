package org.junithelper.core.util;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junithelper.core.exception.JUnitHelperCoreException;

public class StderrTest {

    @Test
    public void type() throws Exception {
        assertNotNull(Stderr.class);
    }

    @Test
    public void printf_A$String$ObjectArray() throws Exception {
        String format = "%s";
        Object[] values = new Object[] { "aaaa" };
        Stderr.printf(format, values);
    }

    @Test
    public void p_A$String() throws Exception {
        String str = "test";
        Stderr.p(str);
    }

    @Test
    public void printf_A$String$ObjectArray_StringIsNull() throws Exception {
        String format = null;
        Object[] values = new Object[] {};
        try {
            Stderr.printf(format, values);
            fail();
        } catch (JUnitHelperCoreException e) {
        }
    }

    @Test
    public void printf_A$String$ObjectArray_StringIsEmpty() throws Exception {
        String format = "";
        Object[] values = new Object[] {};
        try {
            Stderr.printf(format, values);
            fail();
        } catch (JUnitHelperCoreException e) {
        }
    }

    @Test
    public void p_A$String_StringIsNull() throws Exception {
        String str = null;
        Stderr.p(str);
    }

    @Test
    public void p_A$String_StringIsEmpty() throws Exception {
        String str = "";
        Stderr.p(str);
    }

}
