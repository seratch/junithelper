package com.googlecode.plugin.junithelper.page;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.googlecode.plugin.junithelper.Activator;

public class PreferencePageInitializer extends AbstractPreferenceInitializer
{

	@Override
	public void initializeDefaultPreferences()
	{

		IPreferenceStore store = Activator.getDefault().getPreferenceStore();

		store.setDefault("LANG", "JP");

	}

}
