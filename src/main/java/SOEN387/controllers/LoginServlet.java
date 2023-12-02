package SOEN387.controllers;

import java.io.IOException;

import java.util.HashMap;

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
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/login.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
    		              throws ServletException, IOException {
    	
        String passcode = request.getParameter("passcode");

        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(1800); // Set the session timeout to 30 minutes (1800 seconds)

        if (passcode == null || passcode.equals("")) {
            response.sendRedirect("/error");
        }
        if (passcodes.containsKey(passcode) && passcodes.get(passcode)) {
            session.setAttribute("passcode", passcode);
            session.setAttribute("role", "staff");
            response.sendRedirect("/JavaECOM/products");  
        } 

        else if (passcodes.containsKey(passcode) && !(passcodes.get(passcode))) {
            session.setAttribute("name", passcode);
            response.sendRedirect("/JavaECOM/products");
        } else {
            response.sendRedirect("/login?error=true");
        }
    }


}





