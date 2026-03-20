from db import db


class JobApplication(db.Model):
    """JobApplication model matching the job_application table in schema.sql."""

    __tablename__ = "job_application"

    application_id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    student_id = db.Column(db.Integer, db.ForeignKey("student.student_id"), nullable=False)
    job_id = db.Column(db.Integer, db.ForeignKey("job_posting.job_id"), nullable=False)
    apply_date = db.Column(db.Date, nullable=False)
    status = db.Column(db.String(50), default="Applied")

    # Relationships for easy access in templates
    student = db.relationship("Student", backref="applications")
    job = db.relationship("JobPosting", backref="applications")
