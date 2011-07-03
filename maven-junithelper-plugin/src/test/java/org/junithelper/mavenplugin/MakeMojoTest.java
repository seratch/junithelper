package org.junithelper.mavenplugin;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

public class MakeMojoTest {

	@Test
	public void type() throws Exception {
		assertNotNull(MakeMojo.class);
	}

	@Test
	public void instantiation() throws Exception {
		MakeMojo target = new MakeMojo();
		assertNotNull(target);
	}

	@Test
	@Ignore
	public void execute_A$() throws Exception {
		System.setProperty("junithelper.skipConfirming", "true");
		MakeMojo target = new MakeMojo();
		target.execute();
	}

}
