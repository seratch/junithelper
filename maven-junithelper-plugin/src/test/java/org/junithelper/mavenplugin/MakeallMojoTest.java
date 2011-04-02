package org.junithelper.mavenplugin;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class MakeallMojoTest {

    @Test
    public void type() throws Exception {
        assertNotNull(MakeallMojo.class);
    }

    @Test
    public void instantiation() throws Exception {
        MakeallMojo target = new MakeallMojo();
        assertNotNull(target);
    }

    @Test
    public void execute_A$() throws Exception {
        System.setProperty("junithelper.skipConfirming", "true");
        MakeallMojo target = new MakeallMojo();
        target.execute();
    }

}
