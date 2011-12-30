package org.junithelper.core.meta;

import static org.junit.Assert.*;

import org.junit.Test;

public class MethodMetaTest {

    @Test
    public void type() throws Exception {
        assertNotNull(MethodMeta.class);
    }

    @Test
    public void instantiation() throws Exception {
        MethodMeta target = new MethodMeta();
        assertNotNull(target);
    }

    @Test
    public void toString_A$() throws Exception {
        // given
        MethodMeta target = new MethodMeta();
        target.name = "doSomething";
        ArgTypeMeta arg1 = new ArgTypeMeta();
        arg1.name = "List<String>";
        arg1.nameInMethodName = "List";
        arg1.generics.add("String");
        target.argTypes.add(arg1);
        ArgTypeMeta arg2 = new ArgTypeMeta();
        arg2.name = "Map<String,Object>";
        arg2.nameInMethodName = "Map";
        arg2.generics.add("String");
        arg2.generics.add("Object");
        target.argTypes.add(arg2);
        // when
        String actual = target.toString();
        // then
        String expected = "doSomething$List$Map";
        assertEquals(expected, actual);
    }

}
