<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ page import="java.util.List" %>
<%@ page import="SOEN387.models.Product" %>
<%@ page import="SOEN387.services.CartService" %>
<%@ page import="SOEN387.models.CartItem" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jsp/css/styles.css">


    <title>Product</title>
    <script src="https://unpkg.com/htmx.org@1.9.6"
            integrity="sha384-FhXw7b6AlE/jyjlZH5iHa/tTe9EpJ1Y55RjcgPbjeWMskSxZt1v9qkxLJWNJaGni"
            crossorigin="anonymous"></script>
    <script>
        function addToCart(productSlug, sku) {
            var xhr = new XMLHttpRequest();
            xhr.open("POST", "/JavaTomProject_war_exploded/cart/products/" + productSlug, true);
            xhr.onreadystatechange = function () {
                if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
                    // Update display to show quantity controls
                    document.getElementById('cart-controls-' + sku).innerHTML = `
                        <button type="button" onclick="decreaseQuantity('${sku}')">-</button>
                        <span id="quantity-${sku}">1</span>
                        <button type="button" onclick="increaseQuantity('${sku}')">+</button>
                    `;
                }
            };
            xhr.send();
        }


        function updateQuantity(sku, increase) {
            var xhr = new XMLHttpRequest();
            xhr.open("POST", "/JavaTomProject_war_exploded/modifyCartQuantity", true);
            xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
            xhr.onreadystatechange = function() {
                if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
                    var updatedQuantity = xhr.responseText;
                    document.getElementById('quantity-' + sku).innerText = updatedQuantity;
                }
            };
            xhr.send("sku=" + sku + "&action=" + (increase ? "increase" : "decrease"));
        }

        function increaseQuantity(sku) {
            updateQuantity(sku, true);
        }

        function decreaseQuantity(sku) {
            updateQuantity(sku, false);
        }

    </script>
</head>

<body>

<div class="video-background">
    <video id="backgroundVideo" autoplay="autoplay" muted="muted" loop="loop">
        <source src="${pageContext.request.contextPath}/jsp/assets/videos/black-suv-neon.mp4" type="video/mp4">
    </video>
</div>


<% @SuppressWarnings("unchecked") // Suppress the warning
String sessionRoleParams = (String) session.getAttribute("role");
%>

<jsp:include page="Navbar.jsp"/>

<%
    Product product = (Product) request.getAttribute("product");
    String username = (String) session.getAttribute("name");
    CartService cartService = new CartService();
    List<CartItem> cartItems = cartService.getCart(username);
    boolean isInCart = false;
    int quantityInCart = 0;
    if (cartItems != null) {
        for (CartItem item : cartItems) {
            if (item.getProduct().getSku().equals(product.getSku())) {
                isInCart = true;
                quantityInCart = item.getQuantity();
                break;
            }
        }
    }
%>

<%if (product != null) { %>
<div class="MiddleCard">
    <div class="imgBox">
        <img src="<%= product.getImage() %>" alt="<%= product.getName() %>">
    </div>
    <div class="detailedContentBox">
        <h2><%= product.getName() %>
        </h2>
        <p><%= product.getDescription() %>
        </p>
        <h3><%= product.getPrice() %> $</h3>

        <% if (!("staff".equals(sessionRoleParams))) {
            if (isInCart && quantityInCart > 0) { %>
        <div id="cart-controls-<%= product.getSku() %>" style="display: flex; align-items: center;">
            <button type="button" onclick="decreaseQuantity('<%= product.getSku() %>')">-</button>
            <span id="quantity-<%= product.getSku() %>"><%= quantityInCart %></span>
            <button type="button" onclick="increaseQuantity('<%= product.getSku() %>')">+</button>
        </div>
        <% } else { %>
        <button type="button" onclick="addToCart('<%= product.getUrlSlug() %>', '<%= product.getSku() %>')">Add To Your Cart</button>
        <% }
        } %>
    </div>


    <% if (("staff".equals(sessionRoleParams))) {%>
    <form id="updateProductForm"
          action="http://localhost:8080/JavaTomProject_war_exploded/productDetails/<%=product.getUrlSlug()%>" method="post">
        <input type="text" name="name" placeholder="Product Name">
        <br>
        <input type="text" name="description" placeholder="Product Description">
        <br>
        <input type="text" name="vendor" placeholder="Vendor">
        <br>
        <input type="number" name="price" placeholder="Price">
        <br>
        <input type="text" name="SKU" placeholder="SKU">
        <br>
        <button type="submit">Update Product</button>
    </form>
    <% } %>
</div>
<%} %>

</body>
</html>





