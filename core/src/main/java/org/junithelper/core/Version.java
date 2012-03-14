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
package org.junithelper.core;

import java.io.InputStream;

import org.junithelper.core.util.IOUtil;

public class Version {

    private Version() {
    }

    private final static Version SINGLETON = new Version();

    private static String version;

    public static final String get() {
        return SINGLETON._get();
    }

    private String _get() {
        if (version == null) {
            try {
                InputStream is = this.getClass().getClassLoader().getResourceAsStream("version.txt");
                version = IOUtil.readAsString(is, null);
            } catch (Exception e) {
                e.printStackTrace();
                version = "unknown";
            }
        }
        return version;
    }

}
