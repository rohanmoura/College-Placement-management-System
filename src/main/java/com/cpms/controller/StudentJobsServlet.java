package com.cpms.controller;

import com.cpms.dao.JobPostingDAO;
import com.cpms.util.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

public class StudentJobsServlet extends HttpServlet {
    private final JobPostingDAO jobDAO = new JobPostingDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!WebUtil.isStudentLoggedIn(request)) {
            WebUtil.flash(request, "danger", "Please login first.");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        try {
            request.setAttribute("jobs", jobDAO.findAllWithCompanyByDeadlineDesc());
            request.setAttribute("title", "Available Jobs - CPMS");
            request.setAttribute("bodyClass", "student-jobs");
            request.getRequestDispatcher("/WEB-INF/views/student/jobs.jsp").forward(request, response);
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
}
