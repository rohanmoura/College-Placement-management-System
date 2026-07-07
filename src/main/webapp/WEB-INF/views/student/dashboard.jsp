<%@ include file="../common/header.jsp" %>
<h2>Student Dashboard</h2>

<table class="info-table">
    <tr><th>Name</th><td><c:out value="${student.name}"/></td></tr>
    <tr><th>Email</th><td><c:out value="${student.email}"/></td></tr>
    <tr><th>Contact</th><td><c:out value="${student.contact}"/></td></tr>
    <tr><th>Course</th><td><c:out value="${student.course}"/></td></tr>
    <tr><th>Year</th><td><c:out value="${student.year}"/></td></tr>
    <tr><th>Skills</th><td><c:out value="${empty student.skills ? 'Not provided' : student.skills}"/></td></tr>
    <tr>
        <th>Total Applications</th>
        <td><strong><c:out value="${totalApplications}"/></strong></td>
    </tr>
</table>

<div class="action-links">
    <a href="${pageContext.request.contextPath}/student/jobs" class="btn-link">View Jobs</a>
    <a href="${pageContext.request.contextPath}/student/applications" class="btn-link">My Applications</a>
</div>
<%@ include file="../common/footer.jsp" %>
