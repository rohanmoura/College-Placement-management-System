<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><c:out value="${title != null ? title : 'CPMS'}"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
</head>
<body class="<c:out value='${bodyClass}'/>">
<nav class="navbar">
    <span class="brand">CPMS</span>
    <div class="nav-links">
        <a href="${pageContext.request.contextPath}/" class="${pageContext.request.servletPath == '/' ? 'active' : ''}">Home</a>

        <c:choose>
            <c:when test="${not empty sessionScope.studentId}">
                <a href="${pageContext.request.contextPath}/student/dashboard" class="${fn:contains(pageContext.request.requestURI, '/student/dashboard') ? 'active' : ''}">Dashboard</a>
                <a href="${pageContext.request.contextPath}/student/jobs" class="${fn:contains(pageContext.request.requestURI, '/student/jobs') ? 'active' : ''}">Jobs</a>
                <a href="${pageContext.request.contextPath}/student/applications" class="${fn:contains(pageContext.request.requestURI, '/student/applications') ? 'active' : ''}">My Applications</a>
                <a href="${pageContext.request.contextPath}/student/interviews" class="${fn:contains(pageContext.request.requestURI, '/student/interviews') ? 'active' : ''}">Interviews</a>
                <a href="${pageContext.request.contextPath}/logout">Logout</a>
            </c:when>
            <c:when test="${not empty sessionScope.adminId}">
                <a href="${pageContext.request.contextPath}/admin/dashboard" class="${fn:contains(pageContext.request.requestURI, '/admin/dashboard') ? 'active' : ''}">Dashboard</a>
                <a href="${pageContext.request.contextPath}/admin/company/add" class="${fn:contains(pageContext.request.requestURI, '/admin/company') ? 'active' : ''}">Add Company</a>
                <a href="${pageContext.request.contextPath}/admin/job/add" class="${fn:contains(pageContext.request.requestURI, '/admin/job') ? 'active' : ''}">Add Job</a>
                <a href="${pageContext.request.contextPath}/admin/applications" class="${fn:contains(pageContext.request.requestURI, '/admin/applications') ? 'active' : ''}">Applications</a>
                <a href="${pageContext.request.contextPath}/admin/schedule" class="${fn:contains(pageContext.request.requestURI, '/admin/schedule') ? 'active' : ''}">Schedule</a>
                <a href="${pageContext.request.contextPath}/logout">Logout</a>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/login" class="${fn:contains(pageContext.request.requestURI, '/login') and not fn:contains(pageContext.request.requestURI, '/admin') ? 'active' : ''}">Student Login</a>
                <a href="${pageContext.request.contextPath}/admin/login" class="${fn:contains(pageContext.request.requestURI, '/admin/login') ? 'active' : ''}">Admin Login</a>
                <a href="${pageContext.request.contextPath}/register" class="${fn:contains(pageContext.request.requestURI, '/register') ? 'active' : ''}">Register</a>
            </c:otherwise>
        </c:choose>
    </div>
</nav>

<div class="container">
    <c:if test="${not empty sessionScope.flashMessage}">
        <div class="flash ${sessionScope.flashType}">
            <c:out value="${sessionScope.flashMessage}"/>
        </div>
        <c:remove var="flashMessage" scope="session"/>
        <c:remove var="flashType" scope="session"/>
    </c:if>
