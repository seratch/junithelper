/* 
 * Copyright 2009-2011 junithelper.org. 
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
package org.junithelper.core.generator;

import org.junithelper.core.constant.RegExp;
import org.junithelper.core.constant.StringValue;
import org.junithelper.core.util.Assertion;

class SourceCodeAppender {

    private LineBreakProvider lineBreakProvider;
    private IndentationProvider indentationProvider;

    LineBreakProvider getLineBreakProvider() {
        return lineBreakProvider;
    }

    IndentationProvider getIndentationProvider() {
        return indentationProvider;
    }

    SourceCodeAppender(LineBreakProvider lineBreakProvider, IndentationProvider indentationProvider) {
        this.lineBreakProvider = lineBreakProvider;
        this.indentationProvider = indentationProvider;
    }

    void appendExtensionSourceCode(StringBuilder buf, String code) {

        Assertion.on("buf(StringBuilder)").mustNotBeNull(buf);
        Assertion.on("code").mustNotBeNull(code);

        String[] separatedListBySemicolon = code.split(StringValue.Semicolon);
        for (String separatedBySemicolon : separatedListBySemicolon) {
            if (separatedBySemicolon != null && separatedBySemicolon.trim().length() > 0) {
                separatedBySemicolon = separatedBySemicolon.trim().replaceAll(StringValue.CarriageReturn,
                        StringValue.Empty);
                String[] lines = separatedBySemicolon.split(StringValue.LineFeed);
                for (int i = 0; i < (lines.length - 1); i++) {
                    String line = lines[i];
                    if (line != null && line.trim().length() > 0) {
                        appendTabs(buf, 2);
                        buf.append(line.trim());
                        appendLineBreak(buf);
                    }
                }
                String lastLine = lines[lines.length - 1];
                if (lastLine != null && lastLine.trim().length() > 0) {
                    appendTabs(buf, 2);
                    buf.append(lastLine.trim());
                    if (!lastLine.endsWith("}") && !lastLine.endsWith("/")) {
                        buf.append(StringValue.Semicolon);
                    }
                    appendLineBreak(buf);
                }
            }
        }
    }

    void appendExtensionPostAssignSourceCode(StringBuilder buf, String code, String[] fromList, String to) {

        Assertion.on("code").mustNotBeNull(code);
        Assertion.on("fromList").mustNotBeNull(fromList);
        Assertion.on("to").mustNotBeNull(to);

        String[] separatedListBySemicolon = code.split(StringValue.Semicolon);
        for (String separatedBySemicolon : separatedListBySemicolon) {
            if (separatedBySemicolon != null && separatedBySemicolon.trim().length() > 0) {
                separatedBySemicolon = separatedBySemicolon.trim().replaceAll(StringValue.CarriageReturn, "");
                String[] lines = separatedBySemicolon.split(StringValue.LineFeed);
                for (int i = 0; i < (lines.length - 1); i++) {
                    String line = lines[i];
                    if (line != null && line.trim().length() > 0) {
                        appendTabs(buf, 2);
                        buf.append(line.trim());
                        appendLineBreak(buf);
                    }
                }
                String lastLine = lines[lines.length - 1];
                if (lastLine != null && lastLine.trim().length() > 0) {
                    appendTabs(buf, 2);
                    buf.append(lastLine.trim());
                    if (!lastLine.endsWith("}") && !lastLine.endsWith("/")) {
                        buf.append(StringValue.Semicolon);
                    }
                    appendLineBreak(buf);
                }
            }
        }
    }

    void appendIfNotExists(StringBuilder buf, String src, String importLine) {

        Assertion.on("buf").mustNotBeNull(buf);
        Assertion.on("src").mustNotBeNull(src);
        Assertion.on("importLine").mustNotBeNull(importLine);

        String oneline = src.replaceAll(RegExp.CRLF, StringValue.Space);
        importLine = importLine.replace(StringValue.CarriageReturn + StringValue.LineFeed, StringValue.Empty);
        String importLineRegExp = importLine.replaceAll("\\s+", "\\\\s+").replaceAll("\\.", "\\\\.").replaceAll("\\*",
                "\\\\*");
        if (!oneline.matches(RegExp.Anything_ZeroOrMore_Min + importLineRegExp + RegExp.Anything_ZeroOrMore_Min)) {
            buf.append(importLine);
            buf.append(StringValue.CarriageReturn);
            buf.append(StringValue.LineFeed);
        }

    }

    void appendLineBreak(StringBuilder buf) {
        buf.append(lineBreakProvider.getLineBreak());
    }

    void appendTabs(StringBuilder buf, int times) {

        Assertion.on("buf").mustNotBeNull(buf);
        Assertion.on("times").mustBeGreaterThanOrEqual(times, 0);

        for (int i = 0; i < times; i++) {
            buf.append(indentationProvider.getIndentation());
        }
    }

}
