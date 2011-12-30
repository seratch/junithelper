package org.junithelper.core.file;


public class FileSearcherFactory {

    private FileSearcherFactory() {
    }

    public static FileSearcher create() {
        return new CommonsIOFileSearcher();
    }

}
