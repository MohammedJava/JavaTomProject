package SOEN387.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import SOEN387.models.Order;
import SOEN387.services.OrderService;
import SOEN387.services.UserService;

import java.io.IOException;
import java.util.List;
@WebServlet("/shipOrder/*")
public class ShipOrdersServlet extends HttpServlet {
    private OrderService orderService;

    public ShipOrdersServlet() {
        orderService = new OrderService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo != null && pathInfo.length() > 1) {
            int orderId = Integer.parseInt(pathInfo.substring(1));
            // Implement the logic to set the tracking number and update the order status
            String trackingNumber = "someTrackingNumber"; // This should come from somewhere else
            orderService.shipOrder(orderId, trackingNumber);
            response.sendRedirect("allOrders"); // Redirect to the all orders page
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid order ID");
        }
    }
}
