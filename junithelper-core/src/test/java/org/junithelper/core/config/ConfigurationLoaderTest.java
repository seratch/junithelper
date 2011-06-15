package org.junithelper.core.config;

import org.junit.Test;
import org.junithelper.core.util.IOUtil;

import java.io.InputStream;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class ConfigurationLoaderTest {

	@Test
	public void type() throws Exception {
		assertNotNull(ConfigurationLoader.class);
	}

	@Test
	public void instantiation() throws Exception {
		ConfigurationLoader target = new ConfigurationLoader();
		assertNotNull(target);
	}

	@Test
	public void load_A$String() throws Exception {
		ConfigurationLoader target = new ConfigurationLoader();
		// given
		String filepath = "release/junithelper-config.properties";
		// when
		Configuration actual = target.load(filepath);
		// then
		assertNotNull(actual);
	}

	@Test
	public void load_A$String_T$Exception() throws Exception {
		ConfigurationLoader target = new ConfigurationLoader();
		// given
		String filepath = null;
		try {
			// when
			target.load(filepath);
			fail("Expected exception was not thrown!");
		} catch (Exception e) {
			// then
		}
	}

	@Test
	public void load_A$InputStream() throws Exception {
		ConfigurationLoader target = new ConfigurationLoader();
		// given
		InputStream is = IOUtil
				.getResourceAsStream("junithelper-config.properties");
		// when
		Configuration actual = target.load(is);
		// then
		assertNotNull(actual);
	}

	@Test
	public void load_A$InputStream_T$Exception() throws Exception {
		ConfigurationLoader target = new ConfigurationLoader();
		// given
		InputStream is = null;
		// e.g. : given(mocked.called()).willReturn(1);
		try {
			// when
			target.load(is);
			fail("Expected exception was not thrown!");
		} catch (Exception e) {
			// then
		}
	}

}
