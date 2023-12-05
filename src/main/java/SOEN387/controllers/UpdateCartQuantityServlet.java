package SOEN387.controllers;

import SOEN387.services.CartService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/updateCartQuantity")
public class UpdateCartQuantityServlet extends HttpServlet {

    private CartService cartService;

    public UpdateCartQuantityServlet() {
        cartService = new CartService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String user = (String) request.getSession().getAttribute("name");
        String sku = request.getParameter("sku");
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        cartService.SetProductQuantityInCart(user, sku, quantity);

        response.sendRedirect("/JavaTomProject_war_exploded/cart/products");
    }
}

