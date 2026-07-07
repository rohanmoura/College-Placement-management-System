package com.cpms.dao;

import com.cpms.model.Student;
import com.cpms.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    public Student findByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM student WHERE email = ?";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        }
    }

    public Student findByContact(String contact) throws SQLException {
        String sql = "SELECT * FROM student WHERE contact = ?";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, contact);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        }
    }

    public Student findById(int studentId) throws SQLException {
        String sql = "SELECT * FROM student WHERE student_id = ?";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        }
    }

    public List<Student> findAllOrderedByName() throws SQLException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM student ORDER BY name";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                students.add(map(rs));
            }
        }
        return students;
    }

    public int count() throws SQLException {
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM student");
             ResultSet rs = statement.executeQuery()) {
            rs.next();
            return rs.getInt(1);
        }
    }

    public void create(Student student) throws SQLException {
        String sql = """
                INSERT INTO student (name, email, contact, course, year, skills, resume, password)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, student.getName());
            statement.setString(2, student.getEmail());
            statement.setString(3, student.getContact());
            statement.setString(4, student.getCourse());
            statement.setString(5, student.getYear());
            statement.setString(6, student.getSkills());
            statement.setString(7, student.getResume());
            statement.setString(8, student.getPassword());
            statement.executeUpdate();
        }
    }

    static Student map(ResultSet rs) throws SQLException {
        Student student = new Student();
        student.setStudentId(rs.getInt("student_id"));
        student.setName(rs.getString("name"));
        student.setEmail(rs.getString("email"));
        student.setContact(rs.getString("contact"));
        student.setCourse(rs.getString("course"));
        student.setYear(rs.getString("year"));
        student.setSkills(rs.getString("skills"));
        student.setResume(rs.getString("resume"));
        student.setPassword(rs.getString("password"));
        return student;
    }
}
