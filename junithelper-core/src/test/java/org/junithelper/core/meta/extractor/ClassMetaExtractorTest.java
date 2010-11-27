package org.junithelper.core.meta.extractor;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junithelper.core.config.Configulation;
import org.junithelper.core.meta.ClassMeta;

public class ClassMetaExtractorTest {

	@Test
	public void extract_A$String() throws Exception {
		Configulation config = new Configulation();
		ClassMetaExtractor target = new ClassMetaExtractor(config);
		String sourceCodeString = "package hoge.foo; @SuppressWarnings(value = { \"issue 28\" })public class Sample { public Sample() {}\r\n public void doSomething(String str) { System.out.println(\"aaaa\") } }";
		ClassMeta actual = target.extract(sourceCodeString);
		assertEquals("hoge.foo", actual.packageName);
		assertEquals("Sample", actual.name);
	}

}
