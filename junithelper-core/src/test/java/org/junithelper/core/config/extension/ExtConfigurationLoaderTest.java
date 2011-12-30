package org.junithelper.core.config.extension;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.InputStream;

import org.junit.Test;
import org.junithelper.core.exception.JUnitHelperCoreException;
import org.junithelper.core.util.IOUtil;

public class ExtConfigurationLoaderTest {

    @Test
    public void type() throws Exception {
        assertThat(ExtConfigurationLoader.class, notNullValue());
    }

    @Test
    public void instantiation() throws Exception {
        ExtConfigurationLoader target = new ExtConfigurationLoader();
        assertThat(target, notNullValue());
    }

    @Test
    public void load_A$InputStream() throws Exception {
        ExtConfigurationLoader loader = new ExtConfigurationLoader();
        // given
        InputStream inputStream = IOUtil.getResourceAsStream("junithelper-extension.xml");
        // when
        ExtConfiguration actual = loader.load(inputStream);
        // then
        assertThat(actual.extArgs.size(), is(equalTo(4)));
        assertThat(actual.extArgs.get(0).canonicalClassName, is(equalTo("java.util.Calendar")));
        assertThat(actual.extArgs.get(0).importList.size(), is(equalTo(3)));
        assertThat(actual.extArgs.get(0).patterns.size(), is(equalTo(3)));
        assertThat(actual.extReturns.size(), is(equalTo(1)));
        assertThat(actual.extReturns.get(0).asserts.size(), is(equalTo(2)));
    }

    @Test
    public void load_A$String() throws Exception {
        ExtConfigurationLoader target = new ExtConfigurationLoader();
        // given
        String filepath = "./src/test/resources/junithelper-extension.xml";
        // when
        ExtConfiguration actual = target.load(filepath);
        // then
        assertThat(actual, notNullValue());
        assertThat(actual.extArgs.size(), is(equalTo(4)));
    }

    @Test
    public void load_A$String_StringIsNull() throws Exception {
        ExtConfigurationLoader target = new ExtConfigurationLoader();
        String filepath = null;
        try {
            target.load(filepath);
            fail("filepath is null, but it does not throw exception.");
        } catch (JUnitHelperCoreException e) {
        }
    }

    @Test
    public void load_A$String_StringIsEmpty() throws Exception {
        ExtConfigurationLoader target = new ExtConfigurationLoader();
        String filepath = "";
        try {
            target.load(filepath);
            fail("filepath is empty, but it does not throw exception.");
        } catch (FileNotFoundException e) {
        }
    }

}
