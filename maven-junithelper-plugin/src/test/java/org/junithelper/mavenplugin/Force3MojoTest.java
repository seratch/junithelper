package org.junithelper.mavenplugin;



import static org.hamcrest.CoreMatchers.*;



import org.junithelper.mavenplugin.Force3Mojo.*;
import static org.junit.Assert.*;
import org.junit.Test;

import org.apache.maven.plugin.MojoExecutionException;
import org.junithelper.command.ForceJUnitVersion3Command;
import org.junithelper.command.MakeTestCommand;

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
	public void execute_A$() throws Exception {
        System.setProperty("junithelper.skipConfirming", "true");
		Force3Mojo target = new Force3Mojo();
		target.execute();
	}

}
