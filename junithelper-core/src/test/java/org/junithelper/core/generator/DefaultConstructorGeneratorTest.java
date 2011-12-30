package org.junithelper.core.generator;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junithelper.core.config.Configuration;
import org.junithelper.core.config.extension.ExtInstantiation;
import org.junithelper.core.generator.DefaultConstructorGenerator;
import org.junithelper.core.meta.ArgTypeMeta;
import org.junithelper.core.meta.ClassMeta;
import org.junithelper.core.meta.ConstructorMeta;
import org.junithelper.core.meta.extractor.ClassMetaExtractor;

public class DefaultConstructorGeneratorTest {

    Configuration config = new Configuration();
    ClassMetaExtractor classMetaExtractor = new ClassMetaExtractor(config);
    ClassMeta targetClassMeta;
    DefaultConstructorGenerator target;

    @Before
    public void setUp() {
        String sourceCodeString = "package hoge.foo; import java.util.List; public class Sample { public Sample() {}\r\n public int doSomething(String str, long longValue) throws Throwable { System.out.println(\"aaaa\") } }";
        targetClassMeta = classMetaExtractor.extract(sourceCodeString);
        target = new DefaultConstructorGenerator();
    }

    @Test
    public void type() throws Exception {
        assertNotNull(DefaultConstructorGenerator.class);
    }

    @Test
    public void instantiation() throws Exception {
        DefaultConstructorGenerator target = new DefaultConstructorGenerator();
        assertNotNull(target);
    }

    @Test
    public void getAllInstantiationSourceCodeList_A$Configuration$ClassMeta() throws Exception {
        // given
        ClassMeta classMeta = targetClassMeta;
        // e.g. : given(mocked.called()).willReturn(1);
        // when
        List<String> actual = target.getAllInstantiationSourceCodeList(config, classMeta);
        // then
        // e.g. : verify(mocked).called();
        assertEquals("		Sample target = new Sample();\r\n", actual.get(0));
    }

    @Test
    public void getFirstInstantiationSourceCode_A$Configuration$ClassMeta() throws Exception {
        // given
        ClassMeta classMeta = targetClassMeta;
        // e.g. : given(mocked.called()).willReturn(1);
        // when
        String actual = target.getFirstInstantiationSourceCode(config, classMeta);
        // then
        // e.g. : verify(mocked).called();
        assertEquals("		Sample target = new Sample();\r\n", actual);
    }

    @Test
    public void getInstantiationSourceCode_A$Configuration$ClassMeta$ConstructorMeta() throws Exception {
        // given
        ClassMeta classMeta = targetClassMeta;
        ConstructorMeta constructorMeta = target.getFirstConstructor(classMeta);
        // e.g. : given(mocked.called()).willReturn(1);
        // when
        String actual = target.getInstantiationSourceCode(config, classMeta, constructorMeta);
        // then
        // e.g. : verify(mocked).called();
        assertEquals("		Sample target = new Sample();\r\n", actual);
    }

    @Test
    public void getInstantiationSourceCode_A$Configuration$ClassMeta$ConstructorMeta_Issue69() throws Exception {
        // given
        ClassMeta classMeta = new ClassMeta();
        classMeta.name = "Sample";
        classMeta.importedList.add("java.util.Calendar");
        ConstructorMeta cons = new ConstructorMeta();
        cons.argNames.add("cal");
        ArgTypeMeta argType = new ArgTypeMeta();
        argType.name = "Calendar";
        argType.nameInMethodName = "Calendar";
        cons.argTypes.add(argType);
        classMeta.constructors.add(cons);
        ConstructorMeta constructorMeta = target.getFirstConstructor(classMeta);
        Configuration config = new Configuration();
        config.isExtensionEnabled = true;
        ExtInstantiation ins = new ExtInstantiation("java.util.Calendar");
        ins.assignCode = "Calendar.getInstance()";
        config.extConfiguration.extInstantiations.add(ins);
        // when
        String actual = target.getInstantiationSourceCode(config, classMeta, constructorMeta);
        // then
        assertEquals("		Calendar cal = Calendar.getInstance();\r\n" + "		Sample target = new Sample(cal);\r\n", actual);
    }

    @Test
    public void getFirstConstructor_A$ClassMeta() throws Exception {
        // given
        ClassMeta classMeta = targetClassMeta;
        // e.g. : given(mocked.called()).willReturn(1);
        // when
        ConstructorMeta actual = target.getFirstConstructor(classMeta);
        // then
        // e.g. : verify(mocked).called();
        assertNotNull(actual);
    }

}
