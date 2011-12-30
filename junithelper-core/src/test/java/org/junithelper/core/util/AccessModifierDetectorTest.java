package org.junithelper.core.util;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junithelper.core.util.AccessModifierDetector;

public class AccessModifierDetectorTest {

    @Test
    public void type() throws Exception {
        assertNotNull(AccessModifierDetector.class);
    }

    @Test
    public void isPublic_A$String_true() throws Exception {
        String methodSignature = " public static void main(String[] args) { ";
        boolean actual = AccessModifierDetector.isPublic(methodSignature);
        boolean expected = true;
        assertEquals(expected, actual);
    }

    @Test
    public void isPublic_A$String_true_bracePrefix() throws Exception {
        String methodSignature = "  { public void main(String[] args) { ";
        boolean actual = AccessModifierDetector.isPublic(methodSignature);
        boolean expected = true;
        assertEquals(expected, actual);
    }

    @Test
    public void isPublic_A$String_false() throws Exception {
        String methodSignature = "protected static void main(String[] args) {";
        boolean actual = AccessModifierDetector.isPublic(methodSignature);
        boolean expected = false;
        assertEquals(expected, actual);
    }

    @Test
    public void isProtected_A$String_true() throws Exception {
        String methodSignature = "protected String hogehge(String hoge) throws Exception {";
        boolean actual = AccessModifierDetector.isProtected(methodSignature);
        boolean expected = true;
        assertEquals(expected, actual);
    }

    @Test
    public void isProtected_A$String_false() throws Exception {
        String methodSignature = "private String hogehge(String hoge) throws Exception {";
        boolean actual = AccessModifierDetector.isProtected(methodSignature);
        boolean expected = false;
        assertEquals(expected, actual);
    }

    @Test
    public void isPackageLocal_A$String_true() throws Exception {
        String methodSignature = " static final String hogehoge(List<String> list) throws Throwable { ";
        boolean actual = AccessModifierDetector.isPackageLocal(methodSignature);
        boolean expected = true;
        assertEquals(expected, actual);
    }

    @Test
    public void isPackageLocal_A$String_false() throws Exception {
        String methodSignature = "private final String hogehoge(List<String> list) throws Throwable { ";
        boolean actual = AccessModifierDetector.isPackageLocal(methodSignature);
        boolean expected = false;
        assertEquals(expected, actual);
    }

    @Test
    public void isPrivate_A$String_true() throws Exception {
        String methodSignature = "private void _do(String name) {";
        boolean actual = AccessModifierDetector.isPrivate(methodSignature);
        boolean expected = true;
        assertEquals(expected, actual);
    }

    @Test
    public void isPrivate_A$String_false() throws Exception {
        String methodSignature = "void _do(String name) {";
        boolean actual = AccessModifierDetector.isPrivate(methodSignature);
        boolean expected = false;
        assertEquals(expected, actual);
    }

    @Test
    public void isPublic_A$String_StringIsNull() throws Exception {
        String methodSignature = null;
        boolean actual = AccessModifierDetector.isPublic(methodSignature);
        boolean expected = false;
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void isPublic_A$String_StringIsEmpty() throws Exception {
        String methodSignature = "";
        boolean actual = AccessModifierDetector.isPublic(methodSignature);
        boolean expected = false;
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void isProtected_A$String_StringIsNull() throws Exception {
        String methodSignature = null;
        boolean actual = AccessModifierDetector.isProtected(methodSignature);
        boolean expected = false;
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void isProtected_A$String_StringIsEmpty() throws Exception {
        String methodSignature = "";
        boolean actual = AccessModifierDetector.isProtected(methodSignature);
        boolean expected = false;
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void isPackageLocal_A$String_StringIsNull() throws Exception {
        String methodSignature = null;
        boolean actual = AccessModifierDetector.isPackageLocal(methodSignature);
        boolean expected = false;
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void isPackageLocal_A$String_StringIsEmpty() throws Exception {
        String methodSignature = "";
        boolean actual = AccessModifierDetector.isPackageLocal(methodSignature);
        boolean expected = true;
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void isPrivate_A$String_StringIsNull() throws Exception {
        String methodSignature = null;
        boolean actual = AccessModifierDetector.isPrivate(methodSignature);
        boolean expected = false;
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void isPrivate_A$String_StringIsEmpty() throws Exception {
        String methodSignature = "";
        boolean actual = AccessModifierDetector.isPrivate(methodSignature);
        boolean expected = false;
        assertThat(actual, is(equalTo(expected)));
    }

}
