package com.cpms.dao;

import com.cpms.model.Admin;
import com.cpms.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAO {
    public Admin findByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM admin WHERE username = ?";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        }
    }

    static Admin map(ResultSet rs) throws SQLException {
        Admin admin = new Admin();
        admin.setAdminId(rs.getInt("admin_id"));
        admin.setUsername(rs.getString("username"));
        admin.setPassword(rs.getString("password"));
        admin.setRole(rs.getString("role"));
        admin.setEmail(rs.getString("email"));
        admin.setContact(rs.getString("contact"));
        return admin;
    }
}
