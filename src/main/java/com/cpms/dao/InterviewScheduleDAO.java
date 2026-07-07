package com.cpms.dao;

import com.cpms.model.Company;
import com.cpms.model.InterviewSchedule;
import com.cpms.model.JobPosting;
import com.cpms.model.Student;
import com.cpms.util.DBUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class InterviewScheduleDAO {
    public void create(InterviewSchedule interview) throws SQLException {
        String sql = """
                INSERT INTO interview_schedule (student_id, company_id, job_id, interview_date, interview_time)
                VALUES (?, ?, ?, ?, ?)
                """;
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, interview.getStudentId());
            statement.setInt(2, interview.getCompanyId());
            statement.setInt(3, interview.getJobId());
            statement.setDate(4, Date.valueOf(interview.getInterviewDate()));
            statement.setTime(5, Time.valueOf(interview.getInterviewTime()));
            statement.executeUpdate();
        }
    }

    public List<InterviewSchedule> findByStudent(int studentId) throws SQLException {
        List<InterviewSchedule> interviews = new ArrayList<>();
        String sql = """
                SELECT i.*, s.name, s.email AS student_email, s.contact AS student_contact, s.course, s.year, s.skills, s.resume, s.password,
                       j.job_title, j.salary, j.deadline,
                       c.company_name, c.hr_name, c.email AS company_email, c.contact AS company_contact, c.location
                FROM interview_schedule i
                JOIN student s ON s.student_id = i.student_id
                JOIN job_posting j ON j.job_id = i.job_id
                JOIN company c ON c.company_id = i.company_id
                WHERE i.student_id = ?
                ORDER BY i.interview_date DESC
                """;
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    interviews.add(mapFull(rs));
                }
            }
        }
        return interviews;
    }

    private InterviewSchedule mapFull(ResultSet rs) throws SQLException {
        InterviewSchedule interview = new InterviewSchedule();
        interview.setInterviewId(rs.getInt("interview_id"));
        interview.setStudentId(rs.getInt("student_id"));
        interview.setCompanyId(rs.getInt("company_id"));
        interview.setJobId(rs.getInt("job_id"));
        interview.setInterviewDate(rs.getDate("interview_date").toLocalDate());
        interview.setInterviewTime(rs.getTime("interview_time").toLocalTime());

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
        interview.setStudent(student);

        Company company = new Company();
        company.setCompanyId(rs.getInt("company_id"));
        company.setCompanyName(rs.getString("company_name"));
        company.setHrName(rs.getString("hr_name"));
        company.setEmail(rs.getString("company_email"));
        company.setContact(rs.getString("company_contact"));
        company.setLocation(rs.getString("location"));
        interview.setCompany(company);

        JobPosting job = new JobPosting();
        job.setJobId(rs.getInt("job_id"));
        job.setCompanyId(rs.getInt("company_id"));
        job.setJobTitle(rs.getString("job_title"));
        job.setSalary(rs.getString("salary"));
        job.setDeadline(rs.getDate("deadline").toLocalDate());
        job.setCompany(company);
        interview.setJob(job);
        return interview;
    }
}
