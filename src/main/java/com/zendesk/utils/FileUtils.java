package com.zendesk.utils;

import java.io.IOException;
import java.util.Properties;

public class FileUtils {

    public static String getProperty(String propertyFileName,String property) throws IOException{
        Properties properties = new Properties();
        properties.load(FileUtils.class.getClassLoader().getResourceAsStream(propertyFileName));
        return properties.getProperty(property);
    }


}
