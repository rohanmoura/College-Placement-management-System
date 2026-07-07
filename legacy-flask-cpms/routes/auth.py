from flask import Blueprint, render_template, request, redirect, url_for, session, flash
from werkzeug.security import generate_password_hash, check_password_hash
from db import db
from models.student import Student
from models.admin import Admin

auth_bp = Blueprint("auth", __name__)


# ──────────────────────────────────────────────
# Student Registration
# ──────────────────────────────────────────────
@auth_bp.route("/register", methods=["GET", "POST"])
def register():
    if request.method == "POST":
        name = request.form["name"]
        email = request.form["email"]
        contact = request.form["contact"]
        course = request.form["course"]
        year = request.form["year"]
        skills = request.form.get("skills", "")
        password = request.form["password"]

        # Check if email already exists
        if Student.query.filter_by(email=email).first():
            flash("Email already registered.", "danger")
            return redirect(url_for("auth.register"))

        # Check if contact already exists
        if Student.query.filter_by(contact=contact).first():
            flash("Contact number already registered.", "danger")
            return redirect(url_for("auth.register"))

        # Create new student with hashed password
        new_student = Student(
            name=name,
            email=email,
            contact=contact,
            course=course,
            year=year,
            skills=skills,
            password=generate_password_hash(password),
        )
        db.session.add(new_student)
        db.session.commit()

        flash("Registration successful! Please login.", "success")
        return redirect(url_for("auth.login"))

    return render_template("register.html")


# ──────────────────────────────────────────────
# Student Login
# ──────────────────────────────────────────────
@auth_bp.route("/login", methods=["GET", "POST"])
def login():
    if request.method == "POST":
        email = request.form["email"]
        password = request.form["password"]

        student = Student.query.filter_by(email=email).first()

        if student and check_password_hash(student.password, password):
            session["student_id"] = student.student_id
            session["user_type"] = "student"
            flash("Login successful!", "success")
            return redirect(url_for("main.home"))

        flash("Invalid email or password.", "danger")
        return redirect(url_for("auth.login"))

    return render_template("login.html")


# ──────────────────────────────────────────────
# Admin Login
# ──────────────────────────────────────────────
@auth_bp.route("/admin/login", methods=["GET", "POST"])
def admin_login():
    if request.method == "POST":
        username = request.form["username"]
        password = request.form["password"]

        admin = Admin.query.filter_by(username=username).first()

        if admin and check_password_hash(admin.password, password):
            session["admin_id"] = admin.admin_id
            session["user_type"] = "admin"
            flash("Admin login successful!", "success")
            return redirect(url_for("main.home"))

        flash("Invalid username or password.", "danger")
        return redirect(url_for("auth.admin_login"))

    return render_template("admin_login.html")


# ──────────────────────────────────────────────
# Logout (works for both student and admin)
# ──────────────────────────────────────────────
@auth_bp.route("/logout")
def logout():
    session.clear()
    flash("You have been logged out.", "info")
    return redirect(url_for("auth.login"))
