package org.junithelper.core.generator.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junithelper.core.config.Configulation;
import org.junithelper.core.config.MockObjectFramework;
import org.junithelper.core.meta.ClassMeta;
import org.junithelper.core.meta.ExceptionMeta;
import org.junithelper.core.meta.MethodMeta;
import org.junithelper.core.meta.TestMethodMeta;
import org.junithelper.core.meta.extractor.ClassMetaExtractor;

public class DefaultTestMethodGeneratorTest {

	Configulation config = new Configulation();
	DefaultTestMethodGenerator target = new DefaultTestMethodGenerator(config);
	ClassMetaExtractor classMetaExtractor = new ClassMetaExtractor(config);

	@Test
	public void type() throws Exception {
		assertNotNull(DefaultTestMethodGenerator.class);
	}

	@Test
	public void instantiation() throws Exception {
		Configulation config = null;
		DefaultTestMethodGenerator target = new DefaultTestMethodGenerator(
				config);
		assertNotNull(target);
	}

	@Test
	public void initialize_A$ClassMeta() throws Exception {
		// given
		String sourceCodeString = "package hoge.foo; import java.util.List; public class Sample { public Sample() {}\r\n public int doSomething(String str, long longValue) throws Throwable { System.out.println(\"aaaa\") } }";
		ClassMeta targetClassMeta = classMetaExtractor
				.extract(sourceCodeString);
		// when
		target.initialize(targetClassMeta);
		// then
	}

	@Test
	public void getTestMethodMeta_A$MethodMeta() throws Exception {
		// given
		String sourceCodeString = "package hoge.foo; import java.util.List; public class Sample { public Sample() {}\r\n public int doSomething(String str, long longValue) throws Throwable { System.out.println(\"aaaa\") } }";
		ClassMeta targetClassMeta = classMetaExtractor
				.extract(sourceCodeString);
		MethodMeta targetMethodMeta = targetClassMeta.methods.get(0);
		target.initialize(targetClassMeta);
		// when
		TestMethodMeta actual = target.getTestMethodMeta(targetMethodMeta);
		// then
		assertEquals(false, actual.isTypeTest);
		assertEquals(false, actual.isInstantiationTest);
		assertEquals(targetClassMeta, actual.classMeta);
		assertEquals(targetMethodMeta, actual.methodMeta);
		assertEquals(null, actual.testingTargetException);
	}

	@Test
	public void getTestMethodMeta_A$MethodMeta$ExceptionMeta() throws Exception {
		// given
		String sourceCodeString = "package hoge.foo; import java.util.List; public class Sample { public Sample() {}\r\n public int doSomething(String str, long longValue) throws Throwable { System.out.println(\"aaaa\") } }";
		ClassMeta targetClassMeta = classMetaExtractor
				.extract(sourceCodeString);
		MethodMeta targetMethodMeta = targetClassMeta.methods.get(0);
		ExceptionMeta exception = new ExceptionMeta();
		exception.name = "Exception";
		exception.nameInMethodName = "Exception";
		target.initialize(targetClassMeta);
		// when
		TestMethodMeta actual = target.getTestMethodMeta(targetMethodMeta,
				exception);
		// then
		assertEquals(false, actual.isTypeTest);
		assertEquals(false, actual.isInstantiationTest);
		assertEquals(targetClassMeta, actual.classMeta);
		assertEquals(targetMethodMeta, actual.methodMeta);
		assertNotNull(actual.testingTargetException);
	}

	@Test
	public void getTestMethodNamePrefix_A$TestMethodMeta() throws Exception {
		// given
		String sourceCodeString = "package hoge.foo; import java.util.List; public class Sample { public Sample() {}\r\n public int doSomething(String str, long longValue) throws Throwable { System.out.println(\"aaaa\") } }";
		ClassMeta targetClassMeta = classMetaExtractor
				.extract(sourceCodeString);
		target.initialize(targetClassMeta);
		MethodMeta targetMethodMeta = targetClassMeta.methods.get(0);
		TestMethodMeta testMethodMeta = target
				.getTestMethodMeta(targetMethodMeta);
		// when
		String actual = target.getTestMethodNamePrefix(testMethodMeta);
		// then
		String expected = "doSomething_A$String$long";
		assertEquals(expected, actual);
	}

	@Test
	public void getTestMethodNamePrefix_A$TestMethodMeta$ExceptionMeta()
			throws Exception {
		// given
		String sourceCodeString = "package hoge.foo; import java.util.List; public class Sample { public Sample() {}\r\n public int doSomething(String str, long longValue) throws Throwable { System.out.println(\"aaaa\") } }";
		ClassMeta targetClassMeta = classMetaExtractor
				.extract(sourceCodeString);
		target.initialize(targetClassMeta);
		MethodMeta targetMethodMeta = targetClassMeta.methods.get(0);
		TestMethodMeta testMethodMeta = target
				.getTestMethodMeta(targetMethodMeta);
		ExceptionMeta exception = new ExceptionMeta();
		exception.name = "Exception";
		exception.nameInMethodName = "Exception";
		// when
		String actual = target.getTestMethodNamePrefix(testMethodMeta,
				exception);
		// then
		String expected = "doSomething_A$String$long_T$Exception";
		assertEquals(expected, actual);
	}

	@Test
	public void getTestMethodSourceCode_A$TestMethodMeta() throws Exception {
		// given
		String sourceCodeString = "package hoge.foo; import java.util.List; public class Sample { public Sample() {}\r\n public int doSomething(String str, long longValue) throws Throwable { System.out.println(\"aaaa\") } }";
		ClassMeta targetClassMeta = classMetaExtractor
				.extract(sourceCodeString);
		target.initialize(targetClassMeta);
		MethodMeta targetMethodMeta = targetClassMeta.methods.get(0);
		TestMethodMeta testMethodMeta = target
				.getTestMethodMeta(targetMethodMeta);
		// when
		String actual = target.getTestMethodSourceCode(testMethodMeta);
		// then
		String expected = "	@Test\r\n	public void doSomething_A$String$long() throws Exception {\r\n		// TODO auto-generated by JUnit Helper.\r\n		Sample target = new Sample();\r\n		String str = null;\r\n		long longValue = 0L;\r\n		int actual = target.doSomething(str, longValue);\r\n		int expected = 0;\r\n		assertEquals(expected, actual);\r\n	}\r\n";
		assertEquals(expected, actual);
	}

	@Test
	public void appendCRLF_A$StringBuilder() throws Exception {
		// given
		StringBuilder buf = new StringBuilder();
		// e.g. : given(mocked.called()).willReturn(1);
		// when
		target.appendCRLF(buf);
		// then
		// e.g. : verify(mocked).called();
		assertEquals("\r\n", buf.toString());
	}

	@Test
	public void appendTabs_A$StringBuilder$int_0times() throws Exception {
		// given
		StringBuilder buf = new StringBuilder();
		int times = 0;
		// when
		target.appendTabs(buf, times);
		// then
		assertEquals("", buf.toString());
	}

	@Test
	public void appendTabs_A$StringBuilder$int_1times() throws Exception {
		// given
		StringBuilder buf = new StringBuilder();
		int times = 1;
		// when
		target.appendTabs(buf, times);
		// then
		assertEquals("\t", buf.toString());
	}

	@Test
	public void appendTabs_A$StringBuilder$int_2times() throws Exception {
		// given
		StringBuilder buf = new StringBuilder();
		int times = 2;
		// when
		target.appendTabs(buf, times);
		// then
		assertEquals("\t\t", buf.toString());
	}

	@Test
	public void appendPreparingArgs_A$StringBuilder$TestMethodMeta()
			throws Exception {
		// given
		StringBuilder buf = new StringBuilder();
		String sourceCodeString = "package hoge.foo; import java.util.List; public class Sample { public Sample() {}\r\n public int doSomething(String str, long longValue) throws Throwable { System.out.println(\"aaaa\") } }";
		ClassMeta targetClassMeta = classMetaExtractor
				.extract(sourceCodeString);
		target.initialize(targetClassMeta);
		MethodMeta targetMethodMeta = targetClassMeta.methods.get(0);
		TestMethodMeta testMethodMeta = target
				.getTestMethodMeta(targetMethodMeta);
		// when
		target.appendPreparingArgs(buf, testMethodMeta);
		// then
		assertEquals("		String str = null;\r\n		long longValue = 0L;\r\n", buf
				.toString());
	}

	@Test
	public void appendMockChecking_A$StringBuilder$int_depth0()
			throws Exception {
		// given
		StringBuilder buf = new StringBuilder();
		int depth = 0;
		// when
		target.appendMockChecking(buf, depth);
		// then
		assertEquals("", buf.toString());
	}

	@Test
	public void appendMockChecking_A$StringBuilder$int_depth1()
			throws Exception {
		// given
		StringBuilder buf = new StringBuilder();
		int depth = 1;
		// when
		target.appendMockChecking(buf, depth);
		// then
		assertEquals("", buf.toString());
	}

	@Test
	public void appendMockChecking_A$StringBuilder$int_depth2()
			throws Exception {
		// given
		StringBuilder buf = new StringBuilder();
		int depth = 2;
		// when
		target.appendMockChecking(buf, depth);
		// then
		assertEquals("", buf.toString());
	}

	@Test
	public void appendMockChecking_A$StringBuilder$int_EasyMock()
			throws Exception {
		Configulation config = new Configulation();
		config.mockObjectFramework = MockObjectFramework.EasyMock;
		DefaultTestMethodGenerator target = new DefaultTestMethodGenerator(
				config);
		// given
		StringBuilder buf = new StringBuilder();
		int depth = 2;
		// e.g. : given(mocked.called()).willReturn(1);
		// when
		target.appendMockChecking(buf, depth);
		// then
		assertEquals(
				"\t\t// e.g. : EasyMock.expect(mocked.called()).andReturn(1);\r\n		mocks.replay();\r\n",
				buf.toString());
	}

	@Test
	public void appendMockChecking_A$StringBuilder$int_JMock2()
			throws Exception {
		Configulation config = new Configulation();
		config.mockObjectFramework = MockObjectFramework.JMock2;
		DefaultTestMethodGenerator target = new DefaultTestMethodGenerator(
				config);
		// given
		StringBuilder buf = new StringBuilder();
		int depth = 2;
		// e.g. : given(mocked.called()).willReturn(1);
		// when
		target.appendMockChecking(buf, depth);
		// then
		assertEquals(
				"\t\tcontext.checking(new Expectations(){{\r\n			// e.g. : allowing(mocked).called(); will(returnValue(1));\r\n		}});\r\n",
				buf.toString());
	}

	@Test
	public void appendMockChecking_A$StringBuilder$int_JMockit()
			throws Exception {
		Configulation config = new Configulation();
		config.mockObjectFramework = MockObjectFramework.JMockit;
		DefaultTestMethodGenerator target = new DefaultTestMethodGenerator(
				config);
		// given
		StringBuilder buf = new StringBuilder();
		int depth = 2;
		// e.g. : given(mocked.called()).willReturn(1);
		// when
		target.appendMockChecking(buf, depth);
		// then
		assertEquals(
				"\t\tnew Expectations(){{\r\n			// e.g. : mocked.get(anyString); returns(200);\r\n		}};\r\n",
				buf.toString());
	}

	@Test
	public void appendMockChecking_A$StringBuilder$int_Mockito()
			throws Exception {
		Configulation config = new Configulation();
		config.mockObjectFramework = MockObjectFramework.Mockito;
		DefaultTestMethodGenerator target = new DefaultTestMethodGenerator(
				config);
		// given
		StringBuilder buf = new StringBuilder();
		int depth = 2;
		// e.g. : given(mocked.called()).willReturn(1);
		// when
		target.appendMockChecking(buf, depth);
		// then
		assertEquals("\t\t// e.g. : given(mocked.called()).willReturn(1);\r\n",
				buf.toString());
	}

	@Test
	public void appendMockVerifying_A$StringBuilder$int_depth0()
			throws Exception {
		// given
		StringBuilder buf = new StringBuilder();
		int depth = 0;
		// when
		target.appendMockVerifying(buf, depth);
		// then
		assertEquals("", buf.toString());
	}

	@Test
	public void appendMockVerifying_A$StringBuilder$int_depth1()
			throws Exception {
		// given
		StringBuilder buf = new StringBuilder();
		int depth = 1;
		// when
		target.appendMockVerifying(buf, depth);
		// then
		assertEquals("", buf.toString());
	}

	@Test
	public void appendMockVerifying_A$StringBuilder$int_depth2()
			throws Exception {
		// given
		StringBuilder buf = new StringBuilder();
		int depth = 2;
		// when
		target.appendMockVerifying(buf, depth);
		// then
		assertEquals("", buf.toString());
	}

	@Test
	public void appendExecutingTargetMethod_A$StringBuilder$TestMethodMeta()
			throws Exception {
		// given
		StringBuilder buf = new StringBuilder();
		String sourceCodeString = "package hoge.foo; import java.util.List; public class Sample { public Sample() {}\r\n public int doSomething(String str, long longValue) throws Throwable { System.out.println(\"aaaa\") } }";
		ClassMeta targetClassMeta = classMetaExtractor
				.extract(sourceCodeString);
		target.initialize(targetClassMeta);
		MethodMeta targetMethodMeta = targetClassMeta.methods.get(0);
		TestMethodMeta testMethodMeta = target
				.getTestMethodMeta(targetMethodMeta);
		// when
		target.appendExecutingTargetMethod(buf, testMethodMeta);
		// then
		assertEquals("target.doSomething(str, longValue);\r\n", buf.toString());
	}

	@Test
	public void appendBDDMockitoComment_A$StringBuilder$String$int_depth0()
			throws Exception {
		// given
		StringBuilder buf = new StringBuilder();
		String value = null;
		int depth = 0;
		// when
		target.appendBDDMockitoComment(buf, value, depth);
		// then
		assertEquals("", buf.toString());
	}

	@Test
	public void appendBDDMockitoComment_A$StringBuilder$String$int_depth1()
			throws Exception {
		// given
		StringBuilder buf = new StringBuilder();
		String value = null;
		int depth = 1;
		// when
		target.appendBDDMockitoComment(buf, value, depth);
		// then
		assertEquals("", buf.toString());
	}

	@Test
	public void appendBDDMockitoComment_A$StringBuilder$String$int_depth2()
			throws Exception {
		// given
		StringBuilder buf = new StringBuilder();
		String value = null;
		int depth = 2;
		// when
		target.appendBDDMockitoComment(buf, value, depth);
		// then
		assertEquals("", buf.toString());
	}

	@Test
	public void getMockedFieldsForJMockit_A$TestMethodMeta() throws Exception {
		// given
		config.mockObjectFramework = MockObjectFramework.JMockit;
		target = new DefaultTestMethodGenerator(config);
		String sourceCodeString = "package hoge.foo; import java.util.List;import fuga.Bean; public class Sample { public Sample() {}\r\n public int doSomething(String str, long longValue, Bean bean) throws Throwable { System.out.println(\"aaaa\") } }";
		ClassMeta targetClassMeta = classMetaExtractor
				.extract(sourceCodeString);
		target.initialize(targetClassMeta);
		MethodMeta targetMethodMeta = targetClassMeta.methods.get(0);
		TestMethodMeta testMethodMeta = target
				.getTestMethodMeta(targetMethodMeta);
		// when
		List<String> actual = target.getMockedFieldsForJMockit(testMethodMeta);
		// then
		// e.g. : verify(mocked).called();
		assertEquals(1, actual.size());
	}

	@Test
	public void getArgValue_A$TestMethodMeta$ArgTypeMeta$String()
			throws Exception {
		// given
		String sourceCodeString = "package hoge.foo; import java.util.List; import java.util.Map; public class Sample { public int doSomething(String str, long longValue, List<String> list, Map<String,Object> map, java.util.HashMap<String, String> hashMap) throws Throwable { System.out.println(\"aaaa\") } }";
		ClassMeta targetClassMeta = classMetaExtractor
				.extract(sourceCodeString);
		target.initialize(targetClassMeta);
		MethodMeta targetMethodMeta = targetClassMeta.methods.get(0);
		TestMethodMeta testMethodMeta = target
				.getTestMethodMeta(targetMethodMeta);
		// when
		// then
		assertEquals("null", target.getArgValue(testMethodMeta,
				targetMethodMeta.argTypes.get(0), targetMethodMeta.argNames
						.get(0)));
		assertEquals("0L", target.getArgValue(testMethodMeta,
				targetMethodMeta.argTypes.get(1), targetMethodMeta.argNames
						.get(1)));
		assertEquals("new ArrayList<String>()", target.getArgValue(
				testMethodMeta, targetMethodMeta.argTypes.get(2),
				targetMethodMeta.argNames.get(2)));
		assertEquals("new HashMap<String, Object>()", target.getArgValue(
				testMethodMeta, targetMethodMeta.argTypes.get(3),
				targetMethodMeta.argNames.get(3)));
		assertEquals("null", target.getArgValue(testMethodMeta,
				targetMethodMeta.argTypes.get(4), targetMethodMeta.argNames
						.get(4)));
	}

	@Test
	public void getArgValue_A$TestMethodMeta$ArgTypeMeta$String_Mockito()
			throws Exception {
		// given
		Configulation config = new Configulation();
		config.mockObjectFramework = MockObjectFramework.Mockito;
		DefaultTestMethodGenerator target = new DefaultTestMethodGenerator(
				config);
		ClassMetaExtractor classMetaExtractor = new ClassMetaExtractor(config);
		String sourceCodeString = "package hoge.foo; import java.util.List; import java.util.Map; public class Sample { public int doSomething(String str, long longValue, List<String> list, Map<String,Object> map, java.util.HashMap<String, String> hashMap) throws Throwable { System.out.println(\"aaaa\") } }";
		ClassMeta targetClassMeta = classMetaExtractor
				.extract(sourceCodeString);
		target.initialize(targetClassMeta);
		MethodMeta targetMethodMeta = targetClassMeta.methods.get(0);
		TestMethodMeta testMethodMeta = target
				.getTestMethodMeta(targetMethodMeta);
		// when
		// then
		assertEquals("null", target.getArgValue(testMethodMeta,
				targetMethodMeta.argTypes.get(0), targetMethodMeta.argNames
						.get(0)));
		assertEquals("0L", target.getArgValue(testMethodMeta,
				targetMethodMeta.argTypes.get(1), targetMethodMeta.argNames
						.get(1)));
		assertEquals("new ArrayList<String>()", target.getArgValue(
				testMethodMeta, targetMethodMeta.argTypes.get(2),
				targetMethodMeta.argNames.get(2)));
		assertEquals("new HashMap<String, Object>()", target.getArgValue(
				testMethodMeta, targetMethodMeta.argTypes.get(3),
				targetMethodMeta.argNames.get(3)));
		assertEquals("mock(java.util.HashMap.class)", target.getArgValue(
				testMethodMeta, targetMethodMeta.argTypes.get(4),
				targetMethodMeta.argNames.get(4)));
	}

	@Test
	public void getArgValue_A$TestMethodMeta$ArgTypeMeta$String_EasyMock()
			throws Exception {
		// given
		Configulation config = new Configulation();
		config.mockObjectFramework = MockObjectFramework.EasyMock;
		DefaultTestMethodGenerator target = new DefaultTestMethodGenerator(
				config);
		ClassMetaExtractor classMetaExtractor = new ClassMetaExtractor(config);
		String sourceCodeString = "package hoge.foo; import java.util.List; import java.util.Map; public class Sample { public int doSomething(String str, long longValue, List<String> list, Map<String,Object> map, java.util.HashMap<String, String> hashMap) throws Throwable { System.out.println(\"aaaa\") } }";
		ClassMeta targetClassMeta = classMetaExtractor
				.extract(sourceCodeString);
		target.initialize(targetClassMeta);
		MethodMeta targetMethodMeta = targetClassMeta.methods.get(0);
		TestMethodMeta testMethodMeta = target
				.getTestMethodMeta(targetMethodMeta);
		// when
		// then
		assertEquals("null", target.getArgValue(testMethodMeta,
				targetMethodMeta.argTypes.get(0), targetMethodMeta.argNames
						.get(0)));
		assertEquals("0L", target.getArgValue(testMethodMeta,
				targetMethodMeta.argTypes.get(1), targetMethodMeta.argNames
						.get(1)));
		assertEquals("new ArrayList<String>()", target.getArgValue(
				testMethodMeta, targetMethodMeta.argTypes.get(2),
				targetMethodMeta.argNames.get(2)));
		assertEquals("new HashMap<String, Object>()", target.getArgValue(
				testMethodMeta, targetMethodMeta.argTypes.get(3),
				targetMethodMeta.argNames.get(3)));
		assertEquals("mocks.createMock(java.util.HashMap.class)", target
				.getArgValue(testMethodMeta, targetMethodMeta.argTypes.get(4),
						targetMethodMeta.argNames.get(4)));
	}

	@Test
	public void getArgValue_A$TestMethodMeta$ArgTypeMeta$String_JMock2()
			throws Exception {
		// given
		Configulation config = new Configulation();
		config.mockObjectFramework = MockObjectFramework.JMock2;
		DefaultTestMethodGenerator target = new DefaultTestMethodGenerator(
				config);
		ClassMetaExtractor classMetaExtractor = new ClassMetaExtractor(config);
		String sourceCodeString = "package hoge.foo; import java.util.List; import java.util.Map; public class Sample { public int doSomething(String str, long longValue, List<String> list, Map<String,Object> map, java.util.HashMap<String, String> hashMap) throws Throwable { System.out.println(\"aaaa\") } }";
		ClassMeta targetClassMeta = classMetaExtractor
				.extract(sourceCodeString);
		target.initialize(targetClassMeta);
		MethodMeta targetMethodMeta = targetClassMeta.methods.get(0);
		TestMethodMeta testMethodMeta = target
				.getTestMethodMeta(targetMethodMeta);
		// when
		// then
		assertEquals("null", target.getArgValue(testMethodMeta,
				targetMethodMeta.argTypes.get(0), targetMethodMeta.argNames
						.get(0)));
		assertEquals("0L", target.getArgValue(testMethodMeta,
				targetMethodMeta.argTypes.get(1), targetMethodMeta.argNames
						.get(1)));
		assertEquals("new ArrayList<String>()", target.getArgValue(
				testMethodMeta, targetMethodMeta.argTypes.get(2),
				targetMethodMeta.argNames.get(2)));
		assertEquals("new HashMap<String, Object>()", target.getArgValue(
				testMethodMeta, targetMethodMeta.argTypes.get(3),
				targetMethodMeta.argNames.get(3)));
		assertEquals("context.mock(java.util.HashMap.class)", target
				.getArgValue(testMethodMeta, targetMethodMeta.argTypes.get(4),
						targetMethodMeta.argNames.get(4)));
	}

	@Test
	public void getArgValue_A$TestMethodMeta$ArgTypeMeta$String_JMockit()
			throws Exception {
		// given
		Configulation config = new Configulation();
		config.mockObjectFramework = MockObjectFramework.JMockit;
		DefaultTestMethodGenerator target = new DefaultTestMethodGenerator(
				config);
		ClassMetaExtractor classMetaExtractor = new ClassMetaExtractor(config);
		String sourceCodeString = "package hoge.foo; import java.util.List; import java.util.Map; public class Sample { public int doSomething(String str, long longValue, List<String> list, Map<String,Object> map, java.util.HashMap<String, String> hashMap) throws Throwable { System.out.println(\"aaaa\") } }";
		ClassMeta targetClassMeta = classMetaExtractor
				.extract(sourceCodeString);
		target.initialize(targetClassMeta);
		MethodMeta targetMethodMeta = targetClassMeta.methods.get(0);
		TestMethodMeta testMethodMeta = target
				.getTestMethodMeta(targetMethodMeta);
		// when
		// then
		assertEquals("null", target.getArgValue(testMethodMeta,
				targetMethodMeta.argTypes.get(0), targetMethodMeta.argNames
						.get(0)));
		assertEquals("0L", target.getArgValue(testMethodMeta,
				targetMethodMeta.argTypes.get(1), targetMethodMeta.argNames
						.get(1)));
		assertEquals("new ArrayList<String>()", target.getArgValue(
				testMethodMeta, targetMethodMeta.argTypes.get(2),
				targetMethodMeta.argNames.get(2)));
		assertEquals("new HashMap<String, Object>()", target.getArgValue(
				testMethodMeta, targetMethodMeta.argTypes.get(3),
				targetMethodMeta.argNames.get(3)));
		assertEquals(
				"this.doSomething_A$String$long$List$Map$javautilHashMap_hashMap",
				target.getArgValue(testMethodMeta, targetMethodMeta.argTypes
						.get(4), targetMethodMeta.argNames.get(4)));
	}

}
