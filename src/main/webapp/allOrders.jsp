<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="SOEN387.models.Order" %>
<%@ page import="SOEN387.models.OrderItem" %>

<!DOCTYPE html>
<html>
<head>
    <title>All Orders</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jsp/css/styles.css">
    <meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>

<jsp:include page="Navbar.jsp"/>

<div class="video-background">
    <video autoplay="autoplay" muted="muted" loop="loop">
        <source src="jsp/assets/videos/neon.mp4" type="video/mp4">
    </video>
</div>

<h1>All Orders Management</h1>

<%
    List<Order> orders = (List<Order>) request.getAttribute("orders");
    String userRole = (String) session.getAttribute("role");

    if ("staff".equals(userRole)) {
        if (orders != null && !orders.isEmpty()) {
            for (Order order : orders) {
%>
<div class="order">
    <div class="order-header">Order ID: <%= order.getId() %>
    </div>
    <div class="order-details">
        <p>User ID: <%= order.getUserId() %>
        </p>
        <p>Order Date: <%= order.getOrderDate() %>
        </p>
        <p>Total Price: <%= order.getTotalPrice() %>
        </p>
        <p>Status: <%= order.getStatus() %>
        </p>
        <p>Shipping Address: <%= order.getShippingAddress() %>
        </p>
    </div>
    <div class="order-items">
        <h3>Order Items:</h3>
        <ul>
            <% for (OrderItem item : order.getOrderItems()) { %>
            <li>SKU: <%= item.getProductSku() %>, Quantity: <%= item.getQuantity() %>, Price: <%= item.getPrice() %>
            </li>
            <% } %>
        </ul>
    </div>
    <div id="button-container">
        <% if (!"Shipped".equals(order.getStatus())) { %>
        <a href="<%=request.getContextPath()%>/shipOrder/<%= order.getId() %>" id="glassmorphic-button">Ship
            Order</a>
        <% } %>
    </div>
</div>
<%
    }
} else {
%>
<p>No orders found.</p>
<%
    }
} else {
%>
<p>Access denied: You do not have permission to view this page.</p>
<%
    }
%>
</body>
</html>
