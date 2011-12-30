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

import java.util.regex.Pattern;

public final class RegExp {

    private RegExp() {
    }

    public static final String Anything_OneOrMore_Min = ".+?";

    public static final String Anything_ZeroOrMore_Min = ".*?";

    public static final String CR = "\\r";

    public static final String LF = "\\n";

    public static final String CRLF = "[\\r\\n]";

    public static final class FileExtension {

        public static final String JavaFile = "\\.java$";

    }

    public static final class WhiteSpace {

        public static final String Consecutive_OneOrMore_Max = "\\s+";

        public static final String Consecutive_ZeroOrMore_Max = "\\s*";

    }

    public static final class NotWhiteSpace {

        public static final String Consecutive_OneOrMore_Min = "[^\\s]+?";

        public static final String Consecutive_ZeroOrMore_Min = "[^\\s]*?";

    }

    public static final String ReturnType = "[a-zA-Z1-9\\[\\]_,\\$<>\\.]+?";

    public static final String Generics = "<.+>";

    public static final String Generics_Group = "<([a-zA-Z0-9,\\$_\\?]+?)>";

    public static String MethodSignatureArea = "[\\{;\\}][^\\{=;\\}]+\\([^\\{;\\}]*\\)[^\\{;\\}]*\\{\\s*";

    /**
     * Regular expression to search static method syntax
     */
    public static String StaticMethodSignature = "";

    static {
        StaticMethodSignature = WhiteSpace.Consecutive_ZeroOrMore_Max + "[<\\w+?>|final|\\s]*" + "static"
                + "[<\\w+?>|final|\\s]*" + NotWhiteSpace.Consecutive_OneOrMore_Min
                + WhiteSpace.Consecutive_OneOrMore_Max + NotWhiteSpace.Consecutive_OneOrMore_Min
                + WhiteSpace.Consecutive_ZeroOrMore_Max + "\\(" + "[^\\)]*?\\)" + WhiteSpace.Consecutive_ZeroOrMore_Max
                + ".*?" + WhiteSpace.Consecutive_ZeroOrMore_Max + "\\{.*";
    }

    /**
     * Regular expression to search method syntax grouped<br>
     * $1 : return value <br>
     * $2 : method name <br>
     * $3 : args<br>
     */
    public static String MethodSignatureWithoutAccessModifier_Group = "";

    static {
        MethodSignatureWithoutAccessModifier_Group = WhiteSpace.Consecutive_ZeroOrMore_Max
                + "[<[^>]+?>|static|final|\\s]*" + "\\s+(" + ReturnType + ")" + WhiteSpace.Consecutive_OneOrMore_Max
                + "(" + NotWhiteSpace.Consecutive_OneOrMore_Min + ")" + WhiteSpace.Consecutive_ZeroOrMore_Max
                + "\\(([^\\)]*?)\\)" + WhiteSpace.Consecutive_ZeroOrMore_Max + "(throws .+)*.*?"
                + WhiteSpace.Consecutive_ZeroOrMore_Max + "\\{.*";
    }

    /**
     * Regular expression to search method arg name grouped<br>
     * $1 : arg name
     */
    public static String MethodArg_Group = "";

    static {
        MethodArg_Group = NotWhiteSpace.Consecutive_OneOrMore_Min + WhiteSpace.Consecutive_OneOrMore_Max + "("
                + NotWhiteSpace.Consecutive_OneOrMore_Min + "$)";
    }

    /**
     * Regular expression to search package name grouped<br>
     * $1 : package def area
     */
    public static String PackageDefArea_Group = "(package\\s+?[^;]+;)";

    /**
     * Regular expression to search package name grouped<br>
     * $1 : package name
     */
    public static String Package_Group = "";

    static {
        Package_Group = WhiteSpace.Consecutive_ZeroOrMore_Max + "package" + WhiteSpace.Consecutive_OneOrMore_Max
                + "([^\\s]+)" + WhiteSpace.Consecutive_ZeroOrMore_Max + ";" + Anything_ZeroOrMore_Min;
    }

    public static final class PatternObject {

        public static Pattern MethodSignatureArea = Pattern.compile(RegExp.MethodSignatureArea);
        public static Pattern MethodSignatureWithoutAccessModifier_Group = Pattern
                .compile(RegExp.MethodSignatureWithoutAccessModifier_Group);
        public static Pattern MethodArg_Group = Pattern.compile(RegExp.MethodArg_Group);
        public static Pattern PackageDefArea_Group = Pattern.compile(RegExp.PackageDefArea_Group);
        public static Pattern Pacakge_Group = Pattern.compile(RegExp.Package_Group);

    }

}
