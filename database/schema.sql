CREATE DATABASE IF NOT EXISTS cpms_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE cpms_db;

DROP TABLE IF EXISTS student_round_score;
DROP TABLE IF EXISTS selection_round;
DROP TABLE IF EXISTS interview_schedule;
DROP TABLE IF EXISTS job_application;
DROP TABLE IF EXISTS job_posting;
DROP TABLE IF EXISTS recruiter;
DROP TABLE IF EXISTS company;
DROP TABLE IF EXISTS admin;
DROP TABLE IF EXISTS student;

CREATE TABLE student (
    student_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    contact VARCHAR(15) NOT NULL UNIQUE,
    course VARCHAR(50) NOT NULL,
    year VARCHAR(20) NOT NULL,
    skills VARCHAR(255),
    resume VARCHAR(255),
    password VARCHAR(255) NOT NULL
);

CREATE TABLE admin (
    admin_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(30) DEFAULT 'Admin',
    email VARCHAR(100) UNIQUE,
    contact VARCHAR(15) UNIQUE
);

CREATE TABLE company (
    company_id INT AUTO_INCREMENT PRIMARY KEY,
    company_name VARCHAR(150) NOT NULL,
    hr_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    contact VARCHAR(15) UNIQUE,
    location VARCHAR(100)
);

CREATE TABLE recruiter (
    recruiter_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    contact VARCHAR(15) UNIQUE,
    position VARCHAR(50),
    company_id INT,
    CONSTRAINT fk_recruiter_company FOREIGN KEY (company_id) REFERENCES company(company_id)
);

CREATE TABLE job_posting (
    job_id INT AUTO_INCREMENT PRIMARY KEY,
    company_id INT NOT NULL,
    job_title VARCHAR(150) NOT NULL,
    salary VARCHAR(50),
    deadline DATE NOT NULL,
    CONSTRAINT fk_job_company FOREIGN KEY (company_id) REFERENCES company(company_id)
);

CREATE TABLE job_application (
    application_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT NOT NULL,
    job_id INT NOT NULL,
    apply_date DATE NOT NULL,
    status VARCHAR(50) DEFAULT 'Applied',
    CONSTRAINT uq_student_job UNIQUE (student_id, job_id),
    CONSTRAINT fk_application_student FOREIGN KEY (student_id) REFERENCES student(student_id),
    CONSTRAINT fk_application_job FOREIGN KEY (job_id) REFERENCES job_posting(job_id)
);

CREATE TABLE interview_schedule (
    interview_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT NOT NULL,
    company_id INT NOT NULL,
    job_id INT NOT NULL,
    interview_date DATE NOT NULL,
    interview_time TIME NOT NULL,
    CONSTRAINT fk_interview_student FOREIGN KEY (student_id) REFERENCES student(student_id),
    CONSTRAINT fk_interview_company FOREIGN KEY (company_id) REFERENCES company(company_id),
    CONSTRAINT fk_interview_job FOREIGN KEY (job_id) REFERENCES job_posting(job_id)
);

CREATE TABLE selection_round (
    round_id INT AUTO_INCREMENT PRIMARY KEY,
    job_id INT NOT NULL,
    round_name VARCHAR(100) NOT NULL,
    max_score INT,
    instructions VARCHAR(250),
    CONSTRAINT fk_round_job FOREIGN KEY (job_id) REFERENCES job_posting(job_id)
);

CREATE TABLE student_round_score (
    score_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT NOT NULL,
    round_id INT NOT NULL,
    score INT,
    status VARCHAR(50),
    CONSTRAINT fk_score_student FOREIGN KEY (student_id) REFERENCES student(student_id),
    CONSTRAINT fk_score_round FOREIGN KEY (round_id) REFERENCES selection_round(round_id)
);

INSERT INTO admin (username, password, role, email, contact) VALUES
('admin', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'Admin', 'admin@cpms.local', '9999999999');

INSERT INTO student (name, email, contact, course, year, skills, password) VALUES
('Demo Student', 'student@cpms.local', '8888888888', 'B.Tech', 'Final Year', 'Java, SQL, Web Development', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy');

INSERT INTO company (company_name, hr_name, email, contact, location) VALUES
('TechNova Solutions', 'Priya Sharma', 'hr@technova.local', '7777777777', 'Bengaluru'),
('CloudWorks India', 'Rahul Mehta', 'careers@cloudworks.local', '6666666666', 'Pune');

INSERT INTO job_posting (company_id, job_title, salary, deadline) VALUES
(1, 'Java Developer Trainee', '6 LPA', DATE_ADD(CURDATE(), INTERVAL 30 DAY)),
(2, 'Web Application Intern', '3 LPA', DATE_ADD(CURDATE(), INTERVAL 20 DAY));
