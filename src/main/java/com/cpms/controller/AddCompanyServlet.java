package com.cpms.controller;

import com.cpms.dao.CompanyDAO;
import com.cpms.model.Company;
import com.cpms.util.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

public class AddCompanyServlet extends HttpServlet {
    private final CompanyDAO companyDAO = new CompanyDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!WebUtil.isAdminLoggedIn(request)) {
            WebUtil.flash(request, "danger", "Admin access required. Please login.");
            response.sendRedirect(request.getContextPath() + "/admin/login");
            return;
        }
        request.setAttribute("title", "Add Company - CPMS");
        request.setAttribute("bodyClass", "admin-company");
        request.getRequestDispatcher("/WEB-INF/views/admin/add_company.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (!WebUtil.isAdminLoggedIn(request)) {
            WebUtil.flash(request, "danger", "Admin access required. Please login.");
            response.sendRedirect(request.getContextPath() + "/admin/login");
            return;
        }
        try {
            String email = request.getParameter("email");
            if (companyDAO.findByEmail(email) != null) {
                WebUtil.flash(request, "danger", "A company with this email already exists.");
                response.sendRedirect(request.getContextPath() + "/admin/company/add");
                return;
            }
            Company company = new Company();
            company.setCompanyName(request.getParameter("company_name"));
            company.setHrName(request.getParameter("hr_name"));
            company.setEmail(email);
            company.setContact(blankToNull(request.getParameter("contact")));
            company.setLocation(blankToNull(request.getParameter("location")));
            companyDAO.create(company);
            WebUtil.flash(request, "success", "Company '" + company.getCompanyName() + "' added successfully!");
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private String blankToNull(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }
}
