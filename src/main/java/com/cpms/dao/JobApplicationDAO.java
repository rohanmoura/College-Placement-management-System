package com.cpms.dao;

import com.cpms.model.Company;
import com.cpms.model.JobApplication;
import com.cpms.model.JobPosting;
import com.cpms.model.Student;
import com.cpms.util.DBUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JobApplicationDAO {
    public boolean exists(int studentId, int jobId) throws SQLException {
        String sql = "SELECT 1 FROM job_application WHERE student_id = ? AND job_id = ?";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);
            statement.setInt(2, jobId);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }
        }
    }

    public int countByStudent(int studentId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM job_application WHERE student_id = ?";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);
            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }
        }
    }

    public int count() throws SQLException {
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM job_application");
             ResultSet rs = statement.executeQuery()) {
            rs.next();
            return rs.getInt(1);
        }
    }

    public void create(JobApplication application) throws SQLException {
        String sql = "INSERT INTO job_application (student_id, job_id, apply_date, status) VALUES (?, ?, ?, ?)";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, application.getStudentId());
            statement.setInt(2, application.getJobId());
            statement.setDate(3, Date.valueOf(application.getApplyDate()));
            statement.setString(4, application.getStatus());
            statement.executeUpdate();
        }
    }

    public List<JobApplication> findByStudent(int studentId) throws SQLException {
        List<JobApplication> applications = new ArrayList<>();
        String sql = """
                SELECT a.*, s.name, s.email AS student_email, s.contact AS student_contact, s.course, s.year, s.skills, s.resume, s.password,
                       j.company_id, j.job_title, j.salary, j.deadline,
                       c.company_name, c.hr_name, c.email AS company_email, c.contact AS company_contact, c.location
                FROM job_application a
                JOIN student s ON s.student_id = a.student_id
                JOIN job_posting j ON j.job_id = a.job_id
                JOIN company c ON c.company_id = j.company_id
                WHERE a.student_id = ?
                ORDER BY a.apply_date DESC
                """;
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    applications.add(mapFull(rs));
                }
            }
        }
        return applications;
    }

    public List<JobApplication> findAll() throws SQLException {
        List<JobApplication> applications = new ArrayList<>();
        String sql = """
                SELECT a.*, s.name, s.email AS student_email, s.contact AS student_contact, s.course, s.year, s.skills, s.resume, s.password,
                       j.company_id, j.job_title, j.salary, j.deadline,
                       c.company_name, c.hr_name, c.email AS company_email, c.contact AS company_contact, c.location
                FROM job_application a
                JOIN student s ON s.student_id = a.student_id
                JOIN job_posting j ON j.job_id = a.job_id
                JOIN company c ON c.company_id = j.company_id
                ORDER BY a.apply_date DESC
                """;
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                applications.add(mapFull(rs));
            }
        }
        return applications;
    }

    public void updateStatus(int applicationId, String status) throws SQLException {
        String sql = "UPDATE job_application SET status = ? WHERE application_id = ?";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, status);
            statement.setInt(2, applicationId);
            statement.executeUpdate();
        }
    }

    public boolean existsById(int applicationId) throws SQLException {
        String sql = "SELECT 1 FROM job_application WHERE application_id = ?";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, applicationId);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }
        }
    }

    private JobApplication mapFull(ResultSet rs) throws SQLException {
        JobApplication application = new JobApplication();
        application.setApplicationId(rs.getInt("application_id"));
        application.setStudentId(rs.getInt("student_id"));
        application.setJobId(rs.getInt("job_id"));
        application.setApplyDate(rs.getDate("apply_date").toLocalDate());
        application.setStatus(rs.getString("status"));

        Student student = new Student();
        student.setStudentId(rs.getInt("student_id"));
        student.setName(rs.getString("name"));
        student.setEmail(rs.getString("student_email"));
        student.setContact(rs.getString("student_contact"));
        student.setCourse(rs.getString("course"));
        student.setYear(rs.getString("year"));
        student.setSkills(rs.getString("skills"));
        student.setResume(rs.getString("resume"));
        student.setPassword(rs.getString("password"));
        application.setStudent(student);

        Company company = new Company();
        company.setCompanyId(rs.getInt("company_id"));
        company.setCompanyName(rs.getString("company_name"));
        company.setHrName(rs.getString("hr_name"));
        company.setEmail(rs.getString("company_email"));
        company.setContact(rs.getString("company_contact"));
        company.setLocation(rs.getString("location"));

        JobPosting job = new JobPosting();
        job.setJobId(rs.getInt("job_id"));
        job.setCompanyId(rs.getInt("company_id"));
        job.setJobTitle(rs.getString("job_title"));
        job.setSalary(rs.getString("salary"));
        job.setDeadline(rs.getDate("deadline").toLocalDate());
        job.setCompany(company);
        application.setJob(job);
        return application;
    }
}
