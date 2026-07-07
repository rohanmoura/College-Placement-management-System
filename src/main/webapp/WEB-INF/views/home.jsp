<%@ include file="common/header.jsp" %>
<h2>College Placement Management System</h2>

<p class="home-description">
    A streamlined platform to connect students with top recruiters.
    Browse job postings, submit applications, and track your placement
    journey - all in one place.
</p>

<div class="action-links">
    <a href="${pageContext.request.contextPath}/login" class="btn-link">Student Login</a>
    <a href="${pageContext.request.contextPath}/admin/login" class="btn-link">Admin Login</a>
    <a href="${pageContext.request.contextPath}/register" class="btn-link">Register</a>
</div>
<%@ include file="common/footer.jsp" %>
