package com.cpms.controller;

import com.cpms.dao.JobApplicationDAO;
import com.cpms.dao.StudentDAO;
import com.cpms.util.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

public class StudentDashboardServlet extends HttpServlet {
    private final StudentDAO studentDAO = new StudentDAO();
    private final JobApplicationDAO applicationDAO = new JobApplicationDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!WebUtil.isStudentLoggedIn(request)) {
            WebUtil.flash(request, "danger", "Please login first.");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        try {
            int studentId = WebUtil.loggedStudentId(request);
            request.setAttribute("student", studentDAO.findById(studentId));
            request.setAttribute("totalApplications", applicationDAO.countByStudent(studentId));
            request.setAttribute("title", "Dashboard - CPMS");
            request.setAttribute("bodyClass", "student-dashboard");
            request.getRequestDispatcher("/WEB-INF/views/student/dashboard.jsp").forward(request, response);
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
}
