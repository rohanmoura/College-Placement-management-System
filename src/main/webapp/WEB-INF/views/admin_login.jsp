<%@ include file="common/header.jsp" %>
<h2>Admin Login</h2>

<form method="POST" action="${pageContext.request.contextPath}/admin/login">
    <div class="form-group">
        <label for="username">Username</label>
        <input type="text" id="username" name="username" placeholder="Enter admin username" required>
    </div>

    <div class="form-group">
        <label for="password">Password</label>
        <input type="password" id="password" name="password" placeholder="Enter admin password" required>
    </div>

    <button type="submit" class="btn">Login as Admin</button>
</form>

<div class="links">
    <a href="${pageContext.request.contextPath}/login">Student Login</a>
</div>
<%@ include file="common/footer.jsp" %>
