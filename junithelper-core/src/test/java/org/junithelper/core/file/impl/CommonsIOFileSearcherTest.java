package org.junithelper.core.file.impl;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.junit.Test;
import org.junithelper.core.file.FileSearcher;

public class CommonsIOFileSearcherTest {

	@Test
	public void type() throws Exception {
		assertNotNull(CommonsIOFileSearcher.class);
	}

	@Test
	public void instantiation() throws Exception {
		CommonsIOFileSearcher target = new CommonsIOFileSearcher();
		assertNotNull(target);
	}

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
