<%@ include file="../common/header.jsp" %>
<h2>Add Company</h2>

<form method="POST" action="${pageContext.request.contextPath}/admin/company/add">
    <div class="form-group">
        <label for="company_name">Company Name</label>
        <input type="text" id="company_name" name="company_name" placeholder="Enter company name" required>
    </div>

    <div class="form-group">
        <label for="hr_name">HR / Representative Name</label>
        <input type="text" id="hr_name" name="hr_name" placeholder="Enter HR name" required>
    </div>

    <div class="form-group">
        <label for="email">Email</label>
        <input type="email" id="email" name="email" placeholder="Enter company email" required>
    </div>

    <div class="form-group">
        <label for="contact">Contact (optional)</label>
        <input type="text" id="contact" name="contact" placeholder="Enter contact number">
    </div>

    <div class="form-group">
        <label for="location">Location (optional)</label>
        <input type="text" id="location" name="location" placeholder="Enter company location">
    </div>

    <button type="submit" class="btn">Add Company</button>
</form>
<%@ include file="../common/footer.jsp" %>
