package org.junithelper.core.config.extension;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class ExtReturnTest {

    @Test
    public void type() throws Exception {
        assertThat(ExtReturn.class, notNullValue());
    }

    @Test
    public void instantiation() throws Exception {
        String canonicalClassName = "com.example.Bean";
        ExtReturn target = new ExtReturn(canonicalClassName);
        assertThat(target, notNullValue());
    }

}
