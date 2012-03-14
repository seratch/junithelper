package org.junithelper.plugin.page;

import org.junithelper.plugin.io.PropertiesLoader;

import junit.framework.TestCase;

public class PropertiesLoaderTest extends TestCase {

	public void test_get_A$String() throws Exception {
		String lang = "en";
		PropertiesLoader target = new PropertiesLoader(lang);
		// given
		String key = "labels.preference.Common.description";
		// e.g. : given(mocked.called()).willReturn(1);
		// when
		String actual = target.get(key);
		// then
		// e.g. : verify(mocked).called();
		String expected = "Settings for JUnit Helper plugin.";
		assertEquals(expected, actual);
	}

}
