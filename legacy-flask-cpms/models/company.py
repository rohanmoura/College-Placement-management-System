from db import db


class Company(db.Model):
    """Company model matching the company table in schema.sql."""

    __tablename__ = "company"

    company_id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    company_name = db.Column(db.String(150), nullable=False)
    hr_name = db.Column(db.String(100), nullable=False)
    email = db.Column(db.String(100), unique=True, nullable=False)
    contact = db.Column(db.String(15), unique=True)
    location = db.Column(db.String(100))
