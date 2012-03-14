package org.junithelper.core.generator;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junithelper.core.config.Configuration;
import org.junithelper.core.config.LineBreakPolicy;
import org.junithelper.core.config.extension.ExtInstantiation;
import org.junithelper.core.extractor.ClassMetaExtractor;
import org.junithelper.core.meta.ArgTypeMeta;
import org.junithelper.core.meta.ClassMeta;
import org.junithelper.core.meta.ConstructorMeta;
import org.junithelper.core.meta.CurrentLineBreak;

public class ConstructorGeneratorImplTest {

    Configuration config = new Configuration();
    ClassMetaExtractor classMetaExtractor = new ClassMetaExtractor(config);
    ClassMeta targetClassMeta;
    ConstructorGeneratorImpl target;

    @Before
    public void setUp() {
        String sourceCodeString = "package hoge.foo; import java.util.List; public class Sample { public Sample() {}\r\n public int doSomething(String str, long longValue) throws Throwable { System.out.println(\"aaaa\") } }";
        targetClassMeta = classMetaExtractor.extract(sourceCodeString);
        LineBreakProvider lineBreakProvider = new LineBreakProvider(config, CurrentLineBreak.CRLF);
        target = new ConstructorGeneratorImpl(config, lineBreakProvider);
    }

    @Test
    public void type() throws Exception {
        assertNotNull(ConstructorGeneratorImpl.class);
    }

    @Test
    public void instantiation() throws Exception {
        LineBreakProvider lineBreakProvider = new LineBreakProvider(config, CurrentLineBreak.CRLF);
        target = new ConstructorGeneratorImpl(config, lineBreakProvider);
        assertNotNull(target);
    }

    @Test
    public void getAllInstantiationSourceCodeList_A$Configuration$ClassMeta() throws Exception {
        ClassMeta classMeta = targetClassMeta;
        List<String> actual = target.getAllInstantiationSourceCodeList(config, classMeta);
        assertEquals("\t\tSample target = new Sample();\r\n", actual.get(0));
    }

    @Test
    public void getAllInstantiationSourceCodeList_A$Configuration$ClassMeta_FORCE_LF() throws Exception {
        ClassMeta classMeta = targetClassMeta;
        config.lineBreakPolicy = LineBreakPolicy.forceLF;
        LineBreakProvider lineBreakProvider = new LineBreakProvider(config, CurrentLineBreak.CRLF);
        target = new ConstructorGeneratorImpl(config, lineBreakProvider);
        List<String> actual = target.getAllInstantiationSourceCodeList(config, classMeta);
        assertEquals("\t\tSample target = new Sample();\n", actual.get(0));
    }

    @Test
    public void getAllInstantiationSourceCodeList_A$Configuration$ClassMeta_NEW_FILE_ONLY_newFIle() throws Exception {
        ClassMeta classMeta = targetClassMeta;
        config.lineBreakPolicy = LineBreakPolicy.forceNewFileLF;
        LineBreakProvider lineBreakProvider = new LineBreakProvider(config, null);
        target = new ConstructorGeneratorImpl(config, lineBreakProvider);
        List<String> actual = target.getAllInstantiationSourceCodeList(config, classMeta);
        assertEquals("\t\tSample target = new Sample();\n", actual.get(0));
    }

    @Test
    public void getAllInstantiationSourceCodeList_A$Configuration$ClassMeta_NEW_FILE_ONLY() throws Exception {
        ClassMeta classMeta = targetClassMeta;
        config.lineBreakPolicy = LineBreakPolicy.forceNewFileLF;
        LineBreakProvider lineBreakProvider = new LineBreakProvider(config, CurrentLineBreak.CRLF);
        target = new ConstructorGeneratorImpl(config, lineBreakProvider);
        List<String> actual = target.getAllInstantiationSourceCodeList(config, classMeta);
        assertEquals("\t\tSample target = new Sample();\r\n", actual.get(0));
    }

    @Test
    public void getFirstInstantiationSourceCode_A$Configuration$ClassMeta() throws Exception {
        ClassMeta classMeta = targetClassMeta;
        String actual = target.getFirstInstantiationSourceCode(config, classMeta);
        assertEquals("		Sample target = new Sample();\r\n", actual);
    }

    @Test
    public void getInstantiationSourceCode_A$Configuration$ClassMeta$ConstructorMeta() throws Exception {
        ClassMeta classMeta = targetClassMeta;
        ConstructorMeta constructorMeta = target.getFirstConstructor(classMeta);
        String actual = target.getInstantiationSourceCode(config, classMeta, constructorMeta);
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
        ClassMeta classMeta = targetClassMeta;
        ConstructorMeta actual = target.getFirstConstructor(classMeta);
        assertNotNull(actual);
    }

}
