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
import org.junithelper.plugin.Activator;
import org.junithelper.plugin.constant.Preference;

/**
 * PreferencePageInitializer<br>
 * <br>
 * 
 * @author Kazuhiro Sera <seratch@gmail.com>
 * @version 1.0
 */
public class PreferencePageInitializer extends AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();

		// common
		{
			store.setDefault(Preference.Common.SRC_MAIN_PATH, "src/main/java");
			store.setDefault(Preference.Common.SRC_TEST_PATH, "src/test/java");
		}
		// generating extended test class
		{
			store.setDefault(Preference.TestClassGen.ENABLE, true);
			store.setDefault(Preference.TestClassGen.CLASS_TO_EXTEND,
					"junit.framework.TestCase");
		}

		// generating test methods
		{
			store.setDefault(Preference.TestMethodGen.ENABLE, true);

			store.setDefault(Preference.TestMethodGen.EXLCUDES_ACCESSORS, true);
			store.setDefault(Preference.TestMethodGen.DELIMITER, "_");

			store.setDefault(Preference.TestMethodGen.ARGS, true);
			store.setDefault(Preference.TestMethodGen.ARGS_PREFIX, "A");
			store.setDefault(Preference.TestMethodGen.ARGS_DELIMITER, "$");

			store.setDefault(Preference.TestMethodGen.RETURN, false);
			store.setDefault(Preference.TestMethodGen.RETURN_PREFIX, "R");
			store.setDefault(Preference.TestMethodGen.RETURN_DELIMITER, "$");

			store.setDefault(Preference.TestMethodGen.METHOD_SAMPLE_IMPL, true);
			store.setDefault(Preference.TestMethodGen.USING_MOCK, "None");
		}
	}

}
