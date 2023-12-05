package SOEN387.controllers;

import SOEN387.models.CartItem;
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

		HttpSession session = request.getSession(true);
		String name = (String) session.getAttribute("name");

		List<CartItem> userProducts = cartService.getCart(name);
		request.setAttribute("cartItems", userProducts);
		request.setAttribute("name", name);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/userCart.jsp");
		dispatcher.forward(request, response);
	}
	
}

