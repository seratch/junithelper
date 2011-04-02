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
package org.junithelper.core.config;

public class TestMethodName {

    public boolean isArgsRequired = true;

    public boolean isReturnRequired = false;

    public String basicDelimiter = "_";

    public String argsAreaPrefix = "A";

    public String argsAreaDelimiter = "$";

    public String returnAreaPrefix = "R";

    public String returnAreaDelimiter = "$";

    public String exceptionAreaPrefix = "T";

    public String exceptionAreaDelimiter = "$";

}
