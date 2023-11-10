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

    public void createOrder(Order order, List<OrderItem> items) {
        order.setOrderItems(items); // Make sure the items are set
        orderDAO.createOrder(order);
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

}
