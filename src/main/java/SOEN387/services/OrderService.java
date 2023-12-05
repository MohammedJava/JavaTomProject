package SOEN387.services;

import SOEN387.DAOs.OrderDAO;
import SOEN387.models.Order;
import SOEN387.models.OrderItem;

import java.util.List;

public class OrderService {
    private OrderDAO orderDAO;

    public OrderService() {
        orderDAO = new OrderDAO();
    }

    public List<Order> getAllOrders() {
        return orderDAO.getAllOrders();
    }

    public Order getOrderById(int orderId) {
        return orderDAO.getOrderById(orderId);
    }

    public int createOrder(Order order, List<OrderItem> items) {
        order.setOrderItems(items);
        System.out.println("Creating order for user ID: " + order.getUserId());
        return orderDAO.createOrder(order);
    }
    public void updateOrder(Order order) {
        orderDAO.updateOrder(order);
    }

    public void deleteOrder(int orderId) {
        orderDAO.deleteOrder(orderId);
    }
    
    public void shipOrder(int orderId, String trackingNumber) {
        orderDAO.shipOrder(orderId, trackingNumber);
    }

    public List<Order> getOrdersByUserId(int userId) {
        return orderDAO.getOrdersByUserId(userId);
    }

    public void setOrderOwner(int orderId, int userId) {
        Order order = getOrderById(orderId);
        if (order != null) {
            if (order.getUserId() == -1) {
                order.setUserId(userId);
                updateOrder(order);
            } else {
                // Handle the case where the order is already claimed
                throw new IllegalArgumentException("Order is already claimed");
            }
        } else {
            // Handle the case where the order doesn't exist
            throw new IllegalArgumentException("Order not found");
        }
    }
}
