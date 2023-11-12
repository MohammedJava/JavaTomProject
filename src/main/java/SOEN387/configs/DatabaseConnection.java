package SOEN387.configs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // JDBC URL, username, and password of MySQL server
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/servletecom";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "December2023";

    public static Connection getConnection() {
        try {
            // Register the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Return a new connection
            return DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null; // Return null in case of failure
        }
    }
}
