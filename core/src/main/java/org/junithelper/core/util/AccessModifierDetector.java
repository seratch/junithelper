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
package org.junithelper.core.util;

public class AccessModifierDetector {

    private AccessModifierDetector() {
    }

    public static boolean isPublic(String methodSignature) {
        return methodSignature != null && methodSignature.matches(RegExp.Prefix + "public" + RegExp.Suffix);
    }

    public static boolean isProtected(String methodSignature) {
        return methodSignature != null && methodSignature.matches(RegExp.Prefix + "protected" + RegExp.Suffix);
    }

    public static boolean isPackageLocal(String methodSignature) {
        return methodSignature != null && !methodSignature.matches(RegExp.Prefix + "public" + RegExp.Suffix)
                && !methodSignature.matches(RegExp.Prefix + "protected" + RegExp.Suffix)
                && !methodSignature.matches(RegExp.Prefix + "private" + RegExp.Suffix);
    }

    public static boolean isPrivate(String methodSignature) {
        return methodSignature != null && methodSignature.matches(RegExp.Prefix + "private" + RegExp.Suffix);
    }

    public static final class RegExp {

        public static final String Prefix = "\\s*[\\{;\\}]*(\\s*.*\\s+)?";
        public static final String Suffix = "\\s+?.*\\{\\s*";

    }

}
