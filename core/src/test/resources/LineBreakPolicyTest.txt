package org.junithelper.core.config;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class LineBreakPolicyTest {

    @Test
    public void type() throws Exception {
        assertThat(LineBreakPolicy.class, notNullValue());
    }

    @Test
    public void toString_A$() throws Exception {
        String actual = LineBreakPolicy.forceCRLF.toString();
        String expected = "forceCRLF";
        assertThat(actual, is(equalTo(expected)));
    }

}
