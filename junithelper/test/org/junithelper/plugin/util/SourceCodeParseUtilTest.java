package org.junithelper.plugin.util;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

public class SourceCodeParseUtilTest extends TestCase {

	public void test_getTargetMethods_A$String() throws Exception {
		List<String> exList = new ArrayList<String>();
		exList.add(" static void methodStaticReturnVoid() {");
		exList.add(" static void methodStaticFinalReturnVoid() {");
		exList.add(" boolean returnPrimitive_boolean(boolean arg) {");
		exList.add(" int returnPrimitive_int(int arg) {");
		exList.add(" byte returnPrimitive_byte(byte arg) {");
		exList.add(" short returnPrimitive_short(short arg) {");
		exList.add(" long returnPrimitive_long(long arg) {");
		exList.add(" char returnPrimitive_char(char arg) {");
		exList.add(" float returnPrimitive_float(float arg) {");
		exList.add(" double returnPrimitive_double(double arg) {");
		exList.add(" double[] returnPrimitiveArray_double(double[] arg) {");
		exList.add(" JUnitHelperTestBean getBean(SampleBean arg) {");
		exList.add(" Map<String,String> getMap() {");
		exList.add(" void setMap(Map<String,Object> map) {");
		exList.add(" List<String> getList() {");
		exList.add(" void setList(List<String> arg) {");
		String setMapList = " void setMapList(Map<String,Object> map,List<String> list) {";
		exList.add(setMapList);
		String methodReturnMapGetMap = " Map<String,Object> methodReturnMapGetMap(Map<Object,String> hoge) {";
		exList.add(methodReturnMapGetMap);
		exList.add(" <T> void issue9(Map<String,T> singletonComponents) {");
		String methodSomeSringArgs = " String methodSomeSringArgs(String arg,String arg2,String arg3,  String arg4) {";
		exList.add(methodSomeSringArgs);
		exList.add(" List<String> getList(String... args) {");
		exList.add(" List<String> toList(String[] args1,String[] args2) {");
		exList.add(" static <T> T[] toArray(List<T> arg) {");
		exList.add(" static <T> List<T> toArrayList(T[] arg) {");
		exList.add(" static <T> T[] deepCopy(T[] arg) {");
		String infoLoggerStringboolean = " static void info(Logger logger,String msg,boolean logging) {";
		exList.add(infoLoggerStringboolean);
		exList.add(" static void info(Logger logger,String msg) {");
		exList.add(" void methodArgFinal(final Logger finalLog) {");
		exList.add(" static Timestamp issue19(java.sql.Date date) {");
		exList.add(" static java.sql.Date issue20() {");
		exList.add(" static java.sql.Date issue21Static() {");
		exList.add(" java.sql.Date issue21Instance() {");
		String issue24 = " static String issue24(Class<?> clazz,String methodName,  Object[] args) {";
		exList.add(issue24);
		exList.add(" String throwsException() throws Exception {");
		exList.add(" } static String[] staticPackageLocal() {");
		exList.add(" } void packageLocal() {");

		String arg = "package test;  import java.io.IOException; import java.io.InputStream; import java.sql.Timestamp; import java.util.List; import java.util.Map; import java.util.logging.Logger;  import test.bean.SampleBean;  @SuppressWarnings(value = { \"issue 28\" }) public class JUnitHelperTestBean {  	public String publicField; 	protected String protectedField = new String(); 	String packageLocalField;  	static {}  	static class Hoge {}  	public static void methodStaticReturnVoid() {}  	public static final void methodStaticFinalReturnVoid() {}  	public boolean returnPrimitive_boolean(boolean arg) {}  String packageLocalField2;	public int returnPrimitive_int(int arg) {}  	public byte returnPrimitive_byte(byte arg) {}  	public short returnPrimitive_short(short arg) {}  	public long returnPrimitive_long(long arg) {}  	public char returnPrimitive_char(char arg) {}  	public float returnPrimitive_float(float arg) {}  	public double returnPrimitive_double(double arg) {}  	public double[] returnPrimitiveArray_double(double[] arg) {}  	public JUnitHelperTestBean getBean(SampleBean arg) {}  	public Map<String,String> getMap() {}  	public void setMap(Map<String,Object> map) {}  	public List<String> getList() {}  	public void setList(List<String> arg) {}  	public void setMapList(Map<String,Object> map,List<String> list) {}  	public Map<String,Object> methodReturnMapGetMap(Map<Object,String> hoge) {}  	public <T> void issue9(Map<String,T> singletonComponents) {}  	 public String methodSomeSringArgs(String arg,String arg2,String arg3,			String arg4) {}  	public List<String> getList(String... args) {}  	public List<String> toList(String[] args1,String[] args2) {}  	public static <T> T[] toArray(List<T> arg) {}  	public static <T> List<T> toArrayList(T[] arg) {}  	public static <T> T[] deepCopy(T[] arg) {}  	public static final void info(Logger logger,String msg,boolean logging) {}  	public static final void info(Logger logger,String msg) {}  	public void methodArgFinal(final Logger finalLog) {}  	public static Timestamp issue19(java.sql.Date date) {}  	public static java.sql.Date issue20() {}  	protected static java.sql.Date issue21Static() {}  	protected java.sql.Date issue21Instance() {}  	public static String issue24(Class<?> clazz,String methodName,			Object[] args) {}  	public static class Inner {}  	protected String throwsException() throws Exception {} static String[] staticPackageLocal() {} void packageLocal() {} } ";
		List<String> actual = SourceCodeParseUtil.getTargetMethods(arg, true,
				true, true);
		assertEquals(exList.size() + "->" + actual.size(), exList.size(),
				actual.size());
		for (int i = 0; i < exList.size(); i++) {
			assertEquals(exList.get(i) + "," + actual, exList.get(i), actual
					.get(i));
		}
	}

	public void test_getLineList_A$IFile() throws Exception {
		// SKIP
	}

	public void test_trimLineComments_A$String() throws Exception {
		String[] expected = { "	", "aaa", "aaa", "aaa", "	aaa" };
		String[] arg0 = { "	// Expected : 45 methods\n", "// hogehoge{\naaa",
				"// hoge{hoge\r\naaa", "// hogehogehoge[]sss{\naaa",
				"	// public int returnPrimitive_int(int arg) {\naaa" };
		for (int i = 0; i < expected.length; i++) {
			String actual = SourceCodeParseUtil.trimLineComments(arg0[i]);
			assertEquals(expected[i], actual);
		}
	}

	public void test_trimAllComments_A$String() throws Exception {
		String[] expected = { "aaa", "aaa", "aaa", "	aaa", "  aa" };
		String[] arg0 = { "// hogehoge{\naaa", "// hogehoge{sdfas{\r\naaa",
				"// hogehogehoge[] ssss(){\naaa",
				"	// public int returnPrimitive_int(int arg) {\naaa",
				" /* hogehoge \n */ aa" };
		for (int i = 0; i < expected.length; i++) {
			String actual = SourceCodeParseUtil.trimAllComments(arg0[i]);
			assertEquals(expected[i], actual);
		}
	}

	public void test_trimInsideOfBraces_A$String() throws Exception {
		String expected = "package test;  import java.io.IOException; import java.io.InputStream; import java.sql.Timestamp; import java.util.List; import java.util.Map; import java.util.logging.Logger;  import test.bean.SampleBean;  @SuppressWarnings(value = { \"issue 28\" }) public class JUnitHelperTestBean {  	public String publicField; 	protected String protectedField = new String(); 	String packageLocalField;  	static {}  	static class Hoge {}  	public static void methodStaticReturnVoid() {}  	public static final void methodStaticFinalReturnVoid() {}  	public boolean returnPrimitive_boolean(boolean arg) {}  String packageLocalField2;	public int returnPrimitive_int(int arg) {}  	public byte returnPrimitive_byte(byte arg) {}  	public short returnPrimitive_short(short arg) {}  	public long returnPrimitive_long(long arg) {}  	public char returnPrimitive_char(char arg) {}  	public float returnPrimitive_float(float arg) {}  	public double returnPrimitive_double(double arg) {}  	public double[] returnPrimitiveArray_double(double[] arg) {}  	public JUnitHelperTestBean getBean(SampleBean arg) {}  	public Map<String,String> getMap() {}  	public void setMap(Map<String,Object> map) {}  	public List<String> getList() {}  	public void setList(List<String> arg) {}  	public void setMapList(Map<String,Object> map,List<String> list) {}  	public Map<String,Object> methodReturnMapGetMap(Map<Object,String> hoge) {}  	public <T> void issue9(Map<String,T> singletonComponents) {}  	 public String methodSomeSringArgs(String arg,String arg2,String arg3,			String arg4) {}  	public List<String> getList(String... args) {}  	public List<String> toList(String[] args1,String[] args2) {}  	public static <T> T[] toArray(List<T> arg) {}  	public static <T> List<T> toArrayList(T[] arg) {}  	public static <T> T[] deepCopy(T[] arg) {}  	public static final void info(Logger logger,String msg,boolean logging) {}  	public static final void info(Logger logger,String msg) {}  	public void methodArgFinal(final Logger finalLog) {}  	public static Timestamp issue19(java.sql.Date date) {}  	public static java.sql.Date issue20() {}  	protected static java.sql.Date issue21Static() {}  	protected java.sql.Date issue21Instance() {}  	public static String issue24(Class<?> clazz,String methodName,			Object[] args) {}  	public static class Inner {}  	protected String throwsException() throws Exception {} static String[] staticPackageLocal() {} void packageLocal() {} } ";
		String arg = "package test;  import java.io.IOException; import java.io.InputStream; import java.sql.Timestamp; import java.util.List; import java.util.Map; import java.util.logging.Logger;  import test.bean.SampleBean;  @SuppressWarnings(value = { \"issue 28\" }) public class JUnitHelperTestBean {  	public String publicField; 	protected String protectedField = new String(); 	String packageLocalField;  	static {}  	static class Hoge {}  	public static void methodStaticReturnVoid() {}  	public static final void methodStaticFinalReturnVoid() {}  	public boolean returnPrimitive_boolean(boolean arg) {}  String packageLocalField2;	public int returnPrimitive_int(int arg) {}  	public byte returnPrimitive_byte(byte arg) {}  	public short returnPrimitive_short(short arg) {}  	public long returnPrimitive_long(long arg) {}  	public char returnPrimitive_char(char arg) {}  	public float returnPrimitive_float(float arg) {}  	public double returnPrimitive_double(double arg) {}  	public double[] returnPrimitiveArray_double(double[] arg) {}  	public JUnitHelperTestBean getBean(SampleBean arg) {}  	public Map<String,String> getMap() {}  	public void setMap(Map<String,Object> map) {}  	public List<String> getList() {}  	public void setList(List<String> arg) {}  	public void setMapList(Map<String,Object> map,List<String> list) {}  	public Map<String,Object> methodReturnMapGetMap(Map<Object,String> hoge) {}  	public <T> void issue9(Map<String,T> singletonComponents) {}  	 public String methodSomeSringArgs(String arg,String arg2,String arg3,			String arg4) {}  	public List<String> getList(String... args) {}  	public List<String> toList(String[] args1,String[] args2) {}  	public static <T> T[] toArray(List<T> arg) {}  	public static <T> List<T> toArrayList(T[] arg) {}  	public static <T> T[] deepCopy(T[] arg) {}  	public static final void info(Logger logger,String msg,boolean logging) {}  	public static final void info(Logger logger,String msg) {}  	public void methodArgFinal(final Logger finalLog) {}  	public static Timestamp issue19(java.sql.Date date) {}  	public static java.sql.Date issue20() {}  	protected static java.sql.Date issue21Static() {}  	protected java.sql.Date issue21Instance() {}  	public static String issue24(Class<?> clazz,String methodName,			Object[] args) {}  	public static class Inner {}  	protected String throwsException() throws Exception {} static String[] staticPackageLocal() {} void packageLocal() {} } ";
		String actual = SourceCodeParseUtil.trimInsideOfBraces(arg);
		assertEquals(expected, actual);
	}
}
