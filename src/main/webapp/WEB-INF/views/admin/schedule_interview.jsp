<%@ include file="../common/header.jsp" %>
<h2>Schedule Interview</h2>

<form method="POST" action="${pageContext.request.contextPath}/admin/schedule">
    <div class="form-group">
        <label for="student_id">Select Student</label>
        <select id="student_id" name="student_id" required>
            <option value="">-- Select Student --</option>
            <c:forEach var="student" items="${students}">
                <option value="${student.studentId}"><c:out value="${student.name}"/> (<c:out value="${student.email}"/>)</option>
            </c:forEach>
        </select>
    </div>

    <div class="form-group">
        <label for="job_id">Select Job</label>
        <select id="job_id" name="job_id" required>
            <option value="">-- Select Job --</option>
            <c:forEach var="job" items="${jobs}">
                <option value="${job.jobId}"><c:out value="${job.jobTitle}"/> - <c:out value="${job.company.companyName}"/></option>
            </c:forEach>
        </select>
    </div>

    <div class="form-group">
        <label for="interview_date">Interview Date</label>
        <input type="date" id="interview_date" name="interview_date" required>
    </div>

    <div class="form-group">
        <label for="interview_time">Interview Time</label>
        <input type="time" id="interview_time" name="interview_time" required>
    </div>

    <button type="submit" class="btn">Schedule Interview</button>
</form>
<%@ include file="../common/footer.jsp" %>
