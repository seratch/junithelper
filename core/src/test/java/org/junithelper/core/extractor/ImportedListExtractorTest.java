package org.junithelper.core.extractor;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junithelper.core.config.Configuration;
import org.junithelper.core.exception.JUnitHelperCoreException;
import org.junithelper.core.extractor.ImportedListExtractor;

public class ImportedListExtractorTest {

    @Test
    public void type() throws Exception {
        assertNotNull(ImportedListExtractor.class);
    }

    @Test
    public void instantiation() throws Exception {
        Configuration config = null;
        ImportedListExtractor target = new ImportedListExtractor(config);
        assertNotNull(target);
    }

    @Test
    public void extract_A$String() throws Exception {
        Configuration config = new Configuration();
        ImportedListExtractor target = new ImportedListExtractor(config);
        // given
        String sourceCodeString = "package foo.var; import java.util.List; import java.io.InputStream; public class Sample { }";
        // when
        List<String> actual = target.extract(sourceCodeString);
        // then
        assertEquals(2, actual.size());
    }

    @Test
    public void extract_A$String_StringIsNull() throws Exception {
        Configuration config = new Configuration();
        ImportedListExtractor target = new ImportedListExtractor(config);
        String sourceCodeString = null;
        try {
            target.extract(sourceCodeString);
            fail();
        } catch (JUnitHelperCoreException e) {
        }
    }

    @Test
    public void extract_A$String_StringIsEmpty() throws Exception {
        Configuration config = new Configuration();
        ImportedListExtractor target = new ImportedListExtractor(config);
        String sourceCodeString = "";
        List<String> actual = target.extract(sourceCodeString);
        assertThat(actual, notNullValue());
    }

}
