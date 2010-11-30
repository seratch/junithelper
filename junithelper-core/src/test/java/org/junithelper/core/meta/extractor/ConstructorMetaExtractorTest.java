package org.junithelper.core.meta.extractor;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.Test;
import org.junithelper.core.config.Configulation;
import org.junithelper.core.meta.AccessModifier;
import org.junithelper.core.meta.ClassMeta;
import org.junithelper.core.meta.ConstructorMeta;

public class ConstructorMetaExtractorTest {

	@Test
	public void type() throws Exception {
		assertNotNull(ConstructorMetaExtractor.class);
	}

	@Test
	public void instantiation() throws Exception {
		Configulation config = null;
		ConstructorMetaExtractor target = new ConstructorMetaExtractor(config);
		assertNotNull(target);
	}

	@Test
	public void initialize_A$String() throws Exception {
		Configulation config = new Configulation();
		ConstructorMetaExtractor target = new ConstructorMetaExtractor(config);
		// given
		String sourceCodeString = "package hoge; public class Sample { public void doSomething(String str) {} }";
		// e.g. : given(mocked.called()).willReturn(1);
		// when
		ConstructorMetaExtractor actual = target.initialize(sourceCodeString);
		// then
		// e.g. : verify(mocked).called();
		ConstructorMetaExtractor expected = target;
		assertEquals(expected, actual);
	}

	@Test
	public void initialize_A$ClassMeta$String() throws Exception {
		Configulation config = new Configulation();
		ConstructorMetaExtractor target = new ConstructorMetaExtractor(config);
		// given
		ClassMeta classMeta = mock(ClassMeta.class);
		String sourceCodeString = "package hoge; public class Sample { public void doSomething(String str) {} }";
		// e.g. : given(mocked.called()).willReturn(1);
		// when
		ConstructorMetaExtractor actual = target.initialize(classMeta,
				sourceCodeString);
		// then
		// e.g. : verify(mocked).called();
		ConstructorMetaExtractor expected = target;
		assertEquals(expected, actual);
	}

	@Test
	public void extract_A$String() throws Exception {
		Configulation config = new Configulation();
		ConstructorMetaExtractor target = new ConstructorMetaExtractor(config);
		// given
		String sourceCodeString = "package hoge; public class Sample { public void doSomething(String str) {} }";
		target.initialize(sourceCodeString);
		// e.g. : given(mocked.called()).willReturn(1);
		// when
		List<ConstructorMeta> actual = target.extract(sourceCodeString);
		// then
		// e.g. : verify(mocked).called();
		assertEquals(1, actual.size());
	}

	@Test
	public void getAccessModifier_A$String() throws Exception {
		Configulation config = new Configulation();
		ConstructorMetaExtractor target = new ConstructorMetaExtractor(config);
		// given
		String methodSignatureArea = "} public static void main(String[] args) {";
		// e.g. : given(mocked.called()).willReturn(1);
		// when
		AccessModifier actual = target.getAccessModifier(methodSignatureArea);
		// then
		// e.g. : verify(mocked).called();
		AccessModifier expected = AccessModifier.Public;
		assertEquals(expected, actual);
	}

	@Test
	public void trimAccessModifierFromMethodSignatureArea_A$String()
			throws Exception {
		Configulation config = new Configulation();
		ConstructorMetaExtractor target = new ConstructorMetaExtractor(config);
		// given
		String methodSignatureArea = "} public static void main(String[] args) {";
		// e.g. : given(mocked.called()).willReturn(1);
		// when
		String actual = target
				.trimAccessModifierFromMethodSignatureArea(methodSignatureArea);
		// then
		// e.g. : verify(mocked).called();
		String expected = " static void main(String[] args) {";
		assertEquals(expected, actual);
	}

}
