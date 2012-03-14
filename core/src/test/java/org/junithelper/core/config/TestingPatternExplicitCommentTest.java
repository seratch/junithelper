package org.junithelper.core.config;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class TestingPatternExplicitCommentTest {

    @Test
    public void type() throws Exception {
        assertThat(TestingPatternExplicitComment.class, notNullValue());
    }

    @Test
    public void toString_A$() throws Exception {
        TestingPatternExplicitComment target = TestingPatternExplicitComment.ArrangeActAssert;
        String actual = target.toString();
        String expected = "ArrangeActAssert";
        assertThat(actual, is(equalTo(expected)));
    }

}
