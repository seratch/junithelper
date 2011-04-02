package org.junithelper.core.config;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestingTargetTest {

    @Test
    public void type() throws Exception {
        assertNotNull(TestingTarget.class);
    }

    @Test
    public void instantiation() throws Exception {
        TestingTarget target = new TestingTarget();
        assertNotNull(target);
    }

}
