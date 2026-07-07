package com.cpms.controller;

import com.cpms.dao.JobApplicationDAO;
import com.cpms.dao.JobPostingDAO;
import com.cpms.model.JobApplication;
import com.cpms.model.JobPosting;
import com.cpms.util.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class ApplyJobServlet extends HttpServlet {
    private final JobPostingDAO jobDAO = new JobPostingDAO();
    private final JobApplicationDAO applicationDAO = new JobApplicationDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!WebUtil.isStudentLoggedIn(request)) {
            WebUtil.flash(request, "danger", "Please login first.");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        try {
            int jobId = Integer.parseInt(request.getParameter("jobId"));
            int studentId = WebUtil.loggedStudentId(request);
            JobPosting job = jobDAO.findById(jobId);
            if (job == null) {
                WebUtil.flash(request, "danger", "Job not found.");
                response.sendRedirect(request.getContextPath() + "/student/jobs");
                return;
            }
            if (applicationDAO.exists(studentId, jobId)) {
                WebUtil.flash(request, "danger", "You have already applied for this job.");
                response.sendRedirect(request.getContextPath() + "/student/jobs");
                return;
            }
            JobApplication application = new JobApplication();
            application.setStudentId(studentId);
            application.setJobId(jobId);
            application.setApplyDate(LocalDate.now());
            application.setStatus("Applied");
            applicationDAO.create(application);
            WebUtil.flash(request, "success", "Application submitted successfully!");
            response.sendRedirect(request.getContextPath() + "/student/applications");
        } catch (NumberFormatException ex) {
            WebUtil.flash(request, "danger", "Job not found.");
            response.sendRedirect(request.getContextPath() + "/student/jobs");
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
}
