<%@ page import="SOEN387.models.Order" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Order Confirmed</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jsp/css/styles.css">
    <meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>

<jsp:include page="Navbar.jsp"/>

<div class="video-background">
    <video autoplay="autoplay" muted="muted" loop="loop">
        <source src="${pageContext.request.contextPath}/jsp/assets/videos/neon.mp4" type="video/mp4">
    </video>
</div>

<h1>Order Confirmed!</h1>

<%
    Order order = (Order) request.getAttribute("order");
    int orderId = (int) request.getAttribute("orderId");

    if (order != null) {
%>
<p>Your order has been confirmed.</p>
<p>Order ID: <%= orderId %></p>
<p>Total Price: <%= order.getTotalPrice() %></p>
<p>Status: <%= order.getStatus() %></p>
<p>Shipping Address: <%= order.getShippingAddress() %></p>
<p>Thank you for your purchase!</p>
<%
} else {
%>
<p>Order confirmation failed.</p>
<%
    }
%>

</body>
</html>
