package org.junithelper.core.file.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junithelper.core.file.FileWriter;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class CommonsIOFileWriterTest {

	@Before
	public void setUp() throws Exception {
		File file = new File("src/test/resources/tmp.txt");
		file.deleteOnExit();
	}

	@After
	public void tearDown() throws Exception {
		File file = new File("src/test/resources/tmp.txt");
		file.deleteOnExit();
	}

	@Test
	public void type() throws Exception {
		assertNotNull(CommonsIOFileWriter.class);
	}

	@Test
	public void instantiation() throws Exception {
		File file = null;
		CommonsIOFileWriter target = new CommonsIOFileWriter(file);
		assertNotNull(target);
	}

	@Test
	public void setWriteTarget_A$File() throws Exception {
		File argFile = null;
		CommonsIOFileWriter target = new CommonsIOFileWriter(argFile);
		// given
		File file = new File("src/test/resources/tmp.txt");
		// when
		FileWriter actual = target.setWriteTarget(file);
		// then
		assertNotNull(actual);
	}

	@Test
	public void writeText_A$String() throws Exception {
		File file = new File("src/test/resources/tmp.txt");
		CommonsIOFileWriter target = new CommonsIOFileWriter(file);
		// given
		String text = "testtest\r\ntest";
		// when
		target.writeText(text);
		// then
	}

	@Test
	public void writeText_A$String_T$IOException() throws Exception {
		File file = new File("src/test/resources/tmp.txt");
		CommonsIOFileWriter target = new CommonsIOFileWriter(file);
		// given
		String text = null;
		try {
			file.setWritable(false);
			// when
			target.writeText(text);
			fail("Expected exception was not thrown!");
		} catch (IOException e) {
			// then
		}
		file.setWritable(true);
	}

	@Test
	public void writeText_A$String$String() throws Exception {
		File file = new File("src/test/resources/tmp.txt");
		CommonsIOFileWriter target = new CommonsIOFileWriter(file);
		// given
		String text = null;
		String encoding = null;
		// e.g. : given(mocked.called()).willReturn(1);
		// when
		target.writeText(text, encoding);
		// then
		// e.g. : verify(mocked).called();
	}

	@Test
	public void writeText_A$String$String_T$IOException() throws Exception {
		File file = new File("src/test/resources/tmp.txt");
		CommonsIOFileWriter target = new CommonsIOFileWriter(file);
		// given
		String text = null;
		String encoding = null;
		// e.g. : given(mocked.called()).willReturn(1);
		try {
			file.setWritable(false);
			// when
			target.writeText(text, encoding);
			fail("Expected exception was not thrown!");
		} catch (IOException e) {
			// then
		}
		file.setWritable(true);
	}

}
