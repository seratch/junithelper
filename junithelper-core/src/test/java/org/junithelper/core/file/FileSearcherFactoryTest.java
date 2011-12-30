package org.junithelper.core.file;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class FileSearcherFactoryTest {

    @Test
    public void type() throws Exception {
        assertThat(FileSearcherFactory.class, notNullValue());
    }

    @Test
    public void create_A$() throws Exception {
        FileSearcher actual = FileSearcherFactory.create();
        assertThat(actual, is(notNullValue()));
    }

}
