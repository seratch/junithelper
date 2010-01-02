package org.junithelper.plugin.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import junit.framework.TestCase;

public class FileResourceUtilTest extends TestCase {

	public void test_close_A$BufferedInputStream() throws Exception {
		BufferedInputStream bis = null;
		FileResourceUtil.close(bis);
	}

	public void test_close_A$BufferedReader() throws Exception {
		BufferedReader br = null;
		FileResourceUtil.close(br);
	}

	public void test_close_A$FileOutputStream() throws Exception {
		FileOutputStream fos = null;
		FileResourceUtil.close(fos);
	}

	public void test_close_A$InputStream() throws Exception {
		InputStream is = null;
		FileResourceUtil.close(is);
	}

	public void test_close_A$InputStreamReader() throws Exception {
		InputStreamReader isr = null;
		FileResourceUtil.close(isr);
	}

	public void test_close_A$OutputStreamWriter() throws Exception {
		OutputStreamWriter osw = null;
		FileResourceUtil.close(osw);
	}

}
