package org.junithelper.core.filter.impl;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junithelper.core.util.IOUtil;

public class TrimCommentFilterTest {

    @Test
    public void type() throws Exception {
        assertNotNull(TrimCommentFilter.class);
    }

    @Test
    public void instantiation() throws Exception {
        TrimCommentFilter target = new TrimCommentFilter();
        assertNotNull(target);
    }

    @Test
    public void trimAll_A$String() throws Exception {
        TrimCommentFilter target = new TrimCommentFilter();
        String src = "// hogehoge \r\npublic void do(String aaa) {\r\n /** aaa\r\n */ /* \r\nbbb */";
        String actual = target.trimAll(src);
        String expected = " public void do(String aaa) {   ";
        assertEquals(expected, actual);
    }

    @Test
    public void trimAll_A$String_TrimFilterManagerTest() throws Exception {
        TrimCommentFilter target = new TrimCommentFilter();
        String src = IOUtil.readAsString(IOUtil.getResourceAsStream("inputs/TrimFilterManagerTest.txt"), "UTF-8");
        String actual = target.trimAll(src);
        String expected = "package org.junithelper.core.filter;  import static org.junit.Assert.assertEquals; import static org.junit.Assert.assertNotNull;  import org.junit.Test; import org.junithelper.core.filter.impl.TrimAnnotationFilter; import org.junithelper.core.filter.impl.TrimCommentFilter; import org.junithelper.core.filter.impl.TrimInsideOfBraceFilter; import org.junithelper.core.filter.impl.TrimQuotationFilter;  public class TrimFilterManagerTest {  	@Test 	public void type() throws Exception { 		assertNotNull(TrimFilterManager.class); 	}  	@Test 	public void instantiation() throws Exception { 		TrimFilterManager target = new TrimFilterManager(); 		assertNotNull(target); 	}  	@Test 	public void addFilter_A$TrimFilterArray_0() throws Exception { 		TrimFilterManager target = new TrimFilterManager(); 		 		TrimFilter[] filters = new TrimFilter[] {}; 		 		target.addFilter(filters); 		 		assertEquals(0, target.getFilters().size()); 	}  	@Test 	public void addFilter_A$TrimFilterArray_1() throws Exception { 		TrimFilterManager target = new TrimFilterManager(); 		 		TrimFilter[] filters = new TrimFilter[] { new TrimAnnotationFilter() }; 		 		target.addFilter(filters); 		 		assertEquals(1, target.getFilters().size()); 	}  	@Test 	public void addFilter_A$TrimFilterArray_2() throws Exception { 		TrimFilterManager target = new TrimFilterManager(); 		 		TrimFilter[] filters = new TrimFilter[] { new TrimAnnotationFilter(), 				new TrimCommentFilter() }; 		 		target.addFilter(filters); 		 		assertEquals(2, target.getFilters().size()); 	}  	@Test 	public void doTrimAll_A$String_null() throws Exception { 		TrimFilterManager target = new TrimFilterManager(); 		target.addFilter(new TrimCommentFilter(), new TrimQuotationFilter()); 		String src = null; 		 		String actual = target.doTrimAll(src); 		 		String expected = null; 		assertEquals(expected, actual); 	}  	@Test 	public void doTrimAll_A$String_notNull() throws Exception { 		TrimFilterManager target = new TrimFilterManager(); 		target.addFilter(new TrimCommentFilter(), 				new TrimInsideOfBraceFilter(), new TrimQuotationFilter()); 		String src = \"\"; 		 		String actual = target.doTrimAll(src); 		 		String expected = \"\"; 		assertEquals(expected, actual); 	}  	@Test 	public void removeFilter_A$Class() throws Exception { 		TrimFilterManager target = new TrimFilterManager(); 		 		target.addFilter(new TrimCommentFilter(), 				new TrimInsideOfBraceFilter(), new TrimQuotationFilter()); 		Class<?> filterClass = TrimQuotationFilter.class; 		 		target.removeFilter(filterClass); 		 		assertEquals(2, target.getFilters().size()); 	}  } ";
        assertEquals(expected, actual);
    }

    @Test
    public void trimAll_A$String_Slim3_HtmlUtil() throws Exception {
        TrimCommentFilter target = new TrimCommentFilter();
        String src = IOUtil.readAsString(IOUtil.getResourceAsStream("inputs/Slim3_HtmlUtil.txt"), "UTF-8");
        String actual = target.trimAll(src);
        String expected = " package org.slim3.util;   public final class HtmlUtil {      private static final int HIGHEST_SPECIAL = '';      private static char[][] specialCharactersRepresentation =         new char[HIGHEST_SPECIAL + 1][];      static {         specialCharactersRepresentation[''] = \"\".toCharArray();         specialCharactersRepresentation[''] = \"\".toCharArray();         specialCharactersRepresentation[''] = \"\".toCharArray();         specialCharactersRepresentation[''] = \"\".toCharArray();         specialCharactersRepresentation[''] = \"\".toCharArray();     }           public static String escape(String input) {         int start = 0;         char[] arrayBuffer = input.toCharArray();         int length = arrayBuffer.length;         StringBuilder escapedBuffer = null;         for (int i = 0; i < length; i++) {             char c = arrayBuffer[i];             if (c <= HIGHEST_SPECIAL) {                 char[] escaped = specialCharactersRepresentation[c];                 if (escaped != null) {                     if (start == 0) {                         escapedBuffer = new StringBuilder(length + 5);                     }                     if (start < i) {                         escapedBuffer.append(arrayBuffer, start, i - start);                     }                     start = i + 1;                     escapedBuffer.append(escaped);                 }             }         }         if (start == 0) {             return input;         }         if (start < length) {             escapedBuffer.append(arrayBuffer, start, length - start);         }         return escapedBuffer.toString();     }      private HtmlUtil() {     } } ";
        assertEquals(expected, actual);
    }

    @Test
    public void trimAll_A$String_StringIsNull() throws Exception {
        TrimCommentFilter target = new TrimCommentFilter();
        String src = null;
        String actual = target.trimAll(src);
        String expected = null;
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void trimAll_A$String_StringIsEmpty() throws Exception {
        TrimCommentFilter target = new TrimCommentFilter();
        String src = "";
        String actual = target.trimAll(src);
        String expected = "";
        assertThat(actual, is(equalTo(expected)));
    }

}
