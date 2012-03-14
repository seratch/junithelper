package org.junithelper.plugin.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junithelper.plugin.constant.Preference;

public class PropertiesLoader {

    private Properties props = new Properties();

    public PropertiesLoader(String lang) {
        if (lang == null || lang.equals("")) {
            lang = Preference.Lang.English;
        }
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("application_" + lang + ".properties");
        try {
            props.load(is);
        } catch (IOException e) {
            try {
                props.load(this.getClass().getClassLoader().getResourceAsStream("application_en.properties"));
            } catch (Exception e2) {
            }
        }
    }

    public String get(String key) {
        String value = props.getProperty(key);
        if (value == null)
            value = "";
        return value;
    }

}
