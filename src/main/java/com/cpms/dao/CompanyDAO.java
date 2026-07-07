package com.cpms.dao;

import com.cpms.model.Company;
import com.cpms.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompanyDAO {
    public Company findByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM company WHERE email = ?";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        }
    }

    public List<Company> findAllOrderedByName() throws SQLException {
        List<Company> companies = new ArrayList<>();
        String sql = "SELECT * FROM company ORDER BY company_name";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                companies.add(map(rs));
            }
        }
        return companies;
    }

    public int count() throws SQLException {
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM company");
             ResultSet rs = statement.executeQuery()) {
            rs.next();
            return rs.getInt(1);
        }
    }

    public void create(Company company) throws SQLException {
        String sql = "INSERT INTO company (company_name, hr_name, email, contact, location) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, company.getCompanyName());
            statement.setString(2, company.getHrName());
            statement.setString(3, company.getEmail());
            statement.setString(4, company.getContact());
            statement.setString(5, company.getLocation());
            statement.executeUpdate();
        }
    }

    static Company map(ResultSet rs) throws SQLException {
        Company company = new Company();
        company.setCompanyId(rs.getInt("company_id"));
        company.setCompanyName(rs.getString("company_name"));
        company.setHrName(rs.getString("hr_name"));
        company.setEmail(rs.getString("email"));
        company.setContact(rs.getString("contact"));
        company.setLocation(rs.getString("location"));
        return company;
    }
}
