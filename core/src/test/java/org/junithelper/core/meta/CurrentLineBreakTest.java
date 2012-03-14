package org.junithelper.core.meta;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class CurrentLineBreakTest {

    @Test
    public void type() throws Exception {
        assertThat(CurrentLineBreak.class, notNullValue());
    }

    @Test
    public void toString_A$() throws Exception {
        String actual = CurrentLineBreak.CRLF.toString();
        String expected = "CRLF";
        assertThat(actual, is(equalTo(expected)));
    }

}
