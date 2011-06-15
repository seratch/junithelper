package org.junithelper.core.generator.impl;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import java.util.List;
import org.junit.Test;
import org.junithelper.core.config.Configuration;
import org.junithelper.core.config.JUnitVersion;
import org.junithelper.core.config.TestingTarget;
import org.junithelper.core.meta.AccessModifier;
import org.junithelper.core.meta.ArgTypeMeta;
import org.junithelper.core.meta.ClassMeta;
import org.junithelper.core.meta.MethodMeta;
import org.junithelper.core.meta.ReturnTypeMeta;
import org.junithelper.core.meta.TestCaseMeta;
import org.junithelper.core.meta.TestMethodMeta;
import org.junithelper.core.meta.extractor.ClassMetaExtractor;
import org.junithelper.core.util.IOUtil;
import org.junithelper.core.util.UniversalDetectorUtil;

public class DefaultTestCaseGeneratorTest {

	Configuration config = new Configuration();
	DefaultTestCaseGenerator target = new DefaultTestCaseGenerator(config);
	ClassMetaExtractor classMetaExtractor = new ClassMetaExtractor(config);

	@Test
	public void type() throws Exception {
		assertNotNull(DefaultTestCaseGenerator.class);
	}

	@Test
	public void instantiation() throws Exception {
		Configuration config = null;
		DefaultTestCaseGenerator target = new DefaultTestCaseGenerator(config);
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
				.getResourceAsStream("parser/impl/DefaultTestCaseGenerator.txt"));
		String sourceCodeString = IOUtil.readAsString(
				IOUtil.getResourceAsStream("parser/impl/DefaultTestCaseGenerator.txt"), encoding);
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
				.getResourceAsStream("parser/impl/DefaultTestCaseGenerator.txt"));
		String sourceCodeString = IOUtil.readAsString(IOUtil.getResourceAsStream("parser/impl/ObjectUtil.txt"),
				encoding);
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
				.getResourceAsStream("parser/impl/DefaultTestCaseGenerator_Log.txt"));
		String sourceCodeString = IOUtil.readAsString(
				IOUtil.getResourceAsStream("parser/impl/DefaultTestCaseGenerator_Log.txt"), encoding);
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
				.getResourceAsStream("parser/impl/DefaultTestCaseGenerator_SimpleHttpClient.txt"));
		String sourceCodeString = IOUtil.readAsString(
				IOUtil.getResourceAsStream("parser/impl/DefaultTestCaseGenerator_SimpleHttpClient.txt"), encoding);
		ClassMeta targetClassMeta = classMetaExtractor.extract(sourceCodeString);
		target.initialize(targetClassMeta);
		String actual = target.getNewTestCaseSourceCode();
		assertNotNull(actual);
	}

	@Test
	public void getNewTestCaseSourceCode_A$_Slim3_AbstractModelRef() throws Exception {
		String encoding = UniversalDetectorUtil.getDetectedEncoding(IOUtil
				.getResourceAsStream("parser/impl/Slim3_AbstractModelRef.txt"));
		String sourceCodeString = IOUtil.readAsString(
				IOUtil.getResourceAsStream("parser/impl/Slim3_AbstractModelRef.txt"), encoding);
		ClassMeta targetClassMeta = classMetaExtractor.extract(sourceCodeString);
		target.initialize(targetClassMeta);
		String actual = target.getNewTestCaseSourceCode();
		assertNotNull(actual);
	}

	@Test
	public void getLackingTestMethodMetaList_A$String_AnsiDialect() throws Exception {
		String encoding = UniversalDetectorUtil.getDetectedEncoding(IOUtil
				.getResourceAsStream("parser/impl/DefaultTestCaseGenerator_AnsiDialect.txt"));
		String sourceCodeString = IOUtil.readAsString(
				IOUtil.getResourceAsStream("parser/impl/DefaultTestCaseGenerator_AnsiDialect.txt"), encoding);
		ClassMeta targetClassMeta = classMetaExtractor.extract(sourceCodeString);
		target.initialize(targetClassMeta);
		String currentTestCaseSourceCode = IOUtil.readAsString(
				IOUtil.getResourceAsStream("parser/impl/DefaultTestCaseGenerator_AnsiDialectTest.txt"), encoding);
		List<TestMethodMeta> actual = target.getLackingTestMethodMetaList(currentTestCaseSourceCode);
		assertEquals("", 0, actual.size());
	}

	@Test
	public void getLackingTestMethodMetaList_A$String_TrimFilterManager() throws Exception {
		String encoding = UniversalDetectorUtil.getDetectedEncoding(IOUtil
				.getResourceAsStream("parser/impl/TrimFilterManager.txt"));
		String sourceCodeString = IOUtil.readAsString(IOUtil.getResourceAsStream("parser/impl/TrimFilterManager.txt"),
				encoding);
		ClassMeta targetClassMeta = classMetaExtractor.extract(sourceCodeString);
		target.initialize(targetClassMeta);
		String currentTestCaseSourceCode = IOUtil.readAsString(
				IOUtil.getResourceAsStream("parser/impl/TrimFilterManagerTest.txt"), encoding);
		List<TestMethodMeta> actual = target.getLackingTestMethodMetaList(currentTestCaseSourceCode);
		assertEquals("", 0, actual.size());
	}

	@Test
	public void getLackingTestMethodMetaList_A$String_Slim3_AbstractModelRef() throws Exception {
		String encoding = UniversalDetectorUtil.getDetectedEncoding(IOUtil
				.getResourceAsStream("parser/impl/Slim3_AbstractModelRef.txt"));
		String sourceCodeString = IOUtil.readAsString(
				IOUtil.getResourceAsStream("parser/impl/Slim3_AbstractModelRef.txt"), encoding);
		ClassMeta targetClassMeta = classMetaExtractor.extract(sourceCodeString);
		target.initialize(targetClassMeta);
		String currentTestCaseSourceCode = "";
		List<TestMethodMeta> actual = target.getLackingTestMethodMetaList(currentTestCaseSourceCode);
		assertEquals("", 6, actual.size());
	}

	@Test
	public void getLackingTestMethodMetaList_A$String_IOUtil() throws Exception {
		String encoding = UniversalDetectorUtil.getDetectedEncoding(IOUtil
				.getResourceAsStream("parser/impl/IOUtil.txt"));
		String sourceCodeString = IOUtil.readAsString(IOUtil.getResourceAsStream("parser/impl/IOUtil.txt"), encoding);
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
	public void getNewTestCaseSourceCode_A$_JUnit3() throws Exception {
		Configuration config = new Configuration();
		config.junitVersion = JUnitVersion.version3;
		DefaultTestCaseGenerator target = new DefaultTestCaseGenerator(config);
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
		String expected = "package hoge.foo;\r\n\r\nimport java.util.ArrayList;\r\nimport java.util.HashMap;\r\nimport hoge.foo.Sample.*;\r\nimport static org.hamcrest.CoreMatchers.*;\r\nimport static org.junit.Assert.*;\r\nimport org.junit.Test;\r\n\r\nimport java.util.List;\r\nimport java.util.Map;\r\n\r\npublic class SampleTest {\r\n\r\n	@Test\r\n	public void type() throws Exception {\r\n		// TODO auto-generated by JUnit Helper.\r\n		assertThat(Sample.class, notNullValue());\r\n	}\r\n\r\n	@Test\r\n	public void instantiation() throws Exception {\r\n		// TODO auto-generated by JUnit Helper.\r\n		Sample target = new Sample();\r\n		assertThat(target, notNullValue());\r\n	}\r\n\r\n	@Test\r\n	public void doSomething_A$String$long() throws Throwable {\r\n		// TODO auto-generated by JUnit Helper.\r\n		Sample target = new Sample();\r\n		String str = null;\r\n		long longValue = 0L;\r\n		int actual = target.doSomething(str, longValue);\r\n		int expected = 0;\r\n		assertThat(actual, is(equalTo(expected)));\r\n	}\r\n\r\n	@Test\r\n	public void doSomething_A$List$Map() throws Exception {\r\n		// TODO auto-generated by JUnit Helper.\r\n		Sample target = new Sample();\r\n		List nested = new ArrayList();\r\n		Map nested2 = new HashMap();\r\n		target.doSomething(nested, nested2);\r\n	}\r\n\r\n}\r\n";
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
		DefaultTestCaseGenerator actual = target.initialize(targetSourceCodeString);
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
	public void addRequiredImportList_A$String() throws Exception {
		String sourceCodeString = "package hoge.foo;\r\nimport java.util.List;\r\npublic class Sample {\r\n\r\n\tpublic Sample() {}\r\n\r\n\tpublic int doSomething(String str, long longValue) throws Throwable {\r\n\t\tSystem.out.println(\"aaaa\");\r\n\t}\r\n}";
		ClassMeta targetClassMeta = classMetaExtractor.extract(sourceCodeString);
		target.initialize(targetClassMeta);
		String src = "package hoge;\r\n\r\n\r\npublic class SampleTest extends TestCase {\r\n\r\n}";
		String actual = target.addRequiredImportList(src);
		String expected = "package hoge;\r\n\r\nimport java.util.List;\r\nimport hoge.foo.Sample.*;\r\nimport static org.hamcrest.CoreMatchers.*;\r\nimport static org.junit.Assert.*;\r\nimport org.junit.Test;\r\n\r\n\r\npublic class SampleTest extends TestCase {\r\n\r\n}";
		assertEquals(expected, actual);
	}

	@Test
	public void addRequiredImportList_A$String$Configuration() throws Exception {
		String sourceCodeString = "package hoge.foo;\r\nimport java.util.List;\r\npublic class Sample {\r\n\r\n\tpublic Sample() {}\r\n\r\n\tpublic int doSomething(String str, long longValue) throws Throwable {\r\n\t\tSystem.out.println(\"aaaa\");\r\n\t}\r\n}";
		ClassMeta targetClassMeta = classMetaExtractor.extract(sourceCodeString);
		target.initialize(targetClassMeta);
		String src = "package hoge;\r\n\r\n\r\npublic class SampleTest extends TestCase {\r\n\r\n}";
		String actual = target.addRequiredImportList(src, config);
		String expected = "package hoge;\r\n\r\nimport java.util.List;\r\nimport hoge.foo.Sample.*;\r\nimport static org.hamcrest.CoreMatchers.*;\r\nimport static org.junit.Assert.*;\r\nimport org.junit.Test;\r\n\r\n\r\npublic class SampleTest extends TestCase {\r\n\r\n}";
		assertEquals(expected, actual);
	}

	@Test
	public void appendIfNotExists_A$StringBuilder$String$String_alradyExists() throws Exception {
		StringBuilder buf = new StringBuilder();
		String src = "package hoge.foo;\r\nimport junit.framework.TestCase;\r\n\r\npublic class Sample {\r\n\r\n}";
		String importLine = "import junit.framework.TestCase;";
		target.appendIfNotExists(buf, src, importLine);
		assertEquals("", buf.toString());
	}

	@Test
	public void appendIfNotExists_A$StringBuilder$String$String_notExists() throws Exception {
		StringBuilder buf = new StringBuilder();
		String src = "package hoge.foo;\r\nimport junit.framework.TestCase;\r\n\r\npublic class Sample {\r\n\r\n}";
		String importLine = "import org.junit.Test;";
		target.appendIfNotExists(buf, src, importLine);
		target.appendIfNotExists(buf, src, importLine);
		assertEquals("import org.junit.Test;\r\nimport org.junit.Test;\r\n", buf.toString());
	}

	@Test
	public void appendIfNotExists_A$StringBuilder$String$String_staticImportAsssert() throws Exception {
		StringBuilder buf = new StringBuilder();
		String src = "package hoge.foo;\r\nimport static org.junit.Assert.assertNotNull;\r\nimport junit.framework.TestCase;\r\n\r\npublic class Sample {\r\n\r\n}";
		String importLine = "import static org.junit.Assert.*;";
		target.appendIfNotExists(buf, src, importLine);
		assertEquals("import static org.junit.Assert.*;\r\n", buf.toString());
	}

	@Test
	public void isPublicMethodAndTestingRequired_A$MethodMeta$TestingTarget() throws Exception {
		MethodMeta methodMeta = new MethodMeta();
		methodMeta.accessModifier = AccessModifier.Public;
		TestingTarget target_ = new TestingTarget();
		target_.isPublicMethodRequired = true;
		boolean actual = target.isPublicMethodAndTestingRequired(methodMeta, target_);
		boolean expected = true;
		assertThat(actual, is(equalTo(expected)));
	}

	@Test
	public void isProtectedMethodAndTestingRequired_A$MethodMeta$TestingTarget() throws Exception {
		MethodMeta methodMeta = new MethodMeta();
		methodMeta.accessModifier = AccessModifier.Protected;
		TestingTarget target_ = new TestingTarget();
		target_.isPublicMethodRequired = true;
		boolean actual = target.isProtectedMethodAndTestingRequired(methodMeta, target_);
		boolean expected = true;
		assertThat(actual, is(equalTo(expected)));
	}

	@Test
	public void isPackageLocalMethodAndTestingRequired_A$MethodMeta$TestingTarget() throws Exception {
		MethodMeta methodMeta = new MethodMeta();
		methodMeta.accessModifier = AccessModifier.PackageLocal;
		TestingTarget target_ = new TestingTarget();
		target_.isPublicMethodRequired = true;
		boolean actual = target.isPackageLocalMethodAndTestingRequired(methodMeta, target_);
		boolean expected = true;
		assertThat(actual, is(equalTo(expected)));
	}

}
