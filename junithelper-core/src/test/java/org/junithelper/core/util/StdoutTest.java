package org.junithelper.core.util;

import org.junit.Test;

import static org.junit.Assert.*;

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
        Object[] values = new Object[]{2};
        // when
        Stdout.printf(format, values);
        // then
    }

}
