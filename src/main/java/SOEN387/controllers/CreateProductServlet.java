package SOEN387.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import SOEN387.models.Product;
import SOEN387.services.ProductService;

import java.io.IOException;

@WebServlet("/createProduct")
public class CreateProductServlet extends HttpServlet {

    private ProductService productService;

    public CreateProductServlet() {
        productService = new ProductService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ProductService productService = new ProductService();
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String vendor = request.getParameter("vendor");
        String urlSlug = request.getParameter("urlSlug");
        String sku = request.getParameter("sku");
        double price = Double.parseDouble(request.getParameter("price"));
        String image = request.getParameter("image");

        Product newProduct = new Product(name, description, vendor, urlSlug, sku, price, image);
        productService.createProduct(newProduct);

        response.sendRedirect("products");
    }
}
