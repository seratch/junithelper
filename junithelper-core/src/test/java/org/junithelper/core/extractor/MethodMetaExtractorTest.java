package org.junithelper.core.extractor;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.Test;
import org.junithelper.core.config.Configuration;
import org.junithelper.core.exception.JUnitHelperCoreException;
import org.junithelper.core.extractor.ClassMetaExtractor;
import org.junithelper.core.extractor.MethodMetaExtractor;
import org.junithelper.core.meta.AccessModifier;
import org.junithelper.core.meta.ClassMeta;
import org.junithelper.core.meta.MethodMeta;

public class MethodMetaExtractorTest {

    @Test
    public void type() throws Exception {
        assertNotNull(MethodMetaExtractor.class);
    }

    @Test
    public void instantiation() throws Exception {
        Configuration config = null;
        MethodMetaExtractor target = new MethodMetaExtractor(config);
        assertNotNull(target);
    }

    Configuration config = new Configuration();
    MethodMetaExtractor target = new MethodMetaExtractor(config);
    ClassMetaExtractor classMetaExtractor = new ClassMetaExtractor(config);

    @Test
    public void initialize_A$String() throws Exception {
        String sourceCodeString = "package hoge.foo; public class Sample { public Sample() {}\r\n public void doSomething(String str) { System.out.println(\"aaaa\") } }";
        target.initialize(sourceCodeString);
    }

    @Test
    public void initialize_A$ClassMeta$String() throws Exception {
        String sourceCodeString = "package hoge.foo; public class Sample { public Sample() {}\r\n public void doSomething(String str) { System.out.println(\"aaaa\") } }";
        ClassMeta classMeta = classMetaExtractor.extract(sourceCodeString);
        target.initialize(classMeta, sourceCodeString);
    }

    @Test
    public void extract_A$String_generics() throws Exception {
        String sourceCodeString = "package hoge.foo; import java.util.List; import java.util.Map; public class Sample { public Sample() {}\r\n public List<String> doSomething(Integer intVal, long longVal) { System.out.println(\"aaaa\") } }";
        ClassMeta classMeta = classMetaExtractor.extract(sourceCodeString);
        target.initialize(classMeta, sourceCodeString);
        List<MethodMeta> actual = target.extract(sourceCodeString);
        assertEquals(1, actual.size());
        assertEquals("doSomething", actual.get(0).name);
        assertEquals(2, actual.get(0).argNames.size());
        assertEquals("List<String>", actual.get(0).returnType.name);
        assertEquals(1, actual.get(0).returnType.generics.size());
    }

    @Test
    public void extract_A$String_nestedGenerics() throws Exception {
        String sourceCodeString = "package hoge.foo; import java.util.List; import java.util.Map; public class Sample { public Sample() {}\r\n public List< Map< String, Object>> doSomething(Integer intVal, long longVal) { System.out.println(\"aaaa\") } }";
        ClassMeta classMeta = classMetaExtractor.extract(sourceCodeString);
        target.initialize(classMeta, sourceCodeString);
        List<MethodMeta> actual = target.extract(sourceCodeString);
        assertEquals(1, actual.size());
        assertEquals("doSomething", actual.get(0).name);
        assertEquals(2, actual.get(0).argNames.size());
        assertEquals("List", actual.get(0).returnType.name);
        assertEquals(0, actual.get(0).returnType.generics.size());
    }

    @Test
    public void extract_A$String_somethingWrong() throws Exception {
        String sourceCodeString = "package foo.var; public class Something { 	public String toLable() {\r\n		String label = \"\";\r\n		try {\r\n			if(name.equals(relax.name)){\r\n				label = \"aaa\";\r\n			} else if(name.equals(nurturing.name)){\r\n				label = \"bbb\";\r\n			} else if(name.equals(word.name)){\r\n				label = \"ccc\";\r\n			}\r\n		}catch (Exception e) {}\r\n		return label;\r\n	}\r\n }";
        ClassMeta classMeta = classMetaExtractor.extract(sourceCodeString);
        target.initialize(classMeta, sourceCodeString);
        List<MethodMeta> actual = target.extract(sourceCodeString);
        assertEquals(1, actual.size());
        assertEquals("toLable", actual.get(0).name);
        assertEquals(0, actual.get(0).argTypes.size());
        assertEquals(0, actual.get(0).argNames.size());
    }

    @Test
    public void initialize_A$ClassMeta() throws Exception {
        // given
        ClassMeta classMeta = mock(ClassMeta.class);
        // e.g. : given(mocked.called()).willReturn(1);
        // when
        MethodMetaExtractor actual = target.initialize(classMeta);
        // then
        // e.g. : verify(mocked).called();
        assertNotNull(actual);
    }

    @Test
    public void isPrivateFieldExists_A$String$String$String() throws Exception {
        // given
        String fieldType = "java.lang.String";
        String fieldName = "str";
        String sourceCodeString = "package hoge; public class Sample { public void doSomething() {} }";
        // e.g. : given(mocked.called()).willReturn(1);
        // when
        boolean actual = target.isPrivateFieldExists(fieldType, fieldName, sourceCodeString);
        // then
        // e.g. : verify(mocked).called();
        boolean expected = false;
        assertEquals(expected, actual);
    }

    @Test
    public void getAccessModifier_A$String() throws Exception {
        // given
        String methodSignatureArea = "} public static void main(String[] args) {";
        // when
        AccessModifier actual = target.getAccessModifier(methodSignatureArea);
        // then
        AccessModifier expected = AccessModifier.Public;
        assertEquals(expected, actual);
    }

    @Test
    public void trimAccessModifierFromMethodSignatureArea_A$String() throws Exception {
        // given
        String methodSignatureArea = "} public static void main(String[] args) {";
        // e.g. : given(mocked.called()).willReturn(1);
        // when
        String actual = MethodMetaExtractor.trimAccessModifierFromMethodSignatureArea(methodSignatureArea);
        // then
        // e.g. : verify(mocked).called();
        String expected = " static void main(String[] args) {";
        assertEquals(expected, actual);
    }

    @Test
    public void trimGenericsIfNested_A$String_argNull() throws Exception {
        // given
        String returnTypeDef = null;
        // when
        String actual = MethodMetaExtractor.trimGenericsIfNested(returnTypeDef);
        // then
        String expected = null;
        assertEquals(expected, actual);
    }

    @Test
    public void trimGenericsIfNested_A$String() throws Exception {
        // given
        String returnTypeDef = "List<String,Map<String,String>>";
        // when
        String actual = MethodMetaExtractor.trimGenericsIfNested(returnTypeDef);
        // then
        String expected = "List";
        assertEquals(expected, actual);
    }

    @Test
    public void initialize_A$String_StringIsNull() throws Exception {
        MethodMetaExtractor target = new MethodMetaExtractor(config);
        String sourceCodeString = null;
        try {
            target.initialize(sourceCodeString);
            fail();
        } catch (JUnitHelperCoreException e) {
        }
    }

    @Test
    public void initialize_A$String_StringIsEmpty() throws Exception {
        MethodMetaExtractor target = new MethodMetaExtractor(config);
        String sourceCodeString = "";
        MethodMetaExtractor actual = target.initialize(sourceCodeString);
        assertThat(actual, notNullValue());
    }

    @Test
    public void extract_A$String_StringIsNull() throws Exception {
        MethodMetaExtractor target = new MethodMetaExtractor(config);
        String sourceCodeString = null;
        try {
            target.extract(sourceCodeString);
            fail();
        } catch (JUnitHelperCoreException e) {
        }
    }

    @Test
    public void extract_A$String_StringIsEmpty() throws Exception {
        MethodMetaExtractor target = new MethodMetaExtractor(config);
        String sourceCodeString = "";
        List<MethodMeta> actual = target.extract(sourceCodeString);
        assertThat(actual, notNullValue());
    }

    @Test
    public void isPrivateFieldExists_A$String$String$String_StringIsNull() throws Exception {
        MethodMetaExtractor target = new MethodMetaExtractor(config);
        String fieldType = null;
        String fieldName = null;
        String sourceCodeString = null;
        try {
            target.isPrivateFieldExists(fieldType, fieldName, sourceCodeString);
            fail();
        } catch (JUnitHelperCoreException e) {
        }
    }

    @Test
    public void isPrivateFieldExists_A$String$String$String_StringIsEmpty() throws Exception {
        MethodMetaExtractor target = new MethodMetaExtractor(config);
        String fieldType = "";
        String fieldName = "";
        String sourceCodeString = "";
        try {
            target.isPrivateFieldExists(fieldType, fieldName, sourceCodeString);
            fail();
        } catch (JUnitHelperCoreException e) {
        }
    }

    @Test
    public void getAccessModifier_A$String_StringIsNull() throws Exception {
        MethodMetaExtractor target = new MethodMetaExtractor(config);
        String methodSignatureArea = null;
        AccessModifier actual = target.getAccessModifier(methodSignatureArea);
        AccessModifier expected = AccessModifier.Public;
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void getAccessModifier_A$String_StringIsEmpty() throws Exception {
        MethodMetaExtractor target = new MethodMetaExtractor(config);
        String methodSignatureArea = "";
        AccessModifier actual = target.getAccessModifier(methodSignatureArea);
        AccessModifier expected = AccessModifier.PackageLocal;
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void trimAccessModifierFromMethodSignatureArea_A$String_StringIsNull() throws Exception {
        String methodSignatureArea = null;
        try {
            MethodMetaExtractor.trimAccessModifierFromMethodSignatureArea(methodSignatureArea);
            fail();
        } catch (JUnitHelperCoreException e) {
        }
    }

    @Test
    public void trimAccessModifierFromMethodSignatureArea_A$String_StringIsEmpty() throws Exception {
        String methodSignatureArea = "";
        String actual = MethodMetaExtractor.trimAccessModifierFromMethodSignatureArea(methodSignatureArea);
        String expected = "";
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void trimGenericsIfNested_A$String_StringIsNull() throws Exception {
        String returnTypeDef = null;
        String actual = MethodMetaExtractor.trimGenericsIfNested(returnTypeDef);
        String expected = null;
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void trimGenericsIfNested_A$String_StringIsEmpty() throws Exception {
        String returnTypeDef = "";
        String actual = MethodMetaExtractor.trimGenericsIfNested(returnTypeDef);
        String expected = "";
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void initialize_A$ClassMeta$String_StringIsNull() throws Exception {
        MethodMetaExtractor target = new MethodMetaExtractor(config);
        ClassMeta classMeta = new ClassMetaExtractor(config).extract("public class Sample {}");
        String sourceCodeString = null;
        MethodMetaExtractor actual = target.initialize(classMeta, sourceCodeString);
        assertThat(actual, notNullValue());
    }

    @Test
    public void initialize_A$ClassMeta$String_StringIsEmpty() throws Exception {
        MethodMetaExtractor target = new MethodMetaExtractor(config);
        ClassMeta classMeta = new ClassMetaExtractor(config).extract("public class Sample {}");
        String sourceCodeString = "";
        MethodMetaExtractor actual = target.initialize(classMeta, sourceCodeString);
        assertThat(actual, notNullValue());
    }

}
