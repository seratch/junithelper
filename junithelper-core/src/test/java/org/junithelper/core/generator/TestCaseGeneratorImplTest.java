package org.junithelper.core.generator;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junithelper.core.config.Configuration;
import org.junithelper.core.config.JUnitVersion;
import org.junithelper.core.config.extension.ExtConfigurationLoader;
import org.junithelper.core.exception.JUnitHelperCoreException;
import org.junithelper.core.extractor.ClassMetaExtractor;
import org.junithelper.core.meta.AccessModifier;
import org.junithelper.core.meta.ArgTypeMeta;
import org.junithelper.core.meta.ClassMeta;
import org.junithelper.core.meta.CurrentLineBreak;
import org.junithelper.core.meta.ReturnTypeMeta;
import org.junithelper.core.meta.TestCaseMeta;
import org.junithelper.core.meta.TestMethodMeta;
import org.junithelper.core.util.IOUtil;
import org.junithelper.core.util.UniversalDetectorUtil;

public class TestCaseGeneratorImplTest {

    Configuration config;
    LineBreakProvider lineBreakProvider;
    TestCaseGeneratorImpl target;
    ClassMetaExtractor classMetaExtractor;

    @Before
    public void setUp() {
        config = new Configuration();
        lineBreakProvider = new LineBreakProvider(config, CurrentLineBreak.CRLF);
        target = new TestCaseGeneratorImpl(config, lineBreakProvider);
        classMetaExtractor = new ClassMetaExtractor(config);
    }

    @Test
    public void type() throws Exception {
        assertNotNull(TestCaseGeneratorImpl.class);
    }

    @Test
    public void instantiation() throws Exception {
        Configuration config = new Configuration();
        lineBreakProvider = new LineBreakProvider(config, CurrentLineBreak.CRLF);
        TestCaseGeneratorImpl target = new TestCaseGeneratorImpl(config, lineBreakProvider);
        assertNotNull(target);
    }

    @Test
    public void initialize_A$ClassMeta() throws Exception {
        String sourceCodeString = "package hoge.foo; import java.util.List; public class Sample { public Sample() {}\r\n public int doSomething(String str, long longValue) throws Throwable { System.out.println(\"aaaa\") } }";
        ClassMeta targetClassMeta = classMetaExtractor.extract(sourceCodeString);
        target.initialize(targetClassMeta);
    }

    @Test
    public void getNewTestCaseMeta_A$() throws Exception {

        // given
        String sourceCodeString = "package hoge.foo; import java.util.List; import java.util.Map; public class Sample { public Sample() {}\r\n public Map<String, List<String>> doSomething(String str, long longValue, Map<String,Obeject> map, Map<String,List<String>> nested, List<List<String>> nestedList, String arr[], String arrOfArr[][]) throws Throwable { System.out.println(\"aaaa\") } }";
        // when
        ClassMeta targetClassMeta = classMetaExtractor.extract(sourceCodeString);
        target.initialize(targetClassMeta);
        TestCaseMeta actual = target.getNewTestCaseMeta();

        // then
        assertEquals("Sample", actual.target.name);
        assertEquals(1, actual.target.constructors.size());
        assertEquals(1, actual.target.methods.size());
        assertEquals("doSomething", actual.target.methods.get(0).name);

        List<ArgTypeMeta> argTypes = actual.target.methods.get(0).argTypes;
        assertEquals("String", argTypes.get(0).name);
        assertEquals("long", argTypes.get(1).name);
        assertEquals("Map<String, Object>", argTypes.get(2).name);
        assertEquals("String", argTypes.get(2).generics.get(0));
        assertEquals("Object", argTypes.get(2).generics.get(1));
        assertEquals("Map", argTypes.get(3).name);
        assertEquals(0, argTypes.get(3).generics.size());
        assertEquals("List", argTypes.get(4).name);
        assertEquals(0, argTypes.get(4).generics.size());
        assertEquals("String[]", argTypes.get(5).name);
        assertEquals("arr", actual.target.methods.get(0).argNames.get(5));
        assertEquals("String[][]", argTypes.get(6).name);
        assertEquals("arrOfArr", actual.target.methods.get(0).argNames.get(6));

        ReturnTypeMeta returnType = actual.target.methods.get(0).returnType;
        assertEquals("Map", returnType.name);

    }

    @Test
    public void getNewTestCaseMeta_A$_2() throws Exception {
        String encoding = UniversalDetectorUtil.getDetectedEncoding(IOUtil
                .getResourceAsStream("inputs/DefaultTestCaseGenerator.txt"));
        String sourceCodeString = IOUtil.readAsString(
                IOUtil.getResourceAsStream("inputs/DefaultTestCaseGenerator.txt"), encoding);
        ClassMeta targetClassMeta = classMetaExtractor.extract(sourceCodeString);
        target.initialize(targetClassMeta);
        TestCaseMeta actual = target.getNewTestCaseMeta();
        assertEquals("DefaultTestCaseGenerator", actual.target.name);
        assertEquals(1, actual.target.constructors.size());
        assertEquals(6, actual.target.methods.size());
    }

    @Test
    public void getNewTestCaseMeta_A$_3() throws Exception {
        String encoding = UniversalDetectorUtil.getDetectedEncoding(IOUtil
                .getResourceAsStream("inputs/DefaultTestCaseGenerator.txt"));
        String sourceCodeString = IOUtil.readAsString(IOUtil.getResourceAsStream("inputs/ObjectUtil.txt"), encoding);
        ClassMeta targetClassMeta = classMetaExtractor.extract(sourceCodeString);
        target.initialize(targetClassMeta);
        TestCaseMeta actual = target.getNewTestCaseMeta();
        assertEquals("ObjectUtil", actual.target.name);
        assertEquals(1, actual.target.constructors.size());
        assertEquals(AccessModifier.Private, actual.target.constructors.get(0).accessModifier);
        assertEquals(2, actual.target.methods.size());
    }

    @Test
    public void getNewTestCaseMeta_A$_Log() throws Exception {
        String encoding = UniversalDetectorUtil.getDetectedEncoding(IOUtil
                .getResourceAsStream("inputs/DefaultTestCaseGenerator_Log.txt"));
        String sourceCodeString = IOUtil.readAsString(IOUtil
                .getResourceAsStream("inputs/DefaultTestCaseGenerator_Log.txt"), encoding);
        ClassMeta targetClassMeta = classMetaExtractor.extract(sourceCodeString);
        target.initialize(targetClassMeta);
        TestCaseMeta actual = target.getNewTestCaseMeta();
        assertEquals("Log", actual.target.name);
        assertEquals(2, actual.target.constructors.size());
        assertEquals("Class", actual.target.constructors.get(0).argTypes.get(0).name);
        assertEquals("String", actual.target.constructors.get(1).argTypes.get(0).name);
        assertEquals(23, actual.target.methods.size());
    }

    @Test
    public void getNewTestCaseSourceCode_A$_SimpleHttpClient() throws Exception {
        String encoding = UniversalDetectorUtil.getDetectedEncoding(IOUtil
                .getResourceAsStream("inputs/DefaultTestCaseGenerator_SimpleHttpClient.txt"));
        String sourceCodeString = IOUtil.readAsString(IOUtil
                .getResourceAsStream("inputs/DefaultTestCaseGenerator_SimpleHttpClient.txt"), encoding);
        ClassMeta targetClassMeta = classMetaExtractor.extract(sourceCodeString);
        target.initialize(targetClassMeta);
        String actual = target.getNewTestCaseSourceCode();
        assertNotNull(actual);
    }

    @Test
    public void getNewTestCaseSourceCode_A$_Slim3_AbstractModelRef() throws Exception {
        String encoding = UniversalDetectorUtil.getDetectedEncoding(IOUtil
                .getResourceAsStream("inputs/Slim3_AbstractModelRef.txt"));
        String sourceCodeString = IOUtil.readAsString(IOUtil.getResourceAsStream("inputs/Slim3_AbstractModelRef.txt"),
                encoding);
        ClassMeta targetClassMeta = classMetaExtractor.extract(sourceCodeString);
        target.initialize(targetClassMeta);
        String actual = target.getNewTestCaseSourceCode();
        assertNotNull(actual);
    }

    @Test
    public void getLackingTestMethodMetaList_A$String_AnsiDialect() throws Exception {
        String encoding = UniversalDetectorUtil.getDetectedEncoding(IOUtil
                .getResourceAsStream("inputs/DefaultTestCaseGenerator_AnsiDialect.txt"));
        String sourceCodeString = IOUtil.readAsString(IOUtil
                .getResourceAsStream("inputs/DefaultTestCaseGenerator_AnsiDialect.txt"), encoding);
        ClassMeta targetClassMeta = classMetaExtractor.extract(sourceCodeString);
        target.initialize(targetClassMeta);
        String currentTestCaseSourceCode = IOUtil.readAsString(IOUtil
                .getResourceAsStream("inputs/DefaultTestCaseGenerator_AnsiDialectTest.txt"), encoding);
        List<TestMethodMeta> actual = target.getLackingTestMethodMetaList(currentTestCaseSourceCode);
        assertEquals("", 0, actual.size());
    }

    @Test
    public void getLackingTestMethodMetaList_A$String_TrimFilterManager() throws Exception {
        String encoding = UniversalDetectorUtil.getDetectedEncoding(IOUtil
                .getResourceAsStream("inputs/TrimFilterManager.txt"));
        String sourceCodeString = IOUtil.readAsString(IOUtil.getResourceAsStream("inputs/TrimFilterManager.txt"),
                encoding);
        ClassMeta targetClassMeta = classMetaExtractor.extract(sourceCodeString);
        target.initialize(targetClassMeta);
        String currentTestCaseSourceCode = IOUtil.readAsString(IOUtil
                .getResourceAsStream("inputs/TrimFilterManagerTest.txt"), encoding);
        List<TestMethodMeta> actual = target.getLackingTestMethodMetaList(currentTestCaseSourceCode);
        assertEquals("", 0, actual.size());
    }

    /**
     * Issue 65: Generated invalid test code when "testCaseClassNameToExtend" is
     * empty.
     * 
     * @see {@link http://code.google.com/p/junithelper/issues/detail?id=65}
     */
    @Test
    public void getNewTestCaseSourceCode_A$_testCaseClassNameToExtendIsEmpty() throws Exception {
        String sourceCodeString = "package com.example; public class Sample {}";
        ClassMeta targetClassMeta = classMetaExtractor.extract(sourceCodeString);
        target.initialize(targetClassMeta);
        config.testCaseClassNameToExtend = "";
        String actual = target.getNewTestCaseSourceCode();
        String expected = "package com.example;\r\n\r\nimport com.example.Sample.*;\r\nimport static org.hamcrest.CoreMatchers.*;\r\nimport static org.junit.Assert.*;\r\nimport org.junit.Test;\r\n\r\n\r\npublic class SampleTest {\r\n\r\n	@Test\r\n	public void type() throws Exception {\r\n		// TODO auto-generated by JUnit Helper.\r\n		assertThat(Sample.class, notNullValue());\r\n	}\r\n\r\n	@Test\r\n	public void instantiation() throws Exception {\r\n		// TODO auto-generated by JUnit Helper.\r\n		Sample target = new Sample();\r\n		assertThat(target, notNullValue());\r\n	}\r\n\r\n}\r\n";
        assertThat(actual, is(equalTo(expected)));
    }

    /**
     * Issue 66: Test code generator does not work fine for default package
     * class.
     * 
     * @see {@link http://code.google.com/p/junithelper/issues/detail?id=66}
     */
    @Test
    public void getNewTestCaseSourceCode_A$_packageIsEmpty() throws Exception {
        String sourceCodeString = "public class Sample {}";
        ClassMeta targetClassMeta = classMetaExtractor.extract(sourceCodeString);
        target.initialize(targetClassMeta);
        config.testCaseClassNameToExtend = "";
        String actual = target.getNewTestCaseSourceCode();
        String expected = "import Sample.*;\r\nimport static org.hamcrest.CoreMatchers.*;\r\nimport static org.junit.Assert.*;\r\nimport org.junit.Test;\r\n\r\npublic class SampleTest {\r\n\r\n	@Test\r\n	public void type() throws Exception {\r\n		// TODO auto-generated by JUnit Helper.\r\n		assertThat(Sample.class, notNullValue());\r\n	}\r\n\r\n	@Test\r\n	public void instantiation() throws Exception {\r\n		// TODO auto-generated by JUnit Helper.\r\n		Sample target = new Sample();\r\n		assertThat(target, notNullValue());\r\n	}\r\n\r\n}\r\n";
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void getLackingTestMethodMetaList_A$String_Ext_NotEnabled() throws Exception {
        // ext config
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("junithelper-extension.xml");
        config.extConfiguration = new ExtConfigurationLoader().load(is);

        String sourceCodeString = "package hoge.foo; import java.util.List; public class Sample { public Sample() {}\r\n public int doSomething(String str, long longValue) throws Throwable { System.out.println(\"aaaa\") } public void overload(String str) { } public void overload(String str, Object obj) { } }";
        ClassMeta targetClassMeta = classMetaExtractor.extract(sourceCodeString);
        target.initialize(targetClassMeta);
        String currentTestCaseSourceCode = "package hoge.foo; public class SampleTest extends TestCase { public void test_overload_A$String$Object() throws Exception { } }";
        List<TestMethodMeta> actual = target.getLackingTestMethodMetaList(currentTestCaseSourceCode);
        assertEquals(4, actual.size());
        assertEquals(true, actual.get(0).isTypeTest);
        assertEquals(true, actual.get(1).isInstantiationTest);
        assertEquals("doSomething", actual.get(2).methodMeta.name);
    }

    @Test
    public void getLackingTestMethodMetaList_A$String_Ext_2_NotEnabled() throws Exception {
        // ext config
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("junithelper-extension.xml");
        config.extConfiguration = new ExtConfigurationLoader().load(is);

        String sourceCodeString = "package hoge.foo; import java.util.List; public class Sample { "
                + "public int doSomething(Something something, String str, String str2, long longValue) throws Throwable { System.out.println(\"aaaa\") } "
                + "public void overload(String str) { } " + "public void overload(String str, Object obj) { } }";
        ClassMeta targetClassMeta = classMetaExtractor.extract(sourceCodeString);
        target.initialize(targetClassMeta);
        String currentTestCaseSourceCode = "package hoge.foo; public class SampleTest extends TestCase { public void test_overload_A$String$Object() throws Exception { } }";
        List<TestMethodMeta> actual = target.getLackingTestMethodMetaList(currentTestCaseSourceCode);
        assertEquals(4, actual.size());
        assertEquals(true, actual.get(0).isTypeTest);
        assertEquals(true, actual.get(1).isInstantiationTest);
        assertEquals("doSomething", actual.get(2).methodMeta.name);
    }

    @Test
    public void getLackingTestMethodMetaList_A$String_Ext() throws Exception {
        // ext config
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("junithelper-extension.xml");
        config.isExtensionEnabled = true;
        config.extConfiguration = new ExtConfigurationLoader().load(is);

        String sourceCodeString = "package hoge.foo; import java.util.List; public class Sample { public Sample() {}\r\n public int doSomething(String str, long longValue) throws Throwable { System.out.println(\"aaaa\") } public void overload(String str) { } public void overload(String str, Object obj) { } }";
        ClassMeta targetClassMeta = classMetaExtractor.extract(sourceCodeString);
        target.initialize(targetClassMeta);
        String currentTestCaseSourceCode = "package hoge.foo; public class SampleTest extends TestCase { public void test_overload_A$String$Object() throws Exception { } }";
        List<TestMethodMeta> actual = target.getLackingTestMethodMetaList(currentTestCaseSourceCode);
        assertEquals(10, actual.size());
        assertEquals(true, actual.get(0).isTypeTest);
        assertEquals(true, actual.get(1).isInstantiationTest);
        assertEquals("doSomething", actual.get(2).methodMeta.name);
    }

    @Test
    public void getLackingTestMethodMetaList_A$String_Ext_2() throws Exception {
        // ext config
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("junithelper-extension.xml");
        config.isExtensionEnabled = true;
        config.extConfiguration = new ExtConfigurationLoader().load(is);

        String sourceCodeString = "package hoge.foo; import java.util.List; public class Sample { "
                + "public int doSomething(Something something, String str, String str2, long longValue) throws Throwable { System.out.println(\"aaaa\") } "
                + "public void overload(String str) { } " + "public void overload(String str, Object obj) { } }";
        ClassMeta targetClassMeta = classMetaExtractor.extract(sourceCodeString);
        target.initialize(targetClassMeta);
        String currentTestCaseSourceCode = "package hoge.foo; public class SampleTest extends TestCase { public void test_overload_A$String$Object() throws Exception { } }";
        List<TestMethodMeta> actual = target.getLackingTestMethodMetaList(currentTestCaseSourceCode);
        assertEquals(10, actual.size());
        assertEquals(true, actual.get(0).isTypeTest);
        assertEquals(true, actual.get(1).isInstantiationTest);
        assertEquals("doSomething", actual.get(2).methodMeta.name);
        String testSourceCode = new TestMethodGeneratorImpl(config, lineBreakProvider).getTestMethodSourceCode(actual
                .get(4));
        assertEquals(
                "	@Test\r\n	public void doSomething_A$Object$String$String$long_StringIsHoge() throws Throwable {\r\n		// TODO auto-generated by JUnit Helper.\r\n		Sample target = new Sample();\r\n		Object something = null;\r\n		String str = \"hoge\";\r\n		String str2 = \"hoge\";\r\n		long longValue = 0L;\r\n		int actual = target.doSomething(something, str, str2, longValue);\r\n		int expected = 0;\r\n		assertThat(actual, is(equalTo(expected)));\r\n	}\r\n",
                testSourceCode);
    }

    @Test
    public void getLackingTestMethodMetaList_A$String_Slim3_AbstractModelRef() throws Exception {
        String encoding = UniversalDetectorUtil.getDetectedEncoding(IOUtil
                .getResourceAsStream("inputs/Slim3_AbstractModelRef.txt"));
        String sourceCodeString = IOUtil.readAsString(IOUtil.getResourceAsStream("inputs/Slim3_AbstractModelRef.txt"),
                encoding);
        ClassMeta targetClassMeta = classMetaExtractor.extract(sourceCodeString);
        target.initialize(targetClassMeta);
        String currentTestCaseSourceCode = "";
        List<TestMethodMeta> actual = target.getLackingTestMethodMetaList(currentTestCaseSourceCode);
        assertEquals("", 6, actual.size());
    }

    @Test
    public void getLackingTestMethodMetaList_A$String_IOUtil() throws Exception {
        String encoding = UniversalDetectorUtil.getDetectedEncoding(IOUtil.getResourceAsStream("inputs/IOUtil.txt"));
        String sourceCodeString = IOUtil.readAsString(IOUtil.getResourceAsStream("inputs/IOUtil.txt"), encoding);
        ClassMeta targetClassMeta = classMetaExtractor.extract(sourceCodeString);
        target.initialize(targetClassMeta);
        String currentTestCaseSourceCode = "";
        List<TestMethodMeta> actual = target.getLackingTestMethodMetaList(currentTestCaseSourceCode);
        assertThat(actual.size(), is(equalTo(10)));
        assertThat(actual.get(2).methodMeta.argTypes.get(0).name, is(equalTo("InputStream")));
    }

    @Test
    public void getLackingTestMethodMetaList_A$String() throws Exception {
        String sourceCodeString = "package hoge.foo; import java.util.List; public class Sample { public Sample() {}\r\n public int doSomething(String str, long longValue) throws Throwable { System.out.println(\"aaaa\") } public void overload(String str) { } public void overload(String str, Object obj) { } }";
        ClassMeta targetClassMeta = classMetaExtractor.extract(sourceCodeString);
        target.initialize(targetClassMeta);
        String currentTestCaseSourceCode = "package hoge.foo; public class SampleTest extends TestCase { public void test_overload_A$String$Object() throws Exception { } }";
        List<TestMethodMeta> actual = target.getLackingTestMethodMetaList(currentTestCaseSourceCode);
        assertEquals(4, actual.size());
        assertEquals(true, actual.get(0).isTypeTest);
        assertEquals(true, actual.get(1).isInstantiationTest);
        assertEquals("doSomething", actual.get(2).methodMeta.name);
        assertEquals("overload", actual.get(3).methodMeta.name);
    }

    @Test
    public void getLackingTestMethodMetaList_A$String_Overload() throws Exception {
        String sourceCodeString = "public class Sample { public void doSomething(String str) throws Throwable {} public void doSomething() throws Throwable {} }";
        ClassMeta targetClassMeta = classMetaExtractor.extract(sourceCodeString);
        target.initialize(targetClassMeta);
        String currentTestCaseSourceCode = "package hoge.foo; public class SampleTest extends TestCase { public void test_doSomething_A$String() throws Exception { } }";
        List<TestMethodMeta> actual = target.getLackingTestMethodMetaList(currentTestCaseSourceCode);
        assertEquals(3, actual.size());
        assertEquals(true, actual.get(0).isTypeTest);
        assertEquals(true, actual.get(1).isInstantiationTest);
        assertEquals("doSomething", actual.get(2).methodMeta.name);
    }

    @Test
    public void getNewTestCaseSourceCode_A$_JUnit3() throws Exception {
        Configuration config = new Configuration();
        config.junitVersion = JUnitVersion.version3;
        TestCaseGeneratorImpl target = new TestCaseGeneratorImpl(config, lineBreakProvider);
        String sourceCodeString = "package hoge.foo; import java.util.List;\r\nimport java.util.Map; public class Sample { public Sample() {}\r\n public int doSomething(String str, long longValue) throws Throwable { System.out.println(\"aaaa\"); } public void doSomething(List<Map<String, String>> nested, Map<String,Map<String,String>> nested2) { } }";
        ClassMeta targetClassMeta = classMetaExtractor.extract(sourceCodeString);
        target.initialize(targetClassMeta);
        String actual = target.getNewTestCaseSourceCode();
        String expected = "package hoge.foo;\r\n\r\nimport java.util.ArrayList;\r\nimport java.util.HashMap;\r\nimport hoge.foo.Sample.*;\r\n\r\nimport java.util.List;\r\nimport java.util.Map;\r\nimport junit.framework.TestCase;\r\n\r\npublic class SampleTest extends TestCase {\r\n\r\n	public void test_type() throws Exception {\r\n		// TODO auto-generated by JUnit Helper.\r\n		assertNotNull(Sample.class);\r\n	}\r\n\r\n	public void test_instantiation() throws Exception {\r\n		// TODO auto-generated by JUnit Helper.\r\n		Sample target = new Sample();\r\n		assertNotNull(target);\r\n	}\r\n\r\n	public void test_doSomething_A$String$long() throws Throwable {\r\n		// TODO auto-generated by JUnit Helper.\r\n		Sample target = new Sample();\r\n		String str = null;\r\n		long longValue = 0L;\r\n		int actual = target.doSomething(str, longValue);\r\n		int expected = 0;\r\n		assertEquals(expected, actual);\r\n	}\r\n\r\n	public void test_doSomething_A$List$Map() throws Exception {\r\n		// TODO auto-generated by JUnit Helper.\r\n		Sample target = new Sample();\r\n		List nested = new ArrayList();\r\n		Map nested2 = new HashMap();\r\n		target.doSomething(nested, nested2);\r\n	}\r\n\r\n}\r\n";
        assertEquals(expected, actual);
    }

    @Test
    public void getNewTestCaseSourceCode_A$_JUnit4() throws Exception {
        String sourceCodeString = "package hoge.foo; import java.util.List;\r\nimport java.util.Map; public class Sample { public Sample() {}\r\n public int doSomething(String str, long longValue) throws Throwable { System.out.println(\"aaaa\"); } public void doSomething(List<Map<String, String>> nested, Map<String,Map<String,String>> nested2) { } }";
        ClassMeta targetClassMeta = classMetaExtractor.extract(sourceCodeString);
        target.initialize(targetClassMeta);
        String actual = target.getNewTestCaseSourceCode();
        String expected = "package hoge.foo;\r\n\r\nimport java.util.ArrayList;\r\nimport java.util.HashMap;\r\nimport hoge.foo.Sample.*;\r\nimport static org.hamcrest.CoreMatchers.*;\r\nimport static org.junit.Assert.*;\r\nimport org.junit.Test;\r\n\r\nimport java.util.List;\r\nimport java.util.Map;\r\npublic class SampleTest {\r\n\r\n	@Test\r\n	public void type() throws Exception {\r\n		// TODO auto-generated by JUnit Helper.\r\n		assertThat(Sample.class, notNullValue());\r\n	}\r\n\r\n	@Test\r\n	public void instantiation() throws Exception {\r\n		// TODO auto-generated by JUnit Helper.\r\n		Sample target = new Sample();\r\n		assertThat(target, notNullValue());\r\n	}\r\n\r\n	@Test\r\n	public void doSomething_A$String$long() throws Throwable {\r\n		// TODO auto-generated by JUnit Helper.\r\n		Sample target = new Sample();\r\n		String str = null;\r\n		long longValue = 0L;\r\n		int actual = target.doSomething(str, longValue);\r\n		int expected = 0;\r\n		assertThat(actual, is(equalTo(expected)));\r\n	}\r\n\r\n	@Test\r\n	public void doSomething_A$List$Map() throws Exception {\r\n		// TODO auto-generated by JUnit Helper.\r\n		Sample target = new Sample();\r\n		List nested = new ArrayList();\r\n		Map nested2 = new HashMap();\r\n		target.doSomething(nested, nested2);\r\n	}\r\n\r\n}\r\n";
        assertEquals(expected, actual);
    }

    @Test
    public void getTestCaseSourceCodeWithLackingTestMethod_A$String() throws Exception {
        String sourceCodeString = "package hoge.foo; import java.util.List; public class Sample { public Sample() {}\r\n public int doSomething(String str, long longValue) throws Throwable { System.out.println(\"aaaa\") } }";
        ClassMeta targetClassMeta = classMetaExtractor.extract(sourceCodeString);
        target.initialize(targetClassMeta);
        String currentTestCaseSourceCode = "package hoge.foo;\r\n\r\nimport java.util.List;\r\n\r\npublic class SampleTest {\r\n\r\n}\r\n";
        String actual = target.getTestCaseSourceCodeWithLackingTestMethod(currentTestCaseSourceCode);
        String expected = "package hoge.foo;\r\n\r\nimport hoge.foo.Sample.*;\r\nimport static org.hamcrest.CoreMatchers.*;\r\nimport static org.junit.Assert.*;\r\nimport org.junit.Test;\r\n\r\nimport java.util.List;\r\n\r\npublic class SampleTest {\r\n\r\n	@Test\r\n	public void type() throws Exception {\r\n		// TODO auto-generated by JUnit Helper.\r\n		assertThat(Sample.class, notNullValue());\r\n	}\r\n\r\n	@Test\r\n	public void instantiation() throws Exception {\r\n		// TODO auto-generated by JUnit Helper.\r\n		Sample target = new Sample();\r\n		assertThat(target, notNullValue());\r\n	}\r\n\r\n	@Test\r\n	public void doSomething_A$String$long() throws Throwable {\r\n		// TODO auto-generated by JUnit Helper.\r\n		Sample target = new Sample();\r\n		String str = null;\r\n		long longValue = 0L;\r\n		int actual = target.doSomething(str, longValue);\r\n		int expected = 0;\r\n		assertThat(actual, is(equalTo(expected)));\r\n	}\r\n\r\n}\r\n";
        assertEquals(expected, actual);
    }

    @Test
    public void initialize_A$String() throws Exception {
        String targetSourceCodeString = "package hoge; public class HogeHoge { }";
        TestCaseGeneratorImpl actual = target.initialize(targetSourceCodeString);
        assertNotNull(actual);
    }

    @Test
    public void getUnifiedVersionTestCaseSourceCode_A$String$JUnitVersion_toJUnit3() throws Exception {
        String currentTestCaseSourceCode = "package hoge;\r\nimport org.junit.Test;\r\n\r\npublic class SampleTest {\r\n\r\n\t@Test\r\n\tpublic void hogehoge() throws Excpetion {\r\n\t}\r\n\r\n }";
        String sourceCodeString = "package hoge.foo; import java.util.List; public class Sample { public Sample() {}\r\n public int doSomething(String str, long longValue) throws Throwable { System.out.println(\"aaaa\") } }";
        ClassMeta targetClassMeta = classMetaExtractor.extract(sourceCodeString);
        target.initialize(targetClassMeta);
        JUnitVersion version = JUnitVersion.version3;
        String actual = target.getUnifiedVersionTestCaseSourceCode(currentTestCaseSourceCode, version);
        String expected = "package hoge;\r\n\r\nimport java.util.List;\r\nimport hoge.foo.Sample.*;\r\nimport junit.framework.TestCase;\r\nimport org.junit.Test;\r\n\r\npublic class SampleTest extends TestCase {\r\n\r\n\tpublic void test_hogehoge() throws Excpetion {\r\n\t}\r\n\r\n }";
        assertEquals(expected, actual);
    }

    @Test
    public void getUnifiedVersionTestCaseSourceCode_A$String$JUnitVersion_toJUnit4() throws Exception {
        String currentTestCaseSourceCode = "package hoge;\r\n\r\npublic class SampleTest extends junit.framework.TestCase {\r\n\r\n\tpublic void test_hogehoge() throws Excpetion {\r\n\t}\r\n\r\n }";
        String sourceCodeString = "package hoge.foo; import java.util.List; public class Sample { public Sample() {}\r\n public int doSomething(String str, long longValue) throws Throwable { System.out.println(\"aaaa\") } }";
        ClassMeta targetClassMeta = classMetaExtractor.extract(sourceCodeString);
        target.initialize(targetClassMeta);
        JUnitVersion version = JUnitVersion.version4;
        String actual = target.getUnifiedVersionTestCaseSourceCode(currentTestCaseSourceCode, version);
        String expected = "package hoge;\r\n\r\nimport java.util.List;\r\nimport hoge.foo.Sample.*;\r\nimport static org.hamcrest.CoreMatchers.*;\r\nimport static org.junit.Assert.*;\r\nimport org.junit.Test;\r\n\r\npublic class SampleTest {\r\n\r\n\t@Test \r\n\tpublic void hogehoge() throws Excpetion {\r\n\t}\r\n\r\n }";
        assertEquals(expected, actual);
    }

    @Test
    public void getUnifiedVersionTestCaseSourceCode_A$String$JUnitVersion_toJUnit4_SuperClassWhenImportedTestCase()
            throws Exception {
        String currentTestCaseSourceCode = "package hoge;\r\n\r\nimport junit.framework.TestCase;\r\n\r\npublic class SampleTest extends TestCase {\r\n\r\n\tpublic void test_hogehoge() throws Excpetion {\r\n\t}\r\n\r\n }";
        String sourceCodeString = "package hoge.foo; import java.util.List; public class Sample { public Sample() {}\r\n public int doSomething(String str, long longValue) throws Throwable { System.out.println(\"aaaa\") } }";
        ClassMeta targetClassMeta = classMetaExtractor.extract(sourceCodeString);
        target.initialize(targetClassMeta);
        JUnitVersion version = JUnitVersion.version4;
        String actual = target.getUnifiedVersionTestCaseSourceCode(currentTestCaseSourceCode, version);
        String expected = "package hoge;\r\n\r\nimport java.util.List;\r\nimport hoge.foo.Sample.*;\r\nimport static org.hamcrest.CoreMatchers.*;\r\nimport static org.junit.Assert.*;\r\nimport org.junit.Test;\r\n\r\n\r\n\r\npublic class SampleTest {\r\n\r\n\t@Test \r\n\tpublic void hogehoge() throws Excpetion {\r\n\t}\r\n\r\n }";
        assertEquals(expected, actual);
    }

    @Test
    public void addTestMethodMetaToListIfNotExists_A$List$TestMethodMeta() throws Exception {
        List<TestMethodMeta> dest = new ArrayList<TestMethodMeta>();
        TestMethodMeta meta = new TestMethodMeta();
        TestCaseGeneratorImpl.addTestMethodMetaToListIfNotExists(dest, meta);
    }

    @Test
    public void appendIfExtensionAssertionsExist_A$TestMethodMeta$Configuration() throws Exception {
        TestMethodMeta testMethodMeta = new TestMethodMeta();
        TestMethodMeta actual = TestCaseGeneratorImpl.appendIfExtensionAssertionsExist(testMethodMeta, config);
        assertThat(actual, notNullValue());
    }

    @Test
    public void initialize_A$String_StringIsNull() throws Exception {
        TestCaseGeneratorImpl target = new TestCaseGeneratorImpl(config, lineBreakProvider);
        String targetSourceCodeString = null;
        try {
            target.initialize(targetSourceCodeString);
            fail();
        } catch (JUnitHelperCoreException e) {
        }
    }

    @Test
    public void initialize_A$String_StringIsEmpty() throws Exception {
        TestCaseGeneratorImpl target = new TestCaseGeneratorImpl(config, lineBreakProvider);
        String targetSourceCodeString = "";
        TestCaseGeneratorImpl actual = target.initialize(targetSourceCodeString);
        assertThat(actual, notNullValue());
    }

    @Test
    public void getLackingTestMethodMetaList_A$String_StringIsNull() throws Exception {
        TestCaseGeneratorImpl target = new TestCaseGeneratorImpl(config, lineBreakProvider);
        String currentTestCaseSourceCode = null;
        try {
            target.getLackingTestMethodMetaList(currentTestCaseSourceCode);
            fail();
        } catch (JUnitHelperCoreException e) {
        }
    }

    @Test
    public void getLackingTestMethodMetaList_A$String_StringIsEmpty() throws Exception {
        TestCaseGeneratorImpl target = new TestCaseGeneratorImpl(config, lineBreakProvider);
        String currentSourceCode = "public class Sample {}";
        target.initialize(new ClassMetaExtractor(config).extract(currentSourceCode));
        String currentTestCaseSourceCode = "";
        List<TestMethodMeta> actual = target.getLackingTestMethodMetaList(currentTestCaseSourceCode);
        assertThat(actual, notNullValue());
    }

    @Test
    public void getTestCaseSourceCodeWithLackingTestMethod_A$String_StringIsNull() throws Exception {
        TestCaseGeneratorImpl target = new TestCaseGeneratorImpl(config, lineBreakProvider);
        String currentTestCaseSourceCode = null;
        try {
            target.getTestCaseSourceCodeWithLackingTestMethod(currentTestCaseSourceCode);
            fail();
        } catch (JUnitHelperCoreException e) {
        }
    }

    @Test
    public void getTestCaseSourceCodeWithLackingTestMethod_A$String_StringIsEmpty() throws Exception {
        TestCaseGeneratorImpl target = new TestCaseGeneratorImpl(config, lineBreakProvider);
        String currentTestCaseSourceCode = "";
        try {
            target.getTestCaseSourceCodeWithLackingTestMethod(currentTestCaseSourceCode);
            fail();
        } catch (JUnitHelperCoreException e) {
        }
    }

    @Test
    public void getUnifiedVersionTestCaseSourceCode_A$String$JUnitVersion_StringIsNull() throws Exception {
        TestCaseGeneratorImpl target = new TestCaseGeneratorImpl(config, lineBreakProvider);
        String currentTestCaseSourceCode = null;
        JUnitVersion version = null;
        try {
            target.getUnifiedVersionTestCaseSourceCode(currentTestCaseSourceCode, version);
            fail();
        } catch (JUnitHelperCoreException e) {
        }
    }

    @Test
    public void getUnifiedVersionTestCaseSourceCode_A$String$JUnitVersion_StringIsEmpty() throws Exception {
        TestCaseGeneratorImpl target = new TestCaseGeneratorImpl(config, lineBreakProvider);
        String currentTestCaseSourceCode = "";
        JUnitVersion version = null;
        String actual = target.getUnifiedVersionTestCaseSourceCode(currentTestCaseSourceCode, version);
        String expected = "";
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void appendRequiredImportListToSourceCode_A$String$ClassMeta$Configuration() throws Exception {
        TestCaseGeneratorImpl target = new TestCaseGeneratorImpl(config, lineBreakProvider);
        String sourceCode = "public class Sample {}";
        ClassMeta targetClassMeta = new ClassMeta();
        String actual = target.appendRequiredImportListToSourceCode(sourceCode, targetClassMeta, config);
        assertThat(actual, notNullValue());
    }

    @Test
    public void appendRequiredImportListToSourceCode_A$String$ClassMeta$Configuration_StringIsNull() throws Exception {
        TestCaseGeneratorImpl target = new TestCaseGeneratorImpl(config, lineBreakProvider);
        String sourceCode = null;
        ClassMeta targetClassMeta = new ClassMeta();
        try {
            target.appendRequiredImportListToSourceCode(sourceCode, targetClassMeta, config);
            fail();
        } catch (JUnitHelperCoreException e) {
        }
    }

    @Test
    public void appendRequiredImportListToSourceCode_A$String$ClassMeta$Configuration_StringIsEmpty() throws Exception {
        TestCaseGeneratorImpl target = new TestCaseGeneratorImpl(config, lineBreakProvider);
        String sourceCode = "";
        ClassMeta targetClassMeta = new ClassMeta();
        String actual = target.appendRequiredImportListToSourceCode(sourceCode, targetClassMeta, config);
        assertThat(actual, notNullValue());
    }

}
