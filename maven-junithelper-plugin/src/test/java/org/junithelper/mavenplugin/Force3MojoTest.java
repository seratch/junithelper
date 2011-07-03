package org.junithelper.mavenplugin;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

public class Force3MojoTest {

	@Test
	public void type() throws Exception {
		assertNotNull(Force3Mojo.class);
	}

	@Test
	public void instantiation() throws Exception {
		Force3Mojo target = new Force3Mojo();
		assertNotNull(target);
	}

	@Test
	@Ignore
	public void execute_A$() throws Exception {
		System.setProperty("junithelper.skipConfirming", "true");
		Force3Mojo target = new Force3Mojo();
		target.execute();
	}

}
