<%@ include file="../common/header.jsp" %>
<h2>My Interviews</h2>

<c:choose>
    <c:when test="${not empty interviews}">
        <table class="data-table">
            <thead>
            <tr>
                <th>#</th>
                <th>Job Title</th>
                <th>Company</th>
                <th>Date</th>
                <th>Time</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="interview" items="${interviews}" varStatus="loop">
                <tr>
                    <td>${loop.index + 1}</td>
                    <td><c:out value="${interview.job.jobTitle}"/></td>
                    <td><c:out value="${interview.company.companyName}"/></td>
                    <td><c:out value="${interview.interviewDate}"/></td>
                    <td><c:out value="${interview.interviewTime}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:when>
    <c:otherwise>
        <p class="links">No interviews scheduled yet.</p>
    </c:otherwise>
</c:choose>
<%@ include file="../common/footer.jsp" %>
