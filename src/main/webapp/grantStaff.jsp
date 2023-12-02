<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
<%@ page import="com.SOEN387.models.User" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" type="text/css" href="jsp/css/styles.css">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>Some</title>
<script src="https://unpkg.com/htmx.org@1.9.6" integrity="sha384-FhXw7b6AlE/jyjlZH5iHa/tTe9EpJ1Y55RjcgPbjeWMskSxZt1v9qkxLJWNJaGni" crossorigin="anonymous"></script>

</head>
<body>
<div class="video-background">
    <video autoplay="autoplay" muted="muted" loop="loop" >
        <source src="jsp/assets/videos/neon.mp4" type="video/mp4">
    </video>
</div>

        <% @SuppressWarnings("unchecked") // Suppress the warning
           List<User> customers = (List<User>) request.getAttribute("customers");
           String sessionRoleParams = (String)session.getAttribute("role");
        %>

<jsp:include page="Navbar.jsp" />


   <h1>Products</h1>
    <ul>
        

        
          
    <div class="product-grid">
         
    <% for (User user : customers) { %>   
        <div class="card">
            <!-- Assuming the Product model has a method called getImageUrl() to retrieve image URL for the product -->
          <span><%= user.getPasscode() %></span>
          <% if(("staff".equals(sessionRoleParams))) {%>
    <form method="post">
            <input type="hidden" name="passcode" value="<%= user.getPasscode() %>">
    
            <span>customer</span>
            <input type="radio" name="userRole" value="customer">
            <br>
            <span>admin</span>
            <input type="radio" name="userRole" placeholder = "admin" value="admin">
            <br>

        <button type="submit" >Submit</button>
    </form>
           <% } %>
        </div>
    <% } %>
    
    </div>
    
        

        
    </ul>
</body>
</html>


