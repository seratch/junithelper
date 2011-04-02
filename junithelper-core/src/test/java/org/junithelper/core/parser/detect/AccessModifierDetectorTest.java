package org.junithelper.core.parser.detect;

import org.junit.Test;

import static org.junit.Assert.*;

public class AccessModifierDetectorTest {

    @Test
    public void type() throws Exception {
        assertNotNull(AccessModifierDetector.class);
    }

    @Test
    public void instantiation() throws Exception {
        AccessModifierDetector target = new AccessModifierDetector();
        assertNotNull(target);
    }

    AccessModifierDetector target = new AccessModifierDetector();

    @Test
    public void isPublic_A$String_true() throws Exception {
        String methodSignature = " public static void main(String[] args) { ";
        boolean actual = target.isPublic(methodSignature);
        boolean expected = true;
        assertEquals(expected, actual);
    }

    @Test
    public void isPublic_A$String_true_bracePrefix() throws Exception {
        String methodSignature = "  { public void main(String[] args) { ";
        boolean actual = target.isPublic(methodSignature);
        boolean expected = true;
        assertEquals(expected, actual);
    }

    @Test
    public void isPublic_A$String_false() throws Exception {
        String methodSignature = "protected static void main(String[] args) {";
        boolean actual = target.isPublic(methodSignature);
        boolean expected = false;
        assertEquals(expected, actual);
    }

    @Test
    public void isProtected_A$String_true() throws Exception {
        String methodSignature = "protected String hogehge(String hoge) throws Exception {";
        boolean actual = target.isProtected(methodSignature);
        boolean expected = true;
        assertEquals(expected, actual);
    }

    @Test
    public void isProtected_A$String_false() throws Exception {
        String methodSignature = "private String hogehge(String hoge) throws Exception {";
        boolean actual = target.isProtected(methodSignature);
        boolean expected = false;
        assertEquals(expected, actual);
    }

    @Test
    public void isPackageLocal_A$String_true() throws Exception {
        String methodSignature = " static final String hogehoge(List<String> list) throws Throwable { ";
        boolean actual = target.isPackageLocal(methodSignature);
        boolean expected = true;
        assertEquals(expected, actual);
    }

    @Test
    public void isPackageLocal_A$String_false() throws Exception {
        String methodSignature = "private final String hogehoge(List<String> list) throws Throwable { ";
        boolean actual = target.isPackageLocal(methodSignature);
        boolean expected = false;
        assertEquals(expected, actual);
    }

    @Test
    public void isPrivate_A$String_true() throws Exception {
        String methodSignature = "private void _do(String name) {";
        boolean actual = target.isPrivate(methodSignature);
        boolean expected = true;
        assertEquals(expected, actual);
    }

    @Test
    public void isPrivate_A$String_false() throws Exception {
        String methodSignature = "void _do(String name) {";
        boolean actual = target.isPrivate(methodSignature);
        boolean expected = false;
        assertEquals(expected, actual);
    }

}
