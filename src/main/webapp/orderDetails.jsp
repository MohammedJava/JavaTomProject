<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Your Orders</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jsp/css/styles.css">
    <meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>

<jsp:include page="Navbar.jsp"/>

<div class="video-background">
    <video autoplay="autoplay" muted="muted" loop="loop">
        <source src="${pageContext.request.contextPath}/jsp/assets/videos/neon2.mp4" type="video/mp4">
    </video>
</div>

<div class="order">
    <h1>Order Details</h1>
    <%
        SOEN387.models.Order order = (SOEN387.models.Order) request.getAttribute("order");
        if (order != null) {
    %>
    <p>Order ID: <%= order.getId() %>
    </p>
    <p>User ID: <%= order.getUserId() %>
    </p>
    <p>Order Date: <%= order.getOrderDate() %>
    </p>
    <p>Total Price: <%= order.getTotalPrice() %>
    </p>
    <p>Status: <%= order.getStatus() %>
    </p>
    <p>Shipping Address: <%= order.getShippingAddress() %></p>

    <h2>Order Items:</h2>
    <ul>
        <% for (SOEN387.models.OrderItem item : order.getOrderItems()) { %>
        <li>SKU: <%= item.getProductSku() %>, Quantity: <%= item.getQuantity() %>, Price: <%= item.getPrice() %>
        </li>
        <% } %>
    </ul>
    <%
    } else {
    %>
    <p>Order not found.</p>
    <%
        }
    %>
</div>
</body>
</html>
