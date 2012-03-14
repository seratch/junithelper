package org.junithelper.core.meta;

import static org.junit.Assert.*;

import org.junit.Test;

public class AccessModifierTest {

    @Test
    public void type() throws Exception {
        assertNotNull(AccessModifier.class);
    }

    @Test
    public void toString_A$() throws Exception {
        // given
        // e.g. : given(mocked.called()).willReturn(1);
        // when
        String actual = AccessModifier.PackageLocal.toString();
        // then
        // e.g. : verify(mocked).called();
        String expected = "";
        assertEquals(expected, actual);
    }

}
