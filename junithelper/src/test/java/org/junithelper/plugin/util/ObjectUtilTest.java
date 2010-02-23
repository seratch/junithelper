package org.junithelper.plugin.util;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junithelper.plugin.bean.MethodInfo;
import org.junithelper.plugin.bean.MethodInfo.ArgType;

public class ObjectUtilTest extends TestCase {

	public void test_deepCopy_A$T_paramNull() throws Exception {
		Object arg = null;
		Object res = ObjectUtil.deepCopy(arg);
		assertNull(res);
		assertNull(arg);
	}

	public void test_deepCopy_A$T_paramObject() throws Exception {
		Object arg = new Object();
		Object res = ObjectUtil.deepCopy(arg);
		assertNotSame(res, arg);
	}

	public void test_deepCopy_A$T_paramClass() throws Exception {
		Object arg = new Object();
		Object res = ObjectUtil.deepCopy(arg);
		assertNotSame(res, arg);
	}

	public void test_deepCopy_A$T_MethodInfo() throws Exception {
		MethodInfo arg = new MethodInfo();
		arg.argTypes.add(new ArgType());
		arg.isStatic = true;
		arg.methodName = "hogehoge";
		arg.testMethodName = "test_hogehoge";
		MethodInfo res = ObjectUtil.deepCopy(arg);
		assertNotSame(arg.argTypes, res.argTypes);
		assertNotSame(arg.argTypes.get(0), res.argTypes.get(0));
		assertEquals(arg.isStatic, res.isStatic);
		assertEquals(arg.methodName, res.methodName);
		assertEquals(arg.testMethodName, res.testMethodName);
	}

	public void test_deepCopyList_A$List_List() throws Exception {
		List<Object> arg = new ArrayList<Object>();
		arg.add(new Object());
		arg.add(new Object());
		List<Object> result = ObjectUtil.deepCopyList(arg);
		assertNotSame(result, arg);
		assertNotSame(result.get(0), arg.get(0));
		assertNotSame(result.get(0), arg.get(0));
	}

	public void test_deepCopyList_A$List_empty() throws Exception {
		List<Object> arg = new ArrayList<Object>();
		List<Object> result = ObjectUtil.deepCopyList(arg);
		assertNotSame(result, arg);
	}

	public void test_deepCopyList_A$List_null() throws Exception {
		List<Object> arg = null;
		List<Object> result = ObjectUtil.deepCopyList(arg);
		assertSame(result, arg);
	}

}
