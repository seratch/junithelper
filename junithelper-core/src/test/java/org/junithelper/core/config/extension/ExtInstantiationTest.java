package org.junithelper.core.config.extension;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class ExtInstantiationTest {

    @Test
    public void type() throws Exception {
        assertThat(ExtInstantiation.class, notNullValue());
    }

    @Test
    public void instantiation() throws Exception {
        String canonicalClassName = "com.example.Bean";
        ExtInstantiation target = new ExtInstantiation(canonicalClassName);
        assertThat(target, notNullValue());
    }

    @Test
    public void getCanonicalClassNameInMethodName_A$() throws Exception {
        String canonicalClassName = "com.example.Bean";
        ExtInstantiation target = new ExtInstantiation(canonicalClassName);
        String actual = target.getCanonicalClassNameInMethodName();
        String expected = "ceBean";
        assertThat(actual, is(equalTo(expected)));
    }

}
