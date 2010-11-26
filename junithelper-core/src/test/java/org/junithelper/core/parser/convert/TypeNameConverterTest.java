package org.junithelper.core.parser.convert;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junithelper.core.config.Configulation;

public class TypeNameConverterTest {

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
}
