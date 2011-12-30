package org.junithelper.core.config;

import static org.junit.Assert.*;

import org.junit.Test;

public class MessageKeyTest {

    @Test
    public void type() throws Exception {
        assertNotNull(MessageKey.class);
    }

    @Test
    public void instantiation() throws Exception {
        MessageKey target = new MessageKey();
        assertNotNull(target);
    }

}
