package com.cpms.controller;

import com.cpms.dao.JobApplicationDAO;
import com.cpms.util.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

public class AdminApplicationsServlet extends HttpServlet {
    private final JobApplicationDAO applicationDAO = new JobApplicationDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!WebUtil.isAdminLoggedIn(request)) {
            WebUtil.flash(request, "danger", "Admin access required. Please login.");
            response.sendRedirect(request.getContextPath() + "/admin/login");
            return;
        }
        try {
            request.setAttribute("applications", applicationDAO.findAll());
            request.setAttribute("title", "All Applications - CPMS");
            request.setAttribute("bodyClass", "admin-applications");
            request.getRequestDispatcher("/WEB-INF/views/admin/applications.jsp").forward(request, response);
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
}
