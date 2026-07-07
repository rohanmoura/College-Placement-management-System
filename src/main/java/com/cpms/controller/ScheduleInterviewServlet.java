package com.cpms.controller;

import com.cpms.dao.InterviewScheduleDAO;
import com.cpms.dao.JobPostingDAO;
import com.cpms.dao.StudentDAO;
import com.cpms.model.InterviewSchedule;
import com.cpms.model.JobPosting;
import com.cpms.util.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class ScheduleInterviewServlet extends HttpServlet {
    private final StudentDAO studentDAO = new StudentDAO();
    private final JobPostingDAO jobDAO = new JobPostingDAO();
    private final InterviewScheduleDAO interviewDAO = new InterviewScheduleDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!WebUtil.isAdminLoggedIn(request)) {
            WebUtil.flash(request, "danger", "Admin access required. Please login.");
            response.sendRedirect(request.getContextPath() + "/admin/login");
            return;
        }
        try {
            request.setAttribute("students", studentDAO.findAllOrderedByName());
            request.setAttribute("jobs", jobDAO.findAllWithCompanyByTitle());
            request.setAttribute("title", "Schedule Interview - CPMS");
            request.setAttribute("bodyClass", "admin-schedule");
            request.getRequestDispatcher("/WEB-INF/views/admin/schedule_interview.jsp").forward(request, response);
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
            int jobId = Integer.parseInt(request.getParameter("job_id"));
            JobPosting job = jobDAO.findById(jobId);
            if (job == null) {
                WebUtil.flash(request, "danger", "Job not found.");
                response.sendRedirect(request.getContextPath() + "/admin/schedule");
                return;
            }
            InterviewSchedule interview = new InterviewSchedule();
            interview.setStudentId(Integer.parseInt(request.getParameter("student_id")));
            interview.setJobId(jobId);
            interview.setCompanyId(job.getCompanyId());
            interview.setInterviewDate(LocalDate.parse(request.getParameter("interview_date")));
            interview.setInterviewTime(LocalTime.parse(request.getParameter("interview_time")));
            interviewDAO.create(interview);
            WebUtil.flash(request, "success", "Interview scheduled successfully!");
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
}
