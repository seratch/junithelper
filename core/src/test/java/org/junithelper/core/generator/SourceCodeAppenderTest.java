package org.junithelper.core.generator;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;
import org.junithelper.core.config.Configuration;
import org.junithelper.core.config.LineBreakPolicy;
import org.junithelper.core.exception.JUnitHelperCoreException;
import org.junithelper.core.meta.CurrentLineBreak;

public class SourceCodeAppenderTest {

    CurrentLineBreak currentLineBreak = CurrentLineBreak.CRLF;
    Configuration config = new Configuration();
    LineBreakProvider lineBreakProvider = new LineBreakProvider(config, currentLineBreak);
    IndentationProvider indentationProvider = new IndentationProvider(config);

    @Test
    public void type() throws Exception {
        assertThat(SourceCodeAppender.class, notNullValue());
    }

    @Test
    public void instantiation() throws Exception {
        SourceCodeAppender target = new SourceCodeAppender(lineBreakProvider, indentationProvider);
        assertThat(target, notNullValue());
    }

    @Test
    public void appendExtensionSourceCode_A$StringBuilder$String() throws Exception {
        SourceCodeAppender target = new SourceCodeAppender(lineBreakProvider, indentationProvider);
        StringBuilder buf = new StringBuilder();
        String code = "foo";
        target.appendExtensionSourceCode(buf, code);
        assertThat(buf.toString(), is(equalTo("\t\tfoo;\r\n")));
    }

    @Test
    public void withLineBreakProvider_FORCE_LF() throws Exception {
        Configuration config = new Configuration();
        config.lineBreakPolicy = LineBreakPolicy.forceLF;
        {
            LineBreakProvider lineBreakProvider = new LineBreakProvider(config, CurrentLineBreak.CRLF);
            SourceCodeAppender target = new SourceCodeAppender(lineBreakProvider, indentationProvider);
            StringBuilder buf = new StringBuilder();
            String code = "foo";
            target.appendExtensionSourceCode(buf, code);
            assertThat(buf.toString(), is(equalTo("\t\tfoo;\n")));
        }
        {
            LineBreakProvider lineBreakProvider = new LineBreakProvider(config, CurrentLineBreak.LF);
            SourceCodeAppender target = new SourceCodeAppender(lineBreakProvider, indentationProvider);
            StringBuilder buf = new StringBuilder();
            String code = "foo";
            target.appendExtensionSourceCode(buf, code);
            assertThat(buf.toString(), is(equalTo("\t\tfoo;\n")));
        }
        {
            LineBreakProvider lineBreakProvider = new LineBreakProvider(config, null);
            SourceCodeAppender target = new SourceCodeAppender(lineBreakProvider, indentationProvider);
            StringBuilder buf = new StringBuilder();
            String code = "foo";
            target.appendExtensionSourceCode(buf, code);
            assertThat(buf.toString(), is(equalTo("\t\tfoo;\n")));
        }
    }

    @Test
    public void withLineBreakProvider_FORCE_CRLF() throws Exception {
        Configuration config = new Configuration();
        config.lineBreakPolicy = LineBreakPolicy.forceCRLF;
        {
            LineBreakProvider lineBreakProvider = new LineBreakProvider(config, CurrentLineBreak.CRLF);
            SourceCodeAppender target = new SourceCodeAppender(lineBreakProvider, indentationProvider);
            StringBuilder buf = new StringBuilder();
            String code = "foo";
            target.appendExtensionSourceCode(buf, code);
            assertThat(buf.toString(), is(equalTo("\t\tfoo;\r\n")));
        }
        {
            LineBreakProvider lineBreakProvider = new LineBreakProvider(config, CurrentLineBreak.LF);
            SourceCodeAppender target = new SourceCodeAppender(lineBreakProvider, indentationProvider);
            StringBuilder buf = new StringBuilder();
            String code = "foo";
            target.appendExtensionSourceCode(buf, code);
            assertThat(buf.toString(), is(equalTo("\t\tfoo;\r\n")));
        }
        {
            LineBreakProvider lineBreakProvider = new LineBreakProvider(config, null);
            SourceCodeAppender target = new SourceCodeAppender(lineBreakProvider, indentationProvider);
            StringBuilder buf = new StringBuilder();
            String code = "foo";
            target.appendExtensionSourceCode(buf, code);
            assertThat(buf.toString(), is(equalTo("\t\tfoo;\r\n")));
        }
    }

    @Test
    public void withLineBreakProvider_CRLF_NEW_FILE_ONLY() throws Exception {
        Configuration config = new Configuration();
        config.lineBreakPolicy = LineBreakPolicy.forceNewFileCRLF;
        {
            LineBreakProvider lineBreakProvider = new LineBreakProvider(config, CurrentLineBreak.CRLF);
            SourceCodeAppender target = new SourceCodeAppender(lineBreakProvider, indentationProvider);
            StringBuilder buf = new StringBuilder();
            String code = "foo";
            target.appendExtensionSourceCode(buf, code);
            assertThat(buf.toString(), is(equalTo("\t\tfoo;\r\n")));
        }
        {
            LineBreakProvider lineBreakProvider = new LineBreakProvider(config, CurrentLineBreak.LF);
            SourceCodeAppender target = new SourceCodeAppender(lineBreakProvider, indentationProvider);
            StringBuilder buf = new StringBuilder();
            String code = "foo";
            target.appendExtensionSourceCode(buf, code);
            assertThat(buf.toString(), is(equalTo("\t\tfoo;\n")));
        }
        {
            LineBreakProvider lineBreakProvider = new LineBreakProvider(config, null);
            SourceCodeAppender target = new SourceCodeAppender(lineBreakProvider, indentationProvider);
            StringBuilder buf = new StringBuilder();
            String code = "foo";
            target.appendExtensionSourceCode(buf, code);
            assertThat(buf.toString(), is(equalTo("\t\tfoo;\r\n")));
        }
    }

    @Test
    public void withLineBreakProvider_LF_NEW_FILE_ONLY() throws Exception {
        Configuration config = new Configuration();
        config.lineBreakPolicy = LineBreakPolicy.forceNewFileLF;
        {
            LineBreakProvider lineBreakProvider = new LineBreakProvider(config, CurrentLineBreak.CRLF);
            SourceCodeAppender target = new SourceCodeAppender(lineBreakProvider, indentationProvider);
            StringBuilder buf = new StringBuilder();
            String code = "foo";
            target.appendExtensionSourceCode(buf, code);
            assertThat(buf.toString(), is(equalTo("\t\tfoo;\r\n")));
        }
        {
            LineBreakProvider lineBreakProvider = new LineBreakProvider(config, CurrentLineBreak.LF);
            SourceCodeAppender target = new SourceCodeAppender(lineBreakProvider, indentationProvider);
            StringBuilder buf = new StringBuilder();
            String code = "foo";
            target.appendExtensionSourceCode(buf, code);
            assertThat(buf.toString(), is(equalTo("\t\tfoo;\n")));
        }
        {
            LineBreakProvider lineBreakProvider = new LineBreakProvider(config, null);
            SourceCodeAppender target = new SourceCodeAppender(lineBreakProvider, indentationProvider);
            StringBuilder buf = new StringBuilder();
            String code = "foo";
            target.appendExtensionSourceCode(buf, code);
            assertThat(buf.toString(), is(equalTo("\t\tfoo;\n")));
        }
    }

    @Test
    public void appendExtensionSourceCode_A$StringBuilder$String_StringIsNull() throws Exception {
        LineBreakProvider lineBreakProvider = new LineBreakProvider(config, currentLineBreak);
        SourceCodeAppender target = new SourceCodeAppender(lineBreakProvider, indentationProvider);
        StringBuilder buf = new StringBuilder();
        String code = "foo";
        target.appendExtensionSourceCode(buf, code);
        assertThat(buf.toString(), is(equalTo("\t\tfoo;\r\n")));
    }

    @Test
    public void appendExtensionSourceCode_A$StringBuilder$String_StringIsEmpty() throws Exception {
        LineBreakProvider lineBreakProvider = new LineBreakProvider(config, currentLineBreak);
        SourceCodeAppender target = new SourceCodeAppender(lineBreakProvider, indentationProvider);
        StringBuilder buf = new StringBuilder();
        String code = "";
        target.appendExtensionSourceCode(buf, code);
    }

    @Test
    public void appendExtensionPostAssignSourceCode_A$StringBuilder$String$StringArray$String() throws Exception {
        LineBreakProvider lineBreakProvider = new LineBreakProvider(config, currentLineBreak);
        SourceCodeAppender target = new SourceCodeAppender(lineBreakProvider, indentationProvider);
        StringBuilder buf = new StringBuilder();
        String code = "foo";
        String[] fromList = new String[] {};
        String to = "bar";
        target.appendExtensionPostAssignSourceCode(buf, code, fromList, to);
        assertThat(buf.toString(), is(equalTo("\t\tfoo;\r\n")));
    }

    @Test
    public void appendExtensionPostAssignSourceCode_A$StringBuilder$String$StringArray$String_StringIsNull()
            throws Exception {
        LineBreakProvider lineBreakProvider = new LineBreakProvider(config, currentLineBreak);
        SourceCodeAppender target = new SourceCodeAppender(lineBreakProvider, indentationProvider);
        StringBuilder buf = new StringBuilder();
        String code = "foo";
        String[] fromList = new String[] {};
        String to = "bar";
        target.appendExtensionPostAssignSourceCode(buf, code, fromList, to);
        assertThat(buf.toString(), is(equalTo("\t\tfoo;\r\n")));
    }

    @Test
    public void appendExtensionPostAssignSourceCode_A$StringBuilder$String$StringArray$String_StringIsEmpty()
            throws Exception {
        LineBreakProvider lineBreakProvider = new LineBreakProvider(config, currentLineBreak);
        SourceCodeAppender target = new SourceCodeAppender(lineBreakProvider, indentationProvider);
        StringBuilder buf = new StringBuilder();
        String code = "";
        String[] fromList = new String[] {};
        String to = "";
        target.appendExtensionPostAssignSourceCode(buf, code, fromList, to);
        assertThat(buf.toString(), is(equalTo("")));
    }

    @Test
    public void appendIfNotExists_A$StringBuilder$String$String() throws Exception {
        LineBreakProvider lineBreakProvider = new LineBreakProvider(config, currentLineBreak);
        SourceCodeAppender target = new SourceCodeAppender(lineBreakProvider, indentationProvider);
        StringBuilder buf = new StringBuilder();
        String src = "foo";
        String importLine = "";
        target.appendIfNotExists(buf, src, importLine);
        assertThat(buf.toString(), is(equalTo("")));
    }

    @Test(expected = JUnitHelperCoreException.class)
    public void appendIfNotExists_A$StringBuilder$String$String_StringIsNull() throws Exception {
        LineBreakProvider lineBreakProvider = new LineBreakProvider(config, currentLineBreak);
        SourceCodeAppender target = new SourceCodeAppender(lineBreakProvider, indentationProvider);
        StringBuilder buf = new StringBuilder();
        String src = null;
        String importLine = null;
        target.appendIfNotExists(buf, src, importLine);
    }

    @Test
    public void appendIfNotExists_A$StringBuilder$String$String_StringIsEmpty() throws Exception {
        LineBreakProvider lineBreakProvider = new LineBreakProvider(config, currentLineBreak);
        SourceCodeAppender target = new SourceCodeAppender(lineBreakProvider, indentationProvider);
        StringBuilder buf = new StringBuilder();
        String src = "";
        String importLine = "";
        target.appendIfNotExists(buf, src, importLine);
    }

    @Test
    public void appendIfNotExists_A$StringBuilder$String$String_alradyExists() throws Exception {
        LineBreakProvider lineBreakProvider = new LineBreakProvider(config, currentLineBreak);
        SourceCodeAppender target = new SourceCodeAppender(lineBreakProvider, indentationProvider);
        StringBuilder buf = new StringBuilder();
        String src = "package hoge.foo;\r\nimport junit.framework.TestCase;\r\n\r\npublic class Sample {\r\n\r\n}";
        String importLine = "import junit.framework.TestCase;";
        target.appendIfNotExists(buf, src, importLine);
        assertEquals("", buf.toString());
    }

    @Test
    public void appendIfNotExists_A$StringBuilder$String$String_notExists() throws Exception {
        LineBreakProvider lineBreakProvider = new LineBreakProvider(config, currentLineBreak);
        SourceCodeAppender target = new SourceCodeAppender(lineBreakProvider, indentationProvider);
        StringBuilder buf = new StringBuilder();
        String src = "package hoge.foo;\r\nimport junit.framework.TestCase;\r\n\r\npublic class Sample {\r\n\r\n}";
        String importLine = "import org.junit.Test;";
        target.appendIfNotExists(buf, src, importLine);
        target.appendIfNotExists(buf, src, importLine);
        assertEquals("import org.junit.Test;\r\nimport org.junit.Test;\r\n", buf.toString());
    }

    @Test
    public void appendIfNotExists_A$StringBuilder$String$String_staticImportAsssert() throws Exception {
        LineBreakProvider lineBreakProvider = new LineBreakProvider(config, currentLineBreak);
        SourceCodeAppender target = new SourceCodeAppender(lineBreakProvider, indentationProvider);
        StringBuilder buf = new StringBuilder();
        String src = "package hoge.foo;\r\nimport static org.junit.Assert.assertNotNull;\r\nimport junit.framework.TestCase;\r\n\r\npublic class Sample {\r\n\r\n}";
        String importLine = "import static org.junit.Assert.*;";
        target.appendIfNotExists(buf, src, importLine);
        assertEquals("import static org.junit.Assert.*;\r\n", buf.toString());
    }

    @Test
    public void appendLineBreak_A$StringBuilder() throws Exception {
        LineBreakProvider lineBreakProvider = new LineBreakProvider(config, currentLineBreak);
        SourceCodeAppender target = new SourceCodeAppender(lineBreakProvider, indentationProvider);
        StringBuilder buf = new StringBuilder();
        target.appendLineBreak(buf);
    }

    @Test
    public void appendTabs_A$StringBuilder$int() throws Exception {
        LineBreakProvider lineBreakProvider = new LineBreakProvider(config, currentLineBreak);
        SourceCodeAppender target = new SourceCodeAppender(lineBreakProvider, indentationProvider);
        StringBuilder buf = new StringBuilder();
        int times = 2;
        target.appendTabs(buf, times);
        assertThat(buf.toString(), is(equalTo("\t\t")));
    }

    @Test(expected = JUnitHelperCoreException.class)
    public void appendTabs_A$StringBuilder$int_intIsMinus1() throws Exception {
        LineBreakProvider lineBreakProvider = new LineBreakProvider(config, currentLineBreak);
        SourceCodeAppender target = new SourceCodeAppender(lineBreakProvider, indentationProvider);
        StringBuilder buf = new StringBuilder();
        int times = -1;
        target.appendTabs(buf, times);
    }

    @Test
    public void appendTabs_A$StringBuilder$int_intIs0() throws Exception {
        LineBreakProvider lineBreakProvider = new LineBreakProvider(config, currentLineBreak);
        SourceCodeAppender target = new SourceCodeAppender(lineBreakProvider, indentationProvider);
        StringBuilder buf = new StringBuilder();
        int times = 0;
        target.appendTabs(buf, times);
    }

    @Test
    public void appendTabs_A$StringBuilder$int_intIs1() throws Exception {
        LineBreakProvider lineBreakProvider = new LineBreakProvider(config, currentLineBreak);
        SourceCodeAppender target = new SourceCodeAppender(lineBreakProvider, indentationProvider);
        StringBuilder buf = new StringBuilder();
        int times = 1;
        target.appendTabs(buf, times);
    }

    @Test
    public void appendTabs_A$StringBuilder$int_intIs2() throws Exception {
        LineBreakProvider lineBreakProvider = new LineBreakProvider(config, currentLineBreak);
        SourceCodeAppender target = new SourceCodeAppender(lineBreakProvider, indentationProvider);
        StringBuilder buf = new StringBuilder();
        int times = 2;
        target.appendTabs(buf, times);
    }

    @Test
    public void appendTabs_A$StringBuilder$int_intIsRandom() throws Exception {
        LineBreakProvider lineBreakProvider = new LineBreakProvider(config, currentLineBreak);
        SourceCodeAppender target = new SourceCodeAppender(lineBreakProvider, indentationProvider);
        StringBuilder buf = new StringBuilder();
        int times = new Random().nextInt(10);
        target.appendTabs(buf, times);
    }

}
