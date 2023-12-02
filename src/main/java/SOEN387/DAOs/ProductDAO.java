package SOEN387.DAOs;

import SOEN387.configs.DatabaseConnection;
import SOEN387.models.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public ProductDAO() {
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT name, description, vendor, urlSlug, sku, price, image FROM products";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
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
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("ProductDAO: Fetched " + products.size() + " products.");
        return products;
    }

    public int getProductIDByName(String productName) {
        String query = "SELECT id FROM products WHERE name = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, productName);

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


    public Product getProduct(String sku) {
        String query = "SELECT name, description, vendor, urlSlug, sku, price, image FROM products WHERE sku = ?";
        Product product = null;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, sku);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    product = new Product(
                            resultSet.getString("name"),
                            resultSet.getString("description"),
                            resultSet.getString("vendor"),
                            resultSet.getString("urlSlug"),
                            resultSet.getString("sku"),
                            resultSet.getDouble("price"),
                            resultSet.getString("image")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return product;
    }

    public Product getProductByUrlSlug(String urlSlug) {
        String query = "SELECT name, description, vendor, urlSlug, sku, price, image FROM products WHERE urlSlug = ?";
        Product product = null;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, urlSlug);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    product = new Product(
                            resultSet.getString("name"),
                            resultSet.getString("description"),
                            resultSet.getString("vendor"),
                            resultSet.getString("urlSlug"),
                            resultSet.getString("sku"),
                            resultSet.getDouble("price"),
                            resultSet.getString("image")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return product;
    }


    public void createProduct(Product product) {
        String query = "INSERT INTO products (name, description, vendor, urlSlug, sku, price, image) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setString(3, product.getVendor());
            statement.setString(4, product.getUrlSlug());
            statement.setString(5, product.getSku());
            statement.setDouble(6, product.getPrice());
            statement.setString(7, product.getImage());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void updateProduct(Product product) {
        String query = "UPDATE products SET name = ?, description = ?, vendor = ?, price = ?, image = ? WHERE sku = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setString(3, product.getVendor());
            statement.setDouble(4, product.getPrice());
            statement.setString(5, product.getImage());
            statement.setString(6, product.getSku());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteProductBySku(String sku) {
        String query = "DELETE FROM products WHERE sku = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, sku);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

