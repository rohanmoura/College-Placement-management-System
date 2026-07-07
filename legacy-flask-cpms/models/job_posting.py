from db import db


class JobPosting(db.Model):
    """JobPosting model matching the job_posting table in schema.sql."""

    __tablename__ = "job_posting"

    job_id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    company_id = db.Column(db.Integer, db.ForeignKey("company.company_id"), nullable=False)
    job_title = db.Column(db.String(150), nullable=False)
    salary = db.Column(db.String(50))
    deadline = db.Column(db.Date, nullable=False)

    # Relationship to access company details via job.company
    company = db.relationship("Company", backref="job_postings")
