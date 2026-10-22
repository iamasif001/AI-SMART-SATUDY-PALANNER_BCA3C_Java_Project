
package com.smartstudy.app.config;

public class DatabaseConfig {

    private static final String URL =
            "jdbc:oracle:thin:@localhost:1521/XEPDB1";

    private static final String USER =
            "STUDYPLANNER";

    private static final String PASSWORD =
            System.getenv("STUDYPLANNER_DB_PASSWORD");

    public static java.sql.Connection getConnection()
            throws java.sql.SQLException {

        if (PASSWORD == null || PASSWORD.isBlank()) {
            throw new java.sql.SQLException(
                    "STUDYPLANNER_DB_PASSWORD environment variable is not set."
            );
        }

        return java.sql.DriverManager.getConnection(
                URL,
                USER,
                PASSWORD
        );
    }
}
