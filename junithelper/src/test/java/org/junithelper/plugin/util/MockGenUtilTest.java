package org.junithelper.plugin.util;

import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.easymock.classextension.EasyMock;
import org.easymock.classextension.IMocksControl;
import org.eclipse.jface.preference.IPreferenceStore;
import org.junithelper.plugin.constant.Preference;
import org.mockito.Mockito;

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
		// IMocksControl mocks = EasyMock.createStrictControl();
		// IPreferenceStore store = mocks.createMock(IPreferenceStore.class);
		// String key = Preference.TestMethodGen.usingMock;
		// String none = Preference.TestMethodGen.usingMockNone;
		// String easyMock = Preference.TestMethodGen.usingMockEasyMock;
		// String jmock2 = Preference.TestMethodGen.usingMockJMock2;
		// EasyMock.expect(store.getString(key)).andReturn(null);
		// EasyMock.expect(store.getString(key)).andReturn(none);
		// EasyMock.expect(store.getString(key)).andReturn(easyMock);
		// EasyMock.expect(store.getString(key)).andReturn(jmock2);
		// mocks.replay();
		// boolean expected1 = false;
		// boolean actual1 = MockGenUtil.isUsingEasyMock(store);
		// assertEquals(expected1, actual1);
		// boolean expected2 = false;
		// boolean actual2 = MockGenUtil.isUsingEasyMock(store);
		// assertEquals(expected2, actual2);
		// boolean expected3 = true;
		// boolean actual3 = MockGenUtil.isUsingEasyMock(store);
		// assertEquals(expected3, actual3);
		// boolean expected4 = false;
		// boolean actual4 = MockGenUtil.isUsingEasyMock(store);
		// assertEquals(expected4, actual4);
		// mocks.verify();
		IPreferenceStore store = Mockito.mock(IPreferenceStore.class);
		String key = Preference.TestMethodGen.usingMock;
		String none = Preference.TestMethodGen.usingMockNone;
		String easyMock = Preference.TestMethodGen.usingMockEasyMock;
		String jmock2 = Preference.TestMethodGen.usingMockJMock2;
		Mockito.when(store.getString(key)).thenReturn(null, none, easyMock,
				jmock2);
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
	}

	public void test_isUsingJMock2_A$IPreferenceStore() throws Exception {
		IMocksControl mocks = EasyMock.createStrictControl();
		IPreferenceStore store = mocks.createMock(IPreferenceStore.class);
		String key = Preference.TestMethodGen.usingMock;
		String none = Preference.TestMethodGen.usingMockNone;
		String easyMock = Preference.TestMethodGen.usingMockEasyMock;
		String jmock2 = Preference.TestMethodGen.usingMockJMock2;
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
		String key = Preference.TestMethodGen.usingMock;
		String none = Preference.TestMethodGen.usingMockNone;
		String easyMock = Preference.TestMethodGen.usingMockEasyMock;
		String jmock2 = Preference.TestMethodGen.usingMockJMock2;
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

	public void test_isUsingMockito_A$IPreferenceStore() throws Exception {
		// given
		IPreferenceStore store = mock(IPreferenceStore.class);
		// when
		boolean actual = MockGenUtil.isUsingMockito(store);
		// then
		boolean expected = false;
		assertEquals(expected, actual);
	}

}
