package SOEN387.controllers;

import SOEN387.services.UserService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import SOEN387.services.CartService;
import SOEN387.services.ProductService;

import java.io.IOException;


@WebServlet("/cart/products/*")
public class ProductFromCartServlet extends HttpServlet {
	
	
	private ProductService productService;
	private CartService cartService;
	private UserService userService;
	
	public ProductFromCartServlet() {
		productService = new ProductService();
		cartService = new CartService();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false); // Get the session without creating a new one (use 'false')
		String username = (session != null) ? (String) session.getAttribute("name") : null;

		String pathInfo = request.getPathInfo();
		String urlSlug = pathInfo.substring(1);
		String sku = productService.getProductByUrlSlug(urlSlug).getSku();

		System.out.println("Adding product to cart. User: " + username + ", SKU: " + sku);

		cartService.addProductToCart(username, sku);

		response.sendRedirect("/JavaTomProject_war_exploded/cart/products");
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		if(session == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No session found");
			return;
		}
		String name = (String) session.getAttribute("name");
		if (name == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No user name found");
			return;
		}

		String pathInfo = request.getPathInfo();
		if (pathInfo == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing product information");
			return;
		}
		String urlSlug = pathInfo.substring(1);
		String SKU = productService.getProductByUrlSlug(urlSlug).getSku();



		//old method
		cartService.removeProductFromCart(name, SKU);
		//cartService.removeSingleItemFromCart(name, urlSlug);

		// Set response status to OK
		response.setStatus(HttpServletResponse.SC_OK);
	}

	   
	 
}


