package org.junithelper.core.extractor;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junithelper.core.meta.CurrentLineBreak;

public class CurrentLineBreakDetectorTest {

    @Test
    public void type() throws Exception {
        assertThat(CurrentLineBreakDetector.class, notNullValue());
    }

    @Test
    public void detect_A$String_CRLF() throws Exception {
        String sourceCodeString = "foo\r\nbar\r\n";
        CurrentLineBreak actual = CurrentLineBreakDetector.detect(sourceCodeString);
        CurrentLineBreak expected = CurrentLineBreak.CRLF;
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void detect_A$String_LF() throws Exception {
        String sourceCodeString = "foo\nbar\n";
        CurrentLineBreak actual = CurrentLineBreakDetector.detect(sourceCodeString);
        CurrentLineBreak expected = CurrentLineBreak.LF;
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void detect_A$String_null() throws Exception {
        String sourceCodeString = "foobar";
        CurrentLineBreak actual = CurrentLineBreakDetector.detect(sourceCodeString);
        CurrentLineBreak expected = null;
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void detect_A$String_StringIsNull() throws Exception {
        String sourceCodeString = null;
        CurrentLineBreak actual = CurrentLineBreakDetector.detect(sourceCodeString);
        CurrentLineBreak expected = null;
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void detect_A$String_StringIsEmpty() throws Exception {
        String sourceCodeString = "";
        CurrentLineBreak actual = CurrentLineBreakDetector.detect(sourceCodeString);
        CurrentLineBreak expected = null;
        assertThat(actual, is(equalTo(expected)));
    }

}
