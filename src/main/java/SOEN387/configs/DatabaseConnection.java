package SOEN387.configs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String DB_FILE_PATH = "C:\\Users\\Mohammed\\IdeaProjects\\JavaTomProject\\database.db";

    public static Connection getConnection() {
        try {
            Class.forName("org.sqlite.JDBC");

            String jdbcUrl = "jdbc:sqlite:" + DB_FILE_PATH;

            return DriverManager.getConnection(jdbcUrl);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
