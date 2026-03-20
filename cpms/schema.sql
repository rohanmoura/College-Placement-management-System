-- ============================================
-- College Placement Management System (CPMS)
-- PostgreSQL Database Schema
-- ============================================

-- -- Create the database
-- CREATE DATABASE cpms_db;

-- Connect to the database
-- \c cpms_db;

-- ============================================
-- 1. STUDENT TABLE
-- ============================================
CREATE TABLE student (
    student_id    SERIAL        PRIMARY KEY,
    name          VARCHAR(100)  NOT NULL,
    email         VARCHAR(100)  UNIQUE NOT NULL,
    contact       VARCHAR(15)   UNIQUE NOT NULL,
    course        VARCHAR(50)   NOT NULL,
    year          VARCHAR(20)   NOT NULL,
    skills        VARCHAR(255),
    resume        VARCHAR(255),
    password      VARCHAR(255)  NOT NULL
);

-- ============================================
-- 2. ADMIN TABLE
-- ============================================
CREATE TABLE admin (
    admin_id    SERIAL        PRIMARY KEY,
    username    VARCHAR(50)   UNIQUE NOT NULL,
    password    VARCHAR(100)  NOT NULL,
    role        VARCHAR(30)   DEFAULT 'Admin',
    email       VARCHAR(100)  UNIQUE,
    contact     VARCHAR(15)   UNIQUE
);

-- ============================================
-- 3. COMPANY TABLE
-- ============================================
CREATE TABLE company (
    company_id    SERIAL        PRIMARY KEY,
    company_name  VARCHAR(150)  NOT NULL,
    hr_name       VARCHAR(100)  NOT NULL,
    email         VARCHAR(100)  UNIQUE NOT NULL,
    contact       VARCHAR(15)   UNIQUE,
    location      VARCHAR(100)
);

-- ============================================
-- 4. RECRUITER TABLE
-- ============================================
CREATE TABLE recruiter (
    recruiter_id  SERIAL        PRIMARY KEY,
    name          VARCHAR(100)  NOT NULL,
    email         VARCHAR(100)  UNIQUE NOT NULL,
    contact       VARCHAR(15)   UNIQUE,
    position      VARCHAR(50),
    company_id    INT           REFERENCES company(company_id)
);

-- ============================================
-- 5. JOB POSTING TABLE
-- ============================================
CREATE TABLE job_posting (
    job_id        SERIAL        PRIMARY KEY,
    company_id    INT           NOT NULL REFERENCES company(company_id),
    job_title     VARCHAR(150)  NOT NULL,
    salary        VARCHAR(50),
    deadline      DATE          NOT NULL
);

-- ============================================
-- 6. JOB APPLICATION TABLE
-- ============================================
CREATE TABLE job_application (
    application_id  SERIAL       PRIMARY KEY,
    student_id      INT          NOT NULL REFERENCES student(student_id),
    job_id          INT          NOT NULL REFERENCES job_posting(job_id),
    apply_date      DATE         NOT NULL,
    status          VARCHAR(50)  DEFAULT 'Applied'
);

-- ============================================
-- 7. INTERVIEW SCHEDULE TABLE
-- ============================================
CREATE TABLE interview_schedule (
    interview_id    SERIAL  PRIMARY KEY,
    student_id      INT     NOT NULL REFERENCES student(student_id),
    company_id      INT     NOT NULL REFERENCES company(company_id),
    job_id          INT     NOT NULL REFERENCES job_posting(job_id),
    interview_date  DATE    NOT NULL,
    interview_time  TIME    NOT NULL
);

-- ============================================
-- 8. SELECTION ROUND TABLE
-- ============================================
CREATE TABLE selection_round (
    round_id      SERIAL        PRIMARY KEY,
    job_id        INT           NOT NULL REFERENCES job_posting(job_id),
    round_name    VARCHAR(100)  NOT NULL,
    max_score     INT,
    instructions  VARCHAR(250)
);

-- ============================================
-- 9. STUDENT ROUND SCORE TABLE
-- ============================================
CREATE TABLE student_round_score (
    score_id    SERIAL       PRIMARY KEY,
    student_id  INT          NOT NULL REFERENCES student(student_id),
    round_id    INT          NOT NULL REFERENCES selection_round(round_id),
    score       INT,
    status      VARCHAR(50)
);
