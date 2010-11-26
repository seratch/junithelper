package org.junithelper.core.meta.extractor;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junithelper.core.config.Configulation;
import org.junithelper.core.meta.ClassMeta;

public class ArgTypeMetaExtractorTest {

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

}
