package org.junithelper.plugin.util;

import static org.mockito.Mockito.mock;
import junit.framework.TestCase;

import org.eclipse.jface.viewers.StructuredSelection;

public class ResourcePathUtilTest extends TestCase {

	public void test_getPathStartsFromProjectRoot_A$StructuredSelection()
			throws Exception {
		// given
		StructuredSelection structuredSelection = mock(StructuredSelection.class);
		// when
		try {
			ResourcePathUtil.getPathStartsFromProjectRoot(structuredSelection);
		} catch (NullPointerException e) {
			fail("TODO");
		}
		// then
	}

}
