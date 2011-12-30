package org.junithelper.core.file;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.junit.Test;
import org.junithelper.core.exception.JUnitHelperCoreException;

public class FileSearcherCommonsIOImplTest {

    @Test
    public void type() throws Exception {
        assertNotNull(FileSearcherCommonsIOImpl.class);
    }

    @Test
    public void instantiation() throws Exception {
        FileSearcherCommonsIOImpl target = new FileSearcherCommonsIOImpl();
        assertNotNull(target);
    }

    @Test
    public void searchFilesRecursivelyByName_A$String$String() throws Exception {
        FileSearcher target = new FileSearcherCommonsIOImpl();
        String baseDir = ".";
        String regexp = ".java";
        List<File> actual = target.searchFilesRecursivelyByName(baseDir, regexp);
        assertNotNull(actual);
    }

    @Test
    public void searchFilesRecursivelyByName_A$String$String_StringIsNull() throws Exception {
        FileSearcherCommonsIOImpl target = new FileSearcherCommonsIOImpl();
        String baseAbsoluteDir = null;
        String regexp = null;
        try {
            target.searchFilesRecursivelyByName(baseAbsoluteDir, regexp);
            fail();
        } catch (JUnitHelperCoreException e) {
        }
    }

    @Test
    public void searchFilesRecursivelyByName_A$String$String_StringIsEmpty() throws Exception {
        FileSearcherCommonsIOImpl target = new FileSearcherCommonsIOImpl();
        String baseAbsoluteDir = "";
        String regexp = "";
        try {
            target.searchFilesRecursivelyByName(baseAbsoluteDir, regexp);
            fail();
        } catch (JUnitHelperCoreException e) {
        }
    }

}
