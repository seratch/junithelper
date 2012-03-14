package org.junithelper.core.meta;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConstructorMetaTest {

    @Test
    public void type() throws Exception {
        assertNotNull(ConstructorMeta.class);
    }

    @Test
    public void instantiation() throws Exception {
        ConstructorMeta target = new ConstructorMeta();
        assertNotNull(target);
    }

}
