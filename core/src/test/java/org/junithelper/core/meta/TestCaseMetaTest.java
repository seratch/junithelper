package org.junithelper.core.meta;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestCaseMetaTest {

    @Test
    public void type() throws Exception {
        assertNotNull(TestCaseMeta.class);
    }

    @Test
    public void instantiation() throws Exception {
        TestCaseMeta target = new TestCaseMeta();
        assertNotNull(target);
    }

}
