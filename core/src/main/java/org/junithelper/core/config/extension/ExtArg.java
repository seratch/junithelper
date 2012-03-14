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
package org.junithelper.core.config.extension;

import java.util.ArrayList;
import java.util.List;

import org.junithelper.core.util.Assertion;

public class ExtArg {

    public ExtArg(String canonicalClassName) {
        Assertion.on("canonicalClassName").mustNotBeNull(canonicalClassName);
        this.canonicalClassName = canonicalClassName;
    }

    public String getCanonicalClassNameInMethodName() {
        String[] splitted = canonicalClassName.replaceFirst("java\\.lang\\.", "").split("\\.");
        StringBuilder buf = new StringBuilder();
        if (splitted.length == 1) {
            buf.append(splitted[0]);
        } else {
            for (int i = 0; i < (splitted.length - 1); i++) {
                buf.append(splitted[i].subSequence(0, 1));
            }
            buf.append(splitted[splitted.length - 1]);
        }
        return buf.toString();
    }

    public String canonicalClassName;

    public List<String> importList = new ArrayList<String>();

    public List<ExtArgPattern> patterns = new ArrayList<ExtArgPattern>();

}
