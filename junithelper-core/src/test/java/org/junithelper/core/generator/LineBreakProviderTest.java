package org.junithelper.core.generator;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junithelper.core.config.Configuration;
import org.junithelper.core.config.LineBreakPolicy;
import org.junithelper.core.meta.CurrentLineBreak;

public class LineBreakProviderTest {

    @Test
    public void type() throws Exception {
        assertThat(LineBreakProvider.class, notNullValue());
    }

    @Test
    public void instantiation() throws Exception {
        Configuration config = new Configuration();
        CurrentLineBreak currentLineBreak = CurrentLineBreak.CRLF;
        LineBreakProvider target = new LineBreakProvider(config, currentLineBreak);
        assertThat(target, notNullValue());
    }

    @Test
    public void getLineBreak_A$_FORCE_CRLF() throws Exception {
        Configuration config = new Configuration();
        config.lineBreakPolicy = LineBreakPolicy.forceCRLF;
        CurrentLineBreak currentLineBreak = CurrentLineBreak.LF;
        LineBreakProvider target = new LineBreakProvider(config, currentLineBreak);
        String actual = target.getLineBreak();
        String expected = "\r\n";
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void getLineBreak_A$_FORCE_LF() throws Exception {
        Configuration config = new Configuration();
        config.lineBreakPolicy = LineBreakPolicy.forceLF;
        CurrentLineBreak currentLineBreak = CurrentLineBreak.CRLF;
        LineBreakProvider target = new LineBreakProvider(config, currentLineBreak);
        String actual = target.getLineBreak();
        String expected = "\n";
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void getLineBreak_A$_CRLF_NEW_FILE_ONLY_1() throws Exception {
        Configuration config = new Configuration();
        config.lineBreakPolicy = LineBreakPolicy.forceNewFileCRLF;
        CurrentLineBreak currentLineBreak = CurrentLineBreak.LF;
        LineBreakProvider target = new LineBreakProvider(config, currentLineBreak);
        String actual = target.getLineBreak();
        String expected = "\n";
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void getLineBreak_A$_CRLF_NEW_FILE_ONLY_2() throws Exception {
        Configuration config = new Configuration();
        config.lineBreakPolicy = LineBreakPolicy.forceNewFileCRLF;
        CurrentLineBreak currentLineBreak = null;
        LineBreakProvider target = new LineBreakProvider(config, currentLineBreak);
        String actual = target.getLineBreak();
        String expected = "\r\n";
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void getLineBreak_A$_LF_NEW_FILE_ONLY_1() throws Exception {
        Configuration config = new Configuration();
        config.lineBreakPolicy = LineBreakPolicy.forceNewFileLF;
        CurrentLineBreak currentLineBreak = CurrentLineBreak.CRLF;
        LineBreakProvider target = new LineBreakProvider(config, currentLineBreak);
        String actual = target.getLineBreak();
        String expected = "\r\n";
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void getLineBreak_A$_LF_NEW_FILE_ONLY_2() throws Exception {
        Configuration config = new Configuration();
        config.lineBreakPolicy = LineBreakPolicy.forceNewFileLF;
        CurrentLineBreak currentLineBreak = null;
        LineBreakProvider target = new LineBreakProvider(config, currentLineBreak);
        String actual = target.getLineBreak();
        String expected = "\n";
        assertThat(actual, is(equalTo(expected)));
    }

}
