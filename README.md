# College Placement Management System (Java)

This project has been migrated from the original Flask/PostgreSQL implementation to a Java web application while preserving the same CPMS idea, roles, pages, workflows, and CRUD behavior.

## Current Tech Stack

- Java 17
- Servlet + JSP + JSTL
- JDBC
- MySQL
- Maven WAR
- Jetty Maven Plugin for local development

The original Python/Flask code is archived in `legacy-flask-cpms/` for reference only. The runnable submission is the Java Maven project in `src/`, `database/`, and `pom.xml`.

## Features

- Student registration and login
- Admin login
- Student dashboard with profile and total applications
- Available jobs listing
- Student job application workflow with duplicate-application prevention
- Student application status tracking
- Student interview schedule view
- Admin dashboard counts
- Admin add company
- Admin add job posting
- Admin view all applications
- Admin update application status
- Admin schedule interviews

## Database Setup

1. Start MySQL.
2. Import the schema and sample data:

```bash
mysql -u root -p < database/schema.sql
```

The default application connection is:

- URL: `jdbc:mysql://localhost:3306/cpms_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC`
- User: `root`
- Password: empty

You can override these without changing code:

```bash
set CPMS_DB_URL=jdbc:mysql://localhost:3306/cpms_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
set CPMS_DB_USER=root
set CPMS_DB_PASSWORD=your_password
```

Or pass JVM properties:

```bash
mvn jetty:run -Dcpms.db.user=root -Dcpms.db.password=your_password
```

## Sample Credentials

Both seeded accounts use the password `password`.

- Admin: `admin`
- Student: `student@cpms.local`

## Build

```bash
mvn clean package
```

The WAR is generated at:

```text
target/cpms.war
```

## Run Locally

```bash
mvn jetty:run
```

Open:

```text
http://localhost:8080/cpms/
```

If port 8080 is already busy:

```bash
mvn jetty:run -Djetty.http.port=8081
```

## Migrated Routes

- `/`
- `/register`
- `/login`
- `/admin/login`
- `/logout`
- `/student/dashboard`
- `/student/jobs`
- `/student/apply?jobId=1`
- `/student/applications`
- `/student/interviews`
- `/admin/dashboard`
- `/admin/company/add`
- `/admin/job/add`
- `/admin/applications`
- `/admin/update-status`
- `/admin/schedule`
