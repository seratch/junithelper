package org.junithelper.core;

import static org.junit.Assert.*;

import org.junit.Test;

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
