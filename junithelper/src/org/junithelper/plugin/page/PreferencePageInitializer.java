package org.junithelper.plugin.page;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.junithelper.plugin.Activator;
import org.junithelper.plugin.STR;

public class PreferencePageInitializer extends AbstractPreferenceInitializer
{

	@Override
	public void initializeDefaultPreferences()
	{
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();

		// common
		store.setDefault(STR.Preference.Common.SRC_MAIN_PATH, "src/main/java");
		store.setDefault(STR.Preference.Common.SRC_TEST_PATH, "src/test/java");

		// generating test methods
		store.setDefault(STR.Preference.TestMethodAutoGenerate.ENABLE, true);
		store.setDefault(STR.Preference.TestMethodAutoGenerate.DELIMITER, "_");
		store.setDefault(STR.Preference.TestMethodAutoGenerate.ARGS, true);
		store.setDefault(STR.Preference.TestMethodAutoGenerate.ARGS_PREFIX, "A");
		store.setDefault(STR.Preference.TestMethodAutoGenerate.ARGS_DELIMITER, "$");
		store.setDefault(STR.Preference.TestMethodAutoGenerate.RETURN, false);
		store.setDefault(STR.Preference.TestMethodAutoGenerate.RETURN_PREFIX, "R");
		store.setDefault(STR.Preference.TestMethodAutoGenerate.RETURN_DELIMITER, "$");
	}

}
