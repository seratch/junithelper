package org.junithelper.core.util;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junithelper.core.exception.JUnitHelperCoreException;

public class StdoutTest {

    @Test
    public void type() throws Exception {
        assertNotNull(Stdout.class);
    }

    @Test
    public void p_A$String() throws Exception {
        // given
        String str = "aaa";
        // when
        Stdout.p(str);
        // then
    }

    @Test
    public void printf_A$String$ObjectArray() throws Exception {
        // given
        String format = "%02d";
        Object[] values = new Object[] { 2 };
        // when
        Stdout.printf(format, values);
        // then
    }

    @Test
    public void printf_A$String$ObjectArray_StringIsNull() throws Exception {
        String format = null;
        Object[] values = new Object[] {};
        try {
            Stdout.printf(format, values);
            fail();
        } catch (JUnitHelperCoreException e) {
        }
    }

    @Test
    public void printf_A$String$ObjectArray_StringIsEmpty() throws Exception {
        String format = "";
        Object[] values = new Object[] {};
        try {
            Stdout.printf(format, values);
            fail();
        } catch (JUnitHelperCoreException e) {
        }
    }

    @Test
    public void p_A$String_StringIsNull() throws Exception {
        String str = null;
        Stdout.p(str);
    }

    @Test
    public void p_A$String_StringIsEmpty() throws Exception {
        String str = "";
        Stdout.p(str);
    }

}
