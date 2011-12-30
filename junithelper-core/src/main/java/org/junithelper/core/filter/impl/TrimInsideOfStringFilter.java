package org.junithelper.core.filter.impl;

import org.junithelper.core.filter.TrimFilter;
import org.junithelper.core.util.Assertion;

public class TrimInsideOfStringFilter implements TrimFilter {

    @Override
    public String trimAll(String src) {
        if (src == null) {
            return null;
        }
        int len = src.length();
        boolean isInsideOfString = false;
        boolean isInsideOfChar = false;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            char current = src.charAt(i);
            // check inside of String or char
            if (i > 0) {
                if (!isInsideOfChar && current == '"') {
                    int count = countPreviousContinuedBackslash(src, i, 0);
                    if (count % 2 == 0) {
                        isInsideOfString = !isInsideOfString;
                        sb.append(current);
                        continue;
                    }
                }
                if (!isInsideOfString && current == '\'') {
                    int count = countPreviousContinuedBackslash(src, i, 0);
                    if (count % 2 == 0) {
                        isInsideOfChar = !isInsideOfChar;
                        sb.append(current);
                        continue;
                    }
                }
            }
            if (isInsideOfChar || isInsideOfString) {
                continue;
            }
            sb.append(current);
        }
        return sb.toString();
    }

    static int countPreviousContinuedBackslash(String str, int currentNotBackslashCharIndex, int count) {
        Assertion.on("currentNotBackslashCharIndex").mustBeGreaterThanOrEqual(currentNotBackslashCharIndex, 0);
        Assertion.on("count").mustBeGreaterThanOrEqual(count, 0);
        Character previous = null;
        if (currentNotBackslashCharIndex > 0) {
            int previousIndex = currentNotBackslashCharIndex - 1;
            previous = str.charAt(previousIndex);
            if (previous == '\\') {
                return countPreviousContinuedBackslash(str, previousIndex, count + 1);
            } else {
                return count;
            }
        }
        return count;
    }

}
