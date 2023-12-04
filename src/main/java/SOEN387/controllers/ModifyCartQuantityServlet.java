package SOEN387.controllers;

import SOEN387.services.CartService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/modifyCartQuantity")
public class ModifyCartQuantityServlet extends HttpServlet {

    private CartService cartService;

    public ModifyCartQuantityServlet() {
        cartService = new CartService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String user = request.getSession().getAttribute("name").toString();
        String sku = request.getParameter("sku");
        String action = request.getParameter("action");

        int currentQuantity = cartService.getProductQuantityInCart(user, sku);
        int newQuantity = action.equals("increase") ? currentQuantity + 1 : Math.max(currentQuantity - 1, 0);

        cartService.SetProductQuantityInCart(user, sku, newQuantity);

        // Respond with the new quantity
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(String.valueOf(newQuantity));
    }
}
