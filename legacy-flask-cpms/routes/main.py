from flask import Blueprint, redirect, url_for, session, render_template

main_bp = Blueprint("main", __name__)


@main_bp.route("/")
def home():
    """Landing page with login/register links, or redirect if logged in."""
    if session.get("student_id"):
        return redirect(url_for("student.dashboard"))
    if session.get("admin_id"):
        return redirect(url_for("admin.dashboard"))

    return render_template("home.html")
