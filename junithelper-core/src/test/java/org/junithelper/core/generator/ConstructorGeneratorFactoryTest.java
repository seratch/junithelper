package org.junithelper.core.generator;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class ConstructorGeneratorFactoryTest {

    @Test
    public void type() throws Exception {
        assertThat(ConstructorGeneratorFactory.class, notNullValue());
    }

    @Test
    public void create_A$() throws Exception {
        ConstructorGenerator actual = ConstructorGeneratorFactory.create();
        assertThat(actual, is(notNullValue()));
    }

}
