package org.junithelper.core.meta.extractor;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junithelper.core.config.Configulation;
import org.junithelper.core.meta.AccessModifier;
import org.junithelper.core.meta.ClassMeta;
import org.junithelper.core.util.IOUtil;

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
	public void extract_A$String_classNameWithGenerics1() throws Exception {
		Configulation config = new Configulation();
		ClassMetaExtractor target = new ClassMetaExtractor(config);
		String sourceCodeString = "package hoge.foo; @SuppressWarnings(value = { \"issue 28\" })public class Sample<T> { public Sample() {}\r\n public void doSomething(String str) { System.out.println(\"aaaa\") } }";
		ClassMeta actual = target.extract(sourceCodeString);
		assertEquals("hoge.foo", actual.packageName);
		assertEquals("Sample", actual.name);
	}

	@Test
	public void extract_A$String_classNameWithGenerics2() throws Exception {
		Configulation config = new Configulation();
		ClassMetaExtractor target = new ClassMetaExtractor(config);
		String sourceCodeString = "package hoge.foo; @SuppressWarnings(value = { \"issue 28\" })public class Sample<M, A> { public Sample() {}\r\n public void doSomething(String str) { System.out.println(\"aaaa\") } }";
		ClassMeta actual = target.extract(sourceCodeString);
		assertEquals("hoge.foo", actual.packageName);
		assertEquals("Sample", actual.name);
	}

	@Test
	public void extract_A$String_classNameWithGenerics3() throws Exception {
		Configulation config = new Configulation();
		ClassMetaExtractor target = new ClassMetaExtractor(config);
		String sourceCodeString = "package hoge.foo; @SuppressWarnings(value = { \"issue 28\" })public class Sample<M extends Object> { public Sample() {}\r\n public void doSomething(String str) { System.out.println(\"aaaa\") } }";
		ClassMeta actual = target.extract(sourceCodeString);
		assertEquals("hoge.foo", actual.packageName);
		assertEquals("Sample", actual.name);
	}

	@Test
	public void extract_A$String_classNameWithGenerics4_nested()
			throws Exception {
		Configulation config = new Configulation();
		ClassMetaExtractor target = new ClassMetaExtractor(config);
		String sourceCodeString = "package hoge.foo; @SuppressWarnings(value = { \"issue 28\" })public class Sample<M<A, B> extends Something> { public Sample() {}\r\n public void doSomething(String str) { System.out.println(\"aaaa\") } }";
		ClassMeta actual = target.extract(sourceCodeString);
		assertEquals("hoge.foo", actual.packageName);
		assertEquals("Sample", actual.name);
	}

	@Test
	public void extract_A$String_Slim3_AbstractModelRef() throws Exception {
		Configulation config = new Configulation();
		ClassMetaExtractor target = new ClassMetaExtractor(config);
		String sourceCodeString = IOUtil.readAsString(IOUtil
				.getResourceAsStream("parser/impl/Slim3_AbstractModelRef.txt"),
				"UTF-8");
		ClassMeta actual = target.extract(sourceCodeString);
		assertEquals("org.slim3.datastore", actual.packageName);
		assertEquals(false, actual.isAbstract);
		assertEquals("AbstractModelRef", actual.name);
	}

	@Test
	public void extract_A$String_Slim3_HtmlUtil() throws Exception {
		Configulation config = new Configulation();
		ClassMetaExtractor target = new ClassMetaExtractor(config);
		String sourceCodeString = IOUtil.readAsString(
				IOUtil.getResourceAsStream("parser/impl/Slim3_HtmlUtil.txt"),
				"UTF-8");
		ClassMeta actual = target.extract(sourceCodeString);
		assertEquals("org.slim3.util", actual.packageName);
		assertEquals("HtmlUtil", actual.name);
		assertEquals(false, actual.isAbstract);
		assertEquals(1, actual.constructors.size());
		assertEquals(AccessModifier.Private,
				actual.constructors.get(0).accessModifier);
	}

	@Test
	public void extract_A$String_Slim3_GlobalTransaction() throws Exception {
		Configulation config = new Configulation();
		config.target.isExceptionPatternRequired = false;
		ClassMetaExtractor target = new ClassMetaExtractor(config);
		String sourceCodeString = IOUtil
				.readAsString(
						IOUtil.getResourceAsStream("parser/impl/Slim3_GlobalTransaction.txt"),
						"UTF-8");
		ClassMeta actual = target.extract(sourceCodeString);
		assertEquals("org.slim3.datastore", actual.packageName);
		assertEquals("GlobalTransaction", actual.name);
		assertEquals(false, actual.isAbstract);
		assertEquals(1, actual.constructors.size());
		assertEquals(4, actual.methods.size());
	}

	@Test
	public void extract_A$String_Enum_ContentType() throws Exception {
		Configulation config = new Configulation();
		ClassMetaExtractor target = new ClassMetaExtractor(config);
		String sourceCodeString = IOUtil.readAsString(
				IOUtil.getResourceAsStream("parser/impl/Enum_ContentType.txt"),
				"UTF-8");
		ClassMeta actual = target.extract(sourceCodeString);
		assertEquals("a.b.c", actual.packageName);
		assertEquals(false, actual.isAbstract);
		assertEquals(true, actual.isEnum);
		assertEquals("ContentType", actual.name);
		assertEquals(2, actual.methods.size());
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

	@Test
	public void isDuplicatedVariableName_A$String_true() throws Exception {
		Configulation config = new Configulation();
		ClassMetaExtractor target = new ClassMetaExtractor(config);
		String[] args = new String[] { "target", "actual", "expected",
				"context", "mocks" };
		for (String name : args) {
			boolean actual = target.isDuplicatedVariableName(name);
			boolean expected = true;
			assertEquals(expected, actual);
		}
	}

	@Test
	public void isDuplicatedVariableName_A$String_false() throws Exception {
		Configulation config = new Configulation();
		ClassMetaExtractor target = new ClassMetaExtractor(config);
		String[] args = new String[] { "target_", "name", "hoge", };
		for (String name : args) {
			boolean actual = target.isDuplicatedVariableName(name);
			boolean expected = false;
			assertEquals(expected, actual);
		}
	}

}
