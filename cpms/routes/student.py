from datetime import date
from flask import Blueprint, render_template, redirect, url_for, session, flash
from db import db
from models.student import Student
from models.job_posting import JobPosting
from models.job_application import JobApplication

student_bp = Blueprint("student", __name__, url_prefix="/student")


def login_required(f):
    """Decorator to protect student routes."""
    from functools import wraps

    @wraps(f)
    def decorated(*args, **kwargs):
        if "student_id" not in session:
            flash("Please login first.", "danger")
            return redirect(url_for("auth.login"))
        return f(*args, **kwargs)

    return decorated


# ──────────────────────────────────────────────
# Student Dashboard
# ──────────────────────────────────────────────
@student_bp.route("/dashboard")
@login_required
def dashboard():
    student = Student.query.get(session["student_id"])
    total_applications = JobApplication.query.filter_by(
        student_id=student.student_id
    ).count()

    return render_template(
        "student/dashboard.html",
        student=student,
        total_applications=total_applications,
    )


# ──────────────────────────────────────────────
# View All Jobs
# ──────────────────────────────────────────────
@student_bp.route("/jobs")
@login_required
def jobs():
    all_jobs = (
        db.session.query(JobPosting)
        .join(JobPosting.company)
        .order_by(JobPosting.deadline.desc())
        .all()
    )

    return render_template("student/jobs.html", jobs=all_jobs)


# ──────────────────────────────────────────────
# Apply for a Job
# ──────────────────────────────────────────────
@student_bp.route("/apply/<int:job_id>")
@login_required
def apply(job_id):
    student_id = session["student_id"]

    # Check if job exists
    job = JobPosting.query.get(job_id)
    if not job:
        flash("Job not found.", "danger")
        return redirect(url_for("student.jobs"))

    # Check if already applied
    existing = JobApplication.query.filter_by(
        student_id=student_id, job_id=job_id
    ).first()
    if existing:
        flash("You have already applied for this job.", "danger")
        return redirect(url_for("student.jobs"))

    # Create new application
    application = JobApplication(
        student_id=student_id,
        job_id=job_id,
        apply_date=date.today(),
        status="Applied",
    )
    db.session.add(application)
    db.session.commit()

    flash("Application submitted successfully!", "success")
    return redirect(url_for("student.applications"))


# ──────────────────────────────────────────────
# View Applied Jobs
# ──────────────────────────────────────────────
@student_bp.route("/applications")
@login_required
def applications():
    student_id = session["student_id"]

    my_applications = (
        db.session.query(JobApplication)
        .join(JobApplication.job)
        .join(JobPosting.company)
        .filter(JobApplication.student_id == student_id)
        .order_by(JobApplication.apply_date.desc())
        .all()
    )

    return render_template("student/applications.html", applications=my_applications)
