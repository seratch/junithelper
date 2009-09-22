package org.junithelper.plugin.page;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
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
			Composite parent = getFieldEditorParent();
			// enable
			BooleanFieldEditor enable = new BooleanFieldEditor(
					STR.Preference.TestMethodAutoGenerate.ENABLE,
					STR.Preference.TestMethodAutoGenerate.ENABLE, parent);
			addField(enable);
			Group parentGroup = new Group(parent, 0);
			{
				FillLayout parentLayout = new FillLayout(256);
				parentLayout.marginHeight = 2;
				parentLayout.marginWidth = 4;
				parentGroup.setLayout(parentLayout);
				parentGroup.setText("Test Method Auto Generate");

				GridData gd = new GridData(768);
				parentGroup.setLayoutData(gd);
				Composite grp = new Composite(parentGroup, 0);
				grp.setLayout(new GridLayout(2, false));
			}

			{
				// delimiter
				Group group = new Group(parentGroup, 0);

				FillLayout layout = new FillLayout(256);
				layout.marginHeight = 4;
				layout.marginWidth = 4;
				group.setLayout(layout);
				group.setText("Delimiter");

				GridData gd = new GridData(768);
				gd.horizontalSpan = 2;
				group.setLayoutData(gd);
				Composite grp = new Composite(group, 0);
				grp.setLayout(new GridLayout(2, false));
				// TODO
				// delimiter
				addField(new StringFieldEditor(
						STR.Preference.TestMethodAutoGenerate.DELIMITER,
						STR.Preference.TestMethodAutoGenerate.DELIMITER, grp));
			}

			{
				// enable args
				BooleanFieldEditor enableArgs = new BooleanFieldEditor(
						STR.Preference.TestMethodAutoGenerate.ARGS,
						STR.Preference.TestMethodAutoGenerate.ARGS, parentGroup);

				addField(enableArgs);

				Group group = new Group(parentGroup, 0);

				FillLayout layout = new FillLayout(256);
				layout.marginHeight = 4;
				layout.marginWidth = 4;
				group.setLayout(layout);
				group.setText("Method Args");

				GridData gd = new GridData(768);
				gd.horizontalSpan = 2;
				group.setLayoutData(gd);
				Composite grp = new Composite(group, 0);
				grp.setLayout(new GridLayout(2, false));
				StringFieldEditor argsPrefix = new StringFieldEditor(
						STR.Preference.TestMethodAutoGenerate.ARGS_PREFIX,
						STR.Preference.TestMethodAutoGenerate.ARGS_PREFIX, grp);
				StringFieldEditor argsDelimiter = new StringFieldEditor(
						STR.Preference.TestMethodAutoGenerate.ARGS_DELIMITER,
						STR.Preference.TestMethodAutoGenerate.ARGS_DELIMITER, grp);
				addField(argsPrefix);
				addField(argsDelimiter);
				// Label label = new Label(grp, 0);
				// label.setText("something message.");
				// gd = new GridData(768);
				// gd.horizontalSpan = 2;
				// label.setLayoutData(gd);
			}
			{
				// enable return
				addField(new BooleanFieldEditor(
						STR.Preference.TestMethodAutoGenerate.RETURN,
						STR.Preference.TestMethodAutoGenerate.RETURN, parentGroup));

				Group group = new Group(parentGroup, 0);

				FillLayout layout = new FillLayout(256);
				layout.marginHeight = 4;
				layout.marginWidth = 4;
				group.setLayout(layout);
				group.setText("Return");

				GridData gd = new GridData(768);
				gd.horizontalSpan = 2;
				group.setLayoutData(gd);
				Composite grp = new Composite(group, 0);
				grp.setLayout(new GridLayout(2, false));
				StringFieldEditor argsPrefix = new StringFieldEditor(
						STR.Preference.TestMethodAutoGenerate.RETURN_PREFIX,
						STR.Preference.TestMethodAutoGenerate.RETURN_PREFIX, grp);
				StringFieldEditor argsDelimiter = new StringFieldEditor(
						STR.Preference.TestMethodAutoGenerate.RETURN_DELIMITER,
						STR.Preference.TestMethodAutoGenerate.RETURN_DELIMITER, grp);
				addField(argsPrefix);
				addField(argsDelimiter);
				// Label label = new Label(grp, 0);
				// label.setText("something message.");
				// gd = new GridData(768);
				// gd.horizontalSpan = 2;
				// label.setLayoutData(gd);

			}
		}
	}

}
