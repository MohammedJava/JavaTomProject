package SOEN387.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import SOEN387.models.Product;
import SOEN387.services.ProductService;

import java.io.IOException;
import java.util.List;



@WebServlet("/products/download")
public class DownloadServlet extends HttpServlet {
	
	 private ProductService productService;
	 
	 public DownloadServlet() {
		 productService = new ProductService();
	 }
	 
	 protected void doGet(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException, JsonProcessingException  {
		  
		    List<Product> allProducts = productService.getAllProducts();
		   
		    // Convert the products list to JSON
	        ObjectMapper objectMapper = new ObjectMapper();
	        String productsJson = objectMapper.writeValueAsString(allProducts);

	        response.setCharacterEncoding("UTF-8"); // Set encoding to UTF-8
	        response.setContentType("application/json");
	        response.setHeader("Content-Disposition", "attachment; filename=products.json");

	        // Get the output stream to write the JSON data
	        try (ServletOutputStream out = response.getOutputStream()) {
	            out.print(productsJson);
	        }
	    }
	 

}