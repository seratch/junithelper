package org.junithelper.plugin.util;

import static org.mockito.Mockito.*;

import java.io.InputStream;

import junit.framework.TestCase;
import org.eclipse.core.resources.IFile;
import org.junithelper.plugin.exception.InvalidPreferenceException;

public class EclipseIFileUtilTest extends TestCase {

	public void test_readFile_A$IFile() throws Exception {
		// given
		// e.g. : given(mocked.called()).willReturn(1);
		IFile file = mock(IFile.class);
		// when
		InputStream actual = EclipseIFileUtil.getInputStreamFrom(file);
		// then
		// e.g. : verify(mocked).called();
		InputStream expected = null;
		assertEquals(expected, actual);
	}

	public void test_readFile_A$IFile_T$InvalidPreferenceException()
			throws Exception {
		// given
		IFile arg0 = null;
		// when
		try {
			EclipseIFileUtil.getInputStreamFrom(arg0);
			fail("Expected exception was not thrown! (InvalidPreferenceException)");
		} catch (InvalidPreferenceException e) {
		}
	}

	public void test_detectEncoding_A$IFile$String() throws Exception {
		// given
		IFile file = mock(IFile.class);
		String defaultEncoding = "abc";
		// when
		String actual = EclipseIFileUtil.getDetectedEncodingFrom(file,
				defaultEncoding);
		// then
		String expected = "abc";
		assertEquals(expected, actual);
	}

}
