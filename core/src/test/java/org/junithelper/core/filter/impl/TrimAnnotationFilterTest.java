package org.junithelper.core.filter.impl;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class TrimAnnotationFilterTest {

    @Test
    public void type() throws Exception {
        assertNotNull(TrimAnnotationFilter.class);
    }

    @Test
    public void instantiation() throws Exception {
        TrimAnnotationFilter target = new TrimAnnotationFilter();
        assertNotNull(target);
    }

    @Test
    public void trimAll_A$String_annotationClass() throws Exception {
        TrimAnnotationFilter target = new TrimAnnotationFilter();
        String src = "@SuppressWarnings(value = { \"issue 28\" }) @Documented @Retention(RetentionPolicy.RUNTIME) @Target( { ElementType.TYPE, ElementType.METHOD }) public @interface AdminRoleRequired {";
        String actual = target.trimAll(src);
        String expected = "        public interface AdminRoleRequired {";
        assertEquals(expected, actual);
    }

    @Test
    public void trimAll_A$String_annotationHasEqual() throws Exception {
        TrimAnnotationFilter target = new TrimAnnotationFilter();
        String src = "package hoge.foo; @SuppressWarnings(value = { \"issue 28\" })public class Sample { }";
        String actual = target.trimAll(src);
        String expected = "package hoge.foo;  public class Sample { }";
        assertEquals(expected, actual);
    }

    @Test
    public void trimAll_A$String_methodAnnotation() throws Exception {
        TrimAnnotationFilter target = new TrimAnnotationFilter();
        String src = "private TestMethodGenerator testMethodGenerator = new DefaultTestMethodGenerator();\r\n\t@Override\r\n\tpublic void initialize(Configuration config, String targetSourceCodeString) {";
        String actual = target.trimAll(src);
        String expected = "private TestMethodGenerator testMethodGenerator = new DefaultTestMethodGenerator();\r\n\t \r\n\tpublic void initialize(Configuration config, String targetSourceCodeString) {";
        assertEquals(expected, actual);
    }

    @Test
    public void trimAll_A$String_1() throws Exception {
        TrimAnnotationFilter target = new TrimAnnotationFilter();
        String src = "@Deprected\r\npublic void aaa(@NotNull String bbb) {";
        String actual = target.trimAll(src);
        String expected = " \r\npublic void aaa(  String bbb) {";
        assertEquals(expected, actual);
    }

    @Test
    public void trimAll_A$String_2() throws Exception {
        TrimAnnotationFilter target = new TrimAnnotationFilter();
        String src = "@Deprected\r\n@Target({ ElementType.TYPE, ElementType.METHOD }) \r\npublic void aaa(@NotNull String bbb) {";
        String actual = target.trimAll(src);
        String expected = " \r\n  \r\npublic void aaa(  String bbb) {";
        assertEquals(expected, actual);
    }

    @Test
    public void trimAll_A$String_StringIsNull() throws Exception {
        TrimAnnotationFilter target = new TrimAnnotationFilter();
        String src = null;
        String actual = target.trimAll(src);
        String expected = null;
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void trimAll_A$String_StringIsEmpty() throws Exception {
        TrimAnnotationFilter target = new TrimAnnotationFilter();
        String src = "";
        String actual = target.trimAll(src);
        String expected = "";
        assertThat(actual, is(equalTo(expected)));
    }

}
