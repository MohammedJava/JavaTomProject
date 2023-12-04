<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page import="java.util.List" %>
<%@ page import="SOEN387.models.CartItem" %>
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
    List<CartItem> cartItems = (List<CartItem>) request.getAttribute("cartItems");
    String sessionRoleParams = (String) session.getAttribute("role");
%>

<div class="order">
    <h1><%= session.getAttribute("name") %>'s Cart</h1>

    <form action="orders" method="post">
        <ul>
            <% if (cartItems != null && !cartItems.isEmpty()) { %>
            <% for (CartItem cartItem : cartItems) { %>
            <li>
                <%= cartItem.getProduct().getName()%> - <%= cartItem.getProduct().getDescription() %> -
                $<%= cartItem.getProduct().getPrice() %>
                Quantity: <%= cartItem.getQuantity() %>
                <input type="number" id="quantity-<%=cartItem.getProduct().getSku()%>" name="quantity" min="1"
                       value="<%= cartItem.getQuantity() %>">
                <button type="button" onclick="updateCartQuantity('<%=cartItem.getProduct().getSku()%>')">Update
                    Quantity
                </button>
                <button type="button" onclick="removeProductFromCart('<%=cartItem.getProduct().getUrlSlug()%>')">
                    Remove
                </button>
            </li>
            <% } %>
            <% } else { %>
            <p>Your cart is empty.</p>
            <% } %>
        </ul>

        <label for="shippingAddress">Shipping Address:</label>
        <input type="text" id="shippingAddress" name="shippingAddress" required>
        <input type="submit" value="Order"/>
    </form>
</div>
</body>
</html>
