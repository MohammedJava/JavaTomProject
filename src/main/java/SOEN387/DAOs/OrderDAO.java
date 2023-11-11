package SOEN387.DAOs;

import SOEN387.configs.DatabaseConnection;
import SOEN387.models.Order;
import SOEN387.models.OrderItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    public OrderDAO() {
    }

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT id, user_id, order_date, total_price, status FROM orders";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Order order = new Order(resultSet.getInt("id"), resultSet.getInt("user_id"),
                        resultSet.getTimestamp("order_date"), resultSet.getDouble("total_price"),
                        resultSet.getString("status"));

                order.setOrderItems(getOrderItemsByOrderId(order.getId()));
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public List<OrderItem> getOrderItemsByOrderId(int orderId) {
        List<OrderItem> orderItems = new ArrayList<>();
        String query = "SELECT id, order_id, product_sku, quantity, price FROM order_items WHERE order_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    OrderItem item = new OrderItem(resultSet.getInt("id"), orderId, resultSet.getString("product_sku"),
                            resultSet.getInt("quantity"), resultSet.getDouble("price"));
                    orderItems.add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderItems;
    }

    public void createOrder(Order order) {
        String insertOrderQuery = "INSERT INTO orders (user_id, total_price, status) VALUES (?, ?, ?)";
        String insertItemQuery = "INSERT INTO order_items (order_id, product_sku, quantity, price) VALUES (?, ?, ?, ?)";

        Connection conn = null;

        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // Start transaction

            // Insert the order
            try (PreparedStatement orderStmt = conn.prepareStatement(insertOrderQuery, Statement.RETURN_GENERATED_KEYS)) {
                orderStmt.setInt(1, order.getUserId());
                orderStmt.setDouble(2, order.getTotalPrice());
                orderStmt.setString(3, order.getStatus());
                int affectedRows = orderStmt.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Creating order failed, no rows affected.");
                }

                try (ResultSet generatedKeys = orderStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        order.setId(generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("Creating order failed, no ID obtained.");
                    }
                }
            }

            // Insert the order items
            try (PreparedStatement itemStmt = conn.prepareStatement(insertItemQuery)) {
                for (OrderItem item : order.getOrderItems()) {
                    itemStmt.setInt(1, order.getId());
                    itemStmt.setString(2, item.getProductSku());
                    itemStmt.setInt(3, item.getQuantity());
                    itemStmt.setDouble(4, item.getPrice());
                    itemStmt.addBatch();
                }
                itemStmt.executeBatch();
            }

            conn.commit(); // Commit transaction
            System.out.println("Order created successfully.");
        } catch (SQLException e) {
            System.out.println("Error creating order: " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback(); // Rollback transaction on error
                    System.out.println("Transaction rolled back due to error.");
                } catch (SQLException ex) {
                    System.out.println("Error rolling back transaction: " + ex.getMessage());
                }
            }
            throw new RuntimeException(e);
        } finally {
            // Close the connection in the finally block
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.out.println("Error closing connection: " + e.getMessage());
                }
            }
        }
    }


    public void updateOrder(Order order) {
        String query = "UPDATE orders SET user_id = ?, total_price = ?, status = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, order.getUserId());
            statement.setDouble(2, order.getTotalPrice());
            statement.setString(3, order.getStatus());
            statement.setInt(4, order.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteOrder(int orderId) {
        String query = "DELETE FROM orders WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderId);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Order getOrderById(int orderId) {
        String query = "SELECT id, user_id, order_date, total_price, status FROM orders WHERE id = ?";
        Order order = null;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    order = new Order(resultSet.getInt("id"), resultSet.getInt("user_id"),
                            resultSet.getTimestamp("order_date"), resultSet.getDouble("total_price"),
                            resultSet.getString("status"));

                    order.setOrderItems(getOrderItemsByOrderId(order.getId()));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    public List<Order> getOrdersByUserId(int userId) {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT id, user_id, order_date, total_price, status FROM orders WHERE user_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Order order = new Order(
                            resultSet.getInt("id"),
                            userId,
                            resultSet.getTimestamp("order_date"),
                            resultSet.getDouble("total_price"),
                            resultSet.getString("status")
                    );
                    order.setOrderItems(getOrderItemsByOrderId(order.getId()));
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public Order getOrderByIdAndUserId(int orderId, int userId) throws SQLException {
        String query = "SELECT * FROM orders WHERE id = ? AND user_id = ?";
        Order order = null;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderId);
            statement.setInt(2, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    order = new Order(
                            resultSet.getInt("id"),
                            resultSet.getInt("user_id"),
                            resultSet.getTimestamp("order_date"),
                            resultSet.getDouble("total_price"),
                            resultSet.getString("status")
                    );
                    order.setOrderItems(getOrderItemsByOrderId(order.getId()));
                } else {
                    throw new SQLException("Order not found or does not belong to the user.");
                }
            }
        }
        return order;
    }

    public void shipOrder(int orderId, String trackingNumber) {
        String query = "UPDATE orders SET status = 'Shipped', tracking_number = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, trackingNumber);
            statement.setInt(2, orderId);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Shipping order failed, no rows affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
