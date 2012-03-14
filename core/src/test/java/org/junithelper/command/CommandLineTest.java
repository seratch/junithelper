package org.junithelper.command;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

public class CommandLineTest {

    @Test
    public void type() throws Exception {
        assertNotNull(CommandLine.class);
    }

    @Test
    @Ignore
    public void main_A$StringArray() throws Exception {
        // given
        String[] args = new String[] {};
        // when
        CommandLine.main(args);
        // then
    }

    @Test
    @Ignore
    public void main_A$StringArray_T$Exception() throws Exception {
        // given
        String[] args = new String[] {};
        try {
            // when
            CommandLine.main(args);
            fail("Expected exception was not thrown!");
        } catch (Exception e) {
            // then
        }
    }

}
