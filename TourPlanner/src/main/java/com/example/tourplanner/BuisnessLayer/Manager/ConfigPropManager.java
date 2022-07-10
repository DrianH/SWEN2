package com.example.tourplanner.BuisnessLayer.Manager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigPropManager {

    public static String getConfigProp(String name) {
        Properties props = new Properties();
        InputStream is = ConfigPropManager.class.getClassLoader().getResourceAsStream("config.properties");

        if(is != null) {
            try {
                props.load(is);
                return props.getProperty(name);
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}
