package org.junithelper.core.generator;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junithelper.core.config.Configuration;

public class TestCaseGeneratorFactoryTest {

    @Test
    public void type() throws Exception {
        assertThat(TestCaseGeneratorFactory.class, notNullValue());
    }

    @Test
    public void create_A$Configuration() throws Exception {
        Configuration config = null;
        TestCaseGenerator actual = TestCaseGeneratorFactory.create(config);
        assertThat(actual, is(notNullValue()));
    }

}
