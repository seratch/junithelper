package org.junithelper.core.config.extension;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;
import org.junithelper.core.exception.JUnitHelperCoreException;
import org.mockito.Mockito;
import org.xml.sax.Attributes;

public class ExtConfigurationParserHandlerTest {

    @Test
    public void type() throws Exception {
        assertThat(ExtConfigurationParserHandler.class, notNullValue());
    }

    @Test
    public void instantiation() throws Exception {
        ExtConfigurationParserHandler target = new ExtConfigurationParserHandler();
        assertThat(target, notNullValue());
    }

    @Test
    public void getExtConfiguration_A$() throws Exception {
        ExtConfigurationParserHandler target = new ExtConfigurationParserHandler();
        ExtConfiguration actual = target.getExtConfiguration();
        ExtConfiguration expected = null;
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void characters_A$charArray$int$int() throws Exception {
        ExtConfigurationParserHandler target = new ExtConfigurationParserHandler();
        char[] ch = new char[] {};
        int start = 0;
        int length = 0;
        target.characters(ch, start, length);
    }

    @Test
    public void characters_A$charArray$int$int_intIsMinus1() throws Exception {
        ExtConfigurationParserHandler target = new ExtConfigurationParserHandler();
        char[] ch = new char[] {};
        int start = -1;
        int length = -1;
        try {
            target.characters(ch, start, length);
            fail();
        } catch (StringIndexOutOfBoundsException e) {
        }
    }

    @Test
    public void characters_A$charArray$int$int_intIs0() throws Exception {
        ExtConfigurationParserHandler target = new ExtConfigurationParserHandler();
        char[] ch = new char[] { 1, 2, 3, 4, 5 };
        int start = 0;
        int length = 0;
        target.characters(ch, start, length);
    }

    @Test
    public void characters_A$charArray$int$int_intIs1() throws Exception {
        ExtConfigurationParserHandler target = new ExtConfigurationParserHandler();
        char[] ch = new char[] { 1, 2, 3, 4, 5 };
        int start = 1;
        int length = 1;
        target.characters(ch, start, length);
    }

    @Test
    public void characters_A$charArray$int$int_intIs2() throws Exception {
        ExtConfigurationParserHandler target = new ExtConfigurationParserHandler();
        char[] ch = new char[] { 1, 2, 3, 4, 5 };
        int start = 2;
        int length = 2;
        target.characters(ch, start, length);
    }

    @Test
    public void startElement_A$String$String$String$Attributes() throws Exception {
        ExtConfigurationParserHandler target = new ExtConfigurationParserHandler();
        String uri = "uri";
        String localName = "localName";
        String name = "arg";
        Attributes attributes = Mockito.mock(Attributes.class);
        target.startElement(uri, localName, name, attributes);
    }

    @Test
    public void startElement_A$String$String$String$Attributes_StringIsNull() throws Exception {
        ExtConfigurationParserHandler target = new ExtConfigurationParserHandler();
        String uri = null;
        String localName = null;
        String name = null;
        Attributes attributes = Mockito.mock(Attributes.class);
        try {
            target.startElement(uri, localName, name, attributes);
            fail();
        } catch (JUnitHelperCoreException e) {
        }
    }

    @Test
    public void startElement_A$String$String$String$Attributes_StringIsEmpty() throws Exception {
        ExtConfigurationParserHandler target = new ExtConfigurationParserHandler();
        String uri = "";
        String localName = "";
        String name = "";
        Attributes attributes = Mockito.mock(Attributes.class);
        try {
            target.startElement(uri, localName, name, attributes);
            fail();
        } catch (JUnitHelperCoreException e) {
        }
    }

    @Test
    public void endElement_A$String$String$String() throws Exception {
        ExtConfigurationParserHandler target = new ExtConfigurationParserHandler();
        target.startElement("", "", "junithelper-extension", null);
        String uri = "uri";
        String localName = "localName";
        String name = "arg";
        target.endElement(uri, localName, name);
    }

    @Test
    public void endElement_A$String$String$String_StringIsNull() throws Exception {
        ExtConfigurationParserHandler target = new ExtConfigurationParserHandler();
        String uri = null;
        String localName = null;
        String name = null;
        try {
            target.endElement(uri, localName, name);
            fail();
        } catch (JUnitHelperCoreException e) {
        }
    }

    @Test
    public void endElement_A$String$String$String_StringIsEmpty() throws Exception {
        ExtConfigurationParserHandler target = new ExtConfigurationParserHandler();
        String uri = "";
        String localName = "";
        String name = "";
        try {
            target.endElement(uri, localName, name);
            fail();
        } catch (JUnitHelperCoreException e) {
        }
    }

    @Test
    public void characters_A$charArray$int$int_intIsMinValue() throws Exception {
        ExtConfigurationParserHandler target = new ExtConfigurationParserHandler();
        char[] ch = new char[] { 'a', 'b', 'c' };
        int start = Integer.MIN_VALUE;
        int length = Integer.MIN_VALUE;
        try {
            target.characters(ch, start, length);
            fail();
        } catch (StringIndexOutOfBoundsException e) {
        }
    }

    @Test
    public void characters_A$charArray$int$int_intIsMaxValue() throws Exception {
        ExtConfigurationParserHandler target = new ExtConfigurationParserHandler();
        char[] ch = new char[] { 'a', 'b', 'c' };
        int start = Integer.MAX_VALUE;
        int length = Integer.MAX_VALUE;
        try {
            target.characters(ch, start, length);
            fail();
        } catch (StringIndexOutOfBoundsException e) {
        }
    }

    @Test
    public void characters_A$charArray$int$int_intIsRandom() throws Exception {
        ExtConfigurationParserHandler target = new ExtConfigurationParserHandler();
        char[] ch = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k' };
        int start = new Random().nextInt(5);
        int length = new Random().nextInt(5);
        target.characters(ch, start, length);
    }

}
