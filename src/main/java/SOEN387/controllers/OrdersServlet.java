package SOEN387.controllers;

import SOEN387.models.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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

        String username = session != null ? (String) session.getAttribute("name") : null;
        int userId = 0; // Default user ID for anonymous orders


        List<CartItem> cartProducts = cartService.getCart(username);
        System.out.println("OrdersServlet: Retrieved cart for user: " + username + ", cart size: " + (cartProducts != null ? cartProducts.size() : "null"));
        if (cartProducts == null || cartProducts.isEmpty()) {
            response.sendRedirect("cart");
            return;
        }

        double totalPrice = calculateTotalPrice(cartProducts);
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem item : cartProducts) {
            orderItems.add(new OrderItem(0, 0, item.getProduct().getSku(), item.getQuantity(), item.getProduct().getPrice()));
        }


        String shippingAddress = request.getParameter("shippingAddress");

        Order order = new Order(0, getUserId(username), new Timestamp(System.currentTimeMillis()),
                totalPrice, "Pending", shippingAddress);

        if (cartProducts != null && !cartProducts.isEmpty()) {
            order.setOrderItems(orderItems);

            int orderId = orderService.createOrder(order, orderItems);

            cartService.clearCart(username);
            System.out.println("Cart cleared for user: " + username);

            request.setAttribute("order", order);
            request.setAttribute("orderId", orderId); // Pass orderId to the view
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
            response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
            response.setDateHeader("Expires", 0); // Proxies.

            RequestDispatcher dispatcher = request.getRequestDispatcher("/orderConfirmation.jsp");
            dispatcher.forward(request, response);
        } else {
            System.out.println("OrdersServlet: Cart Error or Empty Cart for user: " + username);
        }

    }

    private double calculateTotalPrice(List<CartItem> cartItem) {
        double totalPrice = 0;
        for (CartItem item : cartItem) {
            totalPrice += item.getProduct().getPrice();
        }
        return totalPrice;
    }

    private int getUserId(String username) {
        User user = userService.findByPasscode(username);
        return user != null ? user.getId() : -1;
    }
}
