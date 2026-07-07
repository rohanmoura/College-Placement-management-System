<%@ include file="../common/header.jsp" %>
<h2>Admin Dashboard</h2>

<div class="stat-grid">
    <div class="stat-card">
        <span class="stat-number"><c:out value="${totalStudents}"/></span>
        <span class="stat-label">Students</span>
    </div>
    <div class="stat-card">
        <span class="stat-number"><c:out value="${totalCompanies}"/></span>
        <span class="stat-label">Companies</span>
    </div>
    <div class="stat-card">
        <span class="stat-number"><c:out value="${totalJobs}"/></span>
        <span class="stat-label">Job Postings</span>
    </div>
    <div class="stat-card">
        <span class="stat-number"><c:out value="${totalApplications}"/></span>
        <span class="stat-label">Applications</span>
    </div>
</div>

<div class="action-links">
    <a href="${pageContext.request.contextPath}/admin/company/add" class="btn-link">Add Company</a>
    <a href="${pageContext.request.contextPath}/admin/job/add" class="btn-link">Add Job</a>
    <a href="${pageContext.request.contextPath}/admin/applications" class="btn-link">View Applications</a>
</div>
<%@ include file="../common/footer.jsp" %>
