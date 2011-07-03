package org.junithelper.mavenplugin;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

public class Force4MojoTest {

	@Test
	public void type() throws Exception {
		assertNotNull(Force4Mojo.class);
	}

	@Test
	public void instantiation() throws Exception {
		Force4Mojo target = new Force4Mojo();
		assertNotNull(target);
	}

	@Test
	@Ignore
	public void execute_A$() throws Exception {
		System.setProperty("junithelper.skipConfirming", "true");
		Force4Mojo target = new Force4Mojo();
		target.execute();
	}

}
