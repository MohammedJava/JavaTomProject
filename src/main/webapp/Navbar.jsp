<nav class="Navbar">
    <a href="//localhost:8080/JavaTomProject_war_exploded" class="link">Home</a>
    <a href="//localhost:8080/JavaTomProject_war_exploded/products" class="link">Products</a>

    <% String userName = (String) session.getAttribute("name"); %>
    <% String userRole = (String) session.getAttribute("role"); %>

    <% if (userName != null && !userName.isEmpty()) { %>
    <a href="//localhost:8080/JavaTomProject_war_exploded/cart/products" class="link">Cart</a>
    <a href="//localhost:8080/JavaTomProject_war_exploded/customer/orders" class="link">Orders</a>
    <% } %>

    <% if (userRole != null && userRole.equals("staff")) { %>
    <a href="//localhost:8080/JavaTomProject_war_exploded/allOrders" class="link">All Orders</a>
    <% } %>

    <% if (userName != null && !userName.isEmpty() || (userRole != null && userRole.equals("staff"))) { %>
    <a href="//localhost:8080/JavaTomProject_war_exploded/logout" class="link">Logout</a>
    <% } else { %>
    <a href="//localhost:8080/JavaTomProject_war_exploded/login" class="link">Login</a>
    <a href="signup" class="link">Sign Up</a>
    <% } %>
</nav>
