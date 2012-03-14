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
package org.junithelper.core.constant;

public final class StringValue {

    public static final String Empty = "";

    public static final String Space = " ";

    public static final String Tab = "\t";

    public static final String Comma = ",";

    public static final String Dot = ".";

    public static final String Asterisk = "*";

    public static final String Semicolon = ";";

    public static final String CarriageReturn = "\r";

    public static final String LineFeed = "\n";

    public static final class DirectorySeparator {

        public static final String General = "/";

        public static final String WindowsOS = "\\\\";

    }

    public static final class FileExtension {

        public static final String JavaFile = ".java";

        public static final String PropertiesFile = ".properties";

    }

    public static final class JUnit {

        public static final String TestClassNameSuffix = "Test";

        public static final String TestMethodNamePrefixForJUnitVersion3 = "test";

    }

}
