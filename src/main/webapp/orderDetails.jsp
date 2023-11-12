<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Order Details</title>
</head>
<body>
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
</body>
</html>
