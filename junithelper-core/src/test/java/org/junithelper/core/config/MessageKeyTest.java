package org.junithelper.core.config;

import org.junit.Test;

import static org.junit.Assert.*;

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
