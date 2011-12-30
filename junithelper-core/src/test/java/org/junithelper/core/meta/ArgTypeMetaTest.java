package org.junithelper.core.meta;

import static org.junit.Assert.*;

import org.junit.Test;

public class ArgTypeMetaTest {

    @Test
    public void type() throws Exception {
        assertNotNull(ArgTypeMeta.class);
    }

    @Test
    public void instantiation() throws Exception {
        ArgTypeMeta target = new ArgTypeMeta();
        assertNotNull(target);
    }

    @Test
    public void getGenericsAsString_A$() throws Exception {
        // given
        ArgTypeMeta target = new ArgTypeMeta();
        target.generics.add("String");
        target.generics.add("Object");
        // when
        String actual = target.getGenericsAsString();
        // then
        String expected = "<String, Object>";
        assertEquals(expected, actual);
    }

    @Test
    public void toString_A$() throws Exception {
        ArgTypeMeta target = new ArgTypeMeta();
        try {
            target.toString();
            fail();
        } catch (IllegalStateException e) {
        }
    }

}
