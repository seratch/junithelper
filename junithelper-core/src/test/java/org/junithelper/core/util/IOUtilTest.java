package org.junithelper.core.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.junit.Test;

public class IOUtilTest {

	@Test
	public void getResourceAsStream_A$String() throws Exception {
		String name = "parser/impl/Sample.txt";
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
	public void readAsString_A$InputStream() throws Exception {
		String name = "parser/impl/Sample.txt";
		InputStream is = IOUtil.getResourceAsStream(name);
		String actual = IOUtil.readAsString(is);
		String expected = "package org.junithelper.core.parser.impl;\r\n\r\npublic class Sample {\r\n\r\n	public String doSomething(String arg) throws Exception {\r\n		// do something\r\n	}\r\n\r\n}\r\n";
		assertEquals(expected, actual);
	}

	@Test
	public void close_A$InputStream() throws Exception {
		InputStream is = null;
		IOUtil.close(is);
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

}
