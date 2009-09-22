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
		store.setDefault(STR.Preference.TestMethodAutoGenerate.ENABLE, true);
		store.setDefault(STR.Preference.TestMethodAutoGenerate.DELIMITER, "_");
		store.setDefault(STR.Preference.TestMethodAutoGenerate.ARGS, true);
		store.setDefault(STR.Preference.TestMethodAutoGenerate.ARGS_PREFIX, "A");
		store.setDefault(STR.Preference.TestMethodAutoGenerate.ARGS_DELIMITER, "$");
		store.setDefault(STR.Preference.TestMethodAutoGenerate.RETURN, true);
		store.setDefault(STR.Preference.TestMethodAutoGenerate.RETURN_PREFIX, "R");
		store.setDefault(STR.Preference.TestMethodAutoGenerate.RETURN_DELIMITER, "$");
	}

}
