package org.junithelper.core.util;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junithelper.core.meta.ArgTypeMeta;
import org.junithelper.core.meta.MethodMeta;

public class ObjectUtilTest {

    @Test
    public void deepCopy_A$T_paramNull() throws Exception {
        Object arg = null;
        Object res = ObjectUtil.deepCopy(arg);
        assertNull(res);
        assertNull(arg);
    }

    @Test
    public void deepCopy_A$T_paramObject() throws Exception {
        Object arg = new Object();
        Object res = ObjectUtil.deepCopy(arg);
        assertNotSame(res, arg);
    }

    @Test
    public void deepCopy_A$T_paramClass() throws Exception {
        Object arg = new Object();
        Object res = ObjectUtil.deepCopy(arg);
        assertNotSame(res, arg);
    }

    @Test
    public void deepCopy_A$T_MethodInfo() throws Exception {
        MethodMeta arg = new MethodMeta();
        arg.argTypes.add(new ArgTypeMeta());
        arg.isStatic = true;
        arg.name = "hogehoge";
        MethodMeta res = ObjectUtil.deepCopy(arg);
        assertNotSame(arg.argTypes, res.argTypes);
        assertNotSame(arg.argTypes.get(0), res.argTypes.get(0));
        assertEquals(arg.isStatic, res.isStatic);
        assertEquals(arg.name, res.name);
    }

    @Test
    public void deepCopyList_A$List_List() throws Exception {
        List<Object> arg = new ArrayList<Object>();
        arg.add(new Object());
        arg.add(new Object());
        List<Object> result = ObjectUtil.deepCopyList(arg);
        assertNotSame(result, arg);
        assertNotSame(result.get(0), arg.get(0));
        assertNotSame(result.get(0), arg.get(0));
    }

    @Test
    public void deepCopyList_A$List_empty() throws Exception {
        List<Object> arg = new ArrayList<Object>();
        List<Object> result = ObjectUtil.deepCopyList(arg);
        assertNotSame(result, arg);
    }

    @Test
    public void deepCopyList_A$List_null() throws Exception {
        List<Object> arg = null;
        List<Object> result = ObjectUtil.deepCopyList(arg);
        assertSame(result, arg);
    }

    @Test
    public void type() throws Exception {
        assertNotNull(ObjectUtil.class);
    }

    @Test
    public void deepCopy_A$Object_null() throws Exception {
        // given
        Object obj = null;
        // when
        Object actual = ObjectUtil.deepCopy(obj);
        // then
        Object expected = null;
        assertEquals(expected, actual);
    }

    @Test
    public void deepCopy_A$Object_notNull() throws Exception {
        // given
        Object obj = new ObjectUtilTest();
        // when
        Object actual = ObjectUtil.deepCopy(obj);
        // then
        Object expected = obj;
        assertFalse(expected == actual);
    }

}
