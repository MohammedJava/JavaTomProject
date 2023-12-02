package SOEN387.controllers;

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

@WebServlet("/customer/orders")
public class CustomerOrdersServlet extends HttpServlet {
    private OrderService orderService;
    private UserService userService;

    public CustomerOrdersServlet() {
        orderService = new OrderService();
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("name") == null) {
            response.sendRedirect("login");
            return;
        }

        String username = (String) session.getAttribute("name");
        SOEN387.models.User user = userService.findByPasscode(username);
        if (user == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found");
            return;
        }

        List<Order> orders = orderService.getOrdersByUserId(user.getId());
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("/customerOrders.jsp").forward(request, response);
    }
}
