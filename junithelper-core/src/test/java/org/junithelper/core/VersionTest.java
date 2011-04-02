package org.junithelper.core;

import org.junit.Test;

import static org.junit.Assert.*;

public class VersionTest {

    @Test
    public void type() throws Exception {
        assertNotNull(Version.class);
    }

    @Test
    public void get_A$() throws Exception {
        String actual = Version.get();
        assertNotNull(actual);
    }

}
