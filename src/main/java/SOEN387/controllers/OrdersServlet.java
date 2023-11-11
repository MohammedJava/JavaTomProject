package SOEN387.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import SOEN387.models.Order;
import SOEN387.models.OrderItem;
import SOEN387.models.Product;
import SOEN387.models.User;
import SOEN387.services.CartService;
import SOEN387.services.OrderService;
import SOEN387.services.UserService;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/cart/orders")
public class OrdersServlet extends HttpServlet {
    private CartService cartService;
    private OrderService orderService;
    private UserService userService;

    public OrdersServlet() {
        cartService = new CartService();
        orderService = new OrderService();
        userService = new UserService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("name") == null) {
            response.sendRedirect("login");
            return;
        }

        String username = (String) session.getAttribute("name");

        List<Product> cartProducts = cartService.getCart(username);
        System.out.println("OrdersServlet: Retrieved cart for user: " + username + ", cart size: " + (cartProducts != null ? cartProducts.size() : "null"));
        if (cartProducts == null || cartProducts.isEmpty()) {
            response.sendRedirect("cart");
            return;
        }

        double totalPrice = calculateTotalPrice(cartProducts);
        List<OrderItem> orderItems = new ArrayList<>();
        for (Product product : cartProducts) {
            orderItems.add(new OrderItem(0, 0, product.getSku(), 1, product.getPrice()));
        }

        Order order = new Order(0, getUserId(username), new Timestamp(System.currentTimeMillis()), totalPrice, "Pending");
        if (cartProducts != null && !cartProducts.isEmpty()) {
            order.setOrderItems(orderItems);

            // Use the updated OrderService method that also takes the list of order items
            orderService.createOrder(order, orderItems); // Pass both the order and order items to be saved

            cartService.clearCart(username); // Clear cart after creating the order
            System.out.println("Cart cleared for user: " + username);

            request.setAttribute("order", order);
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
            response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
            response.setDateHeader("Expires", 0); // Proxies.

            RequestDispatcher dispatcher = request.getRequestDispatcher("/orderConfirmation.jsp");
            dispatcher.forward(request, response);
        }
        else{
            System.out.println("OrdersServlet: Cart Error or Empty Cart for user: " + username);
        }

    }

    private double calculateTotalPrice(List<Product> products) {
        double totalPrice = 0;
        for (Product product : products) {
            totalPrice += product.getPrice();
        }
        return totalPrice;
    }

    private int getUserId(String username) {
        User user = userService.findByUsername(username);
        return user != null ? user.getId() : -1;
    }
}
