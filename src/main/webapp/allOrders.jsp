<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="SOEN387.models.Order" %>

<!DOCTYPE html>
<html>
<head>
    <title>All Orders</title>
    <!-- Include any CSS or JS here -->
</head>
<body>
<h1>All Orders</h1>
<%
    List<Order> orders = (List<Order>) request.getAttribute("orders");
    String userRole = (String) session.getAttribute("role"); // Retrieve user role from session

    if ("staff".equals(userRole)) { // Check if the user is a staff member
        if (orders != null && !orders.isEmpty()) {
            for (Order order : orders) {
%>
<div class="order">
    <!-- Display order details here -->
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
</div>
<%
    }
} else {
%>
<p>No orders found.</p>
<%
    }
} else {
    // Display a message or redirect if the user is not a staff member
%>
<p>Access denied: You do not have permission to view this page.</p>
<%
    }
%>
</body>
</html>
