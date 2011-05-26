package org.junithelper.core.meta;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ConstructorMetaTest {

	@Test
	public void type() throws Exception {
		assertNotNull(ConstructorMeta.class);
	}

	@Test
	public void instantiation() throws Exception {
		ConstructorMeta target = new ConstructorMeta();
		assertNotNull(target);
	}

}
