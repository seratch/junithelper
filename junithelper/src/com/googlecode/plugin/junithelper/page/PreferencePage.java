package com.googlecode.plugin.junithelper.page;

import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.googlecode.plugin.junithelper.Activator;
import com.googlecode.plugin.junithelper.IConstants;

public class PreferencePage extends FieldEditorPreferencePage implements
        IWorkbenchPreferencePage
{

	public PreferencePage()
	{

		super(FieldEditorPreferencePage.GRID);
		setDescription(IConstants.Preference.Common.DESCRIPTION);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
	}

	public void init(IWorkbench workbench)
	{
	}

	@Override
	protected void createFieldEditors()
	{
		{
			addField(new DirectoryFieldEditor(IConstants.Preference.LANG, "Language",
			        getFieldEditorParent()));
		}
	}
}
