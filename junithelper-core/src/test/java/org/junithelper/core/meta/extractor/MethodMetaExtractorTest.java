package org.junithelper.core.meta.extractor;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.Test;
import org.junithelper.core.config.Configulation;
import org.junithelper.core.meta.AccessModifier;
import org.junithelper.core.meta.ClassMeta;
import org.junithelper.core.meta.MethodMeta;

public class MethodMetaExtractorTest {

	@Test
	public void type() throws Exception {
		assertNotNull(MethodMetaExtractor.class);
	}

	@Test
	public void instantiation() throws Exception {
		Configulation config = null;
		MethodMetaExtractor target = new MethodMetaExtractor(config);
		assertNotNull(target);
	}

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

	@Test
	public void extract_A$String_somethingWrong() throws Exception {
		String sourceCodeString = "package foo.var; public class Something { 	public String toLable() {\r\n		String label = \"\";\r\n		try {\r\n			if(name.equals(relax.name)){\r\n				label = \"aaa\";\r\n			} else if(name.equals(nurturing.name)){\r\n				label = \"bbb\";\r\n			} else if(name.equals(word.name)){\r\n				label = \"ccc\";\r\n			}\r\n		}catch (Exception e) {}\r\n		return label;\r\n	}\r\n }";
		ClassMeta classMeta = classMetaExtractor.extract(sourceCodeString);
		target.initialize(classMeta, sourceCodeString);
		List<MethodMeta> actual = target.extract(sourceCodeString);
		assertEquals(1, actual.size());
		assertEquals("toLable", actual.get(0).name);
		assertEquals(0, actual.get(0).argTypes.size());
		assertEquals(0, actual.get(0).argNames.size());
	}

	@Test
	public void initialize_A$ClassMeta() throws Exception {
		// given
		ClassMeta classMeta = mock(ClassMeta.class);
		// e.g. : given(mocked.called()).willReturn(1);
		// when
		MethodMetaExtractor actual = target.initialize(classMeta);
		// then
		// e.g. : verify(mocked).called();
		assertNotNull(actual);
	}

	@Test
	public void isPrivateFieldExists_A$String$String$String() throws Exception {
		// given
		String fieldType = "java.lang.String";
		String fieldName = "str";
		String sourceCodeString = "package hoge; public class Sample { public void doSomething() {} }";
		// e.g. : given(mocked.called()).willReturn(1);
		// when
		boolean actual = target.isPrivateFieldExists(fieldType, fieldName,
				sourceCodeString);
		// then
		// e.g. : verify(mocked).called();
		boolean expected = false;
		assertEquals(expected, actual);
	}

	@Test
	public void getAccessModifier_A$String() throws Exception {
		// given
		String methodSignatureArea = "} public static void main(String[] args) {";
		// when
		AccessModifier actual = target.getAccessModifier(methodSignatureArea);
		// then
		AccessModifier expected = AccessModifier.Public;
		assertEquals(expected, actual);
	}

	@Test
	public void trimAccessModifierFromMethodSignatureArea_A$String()
			throws Exception {
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
