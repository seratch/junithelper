package org.junithelper.core.util;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

public class UniversalDetectorUtilTest {

    @Test
    public void type() throws Exception {
        assertNotNull(UniversalDetectorUtil.class);
    }

    @Test
    public void getDetectedEncoding_A$InputStream_UTF8() throws Exception {
        // given
        String name = "UTF-8.txt";
        InputStream is = IOUtil.getResourceAsStream(name);
        // when
        String actual = UniversalDetectorUtil.getDetectedEncoding(is);
        // then
        String expected = "UTF-8";
        assertEquals(expected, actual);
    }

    @Test
    public void getDetectedEncoding_A$InputStream_Shift_JIS() throws Exception {
        // given
        String name = "Shift_JIS.txt";
        InputStream is = IOUtil.getResourceAsStream(name);
        // when
        String actual = UniversalDetectorUtil.getDetectedEncoding(is);
        // then
        String expected = "SHIFT_JIS";
        assertEquals(expected, actual);
    }

    @Test
    public void getDetectedEncoding_A$InputStream_T$IOException() throws Exception {
        // given
        InputStream is = mock(InputStream.class);
        // e.g. : given(mocked.called()).willReturn(1);
        when(is.read(any(byte[].class))).thenThrow(new IOException());
        try {
            // when
            UniversalDetectorUtil.getDetectedEncoding(is);
            fail("Expected exception was not thrown!");
        } catch (IOException e) {
            // then
        }
    }

    @Test
    public void getDetectedEncoding_A$File() throws Exception {
        // given
        File file = new File("release/junithelper-config.properties");
        // e.g. : given(mocked.called()).willReturn(1);
        // when
        String actual = UniversalDetectorUtil.getDetectedEncoding(file);
        // then
        // e.g. : verify(mocked).called();
        String expected = null;
        assertEquals(expected, actual);
    }

    @Test
    public void getDetectedEncoding_A$File_T$IOException() throws Exception {
        // given
        File file = mock(File.class);
        // e.g. : given(mocked.called()).willReturn(1);
        try {
            // when
            UniversalDetectorUtil.getDetectedEncoding(file);
            fail("Expected exception was not thrown!");
        } catch (IOException e) {
            // then
        }
    }

}
