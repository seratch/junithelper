package org.junithelper.core.util;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;
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
        IOUtils.closeQuietly(actual);
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

}
