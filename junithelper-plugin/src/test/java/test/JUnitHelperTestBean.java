/* 
 * Copyright 2009-2010 junithelper.org. 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language 
 * governing permissions and limitations under the License. 
 */
package test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import test.bean.SampleBean;

/**
 * JUnitHelperTestBean
 * 
 * @author Kazuhiro Sera <seratch@gmail.com>
 * @version 1.0
 */
@SuppressWarnings(value = { "issue 28" })
public class JUnitHelperTestBean {

	public JUnitHelperTestBean(String constructorArg) {
	}

	/**
	 * comment1
	 * 
	 */

	/*
	 * comment2
	 */

	// 41 methods and 5 exception patterns
	// static String[] staticPackageLocal() {
	// void packageLocal() {
	// public static void methodStaticReturnVoid() {
	// public static final void methodStaticFinalReturnVoid() {
	// public boolean returnPrimitive_boolean(boolean arg) {
	// public int returnPrimitive_int(int arg) {
	// public byte returnPrimitive_byte(byte arg) {
	// public short returnPrimitive_short(short arg) {
	// public long returnPrimitive_long(long arg) {
	// public char returnPrimitive_char(char arg) {
	// public float returnPrimitive_float(float arg) {
	// public double returnPrimitive_double(double arg) {
	// public double[] returnPrimitiveArray_double(double[] arg) {
	// public JUnitHelperTestBean getBean(SampleBean arg) {
	// public List<SampleBean> getList() {
	// public void setList(List<SampleBean> list) {
	// public void setMapList(Map<String, Object> map, List<String> list) {
	// public Map<String, Object> methodReturnMapGetMap(Map<Object, String>
	// hoge) {
	// public <T> void issue9(Map<String, T> singletonComponents) {
	// public String methodSomeSringArgs(String arg, String arg2, String arg3,
	// public List<String> getList(String... args) {
	// public List<String> toList(String[] args1, String[] args2) {
	// public static <T> T[] toArray(List<T> arg) {
	// public static <T> List<T> toArrayList(T[] arg) {
	// public static <T> T[] deepCopy(T[] arg) {
	// public static final void info(Logger logger, String msg, boolean logging)
	// {
	// public static final void info(Logger logger, String msg) {
	// public void methodArgFinal(final Logger finalLog) {
	// public static Timestamp issue19(java.sql.Date date) {
	// public static java.sql.Date issue20() {
	// protected static java.sql.Date issue21Static() {
	// protected java.sql.Date issue21Instance() {
	// public static String issue24(Class<?> clazz, String methodName,
	// protected String throwsException() throws Exception {
	// protected void throwsException2() throws IOException,
	// protected void throwsException3() throws NullPointerException,
	// static public void staticPublicMethod() {
	// static protected void staticProtectedMethod() {
	// public List<SampleBean> issue32_list(List<SampleBean> list) {
	// public Map<String, SampleBean> issue32_map(Map<String, SampleBean> map) {
	// public static Calendar getCalendar(long timeInMillis) {

	// excluded patterns
	// private Map<String, String> map;
	// private static void privateStaticMethod() {
	// private void privateInstanceMethod() {
	// static private void staticPrivateMethod() {
	// public Map<String, String> getMap() {
	// public void setMap(Map<String, String> map) {

	public String publicField;
	protected String protectedField = new String();
	String packageLocalField;

	static {
		System.out.println("hoge");
	}

	static class Hoge {
		public void doSomething() {
		}
	}

	static String[] staticPackageLocal() {
		return null;
	}

	void packageLocal() {
	}

	public static void methodStaticReturnVoid() {
		new InputStream() {
			@Override
			public int read() throws IOException {
				// TODO Auto-generated method stub
				return 0;
			}
		};
	}

	public static final void methodStaticFinalReturnVoid() {
	}

	public boolean returnPrimitive_boolean(boolean arg) {
		return arg;
	}

	public int returnPrimitive_int(int arg) {
		return arg;
	}

	public byte returnPrimitive_byte(byte arg) {
		return arg;
	}

	public short returnPrimitive_short(short arg) {
		return arg;
	}

	public long returnPrimitive_long(long arg) {
		return arg;
	}

	public char returnPrimitive_char(char arg) {
		return arg;
	}

	public float returnPrimitive_float(float arg) {
		return arg;
	}

	public double returnPrimitive_double(double arg) {
		return arg;
	}

	public double[] returnPrimitiveArray_double(double[] arg) {
		return arg;
	}

	public JUnitHelperTestBean getBean(SampleBean arg) {
		return null;
	}

	private Map<String, String> map;

	// will be excluded as an accessor
	public Map<String, String> getMap() {
		return map;
	}

	// will be excluded as an accessor
	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public List<SampleBean> list;

	public List<SampleBean> getList() {
		return this.list;
	}

	public void setList(List<SampleBean> list) {
		this.list = list;
	}

	public void setMapList(Map<String, Object> map, List<String> list) {
	}

	public Map<String, Object> methodReturnMapGetMap(Map<Object, String> hoge) {
		return null;
	}

	public <T> void issue9(Map<String, T> singletonComponents) {
	}

	public String methodSomeSringArgs(String arg, String arg2, String arg3,
			String arg4) {
		return arg;
	}

	public List<String> getList(String... args) {
		return null;
	}

	public List<String> toList(String[] args1, String[] args2) {
		return null;
	}

	public static <T> T[] toArray(List<T> arg) {
		return null;
	}

	public static <T> List<T> toArrayList(T[] arg) {
		return null;
	}

	public static <T> T[] deepCopy(T[] arg) {
		return null;
	}

	public static final void info(Logger logger, String msg, boolean logging) {
	}

	public static final void info(Logger logger, String msg) {
	}

	public void methodArgFinal(final Logger finalLog) {
	}

	public static Timestamp issue19(java.sql.Date date) {
		Timestamp timestamp = new Timestamp(date.getTime());
		return timestamp;
	}

	public static java.sql.Date issue20() {
		return null;
	}

	protected static java.sql.Date issue21Static() {
		return null;
	}

	protected java.sql.Date issue21Instance() {
		return null;
	}

	public static String issue24(Class<?> clazz, String methodName,
			Object[] args) {
		return null;
	}

	public static class Inner {

		public static class InnerInner {
		}

		String innerMethod() {
			return null;
		}
	}

	protected String throwsException() throws Exception {
		return null;
	}

	protected void throwsException2() throws IOException,
			UnsupportedOperationException {
	}

	protected void throwsException3() throws NullPointerException,
			java.lang.IllegalArgumentException {
		staticPrivateMethod();
		privateStaticMethod();
		privateInstanceMethod();
	}

	static public void staticPublicMethod() {
	}

	static protected void staticProtectedMethod() {
	}

	static private void staticPrivateMethod() {
	}

	private static void privateStaticMethod() {
	}

	private void privateInstanceMethod() {
	}

	public List<SampleBean> issue32_list(List<SampleBean> list) {
		return list;
	}

	public Map<String, SampleBean> issue32_map(Map<String, SampleBean> map) {
		return map;
	}

	/**
	 * UNIX TIME(timeInMillis) to {@link Calendar} object
	 * 
	 * @param timeInMillis
	 *            UNIX TIME(timeInMillis)
	 * @return　{@link Calendar} object
	 */
	public static Calendar getCalendar(long timeInMillis) {
		Calendar dest = Calendar.getInstance();
		dest.setTimeInMillis(timeInMillis);
		return dest;
	}

	@Deprecated()
	public String testUser() {
		return "result";
	}

	@Deprecated
	public String issue39_argAnnotaions(@Deprecated @SuppressWarnings({ "a",
			"b", "c" }) String hoge,
			@SuppressWarnings(value = "abbabb") Object value) {
		return null;
	}

}
