package SOEN387.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import SOEN387.models.Product;
import SOEN387.services.ProductService;

import java.io.IOException;



@WebServlet("/products/*")
public class ProductServlet extends HttpServlet {

    private ProductService productService;

    public ProductServlet() {
        productService = new ProductService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String urlSlugPath = request.getPathInfo();
        String urlSlug = urlSlugPath.substring(1);

        Product product = productService.getProductByUrlSlug(urlSlug);

        request.setAttribute("product", product);

        RequestDispatcher dispatcher = request.getRequestDispatcher("Product.jsp");
        dispatcher.forward(request, response);

    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
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


        String pathInfo = request.getPathInfo();
        String urlSlug = pathInfo.substring(1);
        System.out.println("URL Slug: " + urlSlug); // Add this line

        Product product = productService.getProductByUrlSlug(urlSlug);

        String SKU = product.getSku();

        String productName = request.getParameter("name");
        String description = request.getParameter("description");
        String vendor = request.getParameter("vendor");
        String image = request.getParameter("image");

        productName = (productName != null) ? productName : "";
        description = (description != null) ? description : "";
        vendor = (vendor != null) ? vendor : "";
        image = (image != null) ? image : "";

        double price = -1.1;
        if(!(request.getParameter("price").equals(""))){
            price = Double.parseDouble(request.getParameter("price"));
        }


        productService.updateProduct(SKU, productName, description, price, vendor, image);

        RequestDispatcher dispatcher = request.getRequestDispatcher("Product.jsp");
        dispatcher.forward(request, response);


    }
//	 protected void doPut(HttpServletRequest request, HttpServletResponse response)
//	            throws ServletException, IOException {
//		 
//		 HttpSession session = request.getSession(); // Get the session without creating a new one
//		 if(session == null) {
//		        // No session found; handle the situation as needed'
//			 
//			 
//		 }
//		 
//		 String role = (String) session.getAttribute("role");
//		 if (role == null || !("staff".equals(role))) {  
//
//		 }  
//	    }
//	 protected void doDelete(HttpServletRequest request, HttpServletResponse response)
//	            throws ServletException, IOException {
//		  
//		 HttpSession session = request.getSession(false); // Get the session without creating a new one
//		 if(session == null) {
//		        // No session found; handle the situation as needed'
//			 
//			 
//		 }
//		 
//		 String role = (String) session.getAttribute("role");
//		 if (role == null || !("staff".equals(role))) {  
//
//		 }  
//		 
//	       
//	    }

}
