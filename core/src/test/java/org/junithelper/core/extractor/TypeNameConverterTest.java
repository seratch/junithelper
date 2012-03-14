package org.junithelper.core.extractor;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junithelper.core.config.Configuration;
import org.junithelper.core.exception.JUnitHelperCoreException;
import org.junithelper.core.extractor.TypeNameConverter;

public class TypeNameConverterTest {

    @Test
    public void type() throws Exception {
        assertNotNull(TypeNameConverter.class);
    }

    @Test
    public void instantiation() throws Exception {
        Configuration config = null;
        TypeNameConverter target = new TypeNameConverter(config);
        assertNotNull(target);
    }

    Configuration config = new Configuration();
    TypeNameConverter target = new TypeNameConverter(config);

    @Test
    public void toAvailableInMethodName_A$String() throws Exception {
        String[] argList = new String[] { "String", "hoge.foo.Var", "List<String>", "Map<String,Object>", "List<?>",
                "Map<?,?>" };
        String[] expectedList = new String[] { "String", "hogefooVar", "List", "Map", "List", "Map" };
        for (int i = 0; i < argList.length; i++) {
            String arg = argList[i];
            String actual = target.toAvailableInMethodName(arg);
            String expected = expectedList[i];
            assertEquals(expected + "-" + actual, expected, actual);
        }
    }

    @Test
    public void toCompilableType_A$List$List$String() throws Exception {
        String[] argList = new String[] { "String", "hoge.foo.Var", "List<String>", "Map<String,Object>", "List<?>",
                "Map<?,?>" };
        String[] expectedList = new String[] { "String", "hoge.foo.Var", "List", "Map", "List", "Map" };
        List<String> importedList = new ArrayList<String>();
        importedList.add("java.util.List");
        importedList.add("java.util.Map");
        for (int i = 0; i < argList.length; i++) {
            String className = argList[i];
            String actual = target.toCompilableType(className, importedList, null);
            String expected = expectedList[i];
            assertEquals(i + "," + expected + "-" + actual, expected, actual);
        }
    }

    @Test
    public void toCompilableType_A$String$List$String() throws Exception {
        Configuration config = new Configuration();
        TypeNameConverter target = new TypeNameConverter(config);
        // given
        String packageName = null;
        String className = "InputStream";
        List<String> importedList = new ArrayList<String>();
        importedList.add("java.io.InputStream");
        // when
        String actual = target.toCompilableType(className, importedList, packageName);
        // then
        String expected = "InputStream";
        assertEquals(expected, actual);
    }

    @Test
    public void toCompilableType_A$String$List$String_WildcardImport() throws Exception {
        Configuration config = new Configuration();
        TypeNameConverter target = new TypeNameConverter(config);
        // given
        String packageName = null;
        String className = "InputStream";
        List<String> importedList = new ArrayList<String>();
        importedList.add("java.io.*");
        // when
        String actual = target.toCompilableType(className, importedList, packageName);
        // then
        String expected = "InputStream";
        assertEquals(expected, actual);
    }

    @Test
    public void toCompilableType_A$String$List$List$String() throws Exception {
        Configuration config = null;
        TypeNameConverter target = new TypeNameConverter(config);
        // given
        String packageName = null;
        String className = "List";
        List<String> generics = new ArrayList<String>();
        generics.add("String");
        List<String> importedList = new ArrayList<String>();
        importedList.add("java.util.List");
        // when
        String actual = target.toCompilableType(className, generics, importedList, packageName);
        // then
        String expected = "List<String>";
        assertEquals(expected, actual);
    }

    @Test
    public void toAvailableInMethodName_A$String_StringIsNull() throws Exception {
        TypeNameConverter target = new TypeNameConverter(config);
        String typeName = null;
        try {
            target.toAvailableInMethodName(typeName);
            fail();
        } catch (JUnitHelperCoreException e) {
        }
    }

    @Test
    public void toAvailableInMethodName_A$String_StringIsEmpty() throws Exception {
        TypeNameConverter target = new TypeNameConverter(config);
        String typeName = "";
        try {
            target.toAvailableInMethodName(typeName);
            fail();
        } catch (JUnitHelperCoreException e) {
        }
    }

    @Test
    public void toCompilableType_A$String$List$String_StringIsNull() throws Exception {
        TypeNameConverter target = new TypeNameConverter(config);
        String typeName = null;
        List<String> importedList = new ArrayList<String>();
        String callerClassPackageName = null;
        String actual = target.toCompilableType(typeName, importedList, callerClassPackageName);
        String expected = null;
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void toCompilableType_A$String$List$String_StringIsEmpty() throws Exception {
        TypeNameConverter target = new TypeNameConverter(config);
        String typeName = "";
        List<String> importedList = new ArrayList<String>();
        String callerClassPackageName = "";
        String actual = target.toCompilableType(typeName, importedList, callerClassPackageName);
        String expected = "Object";
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void toCompilableType_A$String$List$List$String_StringIsNull() throws Exception {
        TypeNameConverter target = new TypeNameConverter(config);
        String typeName = null;
        List<String> generics = new ArrayList<String>();
        List<String> importedList = new ArrayList<String>();
        String callerClassPackageName = null;
        String actual = target.toCompilableType(typeName, generics, importedList, callerClassPackageName);
        String expected = null;
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void toCompilableType_A$String$List$List$String_StringIsEmpty() throws Exception {
        TypeNameConverter target = new TypeNameConverter(config);
        String typeName = "";
        List<String> generics = new ArrayList<String>();
        List<String> importedList = new ArrayList<String>();
        String callerClassPackageName = "";
        String actual = target.toCompilableType(typeName, generics, importedList, callerClassPackageName);
        String expected = "Object";
        assertThat(actual, is(equalTo(expected)));
    }

}
