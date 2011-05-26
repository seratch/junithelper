package org.junithelper.core.meta;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ClassMetaTest {

	@Test
	public void type() throws Exception {
		assertNotNull(ClassMeta.class);
	}

	@Test
	public void instantiation() throws Exception {
		ClassMeta target = new ClassMeta();
		assertNotNull(target);
	}

}
