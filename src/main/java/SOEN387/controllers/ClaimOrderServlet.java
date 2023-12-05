package SOEN387.controllers;

import SOEN387.models.Order;
import SOEN387.services.OrderService;
import SOEN387.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/claimOrder")
public class ClaimOrderServlet extends HttpServlet {
    private OrderService orderService;
    private UserService userService;

    public ClaimOrderServlet() {
        orderService = new OrderService();
        userService = new UserService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("name");
        int userId = userService.findByPasscode(username).getId();
        int orderId = Integer.parseInt(request.getParameter("orderId"));

        Order order = orderService.getOrderById(orderId);
        if (order != null && ((Order) order).getUserId() == -1) {
            orderService.setOrderOwner(orderId, userId);
            response.sendRedirect("orderClaimed.jsp");
        } else {
            // Handle error (order doesn't exist or already claimed)
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid order ID or order already claimed");
        }
    }
}
