package org.junithelper.core.parser.detect;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junithelper.core.config.Configuration;
import org.junithelper.core.meta.ClassMeta;

public class AvailableTypeDetectorTest {

	@Test
	public void type() throws Exception {
		assertNotNull(AvailableTypeDetector.class);
	}

	@Test
	public void instantiation() throws Exception {
		ClassMeta classMeta = null;
		AvailableTypeDetector target = new AvailableTypeDetector(classMeta);
		assertNotNull(target);
	}

	@Test
	public void isPrimitive_A$String_argNull() throws Exception {
		ClassMeta classMeta = null;
		AvailableTypeDetector target = new AvailableTypeDetector(classMeta);
		// given
		String typeName = null;
		// when
		boolean actual = target.isPrimitive(typeName);
		// then
		boolean expected = false;
		assertEquals(expected, actual);
	}

	@Test
	public void isPrimitive_A$String_true() throws Exception {
		ClassMeta classMeta = null;
		AvailableTypeDetector target = new AvailableTypeDetector(classMeta);
		// given
		String typeName = "long";
		// when
		boolean actual = target.isPrimitive(typeName);
		// then
		boolean expected = true;
		assertEquals(expected, actual);
	}

	@Test
	public void isPrimitive_A$String_false() throws Exception {
		ClassMeta classMeta = null;
		AvailableTypeDetector target = new AvailableTypeDetector(classMeta);
		// given
		String typeName = "String";
		// when
		boolean actual = target.isPrimitive(typeName);
		// then
		boolean expected = false;
		assertEquals(expected, actual);
	}

	@Test
	public void isArray_A$String_argNull() throws Exception {
		ClassMeta classMeta = null;
		AvailableTypeDetector target = new AvailableTypeDetector(classMeta);
		// given
		String typeName = null;
		// when
		boolean actual = target.isArray(typeName);
		// then
		boolean expected = false;
		assertEquals(expected, actual);
	}

	@Test
	public void isArray_A$String_true() throws Exception {
		ClassMeta classMeta = null;
		AvailableTypeDetector target = new AvailableTypeDetector(classMeta);
		// given
		String typeName = "String[]";
		// when
		boolean actual = target.isArray(typeName);
		// then
		boolean expected = true;
		assertEquals(expected, actual);
	}

	@Test
	public void isArray_A$String_false() throws Exception {
		ClassMeta classMeta = null;
		AvailableTypeDetector target = new AvailableTypeDetector(classMeta);
		// given
		String typeName = "String";
		// when
		boolean actual = target.isArray(typeName);
		// then
		boolean expected = false;
		assertEquals(expected, actual);
	}

	@Test
	public void isJavaLangPackageType_A$String_argNull() throws Exception {
		ClassMeta classMeta = null;
		AvailableTypeDetector target = new AvailableTypeDetector(classMeta);
		// given
		String typeName = null;
		// when
		boolean actual = target.isJavaLangPackageType(typeName);
		// then
		boolean expected = false;
		assertEquals(expected, actual);
	}

	@Test
	public void isJavaLangPackageType_A$String_true() throws Exception {
		ClassMeta classMeta = null;
		AvailableTypeDetector target = new AvailableTypeDetector(classMeta);
		// given
		String typeName = "String";
		// when
		boolean actual = target.isJavaLangPackageType(typeName);
		// then
		boolean expected = true;
		assertEquals(expected, actual);
	}

	@Test
	public void isJavaLangPackageType_A$String_false() throws Exception {
		ClassMeta classMeta = null;
		AvailableTypeDetector target = new AvailableTypeDetector(classMeta);
		// given
		String typeName = "List";
		// when
		boolean actual = target.isJavaLangPackageType(typeName);
		// then
		boolean expected = false;
		assertEquals(expected, actual);
	}

	@Test
	public void isAvailableType_A$String$Configuration_true() throws Exception {
		ClassMeta classMeta = new ClassMeta();
		AvailableTypeDetector target = new AvailableTypeDetector(classMeta);
		// given
		String typeName = "String";
		Configuration config = new Configuration();
		// when
		boolean actual = target.isAvailableType(typeName, config);
		// then
		boolean expected = true;
		assertEquals(expected, actual);
	}

	@Test
	public void isAvailableType_A$String$Configuration_false() throws Exception {
		ClassMeta classMeta = new ClassMeta();
		AvailableTypeDetector target = new AvailableTypeDetector(classMeta);
		// given
		String typeName = "org.juithelper.core.Version";
		Configuration config = new Configuration();
		// when
		boolean actual = target.isAvailableType(typeName, config);
		// then
		boolean expected = false;
		assertEquals(expected, actual);
	}

	@Test
	public void isJMockitMockableType_A$String_true() throws Exception {
		ClassMeta classMeta = new ClassMeta();
		classMeta.importedList.add("java.io.InputStream");
		AvailableTypeDetector target = new AvailableTypeDetector(classMeta);
		// given
		String typeName = "InputStream";
		// when
		boolean actual = target.isJMockitMockableType(typeName);
		// then
		boolean expected = true;
		assertEquals(expected, actual);
	}

	@Test
	public void isJMockitMockableType_A$String_false() throws Exception {
		ClassMeta classMeta = new ClassMeta();
		AvailableTypeDetector target = new AvailableTypeDetector(classMeta);
		// given
		String typeName = "foo.var.Bean";
		// when
		boolean actual = target.isJMockitMockableType(typeName);
		// then
		boolean expected = false;
		assertEquals(expected, actual);
	}

}
