package org.junithelper.core.generator;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junithelper.core.config.Configuration;
import org.junithelper.core.meta.CurrentLineBreak;

public class TestMethodGeneratorFactoryTest {

    @Test
    public void type() throws Exception {
        assertThat(TestMethodGeneratorFactory.class, notNullValue());
    }

    @Test
    public void create_A$Configuration$LineBreakProvider() throws Exception {
        Configuration config = new Configuration();
        LineBreakProvider lineBreakProvider = new LineBreakProvider(config, CurrentLineBreak.CRLF);
        TestMethodGenerator actual = TestMethodGeneratorFactory.create(config, lineBreakProvider);
        assertThat(actual, is(notNullValue()));
    }

}
