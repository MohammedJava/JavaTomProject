package SOEN387.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import SOEN387.configs.DatabaseConnection;
import SOEN387.models.User;

public class UserDAO {
    private Connection connection;

    public UserDAO() {
        connection = DatabaseConnection.getConnection();
    }

    public void createUser(String passcode, boolean isStaff) {
        String query = "INSERT INTO users (passcode, isAdmin) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, passcode);
            statement.setBoolean(2, isStaff);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean userExists(String passcode) {
        String query = "SELECT COUNT(*) AS count FROM users WHERE passcode = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, passcode);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt("count");
                    return count > 0; 
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; 
    }

    public boolean changePasscode(String oldPasscode, String newPasscode) {
        String query = "UPDATE users SET passcode = ? WHERE passcode = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, newPasscode);
            statement.setString(2, oldPasscode);

            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String query = "SELECT id, passcode, isAdmin FROM users";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                User user = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("passcode"),
                        resultSet.getBoolean("isAdmin")
                );
                userList.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userList;
    }
    
    public List<User> getAllCustomers() {
        List<User> userList = new ArrayList<>();
        String query = "SELECT id, passcode, isAdmin FROM users WHERE isAdmin = false";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                User user = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("passcode"),
                        resultSet.getBoolean("isAdmin")
                );
                userList.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userList;
    }


    public User findByPasscode(String passcode) {
        User user = null;
        String query = "SELECT id, passcode, isAdmin FROM users WHERE passcode = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, passcode);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    user = new User(
                            resultSet.getInt("id"),
                            resultSet.getString("passcode"),
                            resultSet.getBoolean("isAdmin")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
    public boolean GrantStaffPrivilege(int id, boolean isAdmin) {
        String query = "UPDATE users SET isAdmin = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setBoolean(1, isAdmin);
            statement.setInt(2, id);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace(); 
            return false;
        }
    }
    
    
    public List<User> getAllUsersExceptSelf(String passcode) {
        List<User> userList = new ArrayList<>();
        String query = "SELECT id, passcode, isAdmin FROM users WHERE passcode <> ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, passcode);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    User user = new User(
                            resultSet.getInt("id"),
                            resultSet.getString("passcode"),
                            resultSet.getBoolean("isAdmin")
                    );
                    userList.add(user);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userList;
    }

    public int getUserIDByPasscode(String passcode) {
        String query = "SELECT id FROM users WHERE passcode = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, passcode);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1; 
    }

}
