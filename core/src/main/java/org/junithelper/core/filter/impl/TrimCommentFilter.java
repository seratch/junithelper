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

import org.junithelper.core.constant.RegExp;
import org.junithelper.core.constant.StringValue;
import org.junithelper.core.filter.TrimFilter;

public class TrimCommentFilter implements TrimFilter {

    @Override
    public String trimAll(String src) {
        if (src == null) {
            return null;
        }
        String[] lines = src.split(RegExp.LF);
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            line = new TrimInsideOfStringFilter().trimAll(line).replaceFirst("//.*$", "");
            sb.append(line);
        }
        String withoutLineComments = sb.toString();
        String withoutLineBreak = withoutLineComments.replaceAll("\r|\n", " ");
        String withoutComments = withoutLineBreak.replaceAll("/\\*.*?\\*/", StringValue.Empty);
        return withoutComments;
    }

}
