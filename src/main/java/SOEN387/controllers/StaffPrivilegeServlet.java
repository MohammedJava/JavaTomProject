package SOEN387.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import SOEN387.models.User;
import SOEN387.services.UserService;

/**
 * Servlet implementation class StaffPrivilegeServlet
 */
@WebServlet("/grantStaff")
public class StaffPrivilegeServlet extends HttpServlet {
       
    private UserService userService;
    private List<User> customers;
    public StaffPrivilegeServlet() {
    	userService = new UserService();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 HttpSession session = request.getSession(); 
	     String passcode = "";

	     try {
			    if (session == null) {
			        throw new IllegalStateException("No session found");
			    }
			    passcode = (String) session.getAttribute("passcode");
			    if (passcode == null) {
			        throw new IllegalArgumentException("No user name found");
			    }
		
			} catch (IllegalStateException e) {
			    // Handle the case where no session is found
			    response.getWriter().write("Error: " + e.getMessage());
			} catch (IllegalArgumentException e) {
			    // Handle the case where no user name is found
			    response.getWriter().write("Error: " + e.getMessage());
		}
	     
		customers = userService.getAllUsersExceptSelf(passcode);
        
		request.setAttribute("customers", customers);
        RequestDispatcher dispatcher = request.getRequestDispatcher("grantStaff.jsp");
        dispatcher.forward(request, response);	
        
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		String role = request.getParameter("userRole");
		String customerPasscode = request.getParameter("passcode");
		HttpSession session = request.getSession(false); 
		
		if(role.equals(null) || role.equals("")) {
			
		}
		
	    String passcode = "";
	     try {
			    if (session == null) {
			        throw new IllegalStateException("No session found");
			    }
			    passcode = (String) session.getAttribute("passcode");
			    if (passcode == null) {
			        throw new IllegalArgumentException("No user name found");
			    }
		
			} catch (IllegalStateException e) {
			    // Handle the case where no session is found
			    response.getWriter().write("Error: " + e.getMessage());
			} catch (IllegalArgumentException e) {
			    // Handle the case where no user name is found
			    response.getWriter().write("Error: " + e.getMessage());
		}
	    
		customers = userService.getAllUsersExceptSelf(passcode);
		
        User user = userService.findByPasscode(customerPasscode);
		boolean PrivilegeCondition = userService.GrantStaffPrivilege(user, role);
		if(PrivilegeCondition) {
			
		} else {
			
		}
		request.setAttribute("customers", customers);
        RequestDispatcher dispatcher = request.getRequestDispatcher("grantStaff.jsp");
        dispatcher.forward(request, response);	
		
	}

}
