package org.junithelper.plugin.action;

import static org.mockito.Mockito.*;
import junit.framework.TestCase;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
import org.junithelper.plugin.constant.Preference;

public class CreateNewTestCaseActionTest extends TestCase {

	public void test_run_A$IAction$ISelection() throws Exception {
		CreateNewTestCaseAction target = new CreateNewTestCaseAction();
		// given
		IAction action = mock(IAction.class);
		ISelection selection = mock(ISelection.class);
		IPreferenceStore store = mock(IPreferenceStore.class);
		target.store = store;
		when(store.getString(Preference.TestClassGen.junitVersion)).thenReturn(
				Preference.TestClassGen.junitVersion3);
		// e.g. : given(mocked.called()).willReturn(1);
		// when
		// TODO Exception
		// target.run(action, selection);
		// then
		// e.g. : verify(mocked).called();
	}

	public void test_run_A$IAction() throws Exception {
		CreateNewTestCaseAction target = new CreateNewTestCaseAction();
		// given
		IAction action = mock(IAction.class);
		IPreferenceStore store = mock(IPreferenceStore.class);
		target.store = store;
		when(store.getString(Preference.TestClassGen.junitVersion)).thenReturn(
				Preference.TestClassGen.junitVersion3);
		// e.g. : given(mocked.called()).willReturn(1);
		// when
		// TODO Exception
		// target.run(action);
		// then
		// e.g. : verify(mocked).called();
	}

	public void test_selectionChanged_A$IAction$ISelection() throws Exception {
		CreateNewTestCaseAction target = new CreateNewTestCaseAction();
		// given
		IAction action = mock(IAction.class);
		ISelection selection = mock(ISelection.class);
		// e.g. : given(mocked.called()).willReturn(1);
		// when
		target.selectionChanged(action, selection);
		// then
		// e.g. : verify(mocked).called();
	}

	public void test_setActiveEditor_A$IAction$IEditorPart() throws Exception {
		CreateNewTestCaseAction target = new CreateNewTestCaseAction();
		// given
		IAction action = mock(IAction.class);
		IEditorPart targetEditor = mock(IEditorPart.class);
		// e.g. : given(mocked.called()).willReturn(1);
		// when
		target.setActiveEditor(action, targetEditor);
		// then
		// e.g. : verify(mocked).called();
	}

}
