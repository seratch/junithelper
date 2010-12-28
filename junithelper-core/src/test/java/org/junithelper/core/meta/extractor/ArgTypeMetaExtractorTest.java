package org.junithelper.core.meta.extractor;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.Test;
import org.junithelper.core.config.Configulation;
import org.junithelper.core.meta.ClassMeta;

public class ArgTypeMetaExtractorTest {

	@Test
	public void type() throws Exception {
		assertNotNull(ArgTypeMetaExtractor.class);
	}

	@Test
	public void instantiation() throws Exception {
		Configulation config = null;
		ArgTypeMetaExtractor target = new ArgTypeMetaExtractor(config);
		assertNotNull(target);
	}

	@Test
	public void initialize_A$String() throws Exception {
		Configulation config = new Configulation();
		ArgTypeMetaExtractor target = new ArgTypeMetaExtractor(config);
		String sourceCodeString = "package hoge.foo; public class Sample { public Sample() {}\r\n public void doSomething(String str) { System.out.println(\"aaaa\") } }";
		target.initialize(sourceCodeString);
	}

	@Test
	public void initialize_A$ClassMeta$String() throws Exception {
		Configulation config = new Configulation();
		ArgTypeMetaExtractor target = new ArgTypeMetaExtractor(config);
		String sourceCodeString = "package hoge.foo; public class Sample { public Sample() {}\r\n public void doSomething(String str) { System.out.println(\"aaaa\") } }";
		ClassMeta classMeta = new ClassMetaExtractor(config)
				.extract(sourceCodeString);
		target.initialize(classMeta, sourceCodeString);
	}

	@Test
	public void doExtract_A$String() throws Exception {
		Configulation config = new Configulation();
		ArgTypeMetaExtractor target = new ArgTypeMetaExtractor(config);
		String sourceCodeString = "package hoge.foo; public class Sample { public Sample() {}\r\n public void doSomething(String str) { System.out.println(\"aaaa\") } }";
		target.initialize(sourceCodeString);
		String argsAreaString = "String str";
		target.doExtract(argsAreaString);
		assertEquals(target.getExtractedMetaList().size(), 1);
		assertEquals(target.getExtractedMetaList().get(0).name, "String");
		assertEquals(target.getExtractedNameList().size(), 1);
		assertEquals(target.getExtractedNameList().get(0), "str");
	}

	@Test
	public void initialize_A$ClassMeta() throws Exception {
		Configulation config = new Configulation();
		ArgTypeMetaExtractor target = new ArgTypeMetaExtractor(config);
		// given
		ClassMeta classMeta = mock(ClassMeta.class);
		// e.g. : given(mocked.called()).willReturn(1);
		// when
		ArgTypeMetaExtractor actual = target.initialize(classMeta);
		// then
		// e.g. : verify(mocked).called();
		ArgTypeMetaExtractor expected = target;
		assertEquals(expected, actual);
	}

	@Test
	public void getArgListFromArgsDefAreaString_A$String() throws Exception {
		Configulation config = new Configulation();
		ArgTypeMetaExtractor target = new ArgTypeMetaExtractor(config);
		// given
		String argsDefAreaString = "String str, List<String> list, Map<Object, Object> map, Object obj, Map<String, List<String>> listMap, List<Map<String,String>> mapList, List<Map<Map<String,Object>,List<String>>> deepNest) {";
		// when
		List<String> actual = target
				.getArgListFromArgsDefAreaString(argsDefAreaString);
		// then
		assertEquals("String str", actual.get(0));
		assertEquals("List<String> list", actual.get(1));
		assertEquals("Map<Object,Object> map", actual.get(2));
		assertEquals("Object obj", actual.get(3));
		assertEquals("Map listMap", actual.get(4));
		assertEquals("List mapList", actual.get(5));
		assertEquals("List deepNest", actual.get(6));
	}

	@Test
	public void trimGenericsAreaIfNestedGenericsExists_A$String()
			throws Exception {
		// given
		String target = "List<String> list";
		// when
		String actual = ArgTypeMetaExtractor
				.trimGenericsAreaIfNestedGenericsExists(target);
		// then
		String expected = "List<String> list";
		assertEquals(expected, actual);
	}

	@Test
	public void trimGenericsAreaIfNestedGenericsExists_A$String_exists()
			throws Exception {
		// given
		String target = "List<Map" + ArgTypeMetaExtractor.NESTED_GENERICS_MARK
				+ "> list";
		// when
		String actual = ArgTypeMetaExtractor
				.trimGenericsAreaIfNestedGenericsExists(target);
		// then
		String expected = "List list";
		assertEquals(expected, actual);
	}

}
