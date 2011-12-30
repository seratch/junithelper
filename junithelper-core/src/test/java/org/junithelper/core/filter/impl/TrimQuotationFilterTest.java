package org.junithelper.core.filter.impl;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class TrimQuotationFilterTest {

    @Test
    public void type() throws Exception {
        assertNotNull(TrimQuotationFilter.class);
    }

    @Test
    public void instantiation() throws Exception {
        TrimQuotationFilter target = new TrimQuotationFilter();
        assertNotNull(target);
    }

    @Test
    public void trimAll_A$String_null() throws Exception {
        TrimQuotationFilter target = new TrimQuotationFilter();
        // given
        String src = null;
        // when
        String actual = target.trimAll(src);
        // then
        String expected = null;
        assertEquals(expected, actual);
    }

    @Test
    public void trimAll_A$String_notNull() throws Exception {
        TrimQuotationFilter target = new TrimQuotationFilter();
        // given
        String src = "sdfa\"sdfsf\'a\r\naaa\'asdfsfa\"aaa";
        // when
        String actual = target.trimAll(src);
        // then
        String expected = "sdfasdfsfa\r\naaaasdfsfaaaa";
        assertEquals(expected, actual);
    }

    @Test
    public void trimAll_A$String_StringIsNull() throws Exception {
        TrimQuotationFilter target = new TrimQuotationFilter();
        String src = null;
        String actual = target.trimAll(src);
        String expected = null;
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void trimAll_A$String_StringIsEmpty() throws Exception {
        TrimQuotationFilter target = new TrimQuotationFilter();
        String src = "";
        String actual = target.trimAll(src);
        String expected = "";
        assertThat(actual, is(equalTo(expected)));
    }

}
