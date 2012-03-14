package org.junithelper.core.util;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

public class ThreadUtilTest {

    @Test
    public void type() throws Exception {
        assertNotNull(ThreadUtil.class);
    }

    @Test
    public void sleep_A$long() throws Exception {
        ThreadUtil.sleep(10L);
        ThreadUtil.sleep(0L);
        try {
            ThreadUtil.sleep(-10L);
            fail("Expected exception did not occurred!");
        } catch (IllegalArgumentException expected) {
        }
    }

    @Test
    public void sleep_A$long_longIsMinus1L() throws Exception {
        long millisec = -1L;
        try {
            ThreadUtil.sleep(millisec);
            fail();
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void sleep_A$long_longIs0L() throws Exception {
        long millisec = 0L;
        ThreadUtil.sleep(millisec);
    }

    @Test
    public void sleep_A$long_longIs1L() throws Exception {
        long millisec = 1L;
        ThreadUtil.sleep(millisec);
    }

    @Test
    public void sleep_A$long_longIs2L() throws Exception {
        long millisec = 2L;
        ThreadUtil.sleep(millisec);
    }

    @Test
    public void sleep_A$long_longIsMinValue() throws Exception {
        try {
            long millisec = Long.MIN_VALUE;
            ThreadUtil.sleep(millisec);
            fail();
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    @Ignore
    public void sleep_A$long_longIsMaxValue() throws Exception {
        long millisec = Long.MAX_VALUE;
        ThreadUtil.sleep(millisec);
    }

}
