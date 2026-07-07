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

public class RegisterServlet extends HttpServlet {
    private final StudentDAO studentDAO = new StudentDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("title", "Register - CPMS");
        request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            String email = request.getParameter("email");
            String contact = request.getParameter("contact");
            if (studentDAO.findByEmail(email) != null) {
                WebUtil.flash(request, "danger", "Email already registered.");
                response.sendRedirect(request.getContextPath() + "/register");
                return;
            }
            if (studentDAO.findByContact(contact) != null) {
                WebUtil.flash(request, "danger", "Contact number already registered.");
                response.sendRedirect(request.getContextPath() + "/register");
                return;
            }

            Student student = new Student();
            student.setName(request.getParameter("name"));
            student.setEmail(email);
            student.setContact(contact);
            student.setCourse(request.getParameter("course"));
            student.setYear(request.getParameter("year"));
            student.setSkills(request.getParameter("skills"));
            student.setPassword(PasswordUtil.hash(request.getParameter("password")));
            studentDAO.create(student);

            WebUtil.flash(request, "success", "Registration successful! Please login.");
            response.sendRedirect(request.getContextPath() + "/login");
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
}
