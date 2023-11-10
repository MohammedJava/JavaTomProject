
<nav class="Navbar">
    <a href="//localhost:8080/JavaTomProject_war_exploded" class="link">Home</a>
    <a href="//localhost:8080/JavaTomProject_war_exploded/products" class="link">Products</a>

    <% String userName = (String)session.getAttribute("name"); %>
    <% if(userName != null && !userName.isEmpty()) { %>
    <a href="//localhost:8080/JavaTomProject_war_exploded/logout" class="link">Logout</a>
    <% } else { %>
    <a href="//localhost:8080/JavaTomProject_war_exploded/login" class="link">Login</a>
    <% } %>
</nav>