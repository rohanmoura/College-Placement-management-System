package com.cpms.controller;

import com.cpms.util.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (WebUtil.isStudentLoggedIn(request)) {
            response.sendRedirect(request.getContextPath() + "/student/dashboard");
            return;
        }
        if (WebUtil.isAdminLoggedIn(request)) {
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            return;
        }
        request.setAttribute("title", "CPMS - College Placement Management System");
        request.setAttribute("bodyClass", "home-page");
        request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
    }
}
