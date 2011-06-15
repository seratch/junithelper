package org.junithelper.core.meta.extractor;

import org.junit.Test;
import org.junithelper.core.config.Configuration;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ImportedListExtractorTest {

	@Test
	public void type() throws Exception {
		assertNotNull(ImportedListExtractor.class);
	}

	@Test
	public void instantiation() throws Exception {
		Configuration config = null;
		ImportedListExtractor target = new ImportedListExtractor(config);
		assertNotNull(target);
	}

	@Test
	public void extract_A$String() throws Exception {
		Configuration config = new Configuration();
		ImportedListExtractor target = new ImportedListExtractor(config);
		// given
		String sourceCodeString = "package foo.var; import java.util.List; import java.io.InputStream; public class Sample { }";
		// when
		List<String> actual = target.extract(sourceCodeString);
		// then
		assertEquals(2, actual.size());
	}

}
