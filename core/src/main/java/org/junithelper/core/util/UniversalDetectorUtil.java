package org.junithelper.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.mozilla.universalchardet.UniversalDetector;

public class UniversalDetectorUtil {

    private UniversalDetectorUtil() {
    }

    public static String getDetectedEncoding(InputStream is) throws IOException {
        UniversalDetector detector = new UniversalDetector(null);
        byte[] buf = new byte[4096];
        int nread;
        while ((nread = is.read(buf)) > 0 && !detector.isDone()) {
            detector.handleData(buf, 0, nread);
        }
        detector.dataEnd();
        return detector.getDetectedCharset();
    }

    public static String getDetectedEncoding(File file) throws IOException {
        if (file != null && file.isFile() && file.canRead()) {
            return getDetectedEncoding(new FileInputStream(file));
        }
        throw new IOException("Cannot read file - " + file.getAbsolutePath());
    }

}
