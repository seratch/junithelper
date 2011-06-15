package org.junithelper.mavenplugin;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

import org.apache.maven.plugin.MojoExecutionException;
import org.junithelper.command.ForceJUnitVersion4Command;
import org.junithelper.mavenplugin.Force4Mojo.*;


import org.junit.Test;

import static org.junit.Assert.assertNotNull;

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
	public void execute_A$() throws Exception {
        System.setProperty("junithelper.skipConfirming", "true");
        Force4Mojo target = new Force4Mojo();
        target.execute();
    }

}
