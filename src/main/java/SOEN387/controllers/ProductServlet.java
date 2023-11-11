package SOEN387.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;

import SOEN387.models.Product;
import SOEN387.services.ProductService;

@WebServlet("/productDetails/*")
public class ProductServlet extends HttpServlet {

    private ProductService productService;

    public ProductServlet() {
        productService = new ProductService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String urlSlugPath = request.getPathInfo();
        if (urlSlugPath == null || urlSlugPath.equals("/")) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // Handle missing URL slug
            return;
        }
        String urlSlug = urlSlugPath.substring(1);

        Product product = productService.getProductByUrlSlug(urlSlug);
        if (product == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // Handle product not found
            return;
        }

        request.setAttribute("product", product);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/Product.jsp"); // Ensure the path is correct
        dispatcher.forward(request, response);
    }

    // Other methods (doPost, etc.) as needed
}
