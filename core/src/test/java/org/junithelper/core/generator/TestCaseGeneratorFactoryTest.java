package org.junithelper.core.generator;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junithelper.core.config.Configuration;
import org.junithelper.core.meta.CurrentLineBreak;

public class TestCaseGeneratorFactoryTest {

    @Test
    public void type() throws Exception {
        assertThat(TestCaseGeneratorFactory.class, notNullValue());
    }

    @Test
    public void create_A$Configuration$LineBreakProvider() throws Exception {
        Configuration config = new Configuration();
        LineBreakProvider lineBreakProvider = new LineBreakProvider(config, CurrentLineBreak.CRLF);
        TestCaseGenerator actual = TestCaseGeneratorFactory.create(config, lineBreakProvider);
        assertThat(actual, is(notNullValue()));
    }

}
