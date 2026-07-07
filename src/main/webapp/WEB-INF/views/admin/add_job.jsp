<%@ include file="../common/header.jsp" %>
<h2>Add Job Posting</h2>

<c:choose>
    <c:when test="${not empty companies}">
        <form method="POST" action="${pageContext.request.contextPath}/admin/job/add">
            <div class="form-group">
                <label for="company_id">Select Company</label>
                <select id="company_id" name="company_id" required>
                    <option value="">-- Select Company --</option>
                    <c:forEach var="company" items="${companies}">
                        <option value="${company.companyId}"><c:out value="${company.companyName}"/></option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label for="job_title">Job Title</label>
                <input type="text" id="job_title" name="job_title" placeholder="Enter job title" required>
            </div>

            <div class="form-group">
                <label for="salary">Salary / CTC (optional)</label>
                <input type="text" id="salary" name="salary" placeholder="e.g. 6 LPA">
            </div>

            <div class="form-group">
                <label for="deadline">Application Deadline</label>
                <input type="date" id="deadline" name="deadline" required>
            </div>

            <button type="submit" class="btn">Post Job</button>
        </form>
    </c:when>
    <c:otherwise>
        <p class="links">No companies found. <a href="${pageContext.request.contextPath}/admin/company/add">Add a company first</a>.</p>
    </c:otherwise>
</c:choose>
<%@ include file="../common/footer.jsp" %>
