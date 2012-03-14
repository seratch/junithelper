package org.junithelper.core.generator;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junithelper.core.config.Configuration;

public class IndentationProviderTest {

    @Test
    public void type() throws Exception {
        assertThat(IndentationProvider.class, notNullValue());
    }

    @Test
    public void instantiation() throws Exception {
        Configuration config = null;
        IndentationProvider target = new IndentationProvider(config);
        assertThat(target, notNullValue());
    }

    @Test
    public void getIndentation_A$_hardTabs() throws Exception {
        Configuration config = new Configuration();
        IndentationProvider target = new IndentationProvider(config);
        String actual = target.getIndentation();
        String expected = "\t";
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void getIndentation_A$_softTabs() throws Exception {
        Configuration config = new Configuration();
        config.useSoftTabs = true;
        IndentationProvider target = new IndentationProvider(config);
        String actual = target.getIndentation();
        String expected = "    ";
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void getIndentation_A$_softTabs2() throws Exception {
        Configuration config = new Configuration();
        config.useSoftTabs = true;
        config.softTabSize = 2;
        IndentationProvider target = new IndentationProvider(config);
        String actual = target.getIndentation();
        String expected = "  ";
        assertThat(actual, is(equalTo(expected)));
    }

}
