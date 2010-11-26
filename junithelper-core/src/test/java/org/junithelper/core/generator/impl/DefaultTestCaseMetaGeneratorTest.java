package org.junithelper.core.generator.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junithelper.core.config.Configulation;
import org.junithelper.core.meta.ClassMeta;
import org.junithelper.core.meta.TestCaseMeta;
import org.junithelper.core.meta.extractor.ClassMetaExtractor;

public class DefaultTestCaseMetaGeneratorTest {

	Configulation config = new Configulation();
	DefaultTestCaseGenerator target = new DefaultTestCaseGenerator(config);
	ClassMetaExtractor classMetaExtractor = new ClassMetaExtractor(config);

	@Test
	public void initialize_A$Configulation$ClassMeta() throws Exception {
		String sourceCodeString = "package hoge.foo; public class Sample { public Sample() {}\r\n public int doSomething(String str, long longValue) throws Throwable { System.out.println(\"aaaa\") } }";
		ClassMeta targetClassMeta = classMetaExtractor
				.extract(sourceCodeString);
		target.initialize(targetClassMeta);
	}

	@Test
	public void getNewTestCaseMeta_A$() throws Exception {
		String sourceCodeString = "package hoge.foo; public class Sample { public Sample() {}\r\n public int doSomething(String str, long longValue) throws Throwable { System.out.println(\"aaaa\") } }";
		ClassMeta targetClassMeta = classMetaExtractor
				.extract(sourceCodeString);
		target.initialize(targetClassMeta);
		TestCaseMeta actual = target.getNewTestCaseMeta();
		assertEquals("Sample", actual.target.name);
		assertEquals(1, actual.tests.size());
	}

}
