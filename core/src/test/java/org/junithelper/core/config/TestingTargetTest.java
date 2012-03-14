package org.junithelper.core.config;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

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

    @Test
    public void getRegexpArrayForExclusion_A$() throws Exception {
        TestingTarget target = new TestingTarget();
        String[] actual = target.getRegexpArrayForExclusion();
        String[] expected = new String[] { "" };
        assertThat(actual.length, is(equalTo(expected.length)));
    }

}
