package org.junithelper.core.config.extension;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class ExtConfigurationTest {

    @Test
    public void type() throws Exception {
        assertThat(ExtConfiguration.class, notNullValue());
    }

    @Test
    public void instantiation() throws Exception {
        ExtConfiguration target = new ExtConfiguration();
        assertThat(target, notNullValue());
    }

}
