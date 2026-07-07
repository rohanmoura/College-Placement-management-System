<%@ include file="../common/header.jsp" %>
<h2>Available Jobs</h2>

<c:choose>
    <c:when test="${not empty jobs}">
        <table class="data-table">
            <thead>
            <tr>
                <th>#</th>
                <th>Job Title</th>
                <th>Company</th>
                <th>Salary</th>
                <th>Deadline</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="job" items="${jobs}" varStatus="loop">
                <tr>
                    <td>${loop.index + 1}</td>
                    <td><c:out value="${job.jobTitle}"/></td>
                    <td><c:out value="${job.company.companyName}"/></td>
                    <td><c:out value="${empty job.salary ? 'Not disclosed' : job.salary}"/></td>
                    <td><c:out value="${job.deadline}"/></td>
                    <td>
                        <a href="${pageContext.request.contextPath}/student/apply?jobId=${job.jobId}" class="btn-small">Apply</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:when>
    <c:otherwise>
        <p class="links">No jobs available right now.</p>
    </c:otherwise>
</c:choose>
<%@ include file="../common/footer.jsp" %>
