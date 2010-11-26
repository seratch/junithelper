package org.junithelper.core.meta.extractor;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junithelper.core.config.Configulation;
import org.junithelper.core.meta.ClassMeta;
import org.junithelper.core.meta.MethodMeta;

public class MethodMetaExtractorTest {

	Configulation config = new Configulation();
	MethodMetaExtractor target = new MethodMetaExtractor(config);
	ClassMetaExtractor classMetaExtractor = new ClassMetaExtractor(config);

	@Test
	public void initialize_A$String() throws Exception {
		String sourceCodeString = "package hoge.foo; public class Sample { public Sample() {}\r\n public void doSomething(String str) { System.out.println(\"aaaa\") } }";
		target.initialize(sourceCodeString);
	}

	@Test
	public void initialize_A$ClassMeta$String() throws Exception {
		String sourceCodeString = "package hoge.foo; public class Sample { public Sample() {}\r\n public void doSomething(String str) { System.out.println(\"aaaa\") } }";
		ClassMeta classMeta = classMetaExtractor.extract(sourceCodeString);
		target.initialize(classMeta, sourceCodeString);
	}

	@Test
	public void extract_A$String() throws Exception {
		String sourceCodeString = "package hoge.foo; public class Sample { public Sample() {}\r\n public String doSomething(Integer intVal, long longVal) { System.out.println(\"aaaa\") } }";
		ClassMeta classMeta = classMetaExtractor.extract(sourceCodeString);
		target.initialize(classMeta, sourceCodeString);
		List<MethodMeta> actual = target.extract(sourceCodeString);
		assertEquals(1, actual.size());
		assertEquals("doSomething", actual.get(0).name);
		assertEquals(2, actual.get(0).argNames.size());
		assertEquals("String", actual.get(0).returnType.name);
	}

}
