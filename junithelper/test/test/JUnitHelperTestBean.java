package test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import test.bean.SampleBean;

@SuppressWarnings(value = { "issue 28" })
public class JUnitHelperTestBean {

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

	public Map<String, String> getMap() {
		return null;
	}

	public void setMap(Map<String, Object> map) {
	}

	public List<String> getList() {
		return null;
	}

	public void setList(List<String> arg) {
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

}
