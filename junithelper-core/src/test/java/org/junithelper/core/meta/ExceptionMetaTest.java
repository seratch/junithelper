package org.junithelper.core.meta;

import org.junit.Test;

import static org.junit.Assert.*;

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
