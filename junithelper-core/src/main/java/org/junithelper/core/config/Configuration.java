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

import org.junithelper.core.config.extension.ExtConfiguration;

public class Configuration {

    public boolean isExtensionEnabled = false;

    public String extensionConfigXML = "junithelper-extension.xml";

    public ExtConfiguration extConfiguration = new ExtConfiguration(this);

    public LineBreakPolicy lineBreakPolicy = LineBreakPolicy.forceNewFileCRLF;

    public boolean useSoftTabs = false;

    public int softTabSize = 4;

    public String language = "en";

    public String outputFileEncoding = "UTF-8";

    public String directoryPathOfProductSourceCode = "src/main/java";

    public String directoryPathOfTestSourceCode = "src/test/java";

    public JUnitVersion junitVersion = JUnitVersion.version4;

    public String testCaseClassNameToExtend = "junit.framework.TestCase";

    public boolean isTemplateImplementationRequired = true;

    public TestingTarget target = new TestingTarget();

    public TestMethodName testMethodName = new TestMethodName();

    public MockObjectFramework mockObjectFramework = null;

    public TestingPatternExplicitComment testingPatternExplicitComment = TestingPatternExplicitComment.None;

}
