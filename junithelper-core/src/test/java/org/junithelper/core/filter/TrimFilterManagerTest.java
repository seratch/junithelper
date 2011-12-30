package org.junithelper.core.filter;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junithelper.core.filter.impl.TrimAnnotationFilter;
import org.junithelper.core.filter.impl.TrimCommentFilter;
import org.junithelper.core.filter.impl.TrimInsideOfBraceFilter;
import org.junithelper.core.filter.impl.TrimQuotationFilter;

public class TrimFilterManagerTest {

    @Test
    public void type() throws Exception {
        assertNotNull(TrimFilterManager.class);
    }

    @Test
    public void instantiation() throws Exception {
        TrimFilterManager target = new TrimFilterManager();
        assertNotNull(target);
    }

    @Test
    public void addFilter_A$TrimFilterArray_0() throws Exception {
        TrimFilterManager target = new TrimFilterManager();
        // given
        TrimFilter[] filters = new TrimFilter[] {};
        // when
        target.addFilter(filters);
        // then
        assertEquals(0, target.getFilters().size());
    }

    @Test
    public void addFilter_A$TrimFilterArray_1() throws Exception {
        TrimFilterManager target = new TrimFilterManager();
        // given
        TrimFilter[] filters = new TrimFilter[] { new TrimAnnotationFilter() };
        // when
        target.addFilter(filters);
        // then
        assertEquals(1, target.getFilters().size());
    }

    @Test
    public void addFilter_A$TrimFilterArray_2() throws Exception {
        TrimFilterManager target = new TrimFilterManager();
        // given
        TrimFilter[] filters = new TrimFilter[] { new TrimAnnotationFilter(), new TrimCommentFilter() };
        // when
        target.addFilter(filters);
        // then
        assertEquals(2, target.getFilters().size());
    }

    @Test
    public void doTrimAll_A$String_null() throws Exception {
        TrimFilterManager target = new TrimFilterManager();
        target.addFilter(new TrimCommentFilter(), new TrimQuotationFilter());
        String src = null;
        // when
        String actual = target.doTrimAll(src);
        // then
        String expected = null;
        assertEquals(expected, actual);
    }

    @Test
    public void doTrimAll_A$String_notNull() throws Exception {
        TrimFilterManager target = new TrimFilterManager();
        target.addFilter(new TrimCommentFilter(), new TrimInsideOfBraceFilter(), new TrimQuotationFilter());
        String src = "package foo.var; \r\n public class Sample { \r\n// hogehoge \r\n public void hoge() { System.out.println(\"aaa\"); } }";
        // when
        String actual = target.doTrimAll(src);
        // then
        String expected = "package foo.var;   public class Sample {    public void hoge() {} }";
        assertEquals(expected, actual);
    }

    @Test
    public void removeFilter_A$Class() throws Exception {
        TrimFilterManager target = new TrimFilterManager();
        // given
        target.addFilter(new TrimCommentFilter(), new TrimInsideOfBraceFilter(), new TrimQuotationFilter());
        Class<?> filterClass = TrimQuotationFilter.class;
        // when
        target.removeFilter(filterClass);
        // then
        assertEquals(2, target.getFilters().size());
    }

    @Test
    public void doTrimAll_A$String_StringIsNull() throws Exception {
        TrimFilterManager target = new TrimFilterManager();
        String src = null;
        String actual = target.doTrimAll(src);
        String expected = null;
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void doTrimAll_A$String_StringIsEmpty() throws Exception {
        TrimFilterManager target = new TrimFilterManager();
        String src = "";
        String actual = target.doTrimAll(src);
        String expected = "";
        assertThat(actual, is(equalTo(expected)));
    }

}
