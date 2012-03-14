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

import org.junithelper.core.config.Configuration;
import org.junithelper.core.config.LineBreakPolicy;
import org.junithelper.core.constant.StringValue;
import org.junithelper.core.meta.CurrentLineBreak;

public class LineBreakProvider {

    Configuration config;

    CurrentLineBreak currentLineBreak;

    public LineBreakProvider(Configuration config, CurrentLineBreak currentLineBreak) {
        this.config = config;
        this.currentLineBreak = currentLineBreak;
    }

    private String getLineBreakFromCurrentLineBreak(CurrentLineBreak currentLineBreak) {
        if (currentLineBreak == CurrentLineBreak.CRLF) {
            return StringValue.CarriageReturn + StringValue.LineFeed;
        } else {
            return StringValue.LineFeed;
        }
    }

    public String getLineBreak() {
        if (config.lineBreakPolicy.equals(LineBreakPolicy.forceCRLF)) {
            return StringValue.CarriageReturn + StringValue.LineFeed;
        } else if (config.lineBreakPolicy.equals(LineBreakPolicy.forceLF)) {
            return StringValue.LineFeed;
        } else if (config.lineBreakPolicy.equals(LineBreakPolicy.forceNewFileCRLF)) {
            if (currentLineBreak == null) {
                return StringValue.CarriageReturn + StringValue.LineFeed;
            } else {
                return getLineBreakFromCurrentLineBreak(currentLineBreak);
            }
        } else if (config.lineBreakPolicy.equals(LineBreakPolicy.forceNewFileLF)) {
            if (currentLineBreak == null) {
                return StringValue.LineFeed;
            } else {
                return getLineBreakFromCurrentLineBreak(currentLineBreak);
            }
        }
        return StringValue.CarriageReturn + StringValue.LineFeed;
    }

}
