package com.cpms.controller;

import com.cpms.dao.JobApplicationDAO;
import com.cpms.util.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

public class UpdateStatusServlet extends HttpServlet {
    private final JobApplicationDAO applicationDAO = new JobApplicationDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (!WebUtil.isAdminLoggedIn(request)) {
            WebUtil.flash(request, "danger", "Admin access required. Please login.");
            response.sendRedirect(request.getContextPath() + "/admin/login");
            return;
        }
        try {
            int applicationId = Integer.parseInt(request.getParameter("applicationId"));
            if (!applicationDAO.existsById(applicationId)) {
                WebUtil.flash(request, "danger", "Application not found.");
                response.sendRedirect(request.getContextPath() + "/admin/applications");
                return;
            }
            String status = request.getParameter("status");
            applicationDAO.updateStatus(applicationId, status);
            WebUtil.flash(request, "success", "Status updated to '" + status + "'.");
            response.sendRedirect(request.getContextPath() + "/admin/applications");
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
}
