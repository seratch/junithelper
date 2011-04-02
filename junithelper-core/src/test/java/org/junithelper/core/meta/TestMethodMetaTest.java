package org.junithelper.core.meta;

import org.junit.Test;

import static org.junit.Assert.*;

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
