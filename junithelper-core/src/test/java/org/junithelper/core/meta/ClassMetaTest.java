package org.junithelper.core.meta;

import static org.junit.Assert.*;

import org.junit.Test;

public class ClassMetaTest {

    @Test
    public void type() throws Exception {
        assertNotNull(ClassMeta.class);
    }

    @Test
    public void instantiation() throws Exception {
        ClassMeta target = new ClassMeta();
        assertNotNull(target);
    }

}
