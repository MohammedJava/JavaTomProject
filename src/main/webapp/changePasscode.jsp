<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>JavaECOM</title>
<script src="https://unpkg.com/htmx.org@1.9.6" integrity="sha384-FhXw7b6AlE/jyjlZH5iHa/tTe9EpJ1Y55RjcgPbjeWMskSxZt1v9qkxLJWNJaGni" crossorigin="anonymous"></script>
<link rel="stylesheet" type="text/css" href="jsp/css/styles.css">
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Cairo&family=Vollkorn&display=swap" rel="stylesheet">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

</head>
<body>
<nav class="Navbar">
    	<a href="/JavaECOM" class="link">Home</a>
    	<a href="products" class="link">Products</a>
    	<a href="login" class="link">Login</a>
    	<a href="signup" class="link">Signup</a>	
</nav>


<div class="login">

<div class="video-background">
    <video autoplay="autoplay" muted="muted" loop="loop" >
        <source src="jsp/assets/videos/neon2.mp4" type="video/mp4">
    </video>
</div>

<div class="content">

<div class ="wrapper">
<form  method="post">

    <label for="Passcode">change Passcode</label>
    <input type="text" id="Passcode" name="Passcode" required>
    <input type="submit" value="Submit">
</form>
</div>


</div>

</div>


</body>
</html>