package org.junithelper.plugin.util;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

public class SourceCodeParseUtilTest extends TestCase {

	public void test_getTargetMethods_A$String() throws Exception {
		String arg = "package test;  import java.io.IOException; import java.io.InputStream; import java.sql.Timestamp; import java.util.List; import java.util.Map; import java.util.logging.Logger;  import test.bean.SampleBean;  @SuppressWarnings(value = { \"issue 28\" }) public class JUnitHelperTestBean {  	public String publicField; 	protected String protectedField = new String(); 	String packageLocalField;  	static {}  	static class Hoge {}  	public static void methodStaticReturnVoid() {}  	public static final void methodStaticFinalReturnVoid() {}  	public boolean returnPrimitive_boolean(boolean arg) {}  String packageLocalField2;	public int returnPrimitive_int(int arg) {}  	public byte returnPrimitive_byte(byte arg) {}  	public short returnPrimitive_short(short arg) {}  	public long returnPrimitive_long(long arg) {}  	public char returnPrimitive_char(char arg) {}  	public float returnPrimitive_float(float arg) {}  	public double returnPrimitive_double(double arg) {}  	public double[] returnPrimitiveArray_double(double[] arg) {}  	public JUnitHelperTestBean getBean(SampleBean arg) {}  	public Map<String,String> getMap() {}  	public void setMap(Map<String,Object> map) {}  	public List<String> getList() {}  	public void setList(List<String> arg) {}  	public void setMapList(Map<String,Object> map,List<String> list) {}  	public Map<String,Object> methodReturnMapGetMap(Map<Object,String> hoge) {}  	public <T> void issue9(Map<String,T> singletonComponents) {}  	public String methodSomeSringArgs(String arg,String arg2,String arg3,			String arg4) {}  	public List<String> getList(String... args) {}  	public List<String> toList(String[] args1,String[] args2) {}  	public static <T> T[] toArray(List<T> arg) {}  	public static <T> List<T> toArrayList(T[] arg) {}  	public static <T> T[] deepCopy(T[] arg) {}  	public static final void info(Logger logger,String msg,boolean logging) {}  	public static final void info(Logger logger,String msg) {}  	public void methodArgFinal(final Logger finalLog) {}  	public static Timestamp issue19(java.sql.Date date) {}  	public static java.sql.Date issue20() {}  	protected static java.sql.Date issue21Static() {}  	protected java.sql.Date issue21Instance() {}  	public static String issue24(Class<?> clazz,String methodName,			Object[] args) {}  	public static class Inner {}  	protected String throwsException() throws Exception {} static String[] staticPackageLocal() {} void packageLocal() {} } ";
		List<String> actual = SourceCodeParseUtil.getTargetMethods(arg, true,
				true, true);
		List<String> exList = new ArrayList<String>();
		exList.add("static void methodStaticReturnVoid() {");
		exList.add("static void methodStaticFinalReturnVoid() {");
		exList.add("boolean returnPrimitive_boolean(boolean arg) {");
		exList.add("int returnPrimitive_int(int arg) {");
		exList.add("byte returnPrimitive_byte(byte arg) {");
		exList.add("short returnPrimitive_short(short arg) {");
		exList.add("long returnPrimitive_long(long arg) {");
		exList.add("char returnPrimitive_char(char arg) {");
		exList.add("float returnPrimitive_float(float arg) {");
		exList.add("double returnPrimitive_double(double arg) {");
		exList.add("double[] returnPrimitiveArray_double(double[] arg) {");
		exList.add("JUnitHelperTestBean getBean(SampleBean arg) {");
		exList.add("Map<String,String> getMap() {");
		exList.add("void setMap(Map<String,Object> map) {");
		exList.add("List<String> getList() {");
		exList.add("void setList(List<String> arg) {");
		exList
				.add("void setMapList(Map<String,Object> map,List<String> list) {");
		exList
				.add("Map<String,Object> methodReturnMapGetMap(Map<Object,String> hoge) {");
		exList.add("<T> void issue9(Map<String,T> singletonComponents) {");
		exList
				.add("String methodSomeSringArgs(String arg,String arg2,String arg3,   String arg4) {");
		exList.add("List<String> getList(String... args) {");
		exList.add("List<String> toList(String[] args1,String[] args2) {");
		exList.add("static <T> T[] toArray(List<T> arg) {");
		exList.add("static <T> List<T> toArrayList(T[] arg) {");
		exList.add("static <T> T[] deepCopy(T[] arg) {");
		exList
				.add("static void info(Logger logger,String msg,boolean logging) {");
		exList.add("static void info(Logger logger,String msg) {");
		exList.add("void methodArgFinal(final Logger finalLog) {");
		exList.add("static Timestamp issue19(java.sql.Date date) {");
		exList.add("static java.sql.Date issue20() {");
		exList.add("static java.sql.Date issue21Static() {");
		exList.add("java.sql.Date issue21Instance() {");
		exList
				.add("static String issue24(Class<?> clazz,String methodName,   Object[] args) {");
		exList.add("String throwsException() throws Exception {");
		exList.add("static String[] staticPackageLocal() {");
		exList.add("void packageLocal() {");
		for (int i = 0; i < exList.size(); i++) {
			assertEquals(exList.get(i) + "," + actual, exList.get(i), actual
					.get(i));
		}
	}
}
