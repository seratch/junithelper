package org.junithelper.core.meta.extractor;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junithelper.core.config.Configulation;
import org.junithelper.core.meta.ClassMeta;

public class ClassMetaExtractorTest {

	@Test
	public void type() throws Exception {
		assertNotNull(ClassMetaExtractor.class);
	}

	@Test
	public void instantiation() throws Exception {
		Configulation config = null;
		ClassMetaExtractor target = new ClassMetaExtractor(config);
		assertNotNull(target);
	}

	@Test
	public void extract_A$String() throws Exception {
		Configulation config = new Configulation();
		ClassMetaExtractor target = new ClassMetaExtractor(config);
		String sourceCodeString = "package hoge.foo; @SuppressWarnings(value = { \"issue 28\" })public class Sample { public Sample() {}\r\n public void doSomething(String str) { System.out.println(\"aaaa\") } }";
		ClassMeta actual = target.extract(sourceCodeString);
		assertEquals("hoge.foo", actual.packageName);
		assertEquals("Sample", actual.name);
	}

	@Test
	public void renameIfDuplicatedToConstructorArgNames_A$String$List$List_target()
			throws Exception {
		Configulation config = new Configulation();
		ClassMetaExtractor target = new ClassMetaExtractor(config);
		String argName = "target";
		List<String> constructorArgs = new ArrayList<String>();
		List<String> methodArgs = new ArrayList<String>();
		String actual = target.renameIfDuplicatedToConstructorArgNames(argName,
				constructorArgs, methodArgs);
		String expected = "target_";
		assertEquals(expected, actual);
	}

	@Test
	public void renameIfDuplicatedToConstructorArgNames_A$String$List$List_notDuplicated()
			throws Exception {
		Configulation config = new Configulation();
		ClassMetaExtractor target = new ClassMetaExtractor(config);
		String argName = "name";
		List<String> constructorArgs = new ArrayList<String>();
		constructorArgs.add("category");
		List<String> methodArgs = new ArrayList<String>();
		String actual = target.renameIfDuplicatedToConstructorArgNames(argName,
				constructorArgs, methodArgs);
		String expected = "name";
		assertEquals(expected, actual);
	}

	@Test
	public void renameIfDuplicatedToConstructorArgNames_A$String$List$List_duplicated()
			throws Exception {
		Configulation config = new Configulation();
		ClassMetaExtractor target = new ClassMetaExtractor(config);
		String argName = "name";
		List<String> constructorArgs = new ArrayList<String>();
		constructorArgs.add("name");
		List<String> methodArgs = new ArrayList<String>();
		String actual = target.renameIfDuplicatedToConstructorArgNames(argName,
				constructorArgs, methodArgs);
		String expected = "name_";
		assertEquals(expected, actual);
	}

	@Test
	public void renameIfDuplicatedToConstructorArgNames_A$String$List$List_duplicated2()
			throws Exception {
		Configulation config = new Configulation();
		ClassMetaExtractor target = new ClassMetaExtractor(config);
		String argName = "name";
		List<String> constructorArgs = new ArrayList<String>();
		constructorArgs.add("name");
		List<String> methodArgs = new ArrayList<String>();
		methodArgs.add("name_");
		String actual = target.renameIfDuplicatedToConstructorArgNames(argName,
				constructorArgs, methodArgs);
		String expected = "name__";
		assertEquals(expected, actual);
	}

}
