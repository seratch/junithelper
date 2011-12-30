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
package org.junithelper.core.extractor;

import java.util.ArrayList;
import java.util.List;

import org.junithelper.core.config.Configuration;
import org.junithelper.core.constant.RegExp;
import org.junithelper.core.constant.StringValue;
import org.junithelper.core.filter.TrimFilterUtil;
import org.junithelper.core.util.Assertion;

public class ImportedListExtractor {

    @SuppressWarnings("unused")
    private Configuration config;

    public ImportedListExtractor(Configuration config) {
        this.config = config;
    }

    public List<String> extract(String sourceCodeString) {
        Assertion.on("sourceCodeString").mustNotBeNull(sourceCodeString);
        List<String> dest = new ArrayList<String>();
        sourceCodeString = TrimFilterUtil.doAllFilters(sourceCodeString).replaceAll(RegExp.CRLF, StringValue.Space);
        String[] splittedArray = sourceCodeString.split("import\\s+");
        int len = splittedArray.length;
        for (int i = 1; i < len; i++) {
            String each = splittedArray[i].trim();
            if (each.matches("\\s*(static)*[^;]+?;")) {
                dest.add(each.replaceAll(";", ""));
            } else if (each.matches("\\s*(static)*.+?;.+")) {
                dest.add(each.split(";")[0]);
            }
        }
        return dest;
    }

}
