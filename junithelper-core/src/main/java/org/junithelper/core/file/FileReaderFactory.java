package org.junithelper.core.file;


public class FileReaderFactory {

    private FileReaderFactory() {
    }

    public static FileReader create() {
        return new CommonsIOFileReader();
    }

}
