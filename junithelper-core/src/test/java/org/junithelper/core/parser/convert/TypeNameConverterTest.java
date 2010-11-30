package org.junithelper.core.parser.convert;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junithelper.core.config.Configulation;

public class TypeNameConverterTest {

	@Test
	public void type() throws Exception {
		assertNotNull(TypeNameConverter.class);
	}

	@Test
	public void instantiation() throws Exception {
		Configulation config = null;
		TypeNameConverter target = new TypeNameConverter(config);
		assertNotNull(target);
	}

	Configulation config = new Configulation();
	TypeNameConverter target = new TypeNameConverter(config);

	@Test
	public void toAvailableInMethodName_A$String() throws Exception {
		String[] argList = new String[] { "String", "hoge.foo.Var",
				"List<String>", "Map<String,Object>", "List<?>", "Map<?,?>" };
		String[] expectedList = new String[] { "String", "hogefooVar", "List",
				"Map", "List", "Map" };
		for (int i = 0; i < argList.length; i++) {
			String arg = argList[i];
			String actual = target.toAvailableInMethodName(arg);
			String expected = expectedList[i];
			assertEquals(expected + "-" + actual, expected, actual);
		}
	}

	@Test
	public void toCompilableType_A$String$List$List() throws Exception {
		String[] argList = new String[] { "String", "hoge.foo.Var",
				"List<String>", "Map<String,Object>", "List<?>", "Map<?,?>" };
		String[] expectedList = new String[] { "String", "hoge.foo.Var",
				"List", "Map", "List", "Map" };
		List<String> importedList = new ArrayList<String>();
		importedList.add("java.util.List");
		importedList.add("java.util.Map");
		for (int i = 0; i < argList.length; i++) {
			String className = argList[i];
			String actual = target.toCompilableType(null, className,
					importedList);
			String expected = expectedList[i];
			assertEquals(i + "," + expected + "-" + actual, expected, actual);
		}
	}

	@Test
	public void toCompilableType_A$String$String$List() throws Exception {
		Configulation config = new Configulation();
		TypeNameConverter target = new TypeNameConverter(config);
		// given
		String packageName = null;
		String className = "InputStream";
		List<String> importedList = new ArrayList<String>();
		importedList.add("java.io.InputStream");
		// when
		String actual = target.toCompilableType(packageName, className,
				importedList);
		// then
		String expected = "InputStream";
		assertEquals(expected, actual);
	}

	@Test
	public void toCompilableType_A$String$String$List$List() throws Exception {
		Configulation config = null;
		TypeNameConverter target = new TypeNameConverter(config);
		// given
		String packageName = null;
		String className = "List";
		List<String> generics = new ArrayList<String>();
		generics.add("String");
		List<String> importedList = new ArrayList<String>();
		importedList.add("java.util.List");
		// when
		String actual = target.toCompilableType(packageName, className,
				generics, importedList);
		// then
		String expected = "List<String>";
		assertEquals(expected, actual);
	}

}
