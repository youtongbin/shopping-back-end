package com.ytb.shopping.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtils {

    private static Properties properties = new Properties();

    static {

        InputStream inputStream =Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties");

        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String readByKey(String key){
        return properties.getProperty(key);
    }
}
