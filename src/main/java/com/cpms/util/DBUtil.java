package com.cpms.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBUtil {
    private static final String DEFAULT_URL = "jdbc:mysql://localhost:3306/cpms_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String DEFAULT_USER = "root";
    private static final String DEFAULT_PASSWORD = "";

    private DBUtil() {
    }

    public static Connection getConnection() throws SQLException {
        String url = value("CPMS_DB_URL", "cpms.db.url", DEFAULT_URL);
        String user = value("CPMS_DB_USER", "cpms.db.user", DEFAULT_USER);
        String password = value("CPMS_DB_PASSWORD", "cpms.db.password", DEFAULT_PASSWORD);
        return DriverManager.getConnection(url, user, password);
    }

    private static String value(String envName, String propertyName, String defaultValue) {
        String property = System.getProperty(propertyName);
        if (property != null && !property.isBlank()) {
            return property;
        }
        String env = System.getenv(envName);
        if (env != null && !env.isBlank()) {
            return env;
        }
        return defaultValue;
    }
}
