package org.junithelper.core.config;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConfigurationTest {

    @Test
    public void type() throws Exception {
        assertNotNull(Configuration.class);
    }

    @Test
    public void instantiation() throws Exception {
        Configuration target = new Configuration();
        assertNotNull(target);
    }

}
