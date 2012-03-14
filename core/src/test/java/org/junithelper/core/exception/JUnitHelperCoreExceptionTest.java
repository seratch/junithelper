package org.junithelper.core.exception;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class JUnitHelperCoreExceptionTest {

    @Test
    public void type() throws Exception {
        assertThat(JUnitHelperCoreException.class, notNullValue());
    }

    @Test
    public void instantiation() throws Exception {
        JUnitHelperCoreException target = new JUnitHelperCoreException();
        assertThat(target, notNullValue());
    }

}
