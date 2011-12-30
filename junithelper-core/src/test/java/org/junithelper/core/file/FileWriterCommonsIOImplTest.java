package org.junithelper.core.file;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FileWriterCommonsIOImplTest {

    @Before
    public void setUp() throws Exception {
        File file = new File("src/test/resources/tmp.txt");
        file.deleteOnExit();
    }

    @After
    public void tearDown() throws Exception {
        File file = new File("src/test/resources/tmp.txt");
        file.deleteOnExit();
    }

    @Test
    public void type() throws Exception {
        assertNotNull(FileWriterCommonsIOImpl.class);
    }

    @Test
    public void instantiation() throws Exception {
        File file = null;
        FileWriterCommonsIOImpl target = new FileWriterCommonsIOImpl(file);
        assertNotNull(target);
    }

    @Test
    public void setWriteTarget_A$File() throws Exception {
        File argFile = null;
        FileWriterCommonsIOImpl target = new FileWriterCommonsIOImpl(argFile);
        // given
        File file = new File("src/test/resources/tmp.txt");
        // when
        FileWriter actual = target.setWriteTarget(file);
        // then
        assertNotNull(actual);
    }

    @Test
    public void writeText_A$String() throws Exception {
        File file = new File("src/test/resources/tmp.txt");
        FileWriterCommonsIOImpl target = new FileWriterCommonsIOImpl(file);
        // given
        String text = "testtest\r\ntest";
        // when
        target.writeText(text);
        // then
    }

    @Test
    public void writeText_A$String_T$IOException() throws Exception {
        File file = new File("src/test/resources/tmp.txt");
        FileWriterCommonsIOImpl target = new FileWriterCommonsIOImpl(file);
        // given
        String text = null;
        try {
            file.setWritable(false);
            // when
            target.writeText(text);
            fail("Expected exception was not thrown!");
        } catch (IOException e) {
            // then
        }
        file.setWritable(true);
    }

    @Test
    public void writeText_A$String$String() throws Exception {
        File file = new File("src/test/resources/tmp.txt");
        FileWriterCommonsIOImpl target = new FileWriterCommonsIOImpl(file);
        String text = null;
        String encoding = "UTF-8";
        target.writeText(text, encoding);
    }

    @Test
    public void writeText_A$String$String_NotWritable() throws Exception {
        File file = new File("src/test/resources/tmp.txt");
        FileWriterCommonsIOImpl target = new FileWriterCommonsIOImpl(file);
        String text = null;
        String encoding = "UTF-8";
        try {
            file.setWritable(false);
            target.writeText(text, encoding);
            fail("Expected exception was not thrown!");
        } catch (IOException e) {
        }
        file.setWritable(true);
    }

    @Test
    public void writeText_A$String_StringIsNull() throws Exception {
        File file = new File("src/test/resources/tmp.txt");
        FileWriterCommonsIOImpl target = new FileWriterCommonsIOImpl(file);
        String text = null;
        target.writeText(text);
        file.setWritable(true);
    }

    @Test
    public void writeText_A$String_StringIsEmpty() throws Exception {
        File file = new File("src/test/resources/tmp.txt");
        FileWriterCommonsIOImpl target = new FileWriterCommonsIOImpl(file);
        String text = "";
        target.writeText(text);
        file.setWritable(true);
    }

    @Test
    public void writeText_A$String_StringIsFoo() throws Exception {
        File file = new File("src/test/resources/tmp.txt");
        FileWriterCommonsIOImpl target = new FileWriterCommonsIOImpl(file);
        String text = "foo";
        target.writeText(text);
        file.setWritable(true);
    }

    @Test
    public void writeText_A$String$String_StringIsNull() throws Exception {
        File file = new File("src/test/resources/tmp.txt");
        FileWriterCommonsIOImpl target = new FileWriterCommonsIOImpl(file);
        String text = null;
        String encoding = null;
        target.writeText(text, encoding);
        file.setWritable(true);
    }

    @Test
    public void writeText_A$String$String_StringIsEmpty() throws Exception {
        File file = new File("src/test/resources/tmp.txt");
        FileWriterCommonsIOImpl target = new FileWriterCommonsIOImpl(file);
        String text = "";
        String encoding = "";
        target.writeText(text, encoding);
        file.setWritable(true);
    }

}
