<%@ include file="../common/header.jsp" %>
<h2>My Applications</h2>

<c:choose>
    <c:when test="${not empty applications}">
        <table class="data-table">
            <thead>
            <tr>
                <th>#</th>
                <th>Job Title</th>
                <th>Company</th>
                <th>Applied On</th>
                <th>Status</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="app" items="${applications}" varStatus="loop">
                <tr>
                    <td>${loop.index + 1}</td>
                    <td><c:out value="${app.job.jobTitle}"/></td>
                    <td><c:out value="${app.job.company.companyName}"/></td>
                    <td><c:out value="${app.applyDate}"/></td>
                    <td>
                        <span class="status status-${fn:toLowerCase(app.status)}"><c:out value="${app.status}"/></span>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:when>
    <c:otherwise>
        <p class="links">You haven't applied for any jobs yet.</p>
    </c:otherwise>
</c:choose>

<div class="action-links">
    <a href="${pageContext.request.contextPath}/student/jobs" class="btn-link">Browse Jobs</a>
</div>
<%@ include file="../common/footer.jsp" %>
