package org.junithelper.core.extractor;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junithelper.core.config.Configuration;
import org.junithelper.core.exception.JUnitHelperCoreException;
import org.junithelper.core.extractor.ArgTypeMetaExtractor;
import org.junithelper.core.extractor.ClassMetaExtractor;
import org.junithelper.core.meta.ClassMeta;

public class ArgTypeMetaExtractorTest {

    Configuration config = new Configuration();

    @Test
    public void type() throws Exception {
        assertNotNull(ArgTypeMetaExtractor.class);
    }

    @Test
    public void instantiation() throws Exception {
        Configuration config = null;
        ArgTypeMetaExtractor target = new ArgTypeMetaExtractor(config);
        assertNotNull(target);
    }

    @Test
    public void initialize_A$String() throws Exception {
        Configuration config = new Configuration();
        ArgTypeMetaExtractor target = new ArgTypeMetaExtractor(config);
        String sourceCodeString = "package hoge.foo; public class Sample { public Sample() {}\r\n public void doSomething(String str) { System.out.println(\"aaaa\") } }";
        target.initialize(sourceCodeString);
    }

    @Test
    public void initialize_A$ClassMeta$String() throws Exception {
        Configuration config = new Configuration();
        ArgTypeMetaExtractor target = new ArgTypeMetaExtractor(config);
        String sourceCodeString = "package hoge.foo; public class Sample { public Sample() {}\r\n public void doSomething(String str) { System.out.println(\"aaaa\") } }";
        ClassMeta classMeta = new ClassMetaExtractor(config).extract(sourceCodeString);
        target.initialize(classMeta, sourceCodeString);
    }

    @Test
    public void doExtract_A$String() throws Exception {
        Configuration config = new Configuration();
        ArgTypeMetaExtractor target = new ArgTypeMetaExtractor(config);
        String sourceCodeString = "package hoge.foo; public class Sample { public Sample() {}\r\n public void doSomething(String str) { System.out.println(\"aaaa\") } }";
        target.initialize(sourceCodeString);
        String argsAreaString = "String str";
        target.doExtract(argsAreaString);
        assertEquals(target.getExtractedMetaList().size(), 1);
        assertEquals(target.getExtractedMetaList().get(0).name, "String");
        assertEquals(target.getExtractedNameList().size(), 1);
        assertEquals(target.getExtractedNameList().get(0), "str");
    }

    @Test
    public void initialize_A$ClassMeta() throws Exception {
        Configuration config = new Configuration();
        ArgTypeMetaExtractor target = new ArgTypeMetaExtractor(config);
        // given
        ClassMeta classMeta = mock(ClassMeta.class);
        // e.g. : given(mocked.called()).willReturn(1);
        // when
        ArgTypeMetaExtractor actual = target.initialize(classMeta);
        // then
        // e.g. : verify(mocked).called();
        ArgTypeMetaExtractor expected = target;
        assertEquals(expected, actual);
    }

    @Test
    public void initialize_A$String_StringIsNull() throws Exception {
        ArgTypeMetaExtractor target = new ArgTypeMetaExtractor(config);
        String sourceCodeString = null;
        try {
            target.initialize(sourceCodeString);
            fail();
        } catch (JUnitHelperCoreException e) {
        }
    }

    @Test
    public void initialize_A$String_StringIsEmpty() throws Exception {
        ArgTypeMetaExtractor target = new ArgTypeMetaExtractor(config);
        String sourceCodeString = "";
        ArgTypeMetaExtractor actual = target.initialize(sourceCodeString);
        assertThat(actual, notNullValue());
    }

    @Test
    public void doExtract_A$String_StringIsNull() throws Exception {
        ArgTypeMetaExtractor target = new ArgTypeMetaExtractor(config);
        ClassMeta classMeta = new ClassMetaExtractor(config).extract("public class Sample {}");
        target.initialize(classMeta);
        String argsAreaString = null;
        try {
            target.doExtract(argsAreaString);
            fail();
        } catch (JUnitHelperCoreException e) {
        }
    }

    @Test
    public void doExtract_A$String_StringIsEmpty() throws Exception {
        ArgTypeMetaExtractor target = new ArgTypeMetaExtractor(config);
        ClassMeta classMeta = new ClassMetaExtractor(config).extract("public class Sample {}");
        target.initialize(classMeta);
        String argsAreaString = "";
        target.doExtract(argsAreaString);
    }

    @Test
    public void initialize_A$ClassMeta$String_StringIsNull() throws Exception {
        ArgTypeMetaExtractor target = new ArgTypeMetaExtractor(config);
        ClassMeta classMeta = new ClassMetaExtractor(config).extract("public class Sample {}");
        String sourceCodeString = null;
        ArgTypeMetaExtractor actual = target.initialize(classMeta, sourceCodeString);
        assertThat(actual, notNullValue());
    }

    @Test
    public void initialize_A$ClassMeta$String_StringIsEmpty() throws Exception {
        ArgTypeMetaExtractor target = new ArgTypeMetaExtractor(config);
        ClassMeta classMeta = new ClassMetaExtractor(config).extract("public class Sample {}");
        String sourceCodeString = "";
        ArgTypeMetaExtractor actual = target.initialize(classMeta, sourceCodeString);
        assertThat(actual, notNullValue());
    }

}
