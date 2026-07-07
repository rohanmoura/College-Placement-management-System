<%@ include file="common/header.jsp" %>
<h2>Student Login</h2>

<form method="POST" action="${pageContext.request.contextPath}/login">
    <div class="form-group">
        <label for="email">Email</label>
        <input type="email" id="email" name="email" placeholder="Enter your email" required>
    </div>

    <div class="form-group">
        <label for="password">Password</label>
        <input type="password" id="password" name="password" placeholder="Enter your password" required>
    </div>

    <button type="submit" class="btn">Login</button>
</form>

<div class="links">
    Don't have an account? <a href="${pageContext.request.contextPath}/register">Register here</a>
    <br><br>
    <a href="${pageContext.request.contextPath}/admin/login">Admin Login</a>
</div>
<%@ include file="common/footer.jsp" %>
