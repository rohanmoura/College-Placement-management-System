from db import db


class InterviewSchedule(db.Model):
    """InterviewSchedule model matching the interview_schedule table in schema.sql."""

    __tablename__ = "interview_schedule"

    interview_id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    student_id = db.Column(db.Integer, db.ForeignKey("student.student_id"), nullable=False)
    company_id = db.Column(db.Integer, db.ForeignKey("company.company_id"), nullable=False)
    job_id = db.Column(db.Integer, db.ForeignKey("job_posting.job_id"), nullable=False)
    interview_date = db.Column(db.Date, nullable=False)
    interview_time = db.Column(db.Time, nullable=False)

    # Relationships
    student = db.relationship("Student", backref="interviews")
    company = db.relationship("Company", backref="interviews")
    job = db.relationship("JobPosting", backref="interviews")
