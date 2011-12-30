package org.junithelper.core.generator;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junithelper.core.config.Configuration;
import org.junithelper.core.config.TestingTarget;
import org.junithelper.core.config.extension.ExtConfiguration;
import org.junithelper.core.config.extension.ExtInstantiation;
import org.junithelper.core.exception.JUnitHelperCoreException;
import org.junithelper.core.meta.AccessModifier;
import org.junithelper.core.meta.ArgTypeMeta;
import org.junithelper.core.meta.ClassMeta;
import org.junithelper.core.meta.ConstructorMeta;
import org.junithelper.core.meta.CurrentLineBreak;
import org.junithelper.core.meta.MethodMeta;
import org.junithelper.core.meta.TestMethodMeta;

public class GeneratorImplFunctionTest {

    Configuration config = new Configuration();
    LineBreakProvider lineBreakProvider = new LineBreakProvider(config, CurrentLineBreak.CRLF);
    IndentationProvider indentationProvider = new IndentationProvider(config);
    SourceCodeAppender appender = new SourceCodeAppender(lineBreakProvider, indentationProvider);

    @Test
    public void type() throws Exception {
        assertThat(GeneratorImplFunction.class, notNullValue());
    }

    @Test
    public void isPublicMethodAndTestingRequired_A$MethodMeta$TestingTarget() throws Exception {
        MethodMeta methodMeta = new MethodMeta();
        methodMeta.accessModifier = AccessModifier.Public;
        TestingTarget target_ = new TestingTarget();
        target_.isPublicMethodRequired = true;
        boolean actual = GeneratorImplFunction.isPublicMethodAndTestingRequired(methodMeta, target_);
        boolean expected = true;
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void isProtectedMethodAndTestingRequired_A$MethodMeta$TestingTarget() throws Exception {
        MethodMeta methodMeta = new MethodMeta();
        methodMeta.accessModifier = AccessModifier.Protected;
        TestingTarget target_ = new TestingTarget();
        target_.isPublicMethodRequired = true;
        boolean actual = GeneratorImplFunction.isProtectedMethodAndTestingRequired(methodMeta, target_);
        boolean expected = true;
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void isPackageLocalMethodAndTestingRequired_A$MethodMeta$TestingTarget() throws Exception {
        MethodMeta methodMeta = new MethodMeta();
        methodMeta.accessModifier = AccessModifier.PackageLocal;
        TestingTarget target_ = new TestingTarget();
        target_.isPublicMethodRequired = true;
        boolean actual = GeneratorImplFunction.isPackageLocalMethodAndTestingRequired(methodMeta, target_);
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
        boolean actual = GeneratorImplFunction.isCanonicalClassNameUsed(expectedCanonicalClassName, usedClassName,
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
        boolean actual = GeneratorImplFunction.isCanonicalClassNameUsed(expectedCanonicalClassName, usedClassName,
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
        boolean actual = GeneratorImplFunction.isCanonicalClassNameUsed(expectedCanonicalClassName, usedClassName,
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
        boolean actual = GeneratorImplFunction.isCanonicalClassNameUsed(expectedCanonicalClassName, usedClassName,
                targetClassMeta);
        // then
        boolean expected = false;
        assertThat(actual, is(equalTo(expected)));
    }

    @Test(expected = JUnitHelperCoreException.class)
    public void getInstantiationSourceCode_A$Configuration$SourceCodeAppender$TestMethodMeta_Null() throws Exception {
        Configuration config = null;
        TestMethodMeta testMethodMeta = null;
        GeneratorImplFunction.getInstantiationSourceCode(config, appender, testMethodMeta);
    }

    @Test
    public void getInstantiationSourceCode_A$Configuration$SourceCodeAppender$TestMethodMeta() throws Exception {
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
        String actual = GeneratorImplFunction.getInstantiationSourceCode(config, appender, testMethodMeta);
        String expected = "\t\tcom.example.Bean bean = BeanFactory.getInstance();\r\n\t\tTarget target = new Target(bean);\r\n";
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void getInstantiationSourceCode_A$Configuration$SourceCodeAppender$TestMethodMeta_WithSemicolon()
            throws Exception {
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
        String actual = GeneratorImplFunction.getInstantiationSourceCode(config, appender, testMethodMeta);
        String expected = "\t\tcom.example.Bean bean = BeanFactory.getInstance();\r\n\t\tTarget target = new Target(bean);\r\n";
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void getInstantiationSourceCode_A$Configuration$SourceCodeAppender$TestMethodMeta_TargetByExtInstantiation()
            throws Exception {
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
        String actual = GeneratorImplFunction.getInstantiationSourceCode(config, appender, testMethodMeta);
        String expected = "\t\tBean target = BeanFactory.getInstance();\r\n";
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void getInstantiationSourceCode_A$Configuration$SourceCodeAppender$TestMethodMeta_TargetByExtInstantiation_WithSemicolon()
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
        String actual = GeneratorImplFunction.getInstantiationSourceCode(config, appender, testMethodMeta);
        String expected = "\t\tBean target = BeanFactory.getInstance();\r\n";
        assertThat(actual, is(equalTo(expected)));
    }

    @Test(expected = JUnitHelperCoreException.class)
    public void isCanonicalClassNameUsed_A$String$String$ClassMeta_StringIsNull() throws Exception {
        String expectedCanonicalClassName = null;
        String usedClassName = null;
        ClassMeta targetClassMeta = null;
        GeneratorImplFunction.isCanonicalClassNameUsed(expectedCanonicalClassName, usedClassName, targetClassMeta);
    }

    @Test
    public void isCanonicalClassNameUsed_A$String$String$ClassMeta_StringIsEmpty() throws Exception {
        String expectedCanonicalClassName = "";
        String usedClassName = "";
        ClassMeta targetClassMeta = new ClassMeta();
        GeneratorImplFunction.isCanonicalClassNameUsed(expectedCanonicalClassName, usedClassName, targetClassMeta);
    }

}
