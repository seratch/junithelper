package org.junithelper.core.file;

import java.io.File;


public class FileWriterFactory {

    private FileWriterFactory() {
    }

    public static FileWriter create(File file) {
        return new CommonsIOFileWriter(file);
    }

}
