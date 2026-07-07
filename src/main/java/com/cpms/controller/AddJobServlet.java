package com.cpms.controller;

import com.cpms.dao.CompanyDAO;
import com.cpms.dao.JobPostingDAO;
import com.cpms.model.JobPosting;
import com.cpms.util.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class AddJobServlet extends HttpServlet {
    private final CompanyDAO companyDAO = new CompanyDAO();
    private final JobPostingDAO jobDAO = new JobPostingDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!WebUtil.isAdminLoggedIn(request)) {
            WebUtil.flash(request, "danger", "Admin access required. Please login.");
            response.sendRedirect(request.getContextPath() + "/admin/login");
            return;
        }
        try {
            request.setAttribute("companies", companyDAO.findAllOrderedByName());
            request.setAttribute("title", "Add Job Posting - CPMS");
            request.setAttribute("bodyClass", "admin-job");
            request.getRequestDispatcher("/WEB-INF/views/admin/add_job.jsp").forward(request, response);
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (!WebUtil.isAdminLoggedIn(request)) {
            WebUtil.flash(request, "danger", "Admin access required. Please login.");
            response.sendRedirect(request.getContextPath() + "/admin/login");
            return;
        }
        try {
            JobPosting job = new JobPosting();
            job.setCompanyId(Integer.parseInt(request.getParameter("company_id")));
            job.setJobTitle(request.getParameter("job_title"));
            job.setSalary(blankToNull(request.getParameter("salary")));
            job.setDeadline(LocalDate.parse(request.getParameter("deadline")));
            jobDAO.create(job);
            WebUtil.flash(request, "success", "Job '" + job.getJobTitle() + "' posted successfully!");
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private String blankToNull(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }
}
