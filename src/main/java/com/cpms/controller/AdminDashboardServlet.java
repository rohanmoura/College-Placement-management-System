package com.cpms.controller;

import com.cpms.dao.CompanyDAO;
import com.cpms.dao.JobApplicationDAO;
import com.cpms.dao.JobPostingDAO;
import com.cpms.dao.StudentDAO;
import com.cpms.util.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

public class AdminDashboardServlet extends HttpServlet {
    private final StudentDAO studentDAO = new StudentDAO();
    private final CompanyDAO companyDAO = new CompanyDAO();
    private final JobPostingDAO jobDAO = new JobPostingDAO();
    private final JobApplicationDAO applicationDAO = new JobApplicationDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!WebUtil.isAdminLoggedIn(request)) {
            WebUtil.flash(request, "danger", "Admin access required. Please login.");
            response.sendRedirect(request.getContextPath() + "/admin/login");
            return;
        }
        try {
            request.setAttribute("totalStudents", studentDAO.count());
            request.setAttribute("totalCompanies", companyDAO.count());
            request.setAttribute("totalJobs", jobDAO.count());
            request.setAttribute("totalApplications", applicationDAO.count());
            request.setAttribute("title", "Admin Dashboard - CPMS");
            request.setAttribute("bodyClass", "admin-dashboard");
            request.getRequestDispatcher("/WEB-INF/views/admin/dashboard.jsp").forward(request, response);
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
}
