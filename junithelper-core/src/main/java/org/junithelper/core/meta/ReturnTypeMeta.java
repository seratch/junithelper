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
package org.junithelper.core.meta;

import java.util.ArrayList;
import java.util.List;

public class ReturnTypeMeta {

    public String name;

    public String nameInMethodName;

    public List<String> generics = new ArrayList<String>();

    public String getGenericsAsString() {
        StringBuilder buf = new StringBuilder();
        if (generics.size() > 0) {
            buf.append("<");
            buf.append(generics.get(0));
            if (generics.size() > 1) {
                for (int i = 1; i < generics.size(); i++) {
                    buf.append(", ");
                    buf.append(generics.get(i));
                }
            }
            buf.append(">");
        }
        return buf.toString();
    }

}
