/* 
 * Copyright 2009-2010 junithelper.org. 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language 
 * governing permissions and limitations under the License. 
 */
package org.junithelper.core.filter.impl;

import java.util.Stack;

import org.junithelper.core.constant.RegExp;
import org.junithelper.core.constant.StringValue;
import org.junithelper.core.filter.TrimFilter;
import org.junithelper.core.util.Assertion;

public class TrimInsideOfBraceFilter implements TrimFilter {

    @Override
    public String trimAll(String src) {
        if (src == null) {
            return null;
        }
        src = src.replaceAll(RegExp.CRLF, StringValue.Space).replaceAll(RegExp.LF, StringValue.Space);
        int len = src.length();
        boolean isInsideOfString = false;
        boolean isInsideOfChar = false;
        boolean isInsideOfTargetClass = false;
        boolean isInsideOfFirstBrace = false;
        boolean isInsideOfSecondBrace = false;
        Stack<Character> braceStack = new Stack<Character>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            char current = src.charAt(i);
            // check inside of String or char
            if (i > 0) {
                if (current == '"') {
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
            // waiting for inside of the target class
            if (!isInsideOfTargetClass) {
                sb.append(current);
                // might be started class def
                if (i >= 6 && (src.charAt(i - 6) == ' ' || src.charAt(i - 6) == ';') && src.charAt(i - 5) == 'c'
                        && src.charAt(i - 4) == 'l' && src.charAt(i - 3) == 'a' && src.charAt(i - 2) == 's'
                        && src.charAt(i - 1) == 's' && current == ' ') {
                    // class
                    isInsideOfTargetClass = true;
                } else if (i >= 5 && (src.charAt(i - 5) == ' ' || src.charAt(i - 5) == ';') && src.charAt(i - 4) == 'e'
                        && src.charAt(i - 3) == 'n' && src.charAt(i - 2) == 'u' && src.charAt(i - 1) == 'm'
                        && current == ' ') {
                    // enum
                    isInsideOfTargetClass = true;
                }
                continue;
            }
            // waiting for inside of the first level brace
            if (!isInsideOfFirstBrace) {
                sb.append(current);
                if (current == '{') {
                    isInsideOfFirstBrace = true;
                }
                continue;
            }
            // excluding inside of top brace
            // outer of top braced
            if (!isInsideOfSecondBrace) {
                sb.append(current);
            }
            // brace start
            if (current == '{') {
                isInsideOfSecondBrace = true;
                braceStack.push(current);
            }
            // brace end
            if (!braceStack.empty() && current == '}') {
                braceStack.pop();
                if (braceStack.empty()) {
                    sb.append(current);
                }
            }
            // check the brace stack state
            if (braceStack.empty()) {
                isInsideOfSecondBrace = false;
            }
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
