package org.junithelper.core.config.extension;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class ExtArgPatternTest {

    @Test
    public void type() throws Exception {
        assertThat(ExtArgPattern.class, notNullValue());
    }

    @Test
    public void instantiation() throws Exception {
        ExtArg extArg = new ExtArg("com.example.Bean");
        String name = "null";
        ExtArgPattern target = new ExtArgPattern(extArg, name);
        assertThat(target, notNullValue());
    }

    @Test
    public void getNameWhichFirstCharIsUpper_A$() throws Exception {
        ExtArg extArg = new ExtArg("com.example.Bean");
        String name = "null";
        ExtArgPattern target = new ExtArgPattern(extArg, name);
        String actual = target.getNameWhichFirstCharIsUpper();
        String expected = "Null";
        assertThat(actual, is(equalTo(expected)));
    }

}
