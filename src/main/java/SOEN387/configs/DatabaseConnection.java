package SOEN387.configs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // JDBC URL, username, and password of MySQL server
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/servletecom";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "December2023";

    private static Connection connection = null;

    

    public static Connection getConnection() {
        if (connection == null) {
            try {
                // Register the JDBC driver
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Create a connection to the database
                connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
