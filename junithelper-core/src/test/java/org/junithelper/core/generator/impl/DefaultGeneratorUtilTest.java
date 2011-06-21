package org.junithelper.core.generator.impl;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junithelper.core.config.Configuration;
import org.junithelper.core.config.TestingTarget;
import org.junithelper.core.exception.JUnitHelperCoreException;
import org.junithelper.core.meta.AccessModifier;
import org.junithelper.core.meta.ClassMeta;
import org.junithelper.core.meta.MethodMeta;
import org.junithelper.core.meta.TestMethodMeta;

public class DefaultGeneratorUtilTest {

	@Test
	public void type() throws Exception {
		assertThat(DefaultGeneratorUtil.class, notNullValue());
	}

	@Test
	public void appendIfNotExists_A$StringBuilder$String$String_alradyExists() throws Exception {
		StringBuilder buf = new StringBuilder();
		String src = "package hoge.foo;\r\nimport junit.framework.TestCase;\r\n\r\npublic class Sample {\r\n\r\n}";
		String importLine = "import junit.framework.TestCase;";
		DefaultGeneratorUtil.appendIfNotExists(buf, src, importLine);
		assertEquals("", buf.toString());
	}

	@Test
	public void appendIfNotExists_A$StringBuilder$String$String_notExists() throws Exception {
		StringBuilder buf = new StringBuilder();
		String src = "package hoge.foo;\r\nimport junit.framework.TestCase;\r\n\r\npublic class Sample {\r\n\r\n}";
		String importLine = "import org.junit.Test;";
		DefaultGeneratorUtil.appendIfNotExists(buf, src, importLine);
		DefaultGeneratorUtil.appendIfNotExists(buf, src, importLine);
		assertEquals("import org.junit.Test;\r\nimport org.junit.Test;\r\n", buf.toString());
	}

	@Test
	public void appendIfNotExists_A$StringBuilder$String$String_staticImportAsssert() throws Exception {
		StringBuilder buf = new StringBuilder();
		String src = "package hoge.foo;\r\nimport static org.junit.Assert.assertNotNull;\r\nimport junit.framework.TestCase;\r\n\r\npublic class Sample {\r\n\r\n}";
		String importLine = "import static org.junit.Assert.*;";
		DefaultGeneratorUtil.appendIfNotExists(buf, src, importLine);
		assertEquals("import static org.junit.Assert.*;\r\n", buf.toString());
	}

	@Test
	public void isPublicMethodAndTestingRequired_A$MethodMeta$TestingTarget() throws Exception {
		MethodMeta methodMeta = new MethodMeta();
		methodMeta.accessModifier = AccessModifier.Public;
		TestingTarget target_ = new TestingTarget();
		target_.isPublicMethodRequired = true;
		boolean actual = DefaultGeneratorUtil.isPublicMethodAndTestingRequired(methodMeta, target_);
		boolean expected = true;
		assertThat(actual, is(equalTo(expected)));
	}

	@Test
	public void isProtectedMethodAndTestingRequired_A$MethodMeta$TestingTarget() throws Exception {
		MethodMeta methodMeta = new MethodMeta();
		methodMeta.accessModifier = AccessModifier.Protected;
		TestingTarget target_ = new TestingTarget();
		target_.isPublicMethodRequired = true;
		boolean actual = DefaultGeneratorUtil.isProtectedMethodAndTestingRequired(methodMeta, target_);
		boolean expected = true;
		assertThat(actual, is(equalTo(expected)));
	}

	@Test
	public void isPackageLocalMethodAndTestingRequired_A$MethodMeta$TestingTarget() throws Exception {
		MethodMeta methodMeta = new MethodMeta();
		methodMeta.accessModifier = AccessModifier.PackageLocal;
		TestingTarget target_ = new TestingTarget();
		target_.isPublicMethodRequired = true;
		boolean actual = DefaultGeneratorUtil.isPackageLocalMethodAndTestingRequired(methodMeta, target_);
		boolean expected = true;
		assertThat(actual, is(equalTo(expected)));
	}

	@Test
	public void isCanonicalClassNameUsed_A$String$String$ClassMeta_ImportWildCard() throws Exception {
		String expectedCanonicalClassName = "com.example.bean.SampleBean";
		String usedClassName = "SampleBean";
		ClassMeta targetClassMeta = new ClassMeta();
		targetClassMeta.importedList.add("com.example.bean.*");
		// when
		boolean actual = DefaultGeneratorUtil.isCanonicalClassNameUsed(expectedCanonicalClassName, usedClassName,
				targetClassMeta);
		// then
		boolean expected = true;
		assertThat(actual, is(equalTo(expected)));
	}

	@Test
	public void isCanonicalClassNameUsed_A$String$String$ClassMeta_Imported() throws Exception {
		String expectedCanonicalClassName = "com.example.bean.SampleBean";
		String usedClassName = "SampleBean";
		ClassMeta targetClassMeta = new ClassMeta();
		targetClassMeta.importedList.add("com.example.bean.SampleBean");
		// when
		boolean actual = DefaultGeneratorUtil.isCanonicalClassNameUsed(expectedCanonicalClassName, usedClassName,
				targetClassMeta);
		// then
		boolean expected = true;
		assertThat(actual, is(equalTo(expected)));
	}

	@Test
	public void isCanonicalClassNameUsed_A$String$String$ClassMeta_CanonicalClassName() throws Exception {
		String expectedCanonicalClassName = "com.example.bean.SampleBean";
		String usedClassName = "com.example.bean.SampleBean";
		ClassMeta targetClassMeta = new ClassMeta();
		// when
		boolean actual = DefaultGeneratorUtil.isCanonicalClassNameUsed(expectedCanonicalClassName, usedClassName,
				targetClassMeta);
		// then
		boolean expected = true;
		assertThat(actual, is(equalTo(expected)));
	}

	@Test
	public void isCanonicalClassNameUsed_A$String$String$ClassMeta_NotUsed() throws Exception {
		String expectedCanonicalClassName = "com.example.bean.SampleBean";
		String usedClassName = "SampleBean";
		ClassMeta targetClassMeta = new ClassMeta();
		// when
		boolean actual = DefaultGeneratorUtil.isCanonicalClassNameUsed(expectedCanonicalClassName, usedClassName,
				targetClassMeta);
		// then
		boolean expected = false;
		assertThat(actual, is(equalTo(expected)));
	}

	@Test
	public void getInstantiationSourceCode_A$Configuration$TestMethodMeta_Null() throws Exception {
		Configuration config = null;
		TestMethodMeta testMethodMeta = null;
		try {
			DefaultGeneratorUtil.getInstantiationSourceCode(config, testMethodMeta);
			fail();
		} catch (JUnitHelperCoreException e) {
		}
	}

	@Test
	public void appendIfNotExists_A$StringBuilder$String$String() throws Exception {
		StringBuilder buf = new StringBuilder();
		String src = "abc";
		String importLine = "com.example.Bean";
		DefaultGeneratorUtil.appendIfNotExists(buf, src, importLine);
	}

	@Test
	public void appendIfNotExists_A$StringBuilder$String$String_StringIsNull() throws Exception {
		StringBuilder buf = new StringBuilder();
		String src = null;
		String importLine = null;
		try {
			DefaultGeneratorUtil.appendIfNotExists(buf, src, importLine);
			fail();
		} catch (JUnitHelperCoreException e) {
		}
	}

	@Test
	public void appendIfNotExists_A$StringBuilder$String$String_StringIsEmpty() throws Exception {
		StringBuilder buf = new StringBuilder();
		String src = "";
		String importLine = "";
		DefaultGeneratorUtil.appendIfNotExists(buf, src, importLine);
	}

	@Test
	public void isCanonicalClassNameUsed_A$String$String$ClassMeta_StringIsNull() throws Exception {
		String expectedCanonicalClassName = null;
		String usedClassName = null;
		ClassMeta targetClassMeta = null;
		try {
			DefaultGeneratorUtil.isCanonicalClassNameUsed(expectedCanonicalClassName, usedClassName, targetClassMeta);
			fail();
		} catch (JUnitHelperCoreException e) {
		}
	}

	@Test
	public void isCanonicalClassNameUsed_A$String$String$ClassMeta_StringIsEmpty() throws Exception {
		String expectedCanonicalClassName = "";
		String usedClassName = "";
		ClassMeta targetClassMeta = new ClassMeta();
		DefaultGeneratorUtil.isCanonicalClassNameUsed(expectedCanonicalClassName, usedClassName, targetClassMeta);
	}

	@Test
	public void appendCRLF_A$StringBuilder() throws Exception {
		// given
		StringBuilder buf = new StringBuilder();
		// e.g. : given(mocked.called()).willReturn(1);
		// when
		DefaultGeneratorUtil.appendCRLF(buf);
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
		DefaultGeneratorUtil.appendTabs(buf, times);
		// then
		assertEquals("", buf.toString());
	}

	@Test
	public void appendTabs_A$StringBuilder$int_1times() throws Exception {
		// given
		StringBuilder buf = new StringBuilder();
		int times = 1;
		// when
		DefaultGeneratorUtil.appendTabs(buf, times);
		// then
		assertEquals("\t", buf.toString());
	}

	@Test
	public void appendTabs_A$StringBuilder$int_2times() throws Exception {
		// given
		StringBuilder buf = new StringBuilder();
		int times = 2;
		// when
		DefaultGeneratorUtil.appendTabs(buf, times);
		// then
		assertEquals("\t\t", buf.toString());
	}

	@Test
	public void appendTabs_A$StringBuilder$int_intIsMinus1() throws Exception {
		StringBuilder buf = new StringBuilder();
		int times = -1;
		try {
			DefaultGeneratorUtil.appendTabs(buf, times);
			fail();
		} catch (JUnitHelperCoreException e) {
		}
	}

	@Test
	public void appendTabs_A$StringBuilder$int_intIs0() throws Exception {
		StringBuilder buf = new StringBuilder();
		int times = 0;
		DefaultGeneratorUtil.appendTabs(buf, times);
	}

	@Test
	public void appendTabs_A$StringBuilder$int_intIs1() throws Exception {
		StringBuilder buf = new StringBuilder();
		int times = 1;
		DefaultGeneratorUtil.appendTabs(buf, times);
	}

	@Test
	public void appendTabs_A$StringBuilder$int_intIs2() throws Exception {
		StringBuilder buf = new StringBuilder();
		int times = 2;
		DefaultGeneratorUtil.appendTabs(buf, times);
	}

	@Test
	public void appendTabs_A$StringBuilder$int_intIsMinValue() throws Exception {
		StringBuilder buf = null;
		int times = Integer.MIN_VALUE;
		try {
			DefaultGeneratorUtil.appendTabs(buf, times);
			fail();
		} catch (JUnitHelperCoreException e) {
		}
	}

	@Test
	public void appendTabs_A$StringBuilder$int_intIsMaxValue() throws Exception {
		StringBuilder buf = null;
		int times = Integer.MAX_VALUE;
		try {
			DefaultGeneratorUtil.appendTabs(buf, times);
			fail();
		} catch (JUnitHelperCoreException e) {
		}
	}

}
