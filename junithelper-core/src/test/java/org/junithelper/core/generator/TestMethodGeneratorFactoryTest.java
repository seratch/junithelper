package org.junithelper.core.generator;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junithelper.core.config.Configuration;

public class TestMethodGeneratorFactoryTest {

    @Test
    public void type() throws Exception {
        assertThat(TestMethodGeneratorFactory.class, notNullValue());
    }

    @Test
    public void create_A$Configuration() throws Exception {
        Configuration config = null;
        TestMethodGenerator actual = TestMethodGeneratorFactory.create(config);
        assertThat(actual, is(notNullValue()));
    }

}
