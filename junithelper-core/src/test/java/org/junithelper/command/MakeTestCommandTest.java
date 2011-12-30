package org.junithelper.command;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.junit.Ignore;
import org.junit.Test;
import org.junithelper.core.file.FileWriter;
import org.junithelper.core.file.FileWriterFactory;
import org.junithelper.core.util.IOUtil;

public class MakeTestCommandTest {

    @Test
    public void type() throws Exception {
        assertNotNull(MakeTestCommand.class);
    }

    @Test
    @Ignore
    public void main_A$StringArray() throws Exception {
        // given
        String[] args = new String[] {};
        // when
        MakeTestCommand.main(args);
        // then
    }

    @Test
    public void main_A$StringArray_forceLF() throws Exception {

        try {

            // given
            File testFile = new File("src/test/java/org/junithelper/core/config/LineBreakPolicyTest.java");
            FileWriter writer = FileWriterFactory.create(testFile);
            String preparedSource = IOUtil.readAsString(IOUtil.getResourceAsStream("LineBreakPolicyTestBefore.txt"),
                    "UTF-8");
            writer.writeText(preparedSource);

            // when
            String[] args = new String[] { "src/main/java/org/junithelper/core/config/LineBreakPolicy.java" };
            System.setProperty("junithelper.skipConfirming", "true");
            System.setProperty("junithelper.configProperties",
                    "src/test/resources/junithelper-config_forceLF.properties");
            MakeTestCommand.main(args);

            // then
            Thread.sleep(100L);

            InputStream is = new FileInputStream(testFile);
            String source = IOUtil.readAsString(is, "UTF-8");
            assertThat(source.contains("\r"), is(false)); // TEST

        } finally {

            File testFile = new File("src/test/java/org/junithelper/core/config/LineBreakPolicyTest.java");
            FileWriter writer = FileWriterFactory.create(testFile);
            String preparedSource = IOUtil.readAsString(IOUtil.getResourceAsStream("LineBreakPolicyTest.txt"), "UTF-8");
            writer.writeText(preparedSource);

        }
    }

    @Test
    public void main_A$StringArray_forceCRLF() throws Exception {

        try {

            // given
            File testFile = new File("src/test/java/org/junithelper/core/config/LineBreakPolicyTest.java");
            FileWriter writer = FileWriterFactory.create(testFile);
            String preparedSource = IOUtil.readAsString(IOUtil.getResourceAsStream("LineBreakPolicyTestBeforeLF.txt"),
                    "UTF-8");
            writer.writeText(preparedSource);

            // when
            String[] args = new String[] { "src/main/java/org/junithelper/core/config/LineBreakPolicy.java" };
            System.setProperty("junithelper.skipConfirming", "true");
            System.setProperty("junithelper.configProperties",
                    "src/test/resources/junithelper-config_forceCRLF.properties");
            MakeTestCommand.main(args);

            // then
            Thread.sleep(100L);

            InputStream is = new FileInputStream(testFile);
            String source = IOUtil.readAsString(is, "UTF-8");
            assertThat(source.contains("\r"), is(true)); // TEST

        } finally {

            File testFile = new File("src/test/java/org/junithelper/core/config/LineBreakPolicyTest.java");
            FileWriter writer = FileWriterFactory.create(testFile);
            String preparedSource = IOUtil.readAsString(IOUtil.getResourceAsStream("LineBreakPolicyTest.txt"), "UTF-8");
            writer.writeText(preparedSource);

        }
    }

    @Test
    public void main_A$StringArray_forceNewFileCRLF() throws Exception {

        try {

            // given
            File testFile = new File("src/test/java/org/junithelper/core/config/LineBreakPolicyTest.java");
            FileWriter writer = FileWriterFactory.create(testFile);
            String preparedSource = IOUtil.readAsString(IOUtil.getResourceAsStream("LineBreakPolicyTestBefore.txt"),
                    "UTF-8");
            writer.writeText(preparedSource);

            // when
            String[] args = new String[] { "src/main/java/org/junithelper/core/config/LineBreakPolicy.java" };
            System.setProperty("junithelper.skipConfirming", "true");
            System.setProperty("junithelper.configProperties", "src/test/resources/junithelper-config.properties");
            MakeTestCommand.main(args);

            // then
            Thread.sleep(100L);

            InputStream is = new FileInputStream(testFile);
            String source = IOUtil.readAsString(is, "UTF-8");
            assertThat(source.contains("\r"), is(true)); // TEST

        } finally {

            File testFile = new File("src/test/java/org/junithelper/core/config/LineBreakPolicyTest.java");
            FileWriter writer = FileWriterFactory.create(testFile);
            String preparedSource = IOUtil.readAsString(IOUtil.getResourceAsStream("LineBreakPolicyTest.txt"), "UTF-8");
            writer.writeText(preparedSource);

        }
    }

    @Test
    public void main_A$StringArray_forceNewFileLF() throws Exception {

        try {

            // given
            File testFile = new File("src/test/java/org/junithelper/core/config/LineBreakPolicyTest.java");
            FileWriter writer = FileWriterFactory.create(testFile);
            String preparedSource = IOUtil.readAsString(IOUtil.getResourceAsStream("LineBreakPolicyTestBefore.txt"),
                    "UTF-8");
            writer.writeText(preparedSource);

            // when
            String[] args = new String[] { "src/main/java/org/junithelper/core/config/LineBreakPolicy.java" };
            System.setProperty("junithelper.skipConfirming", "true");
            System.setProperty("junithelper.configProperties",
                    "src/test/resources/junithelper-config_forceNewFileLF.properties");
            MakeTestCommand.main(args);

            // then
            Thread.sleep(100L);

            InputStream is = new FileInputStream(testFile);
            String source = IOUtil.readAsString(is, "UTF-8");
            assertThat(source.contains("\r"), is(true)); // TEST

        } finally {

            File testFile = new File("src/test/java/org/junithelper/core/config/LineBreakPolicyTest.java");
            FileWriter writer = FileWriterFactory.create(testFile);
            String preparedSource = IOUtil.readAsString(IOUtil.getResourceAsStream("LineBreakPolicyTest.txt"), "UTF-8");
            writer.writeText(preparedSource);

        }
    }

    @Test
    public void main_A$StringArray_Issue74() throws Exception {
        String[] args = new String[] { "src/main/java/org/junithelper/core/generator/SourceCodeAppender.java" };
        System.setProperty("junithelper.skipConfirming", "true");
        System.setProperty("junithelper.configProperties", "src/test/resources/junithelper-config.properties");
        MakeTestCommand.main(args);
    }

    @Test
    public void main_A$StringArray_HardTabs() throws Exception {

        try {

            // given
            File testFile = new File("src/test/java/org/junithelper/core/generator/IndentationProviderTest.java");
            FileWriter writer = FileWriterFactory.create(testFile);
            String preparedSource = IOUtil.readAsString(
                    IOUtil.getResourceAsStream("IndentationProviderTestBefore.txt"), "UTF-8");
            writer.writeText(preparedSource);

            // when
            String[] args = new String[] { "src/main/java/org/junithelper/core/generator/IndentationProvider.java" };
            System.setProperty("junithelper.skipConfirming", "true");
            System.setProperty("junithelper.configProperties", "src/test/resources/junithelper-config.properties");
            MakeTestCommand.main(args);

            // then
            Thread.sleep(100L);

            InputStream is = new FileInputStream(testFile);
            String source = IOUtil.readAsString(is, "UTF-8");
            String expected = IOUtil.readAsString(IOUtil.getResourceAsStream("IndentationProviderTestHardTabs.txt"),
                    "UTF-8");
            assertThat(source, is(equalTo(expected))); // TEST

        } finally {

            File testFile = new File("src/test/java/org/junithelper/core/generator/IndentationProviderTest.java");
            FileWriter writer = FileWriterFactory.create(testFile);
            String preparedSource = IOUtil.readAsString(IOUtil.getResourceAsStream("IndentationProviderTestAfter.txt"),
                    "UTF-8");
            writer.writeText(preparedSource);

        }
    }

    @Test
    public void main_A$StringArray_SoftTabs() throws Exception {

        try {

            // given
            File testFile = new File("src/test/java/org/junithelper/core/generator/IndentationProviderTest.java");
            FileWriter writer = FileWriterFactory.create(testFile);
            String preparedSource = IOUtil.readAsString(
                    IOUtil.getResourceAsStream("IndentationProviderTestBefore.txt"), "UTF-8");
            writer.writeText(preparedSource);

            // when
            String[] args = new String[] { "src/main/java/org/junithelper/core/generator/IndentationProvider.java" };
            System.setProperty("junithelper.skipConfirming", "true");
            System.setProperty("junithelper.configProperties",
                    "src/test/resources/junithelper-config_softTabs.properties");
            MakeTestCommand.main(args);

            // then
            Thread.sleep(100L);

            InputStream is = new FileInputStream(testFile);
            String source = IOUtil.readAsString(is, "UTF-8");
            String expected = IOUtil.readAsString(IOUtil
                    .getResourceAsStream("IndentationProviderTestSoftTabsSize2.txt"), "UTF-8");
            assertThat(source, is(equalTo(expected))); // TEST

        } finally {

            File testFile = new File("src/test/java/org/junithelper/core/generator/IndentationProviderTest.java");
            FileWriter writer = FileWriterFactory.create(testFile);
            String preparedSource = IOUtil.readAsString(IOUtil.getResourceAsStream("IndentationProviderTestAfter.txt"),
                    "UTF-8");
            writer.writeText(preparedSource);

        }
    }

}
