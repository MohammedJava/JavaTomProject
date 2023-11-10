package SOEN387.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import SOEN387.models.Product;
import SOEN387.services.CartService;

import java.io.IOException;
import java.util.List;

@WebServlet("/cart/products")
public class ProductsCartServlet extends HttpServlet {
	
	private CartService cartService;
	
	public ProductsCartServlet() {
		cartService = new CartService();
	}
		 
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		            throws ServletException, IOException {
		
		
		
		HttpSession session = request.getSession(false); // Get the session without creating a new one (use 'false')
        String name = "";
		try {
		    if (session == null) {
		        throw new IllegalStateException("No session found");
		    }
		    name = (String) session.getAttribute("name");
		    if (name == null) {
		        throw new IllegalArgumentException("No user name found");
		    }
	
		} catch (IllegalStateException e) {
		    // Handle the case where no session is found
		    response.getWriter().write("Error: " + e.getMessage());
		} catch (IllegalArgumentException e) {
		    // Handle the case where no user name is found
		    response.getWriter().write("Error: " + e.getMessage());
		} 
		 
		 List<Product> userProducts = cartService.getCart(name);
		 request.setAttribute("products", userProducts);
		 request.setAttribute("name", name);

		 RequestDispatcher dispatcher = request.getRequestDispatcher("/userCart.jsp");
		 dispatcher.forward(request, response);
	} 
	
}

