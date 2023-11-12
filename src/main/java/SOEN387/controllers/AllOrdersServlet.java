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

@WebServlet("/allOrders")
public class AllOrdersServlet extends HttpServlet {
    private OrderService orderService;

    public AllOrdersServlet() {
        orderService = new OrderService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Order> orders = orderService.getAllOrders();
        request.setAttribute("orders", orders);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/allOrders.jsp");
        dispatcher.forward(request, response);
    }
}
