package SOEN387.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import SOEN387.services.OrderService;

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
            try {
                int orderId = Integer.parseInt(pathInfo.substring(1));

                String trackingNumber = "TRK" + orderId;

                orderService.shipOrder(orderId, trackingNumber);

                // Redirect to a confirmation page or back to the order list
                response.sendRedirect(request.getContextPath() + "/allOrders");
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid order ID format");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Order ID not provided");
        }
    }
}
