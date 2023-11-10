<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ page import="java.util.List" %>
<%@ page import="SOEN387.models.Product" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Cart</title>
    <!-- Include other scripts and styles if necessary -->
    <script>
        function removeProductFromCart(productSlug) {
            var xhr = new XMLHttpRequest();
            xhr.open("DELETE", "/JavaTomProject_war_exploded/cart/products/" + productSlug, true);
            xhr.onreadystatechange = function() {
                if (xhr.readyState === XMLHttpRequest.DONE) {
                    var status = xhr.status;
                    if (status === 0 || (status >= 200 && status < 400)) {
                        // The request has been completed successfully
                        console.log("Product removed");
                        window.location.reload(); // Reload the page to update the cart
                    } else {
                        console.error("Failed to remove product");
                    }
                }
            };
            xhr.send();
        }
    </script>
</head>
<body>
<h1><%= session.getAttribute("name") %>'s Cart</h1>
<%
    List<Product> products = (List<Product>) request.getAttribute("products");
    String sessionRoleParams = (String) session.getAttribute("role");
%>
<% if (products != null && !products.isEmpty()) { %>
<!-- Start of Order Form -->
<form action="orders" method="post">
    <ul>
        <% for (Product product : products) { %>
        <li>
            <%= product.getName()%> - <%= product.getDescription() %> - $<%= product.getPrice() %>
            <% if (sessionRoleParams == null || !("staff".equals(sessionRoleParams))) { %>
            <!-- Remove Button outside the order form -->
            <% } %>
        </li>
        <% } %>
    </ul>
    <!-- Checkout button -->
    <input type="submit" value="Order"/>
</form>
<!-- End of Order Form -->
<!-- Remove Buttons -->
<% for (Product product : products) { %>
<% if (sessionRoleParams == null || !("staff".equals(sessionRoleParams))) { %>
<button type="button" onclick="removeProductFromCart('<%=product.getUrlSlug()%>')">Remove</button>
<% } %>
<% } %>
<% } else { %>
<p>Your cart is empty.</p>
<% } %>
</body>
</html>
