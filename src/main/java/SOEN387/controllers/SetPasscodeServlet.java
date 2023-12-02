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
import SOEN387.models.User;

import com.fasterxml.jackson.core.JsonProcessingException;

@WebServlet("/updatePasscode")
public class SetPasscodeServlet extends HttpServlet {
   private UserService userService;
   
   public SetPasscodeServlet() {
	   userService = new UserService();
   }
   
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException, JsonProcessingException {
	   RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/changePasscode.jsp");
       dispatcher.forward(request, response);
   }
   
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException, JsonProcessingException {
	     HttpSession session = request.getSession(false); 
	     String oldpasscode = "";
	     try {
			    if (session == null) {
			        throw new IllegalStateException("No session found");
			    }
			    oldpasscode = (String) session.getAttribute("passcode");
			    if (oldpasscode == null) {
			        throw new IllegalArgumentException("No user name found");
			    }
		
			} catch (IllegalStateException e) {
			    // Handle the case where no session is found
			    response.getWriter().write("Error: " + e.getMessage());
			} catch (IllegalArgumentException e) {
			    // Handle the case where no user name is found
			    response.getWriter().write("Error: " + e.getMessage());
		}
       String newpasscode = request.getParameter("Passcode");
       User user = userService.findByPasscode(oldpasscode);
       boolean condition = userService.setPasscode(user, newpasscode);
       if (condition) {
    	   
       }
       RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/changePasscode.jsp");
       dispatcher.forward(request, response);

   }
}
