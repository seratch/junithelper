package org.junithelper.plugin.page;

import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.junithelper.plugin.Activator;
import org.junithelper.plugin.STR;


public class PreferencePage extends FieldEditorPreferencePage implements
        IWorkbenchPreferencePage
{

	public PreferencePage()
	{

		super(FieldEditorPreferencePage.GRID);
		setDescription(STR.Preference.Common.DESCRIPTION);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
	}

	public void init(IWorkbench workbench)
	{
	}

	@Override
	protected void createFieldEditors()
	{
		{
			addField(new DirectoryFieldEditor(STR.Preference.LANG, "Language",
			        getFieldEditorParent()));
		}
	}
}
