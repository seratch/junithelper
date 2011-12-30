package org.junithelper.core.generator;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.junithelper.core.config.Configuration;
import org.junithelper.core.config.JUnitVersion;
import org.junithelper.core.config.MockObjectFramework;
import org.junithelper.core.config.TestingPatternExplicitComment;
import org.junithelper.core.exception.JUnitHelperCoreException;
import org.junithelper.core.extractor.ClassMetaExtractor;
import org.junithelper.core.meta.ArgTypeMeta;
import org.junithelper.core.meta.ClassMeta;
import org.junithelper.core.meta.CurrentLineBreak;
import org.junithelper.core.meta.ExceptionMeta;
import org.junithelper.core.meta.MethodMeta;
import org.junithelper.core.meta.TestMethodMeta;

public class TestMethodGeneratorImplTest {

    Configuration config = new Configuration();
    LineBreakProvider lineBreakProvider = new LineBreakProvider(config, CurrentLineBreak.CRLF);
    TestMethodGeneratorImpl generator = new TestMethodGeneratorImpl(config, lineBreakProvider);
    ClassMetaExtractor classMetaExtractor = new ClassMetaExtractor(config);

    @Test
    public void type() throws Exception {
        assertNotNull(TestMethodGeneratorImpl.class);
    }

    @Test
    public void instantiation() throws Exception {
        Configuration config = null;
        TestMethodGeneratorImpl target = new TestMethodGeneratorImpl(config, lineBreakProvider);
        assertNotNull(target);
    }

    @Test
    public void initialize_A$ClassMeta() throws Exception {
        // given
        String sourceCodeString = "package hoge.foo; import java.util.List; public class Sample { public Sample() {}\r\n public int doSomething(String str, long longValue) throws Throwable { System.out.println(\"aaaa\") } }";
        ClassMeta targetClassMeta = classMetaExtractor.extract(sourceCodeString);
        // when
        generator.initialize(targetClassMeta);
        // then
    }

    @Test
    public void getTestMethodMeta_A$MethodMeta() throws Exception {
        // given
        String sourceCodeString = "package hoge.foo; import java.util.List; public class Sample { public Sample() {}\r\n public int doSomething(String str, long longValue) throws Throwable { System.out.println(\"aaaa\") } }";
        ClassMeta targetClassMeta = classMetaExtractor.extract(sourceCodeString);
        MethodMeta targetMethodMeta = targetClassMeta.methods.get(0);
        generator.initialize(targetClassMeta);
        // when
        TestMethodMeta actual = generator.getTestMethodMeta(targetMethodMeta);
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
        ClassMeta targetClassMeta = classMetaExtractor.extract(sourceCodeString);
        MethodMeta targetMethodMeta = targetClassMeta.methods.get(0);
        ExceptionMeta exception = new ExceptionMeta();
        exception.name = "Exception";
        exception.nameInMethodName = "Exception";
        generator.initialize(targetClassMeta);
        // when
        TestMethodMeta actual = generator.getTestMethodMeta(targetMethodMeta, exception);
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
        ClassMeta targetClassMeta = classMetaExtractor.extract(sourceCodeString);
        generator.initialize(targetClassMeta);
        MethodMeta targetMethodMeta = targetClassMeta.methods.get(0);
        TestMethodMeta testMethodMeta = generator.getTestMethodMeta(targetMethodMeta);
        // when
        String actual = generator.getTestMethodNamePrefix(testMethodMeta);
        // then
        String expected = "doSomething_A$String$long";
        assertEquals(expected, actual);
    }

    @Test
    public void getTestMethodNamePrefix_A$TestMethodMeta$ExceptionMeta() throws Exception {
        // given
        String sourceCodeString = "package hoge.foo; import java.util.List; public class Sample { public Sample() {}\r\n public int doSomething(String str, long longValue) throws Throwable { System.out.println(\"aaaa\") } }";
        ClassMeta targetClassMeta = classMetaExtractor.extract(sourceCodeString);
        generator.initialize(targetClassMeta);
        MethodMeta targetMethodMeta = targetClassMeta.methods.get(0);
        TestMethodMeta testMethodMeta = generator.getTestMethodMeta(targetMethodMeta);
        ExceptionMeta exception = new ExceptionMeta();
        exception.name = "Exception";
        exception.nameInMethodName = "Exception";
        // when
        String actual = generator.getTestMethodNamePrefix(testMethodMeta, exception);
        // then
        String expected = "doSomething_A$String$long_T$Exception";
        assertEquals(expected, actual);
    }

    @Test
    public void getTestMethodSourceCode_A$TestMethodMeta_JUnit4() throws Exception {
        // given
        String sourceCodeString = "package hoge.foo; import java.util.List; public class Sample { public Sample() {}\r\n public int doSomething(String str, long longValue) throws Throwable { System.out.println(\"aaaa\") } }";
        ClassMeta targetClassMeta = classMetaExtractor.extract(sourceCodeString);
        generator.initialize(targetClassMeta);
        MethodMeta targetMethodMeta = targetClassMeta.methods.get(0);
        TestMethodMeta testMethodMeta = generator.getTestMethodMeta(targetMethodMeta);
        // when
        String actual = generator.getTestMethodSourceCode(testMethodMeta);
        // then
        String expected = "	@Test\r\n	public void doSomething_A$String$long() throws Throwable {\r\n		// TODO auto-generated by JUnit Helper.\r\n		Sample target = new Sample();\r\n		String str = null;\r\n		long longValue = 0L;\r\n		int actual = target.doSomething(str, longValue);\r\n		int expected = 0;\r\n		assertThat(actual, is(equalTo(expected)));\r\n	}\r\n";
        assertEquals(expected, actual);
    }

    @Test
    public void getTestMethodSourceCode_A$TestMethodMeta_ArrangeActAssert_JUnit3() throws Exception {
        // given
        Configuration config = new Configuration();
        config.junitVersion = JUnitVersion.version3;
        config.testingPatternExplicitComment = TestingPatternExplicitComment.ArrangeActAssert;
        TestMethodGeneratorImpl localGenerator = new TestMethodGeneratorImpl(config, lineBreakProvider);
        String sourceCodeString = "package hoge.foo; import java.util.List; public class Sample { public Sample() {}\r\n public int doSomething(String str, long longValue) throws Throwable { System.out.println(\"aaaa\") } }";
        ClassMeta targetClassMeta = classMetaExtractor.extract(sourceCodeString);
        localGenerator.initialize(targetClassMeta);
        MethodMeta targetMethodMeta = targetClassMeta.methods.get(0);
        TestMethodMeta testMethodMeta = localGenerator.getTestMethodMeta(targetMethodMeta);
        // when
        String actual = localGenerator.getTestMethodSourceCode(testMethodMeta);
        // then
        String expected = "	public void test_doSomething_A$String$long() throws Throwable {\r\n		// TODO auto-generated by JUnit Helper.\r\n		// Arrange\r\n		Sample target = new Sample();\r\n		String str = null;\r\n		long longValue = 0L;\r\n		// Act\r\n		int actual = target.doSomething(str, longValue);\r\n		// Assert\r\n		int expected = 0;\r\n		assertEquals(expected, actual);\r\n	}\r\n";
        assertEquals(expected, actual);
    }

    @Test
    public void getTestMethodSourceCode_A$TestMethodMeta_GivenWhenThen_JUnit3() throws Exception {
        // given
        Configuration config = new Configuration();
        config.junitVersion = JUnitVersion.version3;
        config.testingPatternExplicitComment = TestingPatternExplicitComment.GivenWhenThen;
        TestMethodGeneratorImpl localGenerator = new TestMethodGeneratorImpl(config, lineBreakProvider);
        String sourceCodeString = "package hoge.foo; import java.util.List; public class Sample { public Sample() {}\r\n public int doSomething(String str, long longValue) throws Throwable { System.out.println(\"aaaa\") } }";
        ClassMeta targetClassMeta = classMetaExtractor.extract(sourceCodeString);
        localGenerator.initialize(targetClassMeta);
        MethodMeta targetMethodMeta = targetClassMeta.methods.get(0);
        TestMethodMeta testMethodMeta = localGenerator.getTestMethodMeta(targetMethodMeta);
        // when
        String actual = localGenerator.getTestMethodSourceCode(testMethodMeta);
        // then
        String expected = "	public void test_doSomething_A$String$long() throws Throwable {\r\n		// TODO auto-generated by JUnit Helper.\r\n		// Given\r\n		Sample target = new Sample();\r\n		String str = null;\r\n		long longValue = 0L;\r\n		// When\r\n		int actual = target.doSomething(str, longValue);\r\n		// Then\r\n		int expected = 0;\r\n		assertEquals(expected, actual);\r\n	}\r\n";
        assertEquals(expected, actual);
    }

    @Test
    public void getTestMethodSourceCode_A$TestMethodMeta_JUnit3() throws Exception {
        // given
        Configuration config = new Configuration();
        config.junitVersion = JUnitVersion.version3;
        config.testingPatternExplicitComment = TestingPatternExplicitComment.GivenWhenThen;
        TestMethodGeneratorImpl localGenerator = new TestMethodGeneratorImpl(config, lineBreakProvider);
        String sourceCodeString = "package hoge.foo; import java.util.List; public class Sample { public Sample() {}\r\n public int doSomething(String str, long longValue) throws Throwable { System.out.println(\"aaaa\") } }";
        ClassMeta targetClassMeta = classMetaExtractor.extract(sourceCodeString);
        localGenerator.initialize(targetClassMeta);
        MethodMeta targetMethodMeta = targetClassMeta.methods.get(0);
        TestMethodMeta testMethodMeta = localGenerator.getTestMethodMeta(targetMethodMeta);
        // when
        String actual = localGenerator.getTestMethodSourceCode(testMethodMeta);
        // then
        String expected = "	public void test_doSomething_A$String$long() throws Throwable {\r\n		// TODO auto-generated by JUnit Helper.\r\n		// Given\r\n		Sample target = new Sample();\r\n		String str = null;\r\n		long longValue = 0L;\r\n		// When\r\n		int actual = target.doSomething(str, longValue);\r\n		// Then\r\n		int expected = 0;\r\n		assertEquals(expected, actual);\r\n	}\r\n";
        assertEquals(expected, actual);
    }

    @Test
    public void getTestMethodSourceCode_A$TestMethodMeta_ArrangeActAssert_JUnit4() throws Exception {
        // given
        Configuration config = new Configuration();
        config.testingPatternExplicitComment = TestingPatternExplicitComment.ArrangeActAssert;
        TestMethodGeneratorImpl localGenerator = new TestMethodGeneratorImpl(config, lineBreakProvider);
        String sourceCodeString = "package hoge.foo; import java.util.List; public class Sample { public Sample() {}\r\n public int doSomething(String str, long longValue) throws Throwable { System.out.println(\"aaaa\") } }";
        ClassMeta targetClassMeta = classMetaExtractor.extract(sourceCodeString);
        localGenerator.initialize(targetClassMeta);
        MethodMeta targetMethodMeta = targetClassMeta.methods.get(0);
        TestMethodMeta testMethodMeta = localGenerator.getTestMethodMeta(targetMethodMeta);
        // when
        String actual = localGenerator.getTestMethodSourceCode(testMethodMeta);
        // then
        String expected = "	@Test\r\n	public void doSomething_A$String$long() throws Throwable {\r\n		// TODO auto-generated by JUnit Helper.\r\n		// Arrange\r\n		Sample target = new Sample();\r\n		String str = null;\r\n		long longValue = 0L;\r\n		// Act\r\n		int actual = target.doSomething(str, longValue);\r\n		// Assert\r\n		int expected = 0;\r\n		assertThat(actual, is(equalTo(expected)));\r\n	}\r\n";
        assertEquals(expected, actual);
    }

    @Test
    public void getTestMethodSourceCode_A$TestMethodMeta_GivenWhenThen_JUnit4() throws Exception {
        // given
        Configuration config = new Configuration();
        config.testingPatternExplicitComment = TestingPatternExplicitComment.GivenWhenThen;
        TestMethodGeneratorImpl localGenerator = new TestMethodGeneratorImpl(config, lineBreakProvider);
        String sourceCodeString = "package hoge.foo; import java.util.List; public class Sample { public Sample() {}\r\n public int doSomething(String str, long longValue) throws Throwable { System.out.println(\"aaaa\") } }";
        ClassMeta targetClassMeta = classMetaExtractor.extract(sourceCodeString);
        localGenerator.initialize(targetClassMeta);
        MethodMeta targetMethodMeta = targetClassMeta.methods.get(0);
        TestMethodMeta testMethodMeta = localGenerator.getTestMethodMeta(targetMethodMeta);
        // when
        String actual = localGenerator.getTestMethodSourceCode(testMethodMeta);
        // then
        String expected = "	@Test\r\n	public void doSomething_A$String$long() throws Throwable {\r\n		// TODO auto-generated by JUnit Helper.\r\n		// Given\r\n		Sample target = new Sample();\r\n		String str = null;\r\n		long longValue = 0L;\r\n		// When\r\n		int actual = target.doSomething(str, longValue);\r\n		// Then\r\n		int expected = 0;\r\n		assertThat(actual, is(equalTo(expected)));\r\n	}\r\n";
        assertEquals(expected, actual);
    }

    @Test
    public void appendPreparingArgs_A$StringBuilder$TestMethodMeta() throws Exception {
        // given
        StringBuilder buf = new StringBuilder();
        String sourceCodeString = "package hoge.foo; import java.util.List; public class Sample { public Sample() {}\r\n public int doSomething(String str, long longValue) throws Throwable { System.out.println(\"aaaa\") } }";
        ClassMeta targetClassMeta = classMetaExtractor.extract(sourceCodeString);
        generator.initialize(targetClassMeta);
        MethodMeta targetMethodMeta = targetClassMeta.methods.get(0);
        TestMethodMeta testMethodMeta = generator.getTestMethodMeta(targetMethodMeta);
        // when
        generator.appendPreparingArgs(buf, testMethodMeta);
        // then
        assertEquals("		String str = null;\r\n		long longValue = 0L;\r\n", buf.toString());
    }

    @Test
    public void appendMockChecking_A$StringBuilder$int_depth0() throws Exception {
        // given
        StringBuilder buf = new StringBuilder();
        int depth = 0;
        // when
        generator.appendMockChecking(buf, depth);
        // then
        assertEquals("", buf.toString());
    }

    @Test
    public void appendMockChecking_A$StringBuilder$int_depth1() throws Exception {
        // given
        StringBuilder buf = new StringBuilder();
        int depth = 1;
        // when
        generator.appendMockChecking(buf, depth);
        // then
        assertEquals("", buf.toString());
    }

    @Test
    public void appendMockChecking_A$StringBuilder$int_depth2() throws Exception {
        // given
        StringBuilder buf = new StringBuilder();
        int depth = 2;
        // when
        generator.appendMockChecking(buf, depth);
        // then
        assertEquals("", buf.toString());
    }

    @Test
    public void appendMockChecking_A$StringBuilder$int_EasyMock() throws Exception {
        Configuration config = new Configuration();
        config.mockObjectFramework = MockObjectFramework.EasyMock;
        TestMethodGeneratorImpl target = new TestMethodGeneratorImpl(config, lineBreakProvider);
        // given
        StringBuilder buf = new StringBuilder();
        int depth = 2;
        // e.g. : given(mocked.called()).willReturn(1);
        // when
        target.appendMockChecking(buf, depth);
        // then
        assertEquals("\t\t// e.g. : EasyMock.expect(mocked.called()).andReturn(1);\r\n		mocks.replay();\r\n", buf
                .toString());
    }

    @Test
    public void appendMockChecking_A$StringBuilder$int_JMock2() throws Exception {
        Configuration config = new Configuration();
        config.mockObjectFramework = MockObjectFramework.JMock2;
        TestMethodGeneratorImpl target = new TestMethodGeneratorImpl(config, lineBreakProvider);
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
    public void appendMockChecking_A$StringBuilder$int_JMockit() throws Exception {
        Configuration config = new Configuration();
        config.mockObjectFramework = MockObjectFramework.JMockit;
        TestMethodGeneratorImpl target = new TestMethodGeneratorImpl(config, lineBreakProvider);
        // given
        StringBuilder buf = new StringBuilder();
        int depth = 2;
        // e.g. : given(mocked.called()).willReturn(1);
        // when
        target.appendMockChecking(buf, depth);
        // then
        assertEquals("\t\tnew Expectations(){{\r\n			// e.g. : mocked.get(anyString); returns(200);\r\n		}};\r\n", buf
                .toString());
    }

    @Test
    public void appendMockChecking_A$StringBuilder$int_Mockito() throws Exception {
        Configuration config = new Configuration();
        config.mockObjectFramework = MockObjectFramework.Mockito;
        TestMethodGeneratorImpl target = new TestMethodGeneratorImpl(config, lineBreakProvider);
        // given
        StringBuilder buf = new StringBuilder();
        int depth = 2;
        // e.g. : given(mocked.called()).willReturn(1);
        // when
        target.appendMockChecking(buf, depth);
        // then
        assertEquals("\t\t// e.g. : given(mocked.called()).willReturn(1);\r\n", buf.toString());
    }

    @Test
    public void appendMockVerifying_A$StringBuilder$int_depth0() throws Exception {
        // given
        StringBuilder buf = new StringBuilder();
        int depth = 0;
        // when
        generator.appendMockVerifying(buf, depth);
        // then
        assertEquals("", buf.toString());
    }

    @Test
    public void appendMockVerifying_A$StringBuilder$int_depth1() throws Exception {
        // given
        StringBuilder buf = new StringBuilder();
        int depth = 1;
        // when
        generator.appendMockVerifying(buf, depth);
        // then
        assertEquals("", buf.toString());
    }

    @Test
    public void appendMockVerifying_A$StringBuilder$int_depth2() throws Exception {
        // given
        StringBuilder buf = new StringBuilder();
        int depth = 2;
        // when
        generator.appendMockVerifying(buf, depth);
        // then
        assertEquals("", buf.toString());
    }

    @Test
    public void appendExecutingTargetMethod_A$StringBuilder$TestMethodMeta() throws Exception {
        // given
        StringBuilder buf = new StringBuilder();
        String sourceCodeString = "package hoge.foo; import java.util.List; public class Sample { public Sample() {}\r\n public int doSomething(String str, long longValue) throws Throwable { System.out.println(\"aaaa\") } }";
        ClassMeta targetClassMeta = classMetaExtractor.extract(sourceCodeString);
        generator.initialize(targetClassMeta);
        MethodMeta targetMethodMeta = targetClassMeta.methods.get(0);
        TestMethodMeta testMethodMeta = generator.getTestMethodMeta(targetMethodMeta);
        // when
        generator.appendExecutingTargetMethod(buf, testMethodMeta);
        // then
        assertEquals("target.doSomething(str, longValue);\r\n", buf.toString());
    }

    @Test
    public void appendBDDMockitoComment_A$StringBuilder$String$int_depth0() throws Exception {
        // given
        StringBuilder buf = new StringBuilder();
        String value = null;
        int depth = 0;
        // when
        generator.appendBDDMockitoComment(buf, value, depth);
        // then
        assertEquals("", buf.toString());
    }

    @Test
    public void appendBDDMockitoComment_A$StringBuilder$String$int_depth1() throws Exception {
        // given
        StringBuilder buf = new StringBuilder();
        String value = null;
        int depth = 1;
        // when
        generator.appendBDDMockitoComment(buf, value, depth);
        // then
        assertEquals("", buf.toString());
    }

    @Test
    public void appendBDDMockitoComment_A$StringBuilder$String$int_depth2() throws Exception {
        // given
        StringBuilder buf = new StringBuilder();
        String value = null;
        int depth = 2;
        // when
        generator.appendBDDMockitoComment(buf, value, depth);
        // then
        assertEquals("", buf.toString());
    }

    @Test
    public void getMockedFieldsForJMockit_A$TestMethodMeta() throws Exception {
        // given
        config.mockObjectFramework = MockObjectFramework.JMockit;
        generator = new TestMethodGeneratorImpl(config, lineBreakProvider);
        String sourceCodeString = "package hoge.foo; import java.util.List;import fuga.Bean; public class Sample { public Sample() {}\r\n public int doSomething(String str, long longValue, Bean bean) throws Throwable { System.out.println(\"aaaa\") } }";
        ClassMeta targetClassMeta = classMetaExtractor.extract(sourceCodeString);
        generator.initialize(targetClassMeta);
        MethodMeta targetMethodMeta = targetClassMeta.methods.get(0);
        TestMethodMeta testMethodMeta = generator.getTestMethodMeta(targetMethodMeta);
        // when
        List<String> actual = generator.getMockedFieldsForJMockit(testMethodMeta);
        // then
        // e.g. : verify(mocked).called();
        assertEquals(1, actual.size());
    }

    @Test
    public void getArgValue_A$TestMethodMeta$ArgTypeMeta$String() throws Exception {
        // given
        String sourceCodeString = "package hoge.foo; import java.util.List; import java.util.Map; public class Sample { public int doSomething(String str, long longValue, List<String> list, Map<String,Object> map, java.util.HashMap<String, String> hashMap, List<?> list2, Map<?,Object> map2) throws Throwable { System.out.println(\"aaaa\") } }";
        ClassMeta targetClassMeta = classMetaExtractor.extract(sourceCodeString);
        generator.initialize(targetClassMeta);
        MethodMeta targetMethodMeta = targetClassMeta.methods.get(0);
        TestMethodMeta testMethodMeta = generator.getTestMethodMeta(targetMethodMeta);
        // when
        // then
        assertEquals("null", generator.getArgValue(testMethodMeta, targetMethodMeta.argTypes.get(0),
                targetMethodMeta.argNames.get(0)));
        assertEquals("0L", generator.getArgValue(testMethodMeta, targetMethodMeta.argTypes.get(1),
                targetMethodMeta.argNames.get(1)));
        assertEquals("new ArrayList<String>()", generator.getArgValue(testMethodMeta, targetMethodMeta.argTypes.get(2),
                targetMethodMeta.argNames.get(2)));
        assertEquals("new HashMap<String, Object>()", generator.getArgValue(testMethodMeta, targetMethodMeta.argTypes
                .get(3), targetMethodMeta.argNames.get(3)));
        assertEquals("null", generator.getArgValue(testMethodMeta, targetMethodMeta.argTypes.get(4),
                targetMethodMeta.argNames.get(4)));
        assertEquals("new ArrayList()", generator.getArgValue(testMethodMeta, targetMethodMeta.argTypes.get(5),
                targetMethodMeta.argNames.get(5)));
        assertEquals("new HashMap()", generator.getArgValue(testMethodMeta, targetMethodMeta.argTypes.get(6),
                targetMethodMeta.argNames.get(6)));
    }

    @Test
    public void getArgValue_A$TestMethodMeta$ArgTypeMeta$String_Mockito() throws Exception {
        // given
        Configuration config = new Configuration();
        config.mockObjectFramework = MockObjectFramework.Mockito;
        TestMethodGeneratorImpl target = new TestMethodGeneratorImpl(config, lineBreakProvider);
        ClassMetaExtractor classMetaExtractor = new ClassMetaExtractor(config);
        String sourceCodeString = "package hoge.foo; import java.util.List; import java.util.Map; public class Sample { public int doSomething(String str, long longValue, List<String> list, Map<String,Object> map, java.util.HashMap<String, String> hashMap) throws Throwable { System.out.println(\"aaaa\") } }";
        ClassMeta targetClassMeta = classMetaExtractor.extract(sourceCodeString);
        target.initialize(targetClassMeta);
        MethodMeta targetMethodMeta = targetClassMeta.methods.get(0);
        TestMethodMeta testMethodMeta = target.getTestMethodMeta(targetMethodMeta);
        // when
        // then
        assertEquals("null", target.getArgValue(testMethodMeta, targetMethodMeta.argTypes.get(0),
                targetMethodMeta.argNames.get(0)));
        assertEquals("0L", target.getArgValue(testMethodMeta, targetMethodMeta.argTypes.get(1),
                targetMethodMeta.argNames.get(1)));
        assertEquals("new ArrayList<String>()", target.getArgValue(testMethodMeta, targetMethodMeta.argTypes.get(2),
                targetMethodMeta.argNames.get(2)));
        assertEquals("new HashMap<String, Object>()", target.getArgValue(testMethodMeta, targetMethodMeta.argTypes
                .get(3), targetMethodMeta.argNames.get(3)));
        assertEquals("mock(java.util.HashMap.class)", target.getArgValue(testMethodMeta, targetMethodMeta.argTypes
                .get(4), targetMethodMeta.argNames.get(4)));
    }

    @Test
    public void getArgValue_A$TestMethodMeta$ArgTypeMeta$String_EasyMock() throws Exception {
        // given
        Configuration config = new Configuration();
        config.mockObjectFramework = MockObjectFramework.EasyMock;
        TestMethodGeneratorImpl target = new TestMethodGeneratorImpl(config, lineBreakProvider);
        ClassMetaExtractor classMetaExtractor = new ClassMetaExtractor(config);
        String sourceCodeString = "package hoge.foo; import java.util.List; import java.util.Map; public class Sample { public int doSomething(String str, long longValue, List<String> list, Map<String,Object> map, java.util.HashMap<String, String> hashMap) throws Throwable { System.out.println(\"aaaa\") } }";
        ClassMeta targetClassMeta = classMetaExtractor.extract(sourceCodeString);
        target.initialize(targetClassMeta);
        MethodMeta targetMethodMeta = targetClassMeta.methods.get(0);
        TestMethodMeta testMethodMeta = target.getTestMethodMeta(targetMethodMeta);
        // when
        // then
        assertEquals("null", target.getArgValue(testMethodMeta, targetMethodMeta.argTypes.get(0),
                targetMethodMeta.argNames.get(0)));
        assertEquals("0L", target.getArgValue(testMethodMeta, targetMethodMeta.argTypes.get(1),
                targetMethodMeta.argNames.get(1)));
        assertEquals("new ArrayList<String>()", target.getArgValue(testMethodMeta, targetMethodMeta.argTypes.get(2),
                targetMethodMeta.argNames.get(2)));
        assertEquals("new HashMap<String, Object>()", target.getArgValue(testMethodMeta, targetMethodMeta.argTypes
                .get(3), targetMethodMeta.argNames.get(3)));
        assertEquals("mocks.createMock(java.util.HashMap.class)", target.getArgValue(testMethodMeta,
                targetMethodMeta.argTypes.get(4), targetMethodMeta.argNames.get(4)));
    }

    @Test
    public void getArgValue_A$TestMethodMeta$ArgTypeMeta$String_JMock2() throws Exception {
        // given
        Configuration config = new Configuration();
        config.mockObjectFramework = MockObjectFramework.JMock2;
        TestMethodGeneratorImpl target = new TestMethodGeneratorImpl(config, lineBreakProvider);
        ClassMetaExtractor classMetaExtractor = new ClassMetaExtractor(config);
        String sourceCodeString = "package hoge.foo; import java.util.List; import java.util.Map; public class Sample { public int doSomething(String str, long longValue, List<String> list, Map<String,Object> map, java.util.HashMap<String, String> hashMap) throws Throwable { System.out.println(\"aaaa\") } }";
        ClassMeta targetClassMeta = classMetaExtractor.extract(sourceCodeString);
        target.initialize(targetClassMeta);
        MethodMeta targetMethodMeta = targetClassMeta.methods.get(0);
        TestMethodMeta testMethodMeta = target.getTestMethodMeta(targetMethodMeta);
        // when
        // then
        assertEquals("null", target.getArgValue(testMethodMeta, targetMethodMeta.argTypes.get(0),
                targetMethodMeta.argNames.get(0)));
        assertEquals("0L", target.getArgValue(testMethodMeta, targetMethodMeta.argTypes.get(1),
                targetMethodMeta.argNames.get(1)));
        assertEquals("new ArrayList<String>()", target.getArgValue(testMethodMeta, targetMethodMeta.argTypes.get(2),
                targetMethodMeta.argNames.get(2)));
        assertEquals("new HashMap<String, Object>()", target.getArgValue(testMethodMeta, targetMethodMeta.argTypes
                .get(3), targetMethodMeta.argNames.get(3)));
        assertEquals("context.mock(java.util.HashMap.class)", target.getArgValue(testMethodMeta,
                targetMethodMeta.argTypes.get(4), targetMethodMeta.argNames.get(4)));
    }

    @Test
    public void getArgValue_A$TestMethodMeta$ArgTypeMeta$String_JMockit() throws Exception {
        // given
        Configuration config = new Configuration();
        config.mockObjectFramework = MockObjectFramework.JMockit;
        TestMethodGeneratorImpl target = new TestMethodGeneratorImpl(config, lineBreakProvider);
        ClassMetaExtractor classMetaExtractor = new ClassMetaExtractor(config);
        String sourceCodeString = "package hoge.foo; import java.util.List; import java.util.Map; public class Sample { public int doSomething(String str, long longValue, List<String> list, Map<String,Object> map, java.util.HashMap<String, String> hashMap) throws Throwable { System.out.println(\"aaaa\") } }";
        ClassMeta targetClassMeta = classMetaExtractor.extract(sourceCodeString);
        target.initialize(targetClassMeta);
        MethodMeta targetMethodMeta = targetClassMeta.methods.get(0);
        TestMethodMeta testMethodMeta = target.getTestMethodMeta(targetMethodMeta);
        // when
        // then
        assertEquals("null", target.getArgValue(testMethodMeta, targetMethodMeta.argTypes.get(0),
                targetMethodMeta.argNames.get(0)));
        assertEquals("0L", target.getArgValue(testMethodMeta, targetMethodMeta.argTypes.get(1),
                targetMethodMeta.argNames.get(1)));
        assertEquals("new ArrayList<String>()", target.getArgValue(testMethodMeta, targetMethodMeta.argTypes.get(2),
                targetMethodMeta.argNames.get(2)));
        assertEquals("new HashMap<String, Object>()", target.getArgValue(testMethodMeta, targetMethodMeta.argTypes
                .get(3), targetMethodMeta.argNames.get(3)));
        assertEquals("this.doSomething_A$String$long$List$Map$javautilHashMap_hashMap", target.getArgValue(
                testMethodMeta, targetMethodMeta.argTypes.get(4), targetMethodMeta.argNames.get(4)));
    }

    @Test
    public void appendTestingPatternExplicitComment_A$StringBuilder$String$int_ArrangeActAssert() throws Exception {
        // Arrange
        Configuration config = new Configuration();
        config.testingPatternExplicitComment = TestingPatternExplicitComment.ArrangeActAssert;
        TestMethodGeneratorImpl target = new TestMethodGeneratorImpl(config, lineBreakProvider);
        StringBuilder buf = new StringBuilder();
        String value = "Act";
        int depth = 0;
        // Act
        target.appendTestingPatternExplicitComment(buf, value, depth);
        // Assert
        assertThat(buf.toString(), is(equalTo("// Act\r\n")));
    }

    @Test
    public void appendTestingPatternExplicitComment_A$StringBuilder$String$int_GivenWhenThen() throws Exception {
        // Arrange
        Configuration config = new Configuration();
        config.testingPatternExplicitComment = TestingPatternExplicitComment.GivenWhenThen;
        TestMethodGeneratorImpl target = new TestMethodGeneratorImpl(config, lineBreakProvider);
        StringBuilder buf = new StringBuilder();
        String value = "Act";
        int depth = 0;
        // Act
        target.appendTestingPatternExplicitComment(buf, value, depth);
        // Assert
        assertThat(buf.toString(), is(equalTo("// When\r\n")));
    }

    @Test
    public void appendMockChecking_A$StringBuilder$int_intIsMinus1() throws Exception {
        TestMethodGeneratorImpl target = new TestMethodGeneratorImpl(config, lineBreakProvider);
        StringBuilder buf = new StringBuilder();
        int depth = -1;
        target.appendMockChecking(buf, depth);
    }

    @Test
    public void appendMockChecking_A$StringBuilder$int_intIs0() throws Exception {
        TestMethodGeneratorImpl target = new TestMethodGeneratorImpl(config, lineBreakProvider);
        StringBuilder buf = new StringBuilder();
        int depth = 0;
        target.appendMockChecking(buf, depth);
    }

    @Test
    public void appendMockChecking_A$StringBuilder$int_intIs1() throws Exception {
        TestMethodGeneratorImpl target = new TestMethodGeneratorImpl(config, lineBreakProvider);
        StringBuilder buf = new StringBuilder();
        int depth = 1;
        target.appendMockChecking(buf, depth);
    }

    @Test
    public void appendMockChecking_A$StringBuilder$int_intIs2() throws Exception {
        TestMethodGeneratorImpl target = new TestMethodGeneratorImpl(config, lineBreakProvider);
        StringBuilder buf = new StringBuilder();
        int depth = 2;
        target.appendMockChecking(buf, depth);
    }

    @Test
    public void appendMockVerifying_A$StringBuilder$int_intIsMinus1() throws Exception {
        TestMethodGeneratorImpl target = new TestMethodGeneratorImpl(config, lineBreakProvider);
        StringBuilder buf = new StringBuilder();
        int depth = -1;
        target.appendMockVerifying(buf, depth);
    }

    @Test
    public void appendMockVerifying_A$StringBuilder$int_intIs0() throws Exception {
        TestMethodGeneratorImpl target = new TestMethodGeneratorImpl(config, lineBreakProvider);
        StringBuilder buf = new StringBuilder();
        int depth = 0;
        target.appendMockVerifying(buf, depth);
    }

    @Test
    public void appendMockVerifying_A$StringBuilder$int_intIs1() throws Exception {
        TestMethodGeneratorImpl target = new TestMethodGeneratorImpl(config, lineBreakProvider);
        StringBuilder buf = new StringBuilder();
        int depth = 1;
        target.appendMockVerifying(buf, depth);
    }

    @Test
    public void appendMockVerifying_A$StringBuilder$int_intIs2() throws Exception {
        TestMethodGeneratorImpl target = new TestMethodGeneratorImpl(config, lineBreakProvider);
        StringBuilder buf = new StringBuilder();
        int depth = 2;
        target.appendMockVerifying(buf, depth);
    }

    @Test
    public void appendBDDMockitoComment_A$StringBuilder$String$int_intIsMinus1() throws Exception {
        TestMethodGeneratorImpl target = new TestMethodGeneratorImpl(config, lineBreakProvider);
        StringBuilder buf = new StringBuilder();
        String value = null;
        int depth = -1;
        target.appendBDDMockitoComment(buf, value, depth);
    }

    @Test
    public void appendBDDMockitoComment_A$StringBuilder$String$int_intIs0() throws Exception {
        TestMethodGeneratorImpl target = new TestMethodGeneratorImpl(config, lineBreakProvider);
        StringBuilder buf = new StringBuilder();
        String value = null;
        int depth = 0;
        target.appendBDDMockitoComment(buf, value, depth);
    }

    @Test
    public void appendBDDMockitoComment_A$StringBuilder$String$int_intIs1() throws Exception {
        TestMethodGeneratorImpl target = new TestMethodGeneratorImpl(config, lineBreakProvider);
        StringBuilder buf = new StringBuilder();
        String value = null;
        int depth = 1;
        target.appendBDDMockitoComment(buf, value, depth);
    }

    @Test
    public void appendBDDMockitoComment_A$StringBuilder$String$int_intIs2() throws Exception {
        TestMethodGeneratorImpl target = new TestMethodGeneratorImpl(config, lineBreakProvider);
        StringBuilder buf = new StringBuilder();
        String value = null;
        int depth = 2;
        target.appendBDDMockitoComment(buf, value, depth);
    }

    @Test
    public void appendBDDMockitoComment_A$StringBuilder$String$int_StringIsNull() throws Exception {
        TestMethodGeneratorImpl target = new TestMethodGeneratorImpl(config, lineBreakProvider);
        StringBuilder buf = new StringBuilder();
        String value = null;
        int depth = 0;
        target.appendBDDMockitoComment(buf, value, depth);
    }

    @Test
    public void appendBDDMockitoComment_A$StringBuilder$String$int_StringIsEmpty() throws Exception {
        TestMethodGeneratorImpl target = new TestMethodGeneratorImpl(config, lineBreakProvider);
        StringBuilder buf = new StringBuilder();
        String value = "";
        int depth = 0;
        target.appendBDDMockitoComment(buf, value, depth);
    }

    @Test
    public void appendTestingPatternExplicitComment_A$StringBuilder$String$int_intIsMinus1() throws Exception {
        TestMethodGeneratorImpl target = new TestMethodGeneratorImpl(config, lineBreakProvider);
        StringBuilder buf = new StringBuilder();
        String value = null;
        int depth = -1;
        target.appendTestingPatternExplicitComment(buf, value, depth);
    }

    @Test
    public void appendTestingPatternExplicitComment_A$StringBuilder$String$int_intIs0() throws Exception {
        TestMethodGeneratorImpl target = new TestMethodGeneratorImpl(config, lineBreakProvider);
        StringBuilder buf = new StringBuilder();
        String value = null;
        int depth = 0;
        target.appendTestingPatternExplicitComment(buf, value, depth);
    }

    @Test
    public void appendTestingPatternExplicitComment_A$StringBuilder$String$int_intIs1() throws Exception {
        TestMethodGeneratorImpl target = new TestMethodGeneratorImpl(config, lineBreakProvider);
        StringBuilder buf = new StringBuilder();
        String value = null;
        int depth = 1;
        target.appendTestingPatternExplicitComment(buf, value, depth);
    }

    @Test
    public void appendTestingPatternExplicitComment_A$StringBuilder$String$int_intIs2() throws Exception {
        TestMethodGeneratorImpl target = new TestMethodGeneratorImpl(config, lineBreakProvider);
        StringBuilder buf = new StringBuilder();
        String value = null;
        int depth = 2;
        target.appendTestingPatternExplicitComment(buf, value, depth);
    }

    @Test
    public void appendTestingPatternExplicitComment_A$StringBuilder$String$int_StringIsNull() throws Exception {
        TestMethodGeneratorImpl target = new TestMethodGeneratorImpl(config, lineBreakProvider);
        StringBuilder buf = new StringBuilder();
        String value = null;
        int depth = 0;
        target.appendTestingPatternExplicitComment(buf, value, depth);
    }

    @Test
    public void appendTestingPatternExplicitComment_A$StringBuilder$String$int_StringIsEmpty() throws Exception {
        TestMethodGeneratorImpl target = new TestMethodGeneratorImpl(config, lineBreakProvider);
        StringBuilder buf = new StringBuilder();
        String value = "";
        int depth = 0;
        target.appendTestingPatternExplicitComment(buf, value, depth);
    }

    @Test
    public void getArgValue_A$TestMethodMeta$ArgTypeMeta$String_StringIsNull() throws Exception {
        TestMethodGeneratorImpl target = new TestMethodGeneratorImpl(config, lineBreakProvider);
        TestMethodMeta testMethodMeta = new TestMethodMeta();
        ArgTypeMeta argTypeMeta = new ArgTypeMeta();
        argTypeMeta.name = "java.lang.String";
        String argName = "name";
        String actual = target.getArgValue(testMethodMeta, argTypeMeta, argName);
        String expected = "null";
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void getArgValue_A$TestMethodMeta$ArgTypeMeta$String_StringIsEmpty() throws Exception {
        TestMethodGeneratorImpl target = new TestMethodGeneratorImpl(config, lineBreakProvider);
        TestMethodMeta testMethodMeta = new TestMethodMeta();
        ArgTypeMeta argTypeMeta = new ArgTypeMeta();
        argTypeMeta.name = "";
        String argName = "";
        try {
            target.getArgValue(testMethodMeta, argTypeMeta, argName);
            fail();
        } catch (JUnitHelperCoreException e) {
        }
    }

    @Test
    public void getArgValue_A$TestMethodMeta$ArgTypeMeta$String_PrimitiveValue() throws Exception {
        TestMethodGeneratorImpl target = new TestMethodGeneratorImpl(config, lineBreakProvider);
        TestMethodMeta testMethodMeta = new TestMethodMeta();
        ArgTypeMeta argTypeMeta = new ArgTypeMeta();
        argTypeMeta.name = "int";
        String argName = "name";
        String actual = target.getArgValue(testMethodMeta, argTypeMeta, argName);
        String expected = "0";
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void appendMockChecking_A$StringBuilder$int_intIsMinValue() throws Exception {
        TestMethodGeneratorImpl target = new TestMethodGeneratorImpl(config, lineBreakProvider);
        StringBuilder buf = new StringBuilder();
        int depth = Integer.MIN_VALUE;
        target.appendMockChecking(buf, depth);
    }

    @Test
    public void appendMockChecking_A$StringBuilder$int_intIsMaxValue() throws Exception {
        TestMethodGeneratorImpl target = new TestMethodGeneratorImpl(config, lineBreakProvider);
        StringBuilder buf = new StringBuilder();
        int depth = Integer.MAX_VALUE;
        target.appendMockChecking(buf, depth);
    }

    @Test
    public void appendMockVerifying_A$StringBuilder$int_intIsMinValue() throws Exception {
        TestMethodGeneratorImpl target = new TestMethodGeneratorImpl(config, lineBreakProvider);
        StringBuilder buf = new StringBuilder();
        int depth = Integer.MIN_VALUE;
        target.appendMockVerifying(buf, depth);
    }

    @Test
    public void appendMockVerifying_A$StringBuilder$int_intIsMaxValue() throws Exception {
        TestMethodGeneratorImpl target = new TestMethodGeneratorImpl(config, lineBreakProvider);
        StringBuilder buf = new StringBuilder();
        int depth = Integer.MAX_VALUE;
        target.appendMockVerifying(buf, depth);
    }

    @Test
    public void appendBDDMockitoComment_A$StringBuilder$String$int_intIsMinValue() throws Exception {
        TestMethodGeneratorImpl target = new TestMethodGeneratorImpl(config, lineBreakProvider);
        StringBuilder buf = new StringBuilder();
        String value = "when";
        int depth = Integer.MIN_VALUE;
        target.appendBDDMockitoComment(buf, value, depth);
    }

    @Test
    public void appendBDDMockitoComment_A$StringBuilder$String$int_intIsMaxValue() throws Exception {
        TestMethodGeneratorImpl target = new TestMethodGeneratorImpl(config, lineBreakProvider);
        StringBuilder buf = new StringBuilder();
        String value = "when";
        int depth = Integer.MAX_VALUE;
        target.appendBDDMockitoComment(buf, value, depth);
    }

    @Test
    public void appendTestingPatternExplicitComment_A$StringBuilder$String$int_intIsMinValue() throws Exception {
        TestMethodGeneratorImpl target = new TestMethodGeneratorImpl(config, lineBreakProvider);
        StringBuilder buf = new StringBuilder();
        String value = "when";
        int depth = Integer.MIN_VALUE;
        target.appendTestingPatternExplicitComment(buf, value, depth);
    }

    @Test
    public void appendTestingPatternExplicitComment_A$StringBuilder$String$int_intIsMaxValue() throws Exception {
        TestMethodGeneratorImpl target = new TestMethodGeneratorImpl(config, lineBreakProvider);
        StringBuilder buf = new StringBuilder();
        String value = "when";
        int depth = Integer.MAX_VALUE;
        target.appendTestingPatternExplicitComment(buf, value, depth);
    }

    @Test
    public void appendMockChecking_A$StringBuilder$int_intIsRandom() throws Exception {
        TestMethodGeneratorImpl target = new TestMethodGeneratorImpl(config, lineBreakProvider);
        StringBuilder buf = new StringBuilder();
        int depth = new Random().nextInt(10);
        target.appendMockChecking(buf, depth);
    }

    @Test
    public void appendMockVerifying_A$StringBuilder$int_intIsRandom() throws Exception {
        TestMethodGeneratorImpl target = new TestMethodGeneratorImpl(config, lineBreakProvider);
        StringBuilder buf = new StringBuilder();
        int depth = new Random().nextInt(10);
        target.appendMockVerifying(buf, depth);
    }

    @Test
    public void appendBDDMockitoComment_A$StringBuilder$String$int_intIsRandom() throws Exception {
        TestMethodGeneratorImpl target = new TestMethodGeneratorImpl(config, lineBreakProvider);
        StringBuilder buf = new StringBuilder();
        String value = "tmp";
        int depth = new Random().nextInt(10);
        target.appendBDDMockitoComment(buf, value, depth);
    }

    @Test
    public void appendTestingPatternExplicitComment_A$StringBuilder$String$int_intIsRandom() throws Exception {
        TestMethodGeneratorImpl target = new TestMethodGeneratorImpl(config, lineBreakProvider);
        StringBuilder buf = new StringBuilder();
        String value = "test";
        int depth = new Random().nextInt(10);
        target.appendTestingPatternExplicitComment(buf, value, depth);
    }

}
