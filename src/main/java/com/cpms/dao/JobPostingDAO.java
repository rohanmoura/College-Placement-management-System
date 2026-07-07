package com.cpms.dao;

import com.cpms.model.Company;
import com.cpms.model.JobPosting;
import com.cpms.util.DBUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JobPostingDAO {
    public JobPosting findById(int jobId) throws SQLException {
        String sql = """
                SELECT j.*, c.company_name, c.hr_name, c.email, c.contact, c.location
                FROM job_posting j
                JOIN company c ON c.company_id = j.company_id
                WHERE j.job_id = ?
                """;
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, jobId);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next() ? mapWithCompany(rs) : null;
            }
        }
    }

    public List<JobPosting> findAllWithCompanyByDeadlineDesc() throws SQLException {
        List<JobPosting> jobs = new ArrayList<>();
        String sql = """
                SELECT j.*, c.company_name, c.hr_name, c.email, c.contact, c.location
                FROM job_posting j
                JOIN company c ON c.company_id = j.company_id
                ORDER BY j.deadline DESC
                """;
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                jobs.add(mapWithCompany(rs));
            }
        }
        return jobs;
    }

    public List<JobPosting> findAllWithCompanyByTitle() throws SQLException {
        List<JobPosting> jobs = new ArrayList<>();
        String sql = """
                SELECT j.*, c.company_name, c.hr_name, c.email, c.contact, c.location
                FROM job_posting j
                JOIN company c ON c.company_id = j.company_id
                ORDER BY j.job_title
                """;
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                jobs.add(mapWithCompany(rs));
            }
        }
        return jobs;
    }

    public int count() throws SQLException {
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM job_posting");
             ResultSet rs = statement.executeQuery()) {
            rs.next();
            return rs.getInt(1);
        }
    }

    public void create(JobPosting job) throws SQLException {
        String sql = "INSERT INTO job_posting (company_id, job_title, salary, deadline) VALUES (?, ?, ?, ?)";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, job.getCompanyId());
            statement.setString(2, job.getJobTitle());
            statement.setString(3, job.getSalary());
            statement.setDate(4, Date.valueOf(job.getDeadline()));
            statement.executeUpdate();
        }
    }

    static JobPosting map(ResultSet rs) throws SQLException {
        JobPosting job = new JobPosting();
        job.setJobId(rs.getInt("job_id"));
        job.setCompanyId(rs.getInt("company_id"));
        job.setJobTitle(rs.getString("job_title"));
        job.setSalary(rs.getString("salary"));
        job.setDeadline(rs.getDate("deadline").toLocalDate());
        return job;
    }

    static JobPosting mapWithCompany(ResultSet rs) throws SQLException {
        JobPosting job = map(rs);
        Company company = new Company();
        company.setCompanyId(rs.getInt("company_id"));
        company.setCompanyName(rs.getString("company_name"));
        company.setHrName(rs.getString("hr_name"));
        company.setEmail(rs.getString("email"));
        company.setContact(rs.getString("contact"));
        company.setLocation(rs.getString("location"));
        job.setCompany(company);
        return job;
    }
}
