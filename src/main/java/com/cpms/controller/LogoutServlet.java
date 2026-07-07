package com.cpms.controller;

import com.cpms.util.WebUtil;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().invalidate();
        request.getSession(true);
        WebUtil.flash(request, "info", "You have been logged out.");
        response.sendRedirect(request.getContextPath() + "/login");
    }
}
