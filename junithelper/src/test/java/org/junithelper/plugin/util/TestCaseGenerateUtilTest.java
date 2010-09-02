package org.junithelper.plugin.util;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.preference.IPreferenceStore;
import org.junithelper.plugin.bean.ClassInfo;
import org.junithelper.plugin.bean.ConstructorInfo;
import org.junithelper.plugin.bean.MethodInfo;
import org.junithelper.plugin.constant.Preference;
import org.junithelper.plugin.page.PreferenceLoader;

public class TestCaseGenerateUtilTest extends TestCase {

	public void test_getClassInfoWithUnimplementedTestMethods_A$String$IFile$IFile()
			throws Exception {
		// given
		String testTargetClassname = "testTargetClassname";
		IFile testTarget = mock(IFile.class, "testTarget");
		IFile testCase = mock(IFile.class, "testCase");
		IPreferenceStore store = mock(IPreferenceStore.class);
		TestCaseGenerateUtil.store = store;
		when(store.getString(Preference.TestClassGen.junitVersion)).thenReturn(
				Preference.TestClassGen.junitVersion3);
		// e.g. : given(mocked.called()).willReturn(1);
		// when
		ClassInfo actual = TestCaseGenerateUtil
				.getClassInfoWithUnimplementedTestMethods(testTargetClassname,
						testTarget, testCase);
		// then
		// e.g. : verify(mocked).called();
		assertNotNull(actual);
	}

	public void test_getMethodNamesAlreadyExists_A$IFile() throws Exception {
		// given
		IFile javaFile = mock(IFile.class);
		IPreferenceStore store = mock(IPreferenceStore.class);
		TestCaseGenerateUtil.store = store;
		when(store.getString(Preference.TestClassGen.junitVersion)).thenReturn(
				Preference.TestClassGen.junitVersion3);
		// e.g. : given(mocked.called()).willReturn(1);
		// when
		ClassInfo actual = TestCaseGenerateUtil
				.getMethodNamesAlreadyExists(javaFile);
		// then
		// e.g. : verify(mocked).called();
		assertNotNull(actual);
	}

	public void test_getConstructors_A$PreferenceLoader$ClassInfo$String()
			throws Exception {
		// given
		PreferenceLoader pref = mock(PreferenceLoader.class);
		ClassInfo classInfo = mock(ClassInfo.class);
		String targetClassSourceStr = "public class Target { }";
		IPreferenceStore store = mock(IPreferenceStore.class);
		TestCaseGenerateUtil.store = store;
		when(store.getString(Preference.TestClassGen.junitVersion)).thenReturn(
				Preference.TestClassGen.junitVersion3);
		// e.g. : given(mocked.called()).willReturn(1);
		// when
		List<ConstructorInfo> actual = TestCaseGenerateUtil.getConstructors(
				pref, classInfo, targetClassSourceStr);
		// then
		// e.g. : verify(mocked).called();
		assertNotNull(actual);
	}

	public void test_getTestClassInfoFromTargetClass_A$String$IFile()
			throws Exception {
		// given
		String testTargetClassname = "testTargetClassname";
		IFile javaFile = mock(IFile.class);
		IPreferenceStore store = mock(IPreferenceStore.class);
		TestCaseGenerateUtil.store = store;
		when(store.getString(Preference.TestClassGen.junitVersion)).thenReturn(
				Preference.TestClassGen.junitVersion3);
		// e.g. : given(mocked.called()).willReturn(1);
		// when
		// TODO handle exception
		ClassInfo actual = TestCaseGenerateUtil
				.getTestClassInfoFromTargetClass(testTargetClassname, javaFile);
		// then
		// e.g. : verify(mocked).called();
		assertNotNull(actual);
	}

	public void test_getRequiredInstanceFieldsSourceForJMockitTestMethod_A$MethodInfo$ClassInfo$String()
			throws Exception {
		// given
		MethodInfo testMethod = mock(MethodInfo.class);
		ClassInfo testClassInfo = mock(ClassInfo.class);
		String testTargetClassname = "testTargetClassname";
		IPreferenceStore store = mock(IPreferenceStore.class);
		TestCaseGenerateUtil.store = store;
		when(store.getString(Preference.TestClassGen.junitVersion)).thenReturn(
				Preference.TestClassGen.junitVersion3);
		// e.g. : given(mocked.called()).willReturn(1);
		// when
		String actual = TestCaseGenerateUtil
				.getRequiredInstanceFieldsSourceForJMockitTestMethod(
						testMethod, testClassInfo, testTargetClassname);
		// then
		// e.g. : verify(mocked).called();
		String expected = null;
		assertEquals(expected, actual);
	}

	public void test_getNotBlankTestMethodSource_A$MethodInfo$ClassInfo$String()
			throws Exception {
		// given
		MethodInfo testMethod = new MethodInfo();
		testMethod.methodName = "doSomething";
		testMethod.returnType.name = "void";
		ClassInfo testClassinfo = new ClassInfo();
		String testTargetClassname = "testTargetClassname";
		IPreferenceStore store = mock(IPreferenceStore.class);
		TestCaseGenerateUtil.store = store;
		when(store.getString(Preference.TestClassGen.junitVersion)).thenReturn(
				Preference.TestClassGen.junitVersion3);
		// e.g. : given(mocked.called()).willReturn(1);
		// when
		String actual = TestCaseGenerateUtil.getNotBlankTestMethodSource(
				testMethod, testClassinfo, testTargetClassname);
		// then
		// e.g. : verify(mocked).called();
		String expected = "\t\ttestTargetClassname target = new testTargetClassname();\r\n\t\ttarget.doSomething();\r\n";
		assertEquals(expected, actual);
	}

	public void test_getType_A$String() throws Exception {
		// given
		String arg = "final List<String> result";
		TestCaseGenerateUtil.store = mock(IPreferenceStore.class);
		// e.g. : given(mocked.called()).willReturn(1);
		// when
		String actual = TestCaseGenerateUtil.getType(arg);
		// then
		// e.g. : verify(mocked).called();
		String expected = "List<String>";
		assertEquals(expected, actual);
	}

	public void test_getTypeAvailableInMethodName_A$String() throws Exception {
		// given
		String arg = "List<String>";
		TestCaseGenerateUtil.store = mock(IPreferenceStore.class);
		// e.g. : given(mocked.called()).willReturn(1);
		// when
		String actual = TestCaseGenerateUtil.getTypeAvailableInMethodName(arg);
		// then
		// e.g. : verify(mocked).called();
		String expected = "List";
		assertEquals(expected, actual);
	}

	public void test_getClassInSourceCode_A$String$String$List()
			throws Exception {
		// given
		String returnTypeToCheck = "String";
		String testTargetClassname = "testTargetClassname";
		List<String> importList = new ArrayList<String>();
		TestCaseGenerateUtil.store = mock(IPreferenceStore.class);
		// e.g. : given(mocked.called()).willReturn(1);
		// when
		String actual = TestCaseGenerateUtil.getClassInSourceCode(
				returnTypeToCheck, testTargetClassname, importList);
		// then
		// e.g. : verify(mocked).called();
		String expected = "String";
		assertEquals(expected, actual);
	}

	public void test_setupRequiredImports_A$PreferenceLoader$List$List()
			throws Exception {
		// given
		PreferenceLoader pref = mock(PreferenceLoader.class);
		List<String> alreadyImportedList = new ArrayList<String>();
		List<String> importList = new ArrayList<String>();
		TestCaseGenerateUtil.store = mock(IPreferenceStore.class);
		// e.g. : given(mocked.called()).willReturn(1);
		// when
		List<String> actual = TestCaseGenerateUtil.setupRequiredImports(pref,
				alreadyImportedList, importList);
		// then
		// e.g. : verify(mocked).called();
		assertNotNull(actual);
	}

	public void test_containsInList_A$List$String() throws Exception {
		// given
		List<String> alreadyExistList = new ArrayList<String>();
		alreadyExistList.add("aa");
		alreadyExistList.add("bb");
		alreadyExistList.add("aabbcc");
		String checkTarget = "aa";
		TestCaseGenerateUtil.store = mock(IPreferenceStore.class);
		// e.g. : given(mocked.called()).willReturn(1);
		// when
		boolean actual = TestCaseGenerateUtil.containsInList(alreadyExistList,
				checkTarget);
		// then
		// e.g. : verify(mocked).called();
		boolean expected = true;
		assertEquals(expected, actual);
	}

}
