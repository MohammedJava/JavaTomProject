package SOEN387.DAOs;

import SOEN387.configs.DatabaseConnection;
import SOEN387.models.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CartDAO {
    private Connection connection;

    public CartDAO() {
        connection = DatabaseConnection.getConnection();
    }

    public void addToCart(int userId, int productId) {
        String queryCheck = "SELECT quantity FROM cart WHERE user_id = ? AND product_id = ?";
        String queryUpdate = "UPDATE cart SET quantity = quantity + 1 WHERE user_id = ? AND product_id = ?";
        String queryInsert = "INSERT INTO cart (user_id, product_id, quantity) VALUES (?, ?, 1)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement checkStmt = connection.prepareStatement(queryCheck)) {

            checkStmt.setInt(1, userId);
            checkStmt.setInt(2, productId);

            try (ResultSet resultSet = checkStmt.executeQuery()) {
                if (resultSet.next()) {
                    // Product already in cart, update quantity
                    try (PreparedStatement updateStmt = connection.prepareStatement(queryUpdate)) {
                        updateStmt.setInt(1, userId);
                        updateStmt.setInt(2, productId);
                        updateStmt.executeUpdate();
                    }
                } else {
                    // Product not in cart, insert new row
                    try (PreparedStatement insertStmt = connection.prepareStatement(queryInsert)) {
                        insertStmt.setInt(1, userId);
                        insertStmt.setInt(2, productId);
                        insertStmt.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Product> getCartItems(int userId) {
        List<Product> cartItems = new ArrayList<>();
        String query = "SELECT p.* FROM products p " +
                "INNER JOIN cart c ON p.id = c.product_id " +
                "WHERE c.user_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Product product = new Product(
                            resultSet.getString("name"),
                            resultSet.getString("description"),
                            resultSet.getString("vendor"),
                            resultSet.getString("urlSlug"),
                            resultSet.getString("sku"),
                            resultSet.getDouble("price"),
                            resultSet.getString("image")
                    );
                    cartItems.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cartItems;
    }


    public void UpdateQuantity(int userId, int productId, int Quantity) {
        String query = "UPDATE cart SET quantity = ? WHERE user_id = ? AND product_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, Quantity);
            statement.setInt(2, userId);
            statement.setInt(3, productId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void removeFromCart(int userId, int productId) {
        String query = "DELETE FROM cart WHERE user_id = ? AND product_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.setInt(2, productId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clearCartItems(int userId) {
        String sql = "DELETE FROM cart WHERE user_id = ?"; // Update table name to 'cart'
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions properly
        }
    }

    // Limit to one item
    public void removeSingleItemFromCart(int userId, int productId) {
        String query = "DELETE FROM cart WHERE user_id = ? AND product_id = ? LIMIT 1";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.setInt(2, productId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
