package org.junithelper.core.generator;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;
import org.junithelper.core.config.Configuration;
import org.junithelper.core.config.TestingTarget;
import org.junithelper.core.config.extension.ExtConfiguration;
import org.junithelper.core.config.extension.ExtInstantiation;
import org.junithelper.core.exception.JUnitHelperCoreException;
import org.junithelper.core.generator.DefaultGeneratorUtil;
import org.junithelper.core.meta.AccessModifier;
import org.junithelper.core.meta.ArgTypeMeta;
import org.junithelper.core.meta.ClassMeta;
import org.junithelper.core.meta.ConstructorMeta;
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

    @Test(expected = JUnitHelperCoreException.class)
    public void getInstantiationSourceCode_A$Configuration$TestMethodMeta_Null() throws Exception {
        Configuration config = null;
        TestMethodMeta testMethodMeta = null;
        DefaultGeneratorUtil.getInstantiationSourceCode(config, testMethodMeta);
    }

    @Test
    public void getInstantiationSourceCode_A$Configuration$TestMethodMeta() throws Exception {
        Configuration config = new Configuration();
        config.isExtensionEnabled = true;
        config.extConfiguration = new ExtConfiguration(config);
        ExtInstantiation ins = new ExtInstantiation("com.example.Bean");
        ins.importList.add("com.example.BeanFactory");
        ins.assignCode = "BeanFactory.getInstance()";
        config.extConfiguration.extInstantiations.add(ins);
        TestMethodMeta testMethodMeta = new TestMethodMeta();
        ConstructorMeta cons = new ConstructorMeta();
        cons.argNames.add("bean");
        ArgTypeMeta argType = new ArgTypeMeta();
        argType.name = "com.example.Bean";
        argType.nameInMethodName = "Bean";
        cons.argTypes.add(argType);
        testMethodMeta.classMeta = new ClassMeta();
        testMethodMeta.classMeta.name = "Target";
        testMethodMeta.classMeta.importedList.add("com.example.Bean");
        testMethodMeta.classMeta.packageName = "com.example";
        testMethodMeta.classMeta.constructors.add(cons);
        String actual = DefaultGeneratorUtil.getInstantiationSourceCode(config, testMethodMeta);
        String expected = "\t\tcom.example.Bean bean = BeanFactory.getInstance();\r\n\t\tTarget target = new Target(bean);\r\n";
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void getInstantiationSourceCode_A$Configuration$TestMethodMeta_WithSemicolon() throws Exception {
        Configuration config = new Configuration();
        config.isExtensionEnabled = true;
        config.extConfiguration = new ExtConfiguration(config);
        ExtInstantiation ins = new ExtInstantiation("com.example.Bean");
        ins.importList.add("com.example.BeanFactory");
        ins.assignCode = "BeanFactory.getInstance();";
        config.extConfiguration.extInstantiations.add(ins);
        TestMethodMeta testMethodMeta = new TestMethodMeta();
        ConstructorMeta cons = new ConstructorMeta();
        cons.argNames.add("bean");
        ArgTypeMeta argType = new ArgTypeMeta();
        argType.name = "com.example.Bean";
        argType.nameInMethodName = "Bean";
        cons.argTypes.add(argType);
        testMethodMeta.classMeta = new ClassMeta();
        testMethodMeta.classMeta.name = "Target";
        testMethodMeta.classMeta.importedList.add("com.example.Bean");
        testMethodMeta.classMeta.packageName = "com.example";
        testMethodMeta.classMeta.constructors.add(cons);
        String actual = DefaultGeneratorUtil.getInstantiationSourceCode(config, testMethodMeta);
        String expected = "\t\tcom.example.Bean bean = BeanFactory.getInstance();\r\n\t\tTarget target = new Target(bean);\r\n";
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void getInstantiationSourceCode_A$Configuration$TestMethodMeta_TargetByExtInstantiation() throws Exception {
        Configuration config = new Configuration();
        config.isExtensionEnabled = true;
        config.extConfiguration = new ExtConfiguration(config);
        ExtInstantiation ins = new ExtInstantiation("com.example.Bean");
        ins.importList.add("com.example.BeanFactory");
        ins.assignCode = "BeanFactory.getInstance()";
        config.extConfiguration.extInstantiations.add(ins);
        TestMethodMeta testMethodMeta = new TestMethodMeta();
        testMethodMeta.classMeta = new ClassMeta();
        testMethodMeta.classMeta.name = "Bean";
        testMethodMeta.classMeta.importedList.add("com.example.Bean");
        testMethodMeta.classMeta.packageName = "com.example";
        String actual = DefaultGeneratorUtil.getInstantiationSourceCode(config, testMethodMeta);
        String expected = "\t\tBean target = BeanFactory.getInstance();\r\n";
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void getInstantiationSourceCode_A$Configuration$TestMethodMeta_TargetByExtInstantiation_WithSemicolon()
            throws Exception {
        Configuration config = new Configuration();
        config.isExtensionEnabled = true;
        config.extConfiguration = new ExtConfiguration(config);
        ExtInstantiation ins = new ExtInstantiation("com.example.Bean");
        ins.importList.add("com.example.BeanFactory");
        ins.assignCode = "BeanFactory.getInstance();";
        config.extConfiguration.extInstantiations.add(ins);
        TestMethodMeta testMethodMeta = new TestMethodMeta();
        testMethodMeta.classMeta = new ClassMeta();
        testMethodMeta.classMeta.name = "Bean";
        testMethodMeta.classMeta.importedList.add("com.example.Bean");
        testMethodMeta.classMeta.packageName = "com.example";
        String actual = DefaultGeneratorUtil.getInstantiationSourceCode(config, testMethodMeta);
        String expected = "\t\tBean target = BeanFactory.getInstance();\r\n";
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void appendIfNotExists_A$StringBuilder$String$String() throws Exception {
        StringBuilder buf = new StringBuilder();
        String src = "abc";
        String importLine = "com.example.Bean";
        DefaultGeneratorUtil.appendIfNotExists(buf, src, importLine);
    }

    @Test(expected = JUnitHelperCoreException.class)
    public void appendIfNotExists_A$StringBuilder$String$String_StringIsNull() throws Exception {
        StringBuilder buf = new StringBuilder();
        String src = null;
        String importLine = null;
        DefaultGeneratorUtil.appendIfNotExists(buf, src, importLine);
    }

    @Test
    public void appendIfNotExists_A$StringBuilder$String$String_StringIsEmpty() throws Exception {
        StringBuilder buf = new StringBuilder();
        String src = "";
        String importLine = "";
        DefaultGeneratorUtil.appendIfNotExists(buf, src, importLine);
    }

    @Test(expected = JUnitHelperCoreException.class)
    public void isCanonicalClassNameUsed_A$String$String$ClassMeta_StringIsNull() throws Exception {
        String expectedCanonicalClassName = null;
        String usedClassName = null;
        ClassMeta targetClassMeta = null;
        DefaultGeneratorUtil.isCanonicalClassNameUsed(expectedCanonicalClassName, usedClassName, targetClassMeta);
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

    @Test(expected = JUnitHelperCoreException.class)
    public void appendTabs_A$StringBuilder$int_intIsMinus1() throws Exception {
        StringBuilder buf = new StringBuilder();
        int times = -1;
        DefaultGeneratorUtil.appendTabs(buf, times);
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

    @Test(expected = JUnitHelperCoreException.class)
    public void appendTabs_A$StringBuilder$int_intIsMinValue() throws Exception {
        StringBuilder buf = null;
        int times = Integer.MIN_VALUE;
        DefaultGeneratorUtil.appendTabs(buf, times);
    }

    @Test(expected = JUnitHelperCoreException.class)
    public void appendTabs_A$StringBuilder$int_intIsMaxValue() throws Exception {
        StringBuilder buf = null;
        int times = Integer.MAX_VALUE;
        DefaultGeneratorUtil.appendTabs(buf, times);
    }

    @Test
    public void appendExtensionSourceCode_A$StringBuilder$String() throws Exception {
        StringBuilder buf = new StringBuilder();
        String code = "\"hoge\"";
        DefaultGeneratorUtil.appendExtensionSourceCode(buf, code);
        assertThat(buf.toString(), is(equalTo("\t\t\"hoge\";\r\n")));
    }

    @Test(expected = JUnitHelperCoreException.class)
    public void appendExtensionSourceCode_A$StringBuilder$String_StringIsNull() throws Exception {
        StringBuilder buf = new StringBuilder();
        String code = null;
        DefaultGeneratorUtil.appendExtensionSourceCode(buf, code);
    }

    @Test
    public void appendExtensionSourceCode_A$StringBuilder$String_StringIsEmpty() throws Exception {
        StringBuilder buf = new StringBuilder();
        String code = "";
        DefaultGeneratorUtil.appendExtensionSourceCode(buf, code);
        assertThat(buf.toString(), is(equalTo("")));
    }

    @Test
    public void appendExtensionSourceCode_A$StringBuilder$String_SeveralLines() throws Exception {
        StringBuilder buf = new StringBuilder();
        String code = "/*\r\n System.out.println(\"test\");\r\n */";
        DefaultGeneratorUtil.appendExtensionSourceCode(buf, code);
        assertThat(buf.toString(), is(equalTo("\t\t/*\r\n\t\tSystem.out.println(\"test\");\r\n\t\t*/\r\n")));
    }

    @Test
    public void appendExtensionPostAssignSourceCode_A$StringBuilder$String$StringArray$String() throws Exception {
        StringBuilder buf = new StringBuilder();
        String code = "new Something(\"test\");";
        String[] fromList = new String[] { "\\{arg\\}" };
        String to = "target";
        DefaultGeneratorUtil.appendExtensionPostAssignSourceCode(buf, code, fromList, to);
        assertThat(buf.toString(), is(equalTo("\t\tnew Something(\"test\");\r\n")));
    }

    @Test(expected = JUnitHelperCoreException.class)
    public void appendExtensionPostAssignSourceCode_A$StringBuilder$String$StringArray$String_StringIsNull()
            throws Exception {
        StringBuilder buf = new StringBuilder();
        String code = null;
        String[] fromList = new String[] {};
        String to = null;
        DefaultGeneratorUtil.appendExtensionPostAssignSourceCode(buf, code, fromList, to);
    }

    @Test
    public void appendExtensionPostAssignSourceCode_A$StringBuilder$String$StringArray$String_StringIsEmpty()
            throws Exception {
        StringBuilder buf = new StringBuilder();
        String code = "";
        String[] fromList = new String[] {};
        String to = "";
        DefaultGeneratorUtil.appendExtensionPostAssignSourceCode(buf, code, fromList, to);
        assertThat(buf.toString(), is(equalTo("")));
    }

    @Test
    public void appendTabs_A$StringBuilder$int_intIsRandom() throws Exception {
        StringBuilder buf = new StringBuilder();
        int times = new Random().nextInt(10);
        DefaultGeneratorUtil.appendTabs(buf, times);
        assertThat(buf.toString().length(), is(equalTo(times)));
    }

}
