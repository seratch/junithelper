package org.junithelper.core.generator.impl;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junithelper.core.config.TestingTarget;
import org.junithelper.core.meta.AccessModifier;
import org.junithelper.core.meta.ClassMeta;
import org.junithelper.core.meta.MethodMeta;

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

}
