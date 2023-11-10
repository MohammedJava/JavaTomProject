<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
<%@ page import="SOEN387.models.Product" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jsp/css/styles.css">


<title>Product</title>
<script src="https://unpkg.com/htmx.org@1.9.6" integrity="sha384-FhXw7b6AlE/jyjlZH5iHa/tTe9EpJ1Y55RjcgPbjeWMskSxZt1v9qkxLJWNJaGni" crossorigin="anonymous"></script>
</head>

<body>

<div class="video-background">
    <video id="backgroundVideo" autoplay="autoplay" muted="muted" loop="loop">
        <source src="${pageContext.request.contextPath}/jsp/assets/videos/black-suv-neon.mp4" type="video/mp4">
    </video>
</div>




        <% @SuppressWarnings("unchecked") // Suppress the warning
           String sessionRoleParams = (String)session.getAttribute("role");
        %>

<jsp:include page="Navbar.jsp" />

<%
    Product product = (Product) request.getAttribute("product");
    String sessionCustomerParams = (String)session.getAttribute("name");
 %>

<%if(product != null){ %>
    <div class="MiddleCard">
                <div class="imgBox">
        <img src="<%= product.getImage() %>" alt="<%= product.getName() %>">
        </div>
                    <div class="detailedContentBox">
        <h2><%= product.getName() %></h2>
        <p><%= product.getDescription() %></p>
        <h3><%= product.getPrice() %> $</h3>

        <% if(!("staff".equals(sessionRoleParams))) {%>
            <button hx-post="http://localhost:8080/JavaTomProject_war_exploded/cart/products/<%=product.getUrlSlug()%>">Add To Your Cart</button>
        <% } %>
        </div>


        <% if(("staff".equals(sessionRoleParams))) {%>
            <form id="updateProductForm" action="http://localhost:8080/JavaTomProject_war_exploded/products/<%=product.getUrlSlug()%>" method="post">
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
