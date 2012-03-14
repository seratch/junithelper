package org.junithelper.core.extractor;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junithelper.core.exception.JUnitHelperCoreException;
import org.junithelper.core.extractor.ArgExtractorHelper;

public class ArgExtractorHelperTest {

    @Test
    public void type() throws Exception {
        assertNotNull(ArgExtractorHelper.class);
    }

    @Test
    public void getArgListFromArgsDefAreaString_A$String() throws Exception {
        // given
        String argsDefAreaString = "String str, List<String> list, Map<Object, Object> map, Object obj, Map<String, List<String>> listMap, List<Map<String,String>> mapList, List<Map<Map<String,Object>,List<String>>> deepNest) {";
        // when
        List<String> actual = ArgExtractorHelper.getArgListFromArgsDefAreaString(argsDefAreaString);
        // then
        assertEquals("String str", actual.get(0));
        assertEquals("List<String> list", actual.get(1));
        assertEquals("Map<Object,Object> map", actual.get(2));
        assertEquals("Object obj", actual.get(3));
        assertEquals("Map listMap", actual.get(4));
        assertEquals("List mapList", actual.get(5));
        assertEquals("List deepNest", actual.get(6));
    }

    @Test
    public void trimGenericsAreaIfNestedGenericsExists_A$String() throws Exception {
        // given
        String target = "List<Map" + ArgExtractorHelper.NESTED_GENERICS_MARK + "> aaa";
        // e.g. : given(mocked.called()).willReturn(1);
        // when
        String actual = ArgExtractorHelper.trimGenericsAreaIfNestedGenericsExists(target);
        // then
        String expected = "List aaa";
        assertEquals(expected, actual);
    }

    @Test
    public void getArgListFromArgsDefAreaString_A$String_StringIsNull() throws Exception {
        String argsDefAreaString = null;
        try {
            ArgExtractorHelper.getArgListFromArgsDefAreaString(argsDefAreaString);
            fail();
        } catch (JUnitHelperCoreException e) {
        }
    }

    @Test
    public void getArgListFromArgsDefAreaString_A$String_StringIsEmpty() throws Exception {
        String argsDefAreaString = "";
        List<String> actual = ArgExtractorHelper.getArgListFromArgsDefAreaString(argsDefAreaString);
        assertThat(actual.size(), is(equalTo(1)));
    }

    @Test
    public void trimGenericsAreaIfNestedGenericsExists_A$String_StringIsNull() throws Exception {
        String target_ = null;
        try {
            ArgExtractorHelper.trimGenericsAreaIfNestedGenericsExists(target_);
            fail();
        } catch (JUnitHelperCoreException e) {
        }
    }

    @Test
    public void trimGenericsAreaIfNestedGenericsExists_A$String_StringIsEmpty() throws Exception {
        String target_ = "";
        String actual = ArgExtractorHelper.trimGenericsAreaIfNestedGenericsExists(target_);
        String expected = "";
        assertThat(actual, is(equalTo(expected)));
    }

}
