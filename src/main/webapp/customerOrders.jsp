<%@ page import="SOEN387.models.Order" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Your Orders</title>
</head>
<body>
<h1>Your Orders</h1>
<%
    List<Order> orders = (List<Order>) request.getAttribute("orders");
    if (orders != null && !orders.isEmpty()) {
        for (Order order : orders) {
%>
<div class="order">
    <p>Order ID: <%= order.getId() %>, Total Price: <%= order.getTotalPrice() %>, Status: <%= order.getStatus() %>
    </p>
    <a href="order/<%= order.getId() %>">View Details</a>
</div>
<%
    }
} else {
%>
<p>No orders found.</p>
<%
    }
%>
</body>
</html>
