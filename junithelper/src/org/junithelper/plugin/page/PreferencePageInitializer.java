/* 
 * Copyright 2009 junithelper.org. 
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
import org.junithelper.plugin.STR;

/**
 * PreferencePageInitializer<br>
 * <br>
 * 
 * @author Kazuhiro Sera
 * @version 1.0
 */
public class PreferencePageInitializer extends AbstractPreferenceInitializer
{

	@Override
	public void initializeDefaultPreferences()
	{
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();

		// common
		{
			store.setDefault(STR.Preference.Common.SRC_MAIN_PATH, "src/main/java");
			store.setDefault(STR.Preference.Common.SRC_TEST_PATH, "src/test/java");
		}

		// generating test methods
		{
			store.setDefault(STR.Preference.TestMethodGen.ENABLE, true);

			store.setDefault(STR.Preference.TestMethodGen.DELIMITER, "_");

			store.setDefault(STR.Preference.TestMethodGen.ARGS, true);
			store.setDefault(STR.Preference.TestMethodGen.ARGS_PREFIX, "A");
			store.setDefault(STR.Preference.TestMethodGen.ARGS_DELIMITER, "$");

			store.setDefault(STR.Preference.TestMethodGen.RETURN, false);
			store.setDefault(STR.Preference.TestMethodGen.RETURN_PREFIX, "R");
			store.setDefault(STR.Preference.TestMethodGen.RETURN_DELIMITER, "$");

			store.setDefault(STR.Preference.TestMethodGen.EXLCUDES_ACCESSORS, true);
			store.setDefault(STR.Preference.TestMethodGen.METHOD_SAMPLE_IMPLEMENTATION,
					true);
		}
	}

}
