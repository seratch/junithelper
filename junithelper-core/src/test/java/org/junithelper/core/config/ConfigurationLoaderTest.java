package org.junithelper.core.config;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.InputStream;

import org.junit.Test;
import org.junithelper.core.exception.JUnitHelperCoreException;
import org.junithelper.core.util.IOUtil;

public class ConfigurationLoaderTest {

    @Test
    public void type() throws Exception {
        assertNotNull(ConfigurationLoader.class);
    }

    @Test
    public void instantiation() throws Exception {
        ConfigurationLoader target = new ConfigurationLoader();
        assertNotNull(target);
    }

    @Test
    public void load_A$String() throws Exception {
        ConfigurationLoader target = new ConfigurationLoader();
        // given
        String filepath = "release/junithelper-config.properties";
        // when
        Configuration actual = target.load(filepath);
        // then
        assertNotNull(actual);
        assertThat(actual.isExtensionEnabled, is(equalTo(false)));
    }

    @Test
    public void load_A$String_T$Exception() throws Exception {
        ConfigurationLoader target = new ConfigurationLoader();
        // given
        String filepath = null;
        try {
            // when
            target.load(filepath);
            fail("Expected exception was not thrown!");
        } catch (Exception e) {
            // then
        }
    }

    @Test
    public void load_A$InputStream() throws Exception {
        ConfigurationLoader target = new ConfigurationLoader();
        // given
        InputStream is = IOUtil.getResourceAsStream("junithelper-config.properties");
        // when
        Configuration actual = target.load(is);
        // then
        assertNotNull(actual);
    }

    @Test
    public void load_A$InputStream_T$Exception() throws Exception {
        ConfigurationLoader target = new ConfigurationLoader();
        // given
        InputStream is = null;
        // e.g. : given(mocked.called()).willReturn(1);
        try {
            // when
            target.load(is);
            fail("Expected exception was not thrown!");
        } catch (Exception e) {
            // then
        }
    }

    @Test
    public void load_A$String_StringIsNull() throws Exception {
        ConfigurationLoader target = new ConfigurationLoader();
        String filepath = null;
        try {
            target.load(filepath);
            fail();
        } catch (JUnitHelperCoreException e) {
        }
    }

    @Test
    public void load_A$String_StringIsEmpty() throws Exception {
        ConfigurationLoader target = new ConfigurationLoader();
        String filepath = "";
        try {
            target.load(filepath);
            fail();
        } catch (FileNotFoundException e) {
        }
    }

}
