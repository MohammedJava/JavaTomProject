package SOEN387.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import SOEN387.models.Order;
import SOEN387.services.OrderService;

import java.io.IOException;

@WebServlet("/order/*")
public class OrderDetailsServlet extends HttpServlet {
    private OrderService orderService;

    public OrderDetailsServlet() {
        orderService = new OrderService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo != null) {
            // Assumes path /{orderId}
            int orderId = Integer.parseInt(pathInfo.substring(1));
            Order order = orderService.getOrderById(orderId);
            request.setAttribute("order", order);
            request.getRequestDispatcher("/orderDetail.jsp").forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Similar to doPost, but for updating an existing order
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo != null) {
            int orderId = Integer.parseInt(pathInfo.substring(1));
            orderService.deleteOrder(orderId);
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
