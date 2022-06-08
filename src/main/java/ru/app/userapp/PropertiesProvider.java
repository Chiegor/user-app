package ru.app.userapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesProvider {

    private static final Logger log = LoggerFactory.getLogger(PropertiesProvider.class);
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
