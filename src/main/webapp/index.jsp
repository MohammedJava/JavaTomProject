<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="ISO-8859-1">
  <link rel="stylesheet" type="text/css" href="jsp/css/styles.css">
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Cairo&family=Vollkorn&display=swap" rel="stylesheet">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Java ECOM</title>
</head>
<body>

<script>
  function redirectToLogin() {
    window.location.href = "login"; // change 'login' to the exact URL of your login page if it's different
  }
</script>

<jsp:include page="Navbar.jsp" />

<div class="video-background">
  <video autoplay="autoplay" muted="muted" loop="loop" >
    <source src="jsp/assets/videos/neon.mp4" type="video/mp4">
  </video>
</div>



<!--  Search bar functionality for later
    <form  class="flex-form">
      <input type="text" placeholder="What are you looking for?">
    </form>
-->

<% String userName = (String)session.getAttribute("name"); %>

<div class="center-container">
  <% if(userName == null || userName.isEmpty()) { %>
  <button class="join-btn" onClick="redirectToLogin()">Register Now</button>
  <% } %>
</div>
</body>
</html>

