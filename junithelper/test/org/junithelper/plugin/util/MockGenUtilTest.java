package org.junithelper.plugin.util;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.easymock.classextension.EasyMock;
import org.easymock.classextension.IMocksControl;
import org.eclipse.jface.preference.IPreferenceStore;
import org.junithelper.plugin.STR;

public class MockGenUtilTest extends TestCase {

	public void test_isMockableClassName_A$String$List() throws Exception {
		List<String> importList = new ArrayList<String>();
		importList.add("java.util.List");
		boolean expected1 = true;
		boolean actual1 = MockGenUtil.isMockableClassName("List", importList);
		assertEquals(expected1, actual1);
		boolean expected2 = false;
		boolean actual2 = MockGenUtil.isMockableClassName("Hoge", importList);
		assertEquals(expected2, actual2);
	}

	public void test_isUsingEasyMock_A$IPreferenceStore() throws Exception {
		IMocksControl mocks = EasyMock.createStrictControl();
		IPreferenceStore store = mocks.createMock(IPreferenceStore.class);
		String key = STR.Preference.TestMethodGen.USING_MOCK;
		String none = STR.Preference.TestMethodGen.USING_MOCK_NONE;
		String easyMock = STR.Preference.TestMethodGen.USING_MOCK_EASYMOCK;
		String jmock2 = STR.Preference.TestMethodGen.USING_MOCK_JMOCK2;
		EasyMock.expect(store.getString(key)).andReturn(null);
		EasyMock.expect(store.getString(key)).andReturn(none);
		EasyMock.expect(store.getString(key)).andReturn(easyMock);
		EasyMock.expect(store.getString(key)).andReturn(jmock2);
		mocks.replay();
		boolean expected1 = false;
		boolean actual1 = MockGenUtil.isUsingEasyMock(store);
		assertEquals(expected1, actual1);
		boolean expected2 = false;
		boolean actual2 = MockGenUtil.isUsingEasyMock(store);
		assertEquals(expected2, actual2);
		boolean expected3 = true;
		boolean actual3 = MockGenUtil.isUsingEasyMock(store);
		assertEquals(expected3, actual3);
		boolean expected4 = false;
		boolean actual4 = MockGenUtil.isUsingEasyMock(store);
		assertEquals(expected4, actual4);
		mocks.verify();
	}

	public void test_isUsingJMock2_A$IPreferenceStore() throws Exception {
		IMocksControl mocks = EasyMock.createStrictControl();
		IPreferenceStore store = mocks.createMock(IPreferenceStore.class);
		String key = STR.Preference.TestMethodGen.USING_MOCK;
		String none = STR.Preference.TestMethodGen.USING_MOCK_NONE;
		String easyMock = STR.Preference.TestMethodGen.USING_MOCK_EASYMOCK;
		String jmock2 = STR.Preference.TestMethodGen.USING_MOCK_JMOCK2;
		EasyMock.expect(store.getString(key)).andReturn(null);
		EasyMock.expect(store.getString(key)).andReturn(none);
		EasyMock.expect(store.getString(key)).andReturn(easyMock);
		EasyMock.expect(store.getString(key)).andReturn(jmock2);
		mocks.replay();
		boolean expected1 = false;
		boolean actual1 = MockGenUtil.isUsingJMock2(store);
		assertEquals(expected1, actual1);
		boolean expected2 = false;
		boolean actual2 = MockGenUtil.isUsingJMock2(store);
		assertEquals(expected2, actual2);
		boolean expected3 = false;
		boolean actual3 = MockGenUtil.isUsingJMock2(store);
		assertEquals(expected3, actual3);
		boolean expected4 = true;
		boolean actual4 = MockGenUtil.isUsingJMock2(store);
		assertEquals(expected4, actual4);
		mocks.verify();
	}

	public void test_isUsingNone_A$IPreferenceStore() throws Exception {
		IMocksControl mocks = EasyMock.createStrictControl();
		IPreferenceStore store = mocks.createMock(IPreferenceStore.class);
		String key = STR.Preference.TestMethodGen.USING_MOCK;
		String none = STR.Preference.TestMethodGen.USING_MOCK_NONE;
		String easyMock = STR.Preference.TestMethodGen.USING_MOCK_EASYMOCK;
		String jmock2 = STR.Preference.TestMethodGen.USING_MOCK_JMOCK2;
		EasyMock.expect(store.getString(key)).andReturn(null);
		EasyMock.expect(store.getString(key)).andReturn(none);
		EasyMock.expect(store.getString(key)).andReturn(easyMock);
		EasyMock.expect(store.getString(key)).andReturn(jmock2);
		mocks.replay();
		boolean expected1 = true;
		boolean actual1 = MockGenUtil.isUsingNone(store);
		assertEquals(expected1, actual1);
		boolean expected2 = true;
		boolean actual2 = MockGenUtil.isUsingNone(store);
		assertEquals(expected2, actual2);
		boolean expected3 = false;
		boolean actual3 = MockGenUtil.isUsingNone(store);
		assertEquals(expected3, actual3);
		boolean expected4 = false;
		boolean actual4 = MockGenUtil.isUsingNone(store);
		assertEquals(expected4, actual4);
		mocks.verify();
	}

}
