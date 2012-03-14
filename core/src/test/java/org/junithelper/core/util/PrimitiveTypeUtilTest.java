package org.junithelper.core.util;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junithelper.core.exception.JUnitHelperCoreException;

public class PrimitiveTypeUtilTest {

    @Test
    public void type() throws Exception {
        assertNotNull(PrimitiveTypeUtil.class);
    }

    @Test
    public void getPrimitiveClass_A$String() throws Exception {
        String[] args = new String[] { "byte", "short", "int", "long", "char", "float", "double", "boolean", "void", };
        Class<?>[] expected = new Class<?>[] { byte.class, short.class, int.class, long.class, char.class, float.class,
                double.class, boolean.class, void.class, };
        for (int i = 0; i < args.length; i++) {
            Class<?> actual = PrimitiveTypeUtil.getPrimitiveClass(args[i]);
            assertEquals(expected[i], actual);
        }
        try {
            PrimitiveTypeUtil.getPrimitiveClass("dummy");
            fail("Expected Exception did not occurred!");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void getTypeDefaultValue_A$String() throws Exception {
        String[] args = new String[] { "byte", "short", "int", "long", "char", "float", "double", "boolean", "void", };
        String[] expected = new String[] { "0", "0", "0", "0L", "'\u0000'", "0.0F", "0.0", "false", "void" };
        for (int i = 0; i < args.length; i++) {
            String actual = PrimitiveTypeUtil.getTypeDefaultValue(args[i]);
            assertEquals(expected[i], actual);
        }
        try {
            PrimitiveTypeUtil.getTypeDefaultValue("dummy");
            fail("Expected Exception did not occurred!");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void isPrimitive_A$String() throws Exception {
        String[] args = new String[] { "byte", "short", "int", "long", "char", "float", "double", "boolean", "void",
                "dummy" };
        boolean[] expected = new boolean[] { true, true, true, true, true, true, true, true, true, false };
        for (int i = 0; i < args.length; i++) {
            boolean actual = PrimitiveTypeUtil.isPrimitive(args[i]);
            assertEquals(expected[i], actual);
        }
    }

    @Test
    public void isPrimitive_A$String_StringIsNull() throws Exception {
        String typeName = null;
        try {
            PrimitiveTypeUtil.isPrimitive(typeName);
            fail();
        } catch (JUnitHelperCoreException e) {
        }
    }

    @Test
    public void isPrimitive_A$String_StringIsEmpty() throws Exception {
        String typeName = "";
        try {
            PrimitiveTypeUtil.isPrimitive(typeName);
            fail();
        } catch (JUnitHelperCoreException e) {
        }
    }

    @Test
    public void getTypeDefaultValue_A$String_StringIsNull() throws Exception {
        String typeName = null;
        try {
            PrimitiveTypeUtil.getTypeDefaultValue(typeName);
            fail();
        } catch (JUnitHelperCoreException e) {
        }
    }

    @Test
    public void getTypeDefaultValue_A$String_StringIsEmpty() throws Exception {
        String typeName = "";
        try {
            PrimitiveTypeUtil.getTypeDefaultValue(typeName);
            fail();
        } catch (JUnitHelperCoreException e) {
        }
    }

}
