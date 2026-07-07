package com.cpms.controller;

import com.cpms.dao.StudentDAO;
import com.cpms.model.Student;
import com.cpms.util.PasswordUtil;
import com.cpms.util.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

public class StudentLoginServlet extends HttpServlet {
    private final StudentDAO studentDAO = new StudentDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("title", "Student Login - CPMS");
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            Student student = studentDAO.findByEmail(request.getParameter("email"));
            if (student != null && PasswordUtil.matches(request.getParameter("password"), student.getPassword())) {
                request.getSession().setAttribute("studentId", student.getStudentId());
                request.getSession().setAttribute("userType", "student");
                WebUtil.flash(request, "success", "Login successful!");
                response.sendRedirect(request.getContextPath() + "/");
                return;
            }
            WebUtil.flash(request, "danger", "Invalid email or password.");
            response.sendRedirect(request.getContextPath() + "/login");
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
}
