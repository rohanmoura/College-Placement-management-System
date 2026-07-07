package com.cpms.controller;

import com.cpms.dao.JobApplicationDAO;
import com.cpms.util.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

public class StudentApplicationsServlet extends HttpServlet {
    private final JobApplicationDAO applicationDAO = new JobApplicationDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!WebUtil.isStudentLoggedIn(request)) {
            WebUtil.flash(request, "danger", "Please login first.");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        try {
            request.setAttribute("applications", applicationDAO.findByStudent(WebUtil.loggedStudentId(request)));
            request.setAttribute("title", "My Applications - CPMS");
            request.setAttribute("bodyClass", "student-applications");
            request.getRequestDispatcher("/WEB-INF/views/student/applications.jsp").forward(request, response);
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
}
