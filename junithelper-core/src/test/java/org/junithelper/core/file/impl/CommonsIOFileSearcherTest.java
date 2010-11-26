package org.junithelper.core.file.impl;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.List;

import org.junit.Test;
import org.junithelper.core.file.FileSearcher;
import org.junithelper.core.file.impl.CommonsIOFileSearcher;

public class CommonsIOFileSearcherTest {

	@Test
	public void searchFilesRecursivelyByName_A$String$String() throws Exception {
		FileSearcher target = new CommonsIOFileSearcher();
		String baseDir = ".";
		String regexp = ".java";
		List<File> actual = target
				.searchFilesRecursivelyByName(baseDir, regexp);
		assertNotNull(actual);
	}

}
