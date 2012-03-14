package org.junithelper.core.meta;

import static org.junit.Assert.*;

import org.junit.Test;

public class ExceptionMetaTest {

    @Test
    public void type() throws Exception {
        assertNotNull(ExceptionMeta.class);
    }

    @Test
    public void instantiation() throws Exception {
        ExceptionMeta target = new ExceptionMeta();
        assertNotNull(target);
    }

}
