package com.github.theimplementer.lucenefacetedsearch;

import org.glassfish.hk2.api.Factory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesFactory implements Factory<Properties> {

    private static final String PROPERTIES_FILE = "application.properties";

    @Override
    public Properties provide() {
        try (final InputStream propertiesInputStream = PropertiesFactory.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            final Properties properties = new Properties();
            properties.load(propertiesInputStream);
            return properties;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void dispose(Properties instance) {
    }
}
