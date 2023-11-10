package SOEN387.models;

public class OrderItem {
    private int id;
    private int orderId;
    private String productSku;
    private int quantity;
    private double price;

    public OrderItem(int id, int orderId, String productSku, int quantity, double price) {
        this.id = id;
        this.orderId = orderId;
        this.productSku = productSku;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getProductSku() {
        return productSku;
    }

    public void setProductSku(String productSku) {
        this.productSku = productSku;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // Override toString() if needed for debugging
    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", productSku=" + productSku +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
