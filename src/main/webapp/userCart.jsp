<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page import="java.util.List" %>
<%@ page import="SOEN387.models.Product" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Cart</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jsp/css/styles.css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script>
        function removeProductFromCart(productSlug) {
            var xhr = new XMLHttpRequest();
            xhr.open("DELETE", "/JavaTomProject_war_exploded/cart/products/" + productSlug, true);
            xhr.onreadystatechange = function () {
                if (xhr.readyState === XMLHttpRequest.DONE) {
                    var status = xhr.status;
                    if (status === 0 || (status >= 200 && status < 400)) {
                        console.log("Product removed");
                        window.location.reload();
                    } else {
                        console.error("Failed to remove product");
                    }
                }
            };
            xhr.send();
        }

        function updateCartQuantity(sku) {
            var quantity = document.getElementById('quantity-' + sku).value;
            var xhr = new XMLHttpRequest();
            xhr.open("POST", "/JavaTomProject_war_exploded/updateCartQuantity", true);
            xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
            xhr.onreadystatechange = function () {
                if (xhr.readyState === XMLHttpRequest.DONE) {
                    if (xhr.status === 200) {
                        console.log("Quantity updated for SKU " + sku);
                    } else {
                        console.error("Failed to update quantity");
                    }
                }
            };
            xhr.send("sku=" + sku + "&quantity=" + quantity);
        }
    </script>
</head>
<body>

<jsp:include page="Navbar.jsp"/>

<div class="video-background">
    <video autoplay="autoplay" muted="muted" loop="loop">
        <source src="${pageContext.request.contextPath}/jsp/assets/videos/neon.mp4" type="video/mp4">
    </video>
</div>

<%
    List<Product> products = (List<Product>) request.getAttribute("products");
    String sessionRoleParams = (String) session.getAttribute("role");
%>

<div class="order">
    <h1><%= session.getAttribute("name") %>'s Cart</h1>

    <form action="orders" method="post">
        <ul>
            <% for (Product product : products) { %>
            <li>
                <%= product.getName()%> - <%= product.getDescription() %> - $<%= product.getPrice() %>
                <% if (sessionRoleParams == null || !("staff".equals(sessionRoleParams))) { %>
                <input type="number" id="quantity-<%=product.getSku()%>" name="quantity" min="1" value="1">
                <button type="button" onclick="updateCartQuantity('<%=product.getSku()%>')">Update Quantity</button>
                <button type="button" onclick="removeProductFromCart('<%=product.getUrlSlug()%>')">Remove</button>
                <% } %>
            </li>
            <% } %>
        </ul>

        <label for="shippingAddress">Shipping Address:</label>
        <input type="text" id="shippingAddress" name="shippingAddress" required>
        <input type="submit" value="Order"/>
    </form>
    <% if (products == null || products.isEmpty()) { %>
    <p>Your cart is empty.</p>
    <% } %>
</div>
</body>
</html>
