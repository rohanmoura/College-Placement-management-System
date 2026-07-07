package com.cpms.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public final class WebUtil {
    private WebUtil() {
    }

    public static void flash(HttpServletRequest request, String type, String message) {
        request.getSession().setAttribute("flashType", type);
        request.getSession().setAttribute("flashMessage", message);
    }

    public static boolean isStudentLoggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute("studentId") != null;
    }

    public static boolean isAdminLoggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute("adminId") != null;
    }

    public static int loggedStudentId(HttpServletRequest request) {
        return (Integer) request.getSession().getAttribute("studentId");
    }

    public static int loggedAdminId(HttpServletRequest request) {
        return (Integer) request.getSession().getAttribute("adminId");
    }
}
