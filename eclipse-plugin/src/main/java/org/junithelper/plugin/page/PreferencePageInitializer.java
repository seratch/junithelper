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
package org.junithelper.plugin.page;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.junithelper.core.config.Configuration;
import org.junithelper.plugin.Activator;
import org.junithelper.plugin.constant.Preference;

public class PreferencePageInitializer extends AbstractPreferenceInitializer {

    @Override
    public void initializeDefaultPreferences() {

        IPreferenceStore store = Activator.getDefault().getPreferenceStore();

        // Language
        store.setDefault(Preference.lang, Preference.Lang.English);

        store.setDefault(Preference.Common.outputFileEncoding, new Configuration().outputFileEncoding);

        // common
        store.setDefault(Preference.Common.srcMainPath, "src/main/java");
        store.setDefault(Preference.Common.srcTestPath, "src/test/java");

        store.setDefault(Preference.Common.lineBreakPolicy, Preference.LineBreakPolicy.forceNewFileCRLF);

        store.setDefault(Preference.Common.useSoftTabs, false);
        store.setDefault(Preference.Common.softTabSize, 4);

        // generating extended test class
        store.setDefault(Preference.TestClassGen.junitVersion, Preference.TestClassGen.junitVersion3);
        store.setDefault(Preference.TestClassGen.classToExtend, "junit.framework.TestCase");

        // generating test methods
        store.setDefault(Preference.TestMethodGen.includePublic, true);
        store.setDefault(Preference.TestMethodGen.includeProtected, true);
        store.setDefault(Preference.TestMethodGen.includePackageLocal, true);
        store.setDefault(Preference.TestMethodGen.excludesAccessors, true);

        store.setDefault(Preference.TestMethodGen.delimiter, "_");

        store.setDefault(Preference.TestMethodGen.enabledArgs, true);
        store.setDefault(Preference.TestMethodGen.argsPrefix, "A");
        store.setDefault(Preference.TestMethodGen.argsDelimiter, "$");

        store.setDefault(Preference.TestMethodGen.enabledReturn, false);
        store.setDefault(Preference.TestMethodGen.returnPrefix, "R");
        store.setDefault(Preference.TestMethodGen.returnDelimiter, "$");

        store.setDefault(Preference.TestMethodGen.enabledException, true);
        store.setDefault(Preference.TestMethodGen.exceptionPrefix, "T");
        store.setDefault(Preference.TestMethodGen.exceptionDelimiter, "$");

        store.setDefault(Preference.TestMethodGen.enabledTestMethodSampleImpl, true);
        store.setDefault(Preference.TestMethodGen.usingMock, Preference.TestMethodGen.usingMockNone);
        store.setDefault(Preference.TestMethodGen.usingTestingPatternComments, Preference.TestMethodGen.commentsNone);

    }

}
