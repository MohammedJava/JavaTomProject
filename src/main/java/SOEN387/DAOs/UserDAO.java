package SOEN387.DAOs;


import SOEN387.configs.DatabaseConnection;
import SOEN387.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    private Connection connection;

    public UserDAO() {
        connection = DatabaseConnection.getConnection();
    }
    
    public void createUser(String username, String password) {
        String query = "INSERT INTO users (name, password, isAdmin) VALUES (?, ?, FALSE)";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean userExists(String username) {
        String query = "SELECT COUNT(*) AS count FROM users WHERE name = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt("count");
                    return count > 0; // If count > 0, a user with the username exists.
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // Return false if an error occurs or no user with the username is found.
    }


    public User findByUsername(String username) {
        User user = null;
        String query = "SELECT * FROM users WHERE name = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                	user = new User(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("email"),
                            resultSet.getString("password"),
                            resultSet.getBoolean("isAdmin")
                        );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
    
    public int getUserIDByUsername(String username) {
        String query = "SELECT id FROM users WHERE name = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1; // Return a negative value (e.g., -1) to indicate that the user was not found.
    }

    // Implement other methods for user-related operations, e.g., createUser, updateUser, deleteUser

    public void close() {
        DatabaseConnection.closeConnection();
    }
}