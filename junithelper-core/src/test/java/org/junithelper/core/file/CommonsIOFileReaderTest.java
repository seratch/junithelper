package org.junithelper.core.file;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;
import org.junithelper.core.exception.JUnitHelperCoreException;
import org.junithelper.core.file.CommonsIOFileReader;
import org.junithelper.core.util.IOUtil;

public class CommonsIOFileReaderTest {

    @Test
    public void type() throws Exception {
        assertNotNull(CommonsIOFileReader.class);
    }

    @Test
    public void instantiation() throws Exception {
        CommonsIOFileReader target = new CommonsIOFileReader();
        assertNotNull(target);
    }

    @Test
    public void getResourceAsStream_A$String() throws Exception {
        CommonsIOFileReader target = new CommonsIOFileReader();
        // given
        String name = "junithelper-config.properties";
        // e.g. : given(mocked.called()).willReturn(1);
        // when
        InputStream actual = target.getResourceAsStream(name);
        // then
        // e.g. : verify(mocked).called();
        assertNotNull(actual);
        IOUtil.close(actual);
    }

    @Test
    public void readAsString_A$File() throws Exception {
        CommonsIOFileReader target = new CommonsIOFileReader();
        // given
        File file = new File("release/junithelper-config.properties");
        // when
        String actual = target.readAsString(file);
        // then
        assertNotNull(actual);
    }

    @Test
    public void readAsString_A$File_T$IOException() throws Exception {
        CommonsIOFileReader target = new CommonsIOFileReader();
        // given
        File file = new File("hogehoge");
        // e.g. : given(mocked.called()).willReturn(1);
        try {
            // when
            target.readAsString(file);
            fail("Expected exception was not thrown!");
        } catch (IOException e) {
            // then
        }
    }

    @Test
    public void getDetectedEncoding_A$File() throws Exception {
        CommonsIOFileReader target = new CommonsIOFileReader();
        // given
        File file = new File("src/test/resources/UTF-8.txt");
        // e.g. : given(mocked.called()).willReturn(1);
        // when
        String actual = target.getDetectedEncoding(file);
        // then
        // e.g. : verify(mocked).called();
        String expected = "UTF-8";
        assertEquals(expected, actual);
    }

    @Test
    public void getResourceAsStream_A$String_StringIsNull() throws Exception {
        CommonsIOFileReader target = new CommonsIOFileReader();
        String name = null;
        try {
            target.getResourceAsStream(name);
            fail();
        } catch (JUnitHelperCoreException e) {
        }
    }

    @Test
    public void getResourceAsStream_A$String_StringIsEmpty() throws Exception {
        CommonsIOFileReader target = new CommonsIOFileReader();
        String name = "";
        InputStream actual = target.getResourceAsStream(name);
        assertThat(actual, notNullValue());
    }

}
