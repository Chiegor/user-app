package ru.app.userapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertiesProvider2 {

    private static final Logger log = LoggerFactory.getLogger(PropertiesProvider2.class);
    private static final Properties appProperties = new Properties();

    static {
        try {
            appProperties.load(new FileInputStream("src/main/resources/application.properties"));
            log.info("loaded application properties");
        } catch (IOException e) {
            throw new RuntimeException("error loading properties", e);
        }
    }

    // defence copy
    public static Properties getProperties() {
        return new Properties(appProperties);
    }
}
