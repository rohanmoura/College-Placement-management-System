package com.cpms.controller;

import com.cpms.dao.AdminDAO;
import com.cpms.model.Admin;
import com.cpms.util.PasswordUtil;
import com.cpms.util.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

public class AdminLoginServlet extends HttpServlet {
    private final AdminDAO adminDAO = new AdminDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("title", "Admin Login - CPMS");
        request.getRequestDispatcher("/WEB-INF/views/admin_login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            Admin admin = adminDAO.findByUsername(request.getParameter("username"));
            if (admin != null && PasswordUtil.matches(request.getParameter("password"), admin.getPassword())) {
                request.getSession().setAttribute("adminId", admin.getAdminId());
                request.getSession().setAttribute("userType", "admin");
                WebUtil.flash(request, "success", "Admin login successful!");
                response.sendRedirect(request.getContextPath() + "/");
                return;
            }
            WebUtil.flash(request, "danger", "Invalid username or password.");
            response.sendRedirect(request.getContextPath() + "/admin/login");
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
}
