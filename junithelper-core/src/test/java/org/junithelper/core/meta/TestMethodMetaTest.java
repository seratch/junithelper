package org.junithelper.core.meta;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestMethodMetaTest {

    @Test
    public void type() throws Exception {
        assertNotNull(TestMethodMeta.class);
    }

    @Test
    public void instantiation() throws Exception {
        TestMethodMeta target = new TestMethodMeta();
        assertNotNull(target);
    }

}
