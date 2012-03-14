package org.junithelper.core.extractor;

import org.junithelper.core.meta.CurrentLineBreak;

public class CurrentLineBreakDetector {

    private CurrentLineBreakDetector() {
    }

    public static CurrentLineBreak detect(String sourceCodeString) {
        if (sourceCodeString == null) {
            return null;
        } else if (sourceCodeString.contains("\r\n")) {
            return CurrentLineBreak.CRLF;
        } else if (sourceCodeString.contains("\n")) {
            return CurrentLineBreak.LF;
        }
        return null;
    }

}
