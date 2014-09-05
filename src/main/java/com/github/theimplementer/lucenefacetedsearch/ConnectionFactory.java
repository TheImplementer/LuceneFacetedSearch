package com.github.theimplementer.lucenefacetedsearch;

import org.glassfish.hk2.api.Factory;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory implements Factory<Connection> {

    private static final String DATABASE_DRIVER_PROPERTY = "database.driver";
    private static final String DATABASE_URL_PROPERTY = "database.url";
    private static final String DATABASE_USERNAME_PROPERTY = "database.username";
    private static final String DATABASE_PASSWORD_PROPERTY = "database.password";

    private final Properties properties;

    @Inject
    public ConnectionFactory(Properties properties) {
        this.properties = properties;
    }

    @Override
    public Connection provide() {
        try {
            Class.forName(properties.getProperty(DATABASE_DRIVER_PROPERTY));
            final String databaseUrl = properties.getProperty(DATABASE_URL_PROPERTY);
            final String username = properties.getProperty(DATABASE_USERNAME_PROPERTY);
            final String password = properties.getProperty(DATABASE_PASSWORD_PROPERTY);
            return DriverManager.getConnection(databaseUrl, username, password);
        } catch (Exception e) {
            throw new RuntimeException("not done yet");
        }
    }

    @Override
    public void dispose(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
