package SOEN387.controllers;

import java.io.IOException;

import java.util.HashMap;

import SOEN387.models.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import SOEN387.services.UserService;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {

   private HashMap<String, Boolean> passcodes;
   private UserService userService;
   
    public LoginServlet() {
    	userService = new UserService();
        passcodes = userService.getAllUsers();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward the request to the login.jsp page
        RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String passcode = request.getParameter("passcode");

        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(1800); // Set the session timeout to 30 minutes (1800 seconds)

        try {
            User user = userService.findByPasscode(passcode);

            if (user != null) {
                session.setAttribute("passcode", passcode);
                if (user.isStaff()) {
                    session.setAttribute("role", "staff");
                } else {
                    session.setAttribute("name", passcode);
                }
                response.sendRedirect("/JavaTomProject_war_exploded/products");
            } else {
                response.sendRedirect("/login?error=true");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing your request.");
        }
    }



}





