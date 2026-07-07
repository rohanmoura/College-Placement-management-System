from functools import wraps
from flask import Blueprint, render_template, request, redirect, url_for, session, flash
from db import db
from models.student import Student
from models.company import Company
from models.job_posting import JobPosting
from models.job_application import JobApplication
from models.interview_schedule import InterviewSchedule

admin_bp = Blueprint("admin", __name__, url_prefix="/admin")


def admin_required(f):
    """Decorator to protect admin routes."""

    @wraps(f)
    def decorated(*args, **kwargs):
        if "admin_id" not in session:
            flash("Admin access required. Please login.", "danger")
            return redirect(url_for("auth.admin_login"))
        return f(*args, **kwargs)

    return decorated


# ──────────────────────────────────────────────
# Admin Dashboard
# ──────────────────────────────────────────────
@admin_bp.route("/dashboard")
@admin_required
def dashboard():
    total_students = Student.query.count()
    total_companies = Company.query.count()
    total_jobs = JobPosting.query.count()
    total_applications = JobApplication.query.count()

    return render_template(
        "admin/dashboard.html",
        total_students=total_students,
        total_companies=total_companies,
        total_jobs=total_jobs,
        total_applications=total_applications,
    )


# ──────────────────────────────────────────────
# Add Company
# ──────────────────────────────────────────────
@admin_bp.route("/company/add", methods=["GET", "POST"])
@admin_required
def add_company():
    if request.method == "POST":
        company_name = request.form["company_name"]
        hr_name = request.form["hr_name"]
        email = request.form["email"]
        contact = request.form.get("contact", "").strip() or None
        location = request.form.get("location", "").strip() or None

        # Check if email already exists
        if Company.query.filter_by(email=email).first():
            flash("A company with this email already exists.", "danger")
            return redirect(url_for("admin.add_company"))

        new_company = Company(
            company_name=company_name,
            hr_name=hr_name,
            email=email,
            contact=contact,
            location=location,
        )
        db.session.add(new_company)
        db.session.commit()

        flash(f"Company '{company_name}' added successfully!", "success")
        return redirect(url_for("admin.dashboard"))

    return render_template("admin/add_company.html")


# ──────────────────────────────────────────────
# Add Job Posting
# ──────────────────────────────────────────────
@admin_bp.route("/job/add", methods=["GET", "POST"])
@admin_required
def add_job():
    companies = Company.query.order_by(Company.company_name).all()

    if request.method == "POST":
        company_id = request.form["company_id"]
        job_title = request.form["job_title"]
        salary = request.form.get("salary", "").strip() or None
        deadline = request.form["deadline"]

        new_job = JobPosting(
            company_id=company_id,
            job_title=job_title,
            salary=salary,
            deadline=deadline,
        )
        db.session.add(new_job)
        db.session.commit()

        flash(f"Job '{job_title}' posted successfully!", "success")
        return redirect(url_for("admin.dashboard"))

    return render_template("admin/add_job.html", companies=companies)


# ──────────────────────────────────────────────
# View All Applications
# ──────────────────────────────────────────────
@admin_bp.route("/applications")
@admin_required
def applications():
    all_applications = (
        db.session.query(JobApplication)
        .join(JobApplication.student)
        .join(JobApplication.job)
        .join(JobPosting.company)
        .order_by(JobApplication.apply_date.desc())
        .all()
    )

    return render_template("admin/applications.html", applications=all_applications)


# ──────────────────────────────────────────────
# Update Application Status
# ──────────────────────────────────────────────
@admin_bp.route("/update-status/<int:application_id>", methods=["POST"])
@admin_required
def update_status(application_id):
    application = JobApplication.query.get(application_id)

    if not application:
        flash("Application not found.", "danger")
        return redirect(url_for("admin.applications"))

    new_status = request.form["status"]
    application.status = new_status
    db.session.commit()

    flash(f"Status updated to '{new_status}'.", "success")
    return redirect(url_for("admin.applications"))


# ──────────────────────────────────────────────
# Schedule Interview
# ──────────────────────────────────────────────
@admin_bp.route("/schedule", methods=["GET", "POST"])
@admin_required
def schedule_interview():
    students = Student.query.order_by(Student.name).all()
    jobs = (
        db.session.query(JobPosting)
        .join(JobPosting.company)
        .order_by(JobPosting.job_title)
        .all()
    )

    if request.method == "POST":
        student_id = request.form["student_id"]
        job_id = request.form["job_id"]
        interview_date = request.form["interview_date"]
        interview_time = request.form["interview_time"]

        # Get company_id from the selected job
        job = JobPosting.query.get(job_id)

        new_interview = InterviewSchedule(
            student_id=student_id,
            company_id=job.company_id,
            job_id=job_id,
            interview_date=interview_date,
            interview_time=interview_time,
        )
        db.session.add(new_interview)
        db.session.commit()

        flash("Interview scheduled successfully!", "success")
        return redirect(url_for("admin.dashboard"))

    return render_template("admin/schedule_interview.html", students=students, jobs=jobs)
