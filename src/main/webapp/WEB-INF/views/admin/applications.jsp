<%@ include file="../common/header.jsp" %>
<h2>All Applications</h2>

<c:choose>
    <c:when test="${not empty applications}">
        <table class="data-table">
            <thead>
            <tr>
                <th>#</th>
                <th>Student</th>
                <th>Job Title</th>
                <th>Company</th>
                <th>Applied On</th>
                <th>Status</th>
                <th>Update</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="app" items="${applications}" varStatus="loop">
                <tr>
                    <td>${loop.index + 1}</td>
                    <td><c:out value="${app.student.name}"/></td>
                    <td><c:out value="${app.job.jobTitle}"/></td>
                    <td><c:out value="${app.job.company.companyName}"/></td>
                    <td><c:out value="${app.applyDate}"/></td>
                    <td>
                        <span class="status status-${fn:toLowerCase(app.status)}"><c:out value="${app.status}"/></span>
                    </td>
                    <td>
                        <form method="POST" action="${pageContext.request.contextPath}/admin/update-status" class="inline-form">
                            <input type="hidden" name="applicationId" value="${app.applicationId}">
                            <select name="status">
                                <option value="Applied" ${app.status == 'Applied' ? 'selected' : ''}>Applied</option>
                                <option value="Shortlisted" ${app.status == 'Shortlisted' ? 'selected' : ''}>Shortlisted</option>
                                <option value="Selected" ${app.status == 'Selected' ? 'selected' : ''}>Selected</option>
                                <option value="Rejected" ${app.status == 'Rejected' ? 'selected' : ''}>Rejected</option>
                            </select>
                            <button type="submit">Save</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:when>
    <c:otherwise>
        <p class="links">No applications yet.</p>
    </c:otherwise>
</c:choose>
<%@ include file="../common/footer.jsp" %>
