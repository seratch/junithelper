package org.junithelper.core.file;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

public class FileWriterFactoryTest {

    @Test
    public void type() throws Exception {
        assertThat(FileWriterFactory.class, notNullValue());
    }

    @Test
    public void create_A$File() throws Exception {
        File file = null;
        FileWriter actual = FileWriterFactory.create(file);
        assertThat(actual, is(notNullValue()));
    }

}
