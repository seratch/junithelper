package org.junithelper.core.file;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class FileReaderFactoryTest {

    @Test
    public void type() throws Exception {
        assertThat(FileReaderFactory.class, notNullValue());
    }

    @Test
    public void create_A$() throws Exception {
        FileReader actual = FileReaderFactory.create();
        assertThat(actual, is(notNullValue()));
    }

}
