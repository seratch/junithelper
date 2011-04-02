package org.junithelper.mavenplugin;

import static org.junit.Assert.*;

import org.apache.maven.plugin.MojoExecutionException;
import org.junithelper.command.MakeTestCommand;
import org.junithelper.mavenplugin.MakeMojo.*;
import junit.framework.TestCase;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

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
	public void execute_A$() throws Exception {
        System.setProperty("junithelper.skipConfirming", "true");
        MakeMojo target = new MakeMojo();
        target.execute();
    }

}
