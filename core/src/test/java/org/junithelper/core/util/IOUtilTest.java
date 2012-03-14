package org.junithelper.core.util;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junithelper.core.exception.JUnitHelperCoreException;

public class IOUtilTest {

    @Test
    public void type() throws Exception {
        assertNotNull(IOUtil.class);
    }

    @Test
    public void getResourceAsStream_A$String() throws Exception {
        String name = "inputs/Sample.txt";
        InputStream actual = IOUtil.getResourceAsStream(name);
        assertNotNull(actual);
        IOUtil.close(actual);
    }

    @Test
    public void close_A$FileOutputStream() throws Exception {
        FileOutputStream fos = null;
        IOUtil.close(fos);
    }

    @Test
    public void readAsString_A$InputStream$String() throws Exception {
        String name = "inputs/Sample.txt";
        InputStream is = IOUtil.getResourceAsStream(name);
        String actual = IOUtil.readAsString(is, "UTF-8");
        String expected = "package org.junithelper.core.parser.impl;\r\n\r\npublic class Sample {\r\n\r\n	public String doSomething(String arg) throws Exception {\r\n		// do something\r\n	}\r\n\r\n}\r\n";
        assertEquals(expected, actual);
    }

    @Test
    public void close_A$InputStreamReader() throws Exception {
        InputStreamReader isr = null;
        IOUtil.close(isr);
    }

    @Test
    public void close_A$BufferedReader() throws Exception {
        BufferedReader br = null;
        IOUtil.close(br);
    }

    @Test
    public void close_A$BufferedInputStream() throws Exception {
        BufferedInputStream bis = null;
        IOUtil.close(bis);
    }

    @Test
    public void close_A$OutputStreamWriter() throws Exception {
        OutputStreamWriter osw = null;
        IOUtil.close(osw);
    }

    @Test
    public void readAsLineList_A$InputStream() throws Exception {
        InputStream is = new ByteArrayInputStream(new byte[] {});
        List<String> actual = IOUtil.readAsLineList(is);
        List<String> expected = Arrays.asList(new String[] {});
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void getResourceAsStream_A$String_StringIsNull() throws Exception {
        String name = null;
        try {
            IOUtil.getResourceAsStream(name);
            fail();
        } catch (JUnitHelperCoreException e) {
        }
    }

    @Test
    public void getResourceAsStream_A$String_StringIsEmpty() throws Exception {
        String name = "";
        InputStream actual = IOUtil.getResourceAsStream(name);
        assertThat(actual, notNullValue());
    }

    @Test
    public void readAsString_A$InputStream$String_StringIsNull() throws Exception {
        InputStream is = null;
        String encoding = null;
        try {
            IOUtil.readAsString(is, encoding);
            fail();
        } catch (JUnitHelperCoreException e) {
        }
    }

    @Test
    public void readAsString_A$InputStream$String_StringIsEmpty() throws Exception {
        InputStream is = null;
        String encoding = "";
        try {
            IOUtil.readAsString(is, encoding);
            fail();
        } catch (JUnitHelperCoreException e) {
        }
    }

    @Test
    public void close_A$InputStream_null() throws Exception {
        InputStream is = null;
        IOUtil.close(is);
    }

    @Test
    public void close_A$InputStream() throws Exception {
        InputStream is = new ByteArrayInputStream(new byte[] {});
        IOUtil.close(is);
    }

}
