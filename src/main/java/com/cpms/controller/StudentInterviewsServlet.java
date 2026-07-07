package com.cpms.controller;

import com.cpms.dao.InterviewScheduleDAO;
import com.cpms.util.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

public class StudentInterviewsServlet extends HttpServlet {
    private final InterviewScheduleDAO interviewDAO = new InterviewScheduleDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!WebUtil.isStudentLoggedIn(request)) {
            WebUtil.flash(request, "danger", "Please login first.");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        try {
            request.setAttribute("interviews", interviewDAO.findByStudent(WebUtil.loggedStudentId(request)));
            request.setAttribute("title", "My Interviews - CPMS");
            request.setAttribute("bodyClass", "student-interviews");
            request.getRequestDispatcher("/WEB-INF/views/student/interviews.jsp").forward(request, response);
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
}
